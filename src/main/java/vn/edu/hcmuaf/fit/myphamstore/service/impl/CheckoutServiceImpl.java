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
import vn.edu.hcmuaf.fit.myphamstore.service.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

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
    private ICartService cartService;
    @Inject
    private LoggingService log;
    private final String CLASS_NAME = "CHECKOUT-SERVICE";

@Override
public void displayCheckout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    log.info(CLASS_NAME, "hiển thị trang checkout");
    HttpSession session = request.getSession();
    UserModel user = (UserModel) session.getAttribute("user");
    List<CartModel> listCartItems = cartService.getCartList(request, response);

    if (listCartItems == null || listCartItems.isEmpty()) {
        request.setAttribute("errorMessage", "Giỏ hàng trống.");
        request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
        return;
    }

    List<CartModelHelper> listCartDisplay = new ArrayList<>();
    AtomicLong totalAmount = new AtomicLong(0);

    try {
        for (CartModel cartItem : listCartItems) {
            ProductModel product = productService.findProductById(cartItem.getProductId());
            if (product == null) {
                request.setAttribute("errorMessage", "Sản phẩm không tồn tại: " + cartItem.getProductId());
                request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
                return;
            }
            totalAmount.addAndGet(product.getPrice() * cartItem.getQuantity());
            CartModelHelper helper = new CartModelHelper();
            helper.setQuantity(cartItem.getQuantity());
            helper.setProduct(product);
            listCartDisplay.add(helper);
        }
    } catch (Exception e) {
        request.setAttribute("errorMessage", "Lỗi khi xử lý giỏ hàng.");
        request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
        return;
    }

    List<AddressModel> addresses = addressDAO.findByUserId(user.getId());
    AddressModel defaultAddress = null;
    for (AddressModel addressModel : addresses) {
        if (addressModel.getIsDefault()) {
            defaultAddress = addressModel;
            break;
        }
    }
    if (defaultAddress == null && !addresses.isEmpty()) {
        defaultAddress = addresses.get(0);
    }

    request.setAttribute("address", defaultAddress);
    request.setAttribute("listCartDisplay", listCartDisplay);
    request.setAttribute("totalAmount", totalAmount.get());
    request.getRequestDispatcher("/frontend/checkout.jsp").forward(request, response);
}

@Override
public void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    log.info(CLASS_NAME, "thực hiện thanh toán");
    HttpSession session = request.getSession();
    List<CartModel> listCartItems = cartService.getCartList(request, response);

    if (listCartItems == null || listCartItems.isEmpty()) {
        request.setAttribute("errorMessage", "Giỏ hàng trống.");
        request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
        return;
    }

    List<CartModelHelper> listCartDisplay = new ArrayList<>();
    Double totalAmount = 0.0;

    try {
        for (CartModel cartItem : listCartItems) {
            ProductModel product = productService.findProductById(cartItem.getProductId());
            if (product == null) {
                request.setAttribute("errorMessage", "Sản phẩm không tồn tại: " + cartItem.getProductId());
                request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
                return;
            }
            totalAmount += product.getPrice() * cartItem.getQuantity();
            CartModelHelper helper = new CartModelHelper();
            helper.setQuantity(cartItem.getQuantity());
            helper.setProduct(product);
            listCartDisplay.add(helper);
        }
    } catch (Exception e) {
        request.setAttribute("errorMessage", "Lỗi khi xử lý giỏ hàng.");
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
            request.setAttribute("errorMessage", "Mã giảm giá không hợp lệ hoặc đã hết hạn.");
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
            .shippingFee(0)
            .status(OrderStatus.PENDING)
            .build();

    Long orderId = orderDAO.saveOrder(order);
    if (orderId == null) {
        request.setAttribute("errorMessage", "Lỗi khi xử lý đơn hàng.");
        request.getRequestDispatcher("/frontend/checkout.jsp").forward(request, response);
        return;
    }

    for (CartModelHelper cartItem : listCartDisplay) {
        OrderDetailModel orderDetail = OrderDetailModel.builder()
                .orderId(orderId)
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .totalPrice((double) (cartItem.getProduct().getPrice() * cartItem.getQuantity()))
                .build();
        orderDAO.saveOrderDetail(orderDetail);
    }

    // Xóa toàn bộ giỏ hàng sau khi thanh toán
    listCartItems.clear();
    session.setAttribute("cart", listCartItems);

    // Cập nhật tổng giá đơn hàng
    order.setTotalPrice(totalAmount);
    orderDAO.updateOrder(order);

    // Gửi email thông báo đơn hàng
    UserModel user = (UserModel) session.getAttribute("user");
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(() -> {
        log.info(CLASS_NAME, "Gửi email thông báo đơn hàng cho: " + user.getEmail());
        SendEmail.notifyOrderToUser(user.getEmail(), order, listCartDisplay, address);
    });
    executorService.shutdown();

    order.setId(orderId);
    response.sendRedirect("/trang-chu");
}
private AddressModel getAddressFromRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        Long userId = user.getId();
        String cityIdStr = request.getParameter("cityId");
        String districtIdStr = request.getParameter("districtId");
        String wardCodeStr = request.getParameter("wardCode");

        Integer cityId = null, districtId = null, wardCode = null;
        try {
            if (cityIdStr != null && !cityIdStr.trim().isEmpty()) {
                cityId = Integer.parseInt(cityIdStr);
            }
            if (districtIdStr != null && !districtIdStr.trim().isEmpty()) {
                districtId = Integer.parseInt(districtIdStr);
            }
            if (wardCodeStr != null && !wardCodeStr.trim().isEmpty()) {
                wardCode = Integer.parseInt(wardCodeStr);
            }
        } catch (NumberFormatException e) {
            log.error("Invalid format for City ID, District ID, or Ward Code: {}", e.getMessage());
        }

        AddressModel address = AddressModel.builder()
                .userId(userId)
                .recipientName(request.getParameter("recipientName"))
                .recipientPhone(request.getParameter("recipientPhone"))
                .cityId(cityId)
                .districtId(districtId)
                .wardCode(wardCode)
                .city(request.getParameter("city"))
                .district(request.getParameter("district"))
                .ward(request.getParameter("ward"))
                .note(request.getParameter("note"))
                .build();

        boolean addressExist = addressDAO.checkAddressIsExist(address, userId);
        Long addressId = null;
        if (!addressExist) {
            log.info(CLASS_NAME, "Địa chỉ không tồn tại, thêm mới địa chỉ");
            addressId = addressDAO.save(address);
        } else {
            List<AddressModel> listAddress = addressDAO.findByUserId(userId);
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

//    @Override
//    public void displayCheckout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        log.info(CLASS_NAME, "hiển thị trang checkout");
//        HttpSession session = request.getSession();
//        UserModel user = (UserModel) session.getAttribute("user");
//        List<CartModel> listCartItems = cartService.getCartList(request,response);
//        String selectedItems = request.getParameter("selectedItems");
//
//        if (listCartItems == null || selectedItems == null || selectedItems.isEmpty()) {
//            request.setAttribute("errorMessage", "Giỏ hàng trống hoặc chưa chọn sản phẩm.");
//            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
//            return;
//        }
//
//        List<String> selectedProductIds = Arrays.asList(selectedItems.split(","));
//        List<CartModelHelper> listCartDisplay = new ArrayList<>();
//        AtomicLong totalAmount = new AtomicLong(0);
//
//        try {
//            for (CartModel cartItem : listCartItems) {
//                if (selectedProductIds.contains(String.valueOf(cartItem.getProductId()))) {
//                    ProductModel product = productService.findProductById(cartItem.getProductId());
//                    if (product == null) {
//                        request.setAttribute("errorMessage", "Sản phẩm không tồn tại: " + cartItem.getProductId());
//                        request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
//                        return;
//                    }
//                    totalAmount.addAndGet(product.getPrice() * cartItem.getQuantity());
//                    CartModelHelper helper = new CartModelHelper();
//                    helper.setQuantity(cartItem.getQuantity());
//                    helper.setProduct(product);
//                    listCartDisplay.add(helper);
//                }
//            }
//            if (listCartDisplay.isEmpty()) {
//                request.setAttribute("errorMessage", "Không có sản phẩm nào được chọn.");
//                request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
//                return;
//            }
//        } catch (Exception e) {
//            request.setAttribute("errorMessage", "Lỗi khi xử lý giỏ hàng.");
//            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
//            return;
//        }
//
//        List<AddressModel> addresses = addressDAO.findByUserId(user.getId());
//        AddressModel defaultAddress = null;
//        for (AddressModel addressModel : addresses) {
//            if (addressModel.getIsDefault()) {
//                defaultAddress = addressModel;
//                break;
//            }
//        }
//        if (defaultAddress == null && !addresses.isEmpty()) {
//            defaultAddress = addresses.get(0);
//        }
//
//        request.setAttribute("address", defaultAddress);
//        request.setAttribute("listCartDisplay", listCartDisplay);
//        request.setAttribute("totalAmount", totalAmount.get());
//        request.getRequestDispatcher("/frontend/checkout.jsp").forward(request, response);
//    }
//    @Override
//    public void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        log.info(CLASS_NAME, "thực hiện thanh toán");
//        HttpSession session = request.getSession();
//        List<CartModel> listCartItems = cartService.getCartList(request,response);
//        String selectedItems = request.getParameter("selectedItems");
//
//        if (listCartItems == null || selectedItems == null || selectedItems.isEmpty()) {
//            request.setAttribute("errorMessage", "Giỏ hàng trống hoặc chưa chọn sản phẩm.");
//            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
//            return;
//        }
//
//        List<String> selectedProductIds = Arrays.asList(selectedItems.split(","));
//        List<CartModelHelper> listCartDisplay = new ArrayList<>();
//        Double totalAmount = 0.0;
//
//        try {
//            for (CartModel cartItem : listCartItems) {
//                if (selectedProductIds.contains(String.valueOf(cartItem.getProductId()))) {
//                    ProductModel product = productService.findProductById(cartItem.getProductId());
//                    if (product == null) {
//                        request.setAttribute("errorMessage", "Sản phẩm không tồn tại: " + cartItem.getProductId());
//                        request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
//                        return;
//                    }
//                    totalAmount += product.getPrice() * cartItem.getQuantity();
//                    CartModelHelper helper = new CartModelHelper();
//                    helper.setQuantity(cartItem.getQuantity());
//                    helper.setProduct(product);
//                    listCartDisplay.add(helper);
//                }
//            }
//            if (listCartDisplay.isEmpty()) {
//                request.setAttribute("errorMessage", "Không có sản phẩm nào được chọn.");
//                request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
//                return;
//            }
//        } catch (Exception e) {
//            request.setAttribute("errorMessage", "Lỗi khi xử lý giỏ hàng.");
//            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
//            return;
//        }
//
//        String couponCode = request.getParameter("couponCode");
//        log.info(CLASS_NAME, "Mã giảm giá: " + couponCode);
//        if (couponCode != null && !couponCode.isEmpty()) {
//            CouponModel coupon = couponService.applyCoupon(couponCode, totalAmount);
//            if (coupon != null) {
//                totalAmount -= coupon.getDiscountValue();
//                request.setAttribute("appliedCoupon", coupon);
//            } else {
//                request.setAttribute("errorMessage", "Mã giảm giá không hợp lệ hoặc đã hết hạn.");
//                request.getRequestDispatcher("/frontend/checkout.jsp").forward(request, response);
//                return;
//            }
//        }
//
//        AddressModel address = this.getAddressFromRequest(request);
//        OrderModel order = OrderModel.builder()
//                .userId(address.getUserId())
//                .addressId(address.getId())
//                .totalPrice(totalAmount)
//                .note(request.getParameter("customerNote"))
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .paymentMethod(PaymentMethod.COD)
//                .orderDate(LocalDateTime.now())
//                .shippingFee(0)
//                .status(OrderStatus.PENDING)
//                .build();
//
//        Long orderId = orderDAO.saveOrder(order);
//        if (orderId == null) {
//            request.setAttribute("errorMessage", "Lỗi khi xử lý đơn hàng.");
//            request.getRequestDispatcher("/frontend/checkout.jsp").forward(request, response);
//            return;
//        }
//
//        for (CartModelHelper cartItem : listCartDisplay) {
//            OrderDetailModel orderDetail = OrderDetailModel.builder()
//                    .orderId(orderId)
//                    .productId(cartItem.getProduct().getId())
//                    .quantity(cartItem.getQuantity())
//                    .totalPrice((double) (cartItem.getProduct().getPrice() * cartItem.getQuantity()))
//                    .build();
//            orderDAO.saveOrderDetail(orderDetail);
//        }
//
//        // Xóa các sản phẩm đã thanh toán khỏi giỏ hàng
//        listCartItems.removeIf(cartItem -> selectedProductIds.contains(String.valueOf(cartItem.getProductId())));
//        session.setAttribute("cart", listCartItems);
//
//        // Cập nhật tổng giá đơn hàng
//        order.setTotalPrice(totalAmount);
//        orderDAO.updateOrder(order);
//
//        // Gửi email thông báo đơn hàng
//        UserModel user = (UserModel) session.getAttribute("user");
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(() -> {
//            log.info(CLASS_NAME, "Gửi email thông báo đơn hàng cho: " + user.getEmail());
//            SendEmail.notifyOrderToUser(user.getEmail(), order, listCartDisplay, address);
//        });
//        executorService.shutdown();
//
//        order.setId(orderId);
//        response.sendRedirect("/trang-chu");
//    }