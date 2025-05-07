package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.myphamstore.common.OrderStatus;
import vn.edu.hcmuaf.fit.myphamstore.common.PaymentMethod;
import vn.edu.hcmuaf.fit.myphamstore.common.SendEmail;
import vn.edu.hcmuaf.fit.myphamstore.dao.IAddressDAO;
import vn.edu.hcmuaf.fit.myphamstore.dao.IOrderDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.*;
import vn.edu.hcmuaf.fit.myphamstore.service.ICheckoutService;
import vn.edu.hcmuaf.fit.myphamstore.service.ICouponService;
import vn.edu.hcmuaf.fit.myphamstore.service.IProductService;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
public class CheckoutServiceImpl implements ICheckoutService {
    @Inject
    private IProductService productService;
    @Inject
    private IAddressDAO addressDAO;
    @Inject
    private IOrderDAO orderDAO;
    @Inject
    private ICouponService couponService;
    @Inject
    private LoggingService  log;
    private final String CLASS_NAME = "CHECKOUT-SERVICE";

    @Override
    public void displayCheckout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info(CLASS_NAME, "hiển thị trang checkout");
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        List<CartModel> listCartItems = (List<CartModel>) session.getAttribute("cart");
        if (listCartItems == null) {
            request.setAttribute("errorMessage", "Your cart is empty.");
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
        return;
        }
        List<CartModelHelper> listCartDisplay = new ArrayList<>();
        AtomicLong totalAmount = new AtomicLong(0);
        try {
            for (CartModel cartItem : listCartItems) {
                ProductModel product = productService.findProductById(cartItem.getProductId());
                if (product == null) {
                    request.setAttribute("errorMessage", "Product not found: " + cartItem.getProductId());
                    request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
                    return;
                }else if(cartItem.getVariantId() == null){
                    totalAmount.addAndGet(product.getPrice() * cartItem.getQuantity());
                    CartModelHelper helper = new CartModelHelper();
                    helper.setQuantity(cartItem.getQuantity());//new CartModelHelper(product, cartItem.getQuantity())
                    helper.setProduct(product);
                    listCartDisplay.add(helper);
                }else{
                    List<ProductVariant> listVariant = productService.getProductVariantsByProductId(cartItem.getProductId());
                    ProductVariant variant = null;
                    for (ProductVariant productVariant : listVariant) {
                        if(cartItem.getVariantId() == productVariant.getId()){
                            variant = productVariant;
                        }
                    }
                    totalAmount.addAndGet((long) (variant.getPrice() * cartItem.getQuantity()));
                    CartModelHelper cartModelHelper =  new CartModelHelper(product, cartItem.getQuantity(), variant);
                    listCartDisplay.add(cartModelHelper);
                }
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while processing your cart.");
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }
        List<AddressModel> addresss = addressDAO.findByUserId(user.getId());
        for (AddressModel addressModel : addresss) {
            if (addressModel.getIsDefault()) {
                request.setAttribute("address", addressModel);
                break;
            }
        }

        request.setAttribute("listCartDisplay", listCartDisplay);
        request.setAttribute("totalAmount", totalAmount);
        request.getRequestDispatcher("/frontend/checkout.jsp").forward(request, response);
    }

    @Override
    public void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info(CLASS_NAME, "thực hiện thanh toán");
        HttpSession session = request.getSession();

        String[] selectedItems = request.getParameterValues("selectedItems");
        if (selectedItems == null || selectedItems.length == 0) {
            request.setAttribute("errorMessage", "Vui lòng chọn sản phẩm để thanh toán.");
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }
        Set<String> selectedKeys = Arrays.stream(selectedItems).collect(Collectors.toSet()); // productId-variantId

        List<CartModel> listCartItems = (List<CartModel>) session.getAttribute("cart");
        if (listCartItems == null) {
            request.setAttribute("errorMessage", "Giỏ hàng trống.");
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }
        List<CartModelHelper> listCartDisplay = new ArrayList<>();
        Double totalAmount = 0.0;
        try {
            for (CartModel cartItem : listCartItems) {

                String key = cartItem.getProductId() + "-" + (cartItem.getVariantId() != null ? cartItem.getVariantId() : "null");
                if (!selectedKeys.contains(key)) continue;

                ProductModel product = productService.findProductById(cartItem.getProductId());
                if (product == null) continue;
//                {
//                    request.setAttribute("errorMessage", "Không tìm thấy sản phẩm: " + cartItem.getProductId());
//                    request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
//                    return;
//                }
                double price = cartItem.getVariantId() != null
                        ? productService.getProductVariantsByProductId(cartItem.getProductId())
                        .stream()
                        .filter(v -> v.getId().equals(cartItem.getVariantId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Variant not found"))
                        .getPrice()
                        : product.getPrice();

                totalAmount += price * cartItem.getQuantity();
                listCartDisplay.add(new CartModelHelper(product, cartItem.getQuantity(),
                        cartItem.getVariantId() != null ? ProductVariant.builder()
                                .id(cartItem.getVariantId())
                                .price(price)
                                .build() : null));

//                if(cartItem.getVariantId() == null){
//                    totalAmount += product.getPrice() * cartItem.getQuantity();
//                    CartModelHelper helper = new CartModelHelper();
//                    helper.setQuantity(cartItem.getQuantity());//new CartModelHelper(product, cartItem.getQuantity())
//                    helper.setProduct(product);
//                    listCartDisplay.add(helper);
//                }else{
//                    List<ProductVariant> listVariant = productService.getProductVariantsByProductId(cartItem.getProductId());
//                    ProductVariant variant = listVariant.stream()
//                            .filter(v -> v.getId().equals(cartItem.getVariantId()))
//                            .findFirst()
//                            .orElse(null);
//
//                    if (variant == null) continue;

//                    for (ProductVariant productVariant : listVariant) {
//                        if(cartItem.getVariantId() == productVariant.getId()){
//                            variant = productVariant;
//                        }
//                    }
//                    totalAmount += (variant.getPrice() * cartItem.getQuantity());
//                    CartModelHelper cartModelHelper =  new CartModelHelper(product, cartItem.getQuantity(), variant);
//                    listCartDisplay.add(cartModelHelper);
//                }
//
        }
            System.out.println("After displaying: " + listCartDisplay);
            if (listCartDisplay.isEmpty()) {
                request.setAttribute("errorMessage", "Không có sản phẩm hợp lệ để thanh toán.");
                request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi xử lý giỏ hàng.");
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }
        String couponCode = request.getParameter("couponCode");
        log.info(CLASS_NAME, "Mã giảm giá: " + couponCode);
        if (couponCode != null && !couponCode.isEmpty()) {
            CouponModel coupon = couponService.applyCoupon(couponCode, totalAmount);
            if (coupon != null) {
                totalAmount -= coupon.getDiscountValue();
                request.setAttribute("appliedCoupon", coupon);
            } else {
                request.setAttribute("errorMessage", "Invalid or expired coupon code.");
                request.getRequestDispatcher("/frontend/checkout.jsp").forward(request, response);
                return;
            }
        }
        AddressModel address = this.getAddressFromRequest(request);
        OrderModel order = OrderModel.builder()
                .userId(address.getUserId())
                .addressId(address.getId())
                .totalPrice(totalAmount)
                .note(request.getParameter("customerNote"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .paymentMethod(PaymentMethod.COD)
                .orderDate(LocalDateTime.now())
                .shippingFee(Integer.parseInt(request.getParameter("submit-fee-cost")))
                .status(OrderStatus.PENDING)
                .build();
        Long orderId = orderDAO.saveOrder(order);
        if (orderId == null) {
            request.setAttribute("errorMessage", "An error occurred while processing your order.");
            request.getRequestDispatcher("/frontend/checkout.jsp").forward(request, response);
            return;
        }
        System.out.println(listCartDisplay);
        for (CartModelHelper cartItem : listCartDisplay) {
            double price = (cartItem.getVariant() != null)
                    ? cartItem.getVariant().getPrice()
                    : cartItem.getProduct().getPrice();

            OrderDetailModel orderDetail = OrderDetailModel.builder()
                    .orderId(orderId)
                    .productId(cartItem.getProduct().getId())
                    .quantity(cartItem.getQuantity())
                    .totalPrice((double) (cartItem.getProduct().getPrice() * cartItem.getQuantity()))
                    .build();
            orderDAO.saveOrderDetail(orderDetail);
        }
        listCartItems.removeIf(item -> selectedKeys.contains(item.getProductId() + "-" + (item.getVariantId() != null ? item.getVariantId() : "null")));
        session.setAttribute("cart", listCartItems.isEmpty() ? null : listCartItems);

//        session.removeAttribute("cart");
        //updatet total price
        order.setTotalPrice(totalAmount);
        orderDAO.updateOrder(order);

        // lấy user từ session
        UserModel user = (UserModel) session.getAttribute("user");
        //gửi email thông báo đơn hàng

        // Sử dụng ExecutorService để gửi email bất đồng bộ
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            log.info(CLASS_NAME, "Gửi email thông báo đơn hàng cho: " + user.getEmail());
            SendEmail.notifyOrderToUser(user.getEmail(), order, listCartDisplay, address);
        });
        executorService.shutdown(); // Đóng ExecutorService sau khi gửi
        order.setId(orderId);


        response.sendRedirect("/trang-chu");
    }


    private AddressModel getAddressFromRequest(HttpServletRequest request) {
        //get user id from session
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        Long userId = user.getId();
        AddressModel address = AddressModel.builder()
                .userId(userId)
                .recipientName(request.getParameter("recipientName"))
                .recipientPhone(request.getParameter("recipientPhone"))
                .city(request.getParameter("city"))
                .district(request.getParameter("district"))
                .ward(request.getParameter("ward"))
                .note(request.getParameter("note"))
                .build();
        //check if address is already exist
        boolean addressExist  = addressDAO.checkAddressIsExist(address, userId);
        Long addressId = null;
        if (!addressExist) {
            log.info(CLASS_NAME, "Địa chỉ không tồn tại, thêm mới địa chỉ Id:" + addressId);
            addressId = addressDAO.save(address);
        }else {
           List<AddressModel> listAddress = addressDAO.findByUserId((long) Integer.parseInt(userId.toString()));
              for (AddressModel addressModel : listAddress) {
                if (addressModel.getRecipientName().equals(address.getRecipientName()) &&
                          addressModel.getRecipientPhone().equals(address.getRecipientPhone()) &&
                          addressModel.getCity().equals(address.getCity()) &&
                          addressModel.getDistrict().equals(address.getDistrict()) &&
                          addressModel.getWard().equals(address.getWard()) &&
                          addressModel.getNote().equals(address.getNote())) {
                     addressId = addressModel.getId();
                     break;
                }
              }
        }
        if (addressId != null) {
            address.setId(addressId);
        }
        return address;
    }
}
