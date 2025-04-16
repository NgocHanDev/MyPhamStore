package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.edu.hcmuaf.fit.myphamstore.dao.ICouponDAO;
import vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl.CouponDAOImpl;
import vn.edu.hcmuaf.fit.myphamstore.model.*;

import vn.edu.hcmuaf.fit.myphamstore.service.ICartService;
import vn.edu.hcmuaf.fit.myphamstore.service.ICouponService;
import vn.edu.hcmuaf.fit.myphamstore.service.IProductService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
public class CartServiceImpl implements ICartService {
    @Inject
    private IProductService productService;
    @Inject
    private ICouponService couponService;
    @Inject
    private ICouponDAO couponDAO;
    @Inject
    private CouponDAOImpl couponDAOImpl;

    @Override
    public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = Long.parseLong(request.getParameter("productId"));
        String variantIdParam = request.getParameter("variantId");
        Long variantId = (variantIdParam == null || variantIdParam.isBlank()) ? null : Long.parseLong(variantIdParam);

        int quantity = Integer.parseInt(request.getParameter("quantity") == null ? "1" : request.getParameter("quantity"));
        ProductModel product = productService.findProductById(productId);
        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            return;
        }
        Long brandId = product.getBrandId();

        CartModel item = CartModel.builder()
                .productId(productId)
                .quantity(quantity)
                .brandId(brandId)
                .variantId(variantId)
                .build();

        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartModel> listCartItems = (List<CartModel>) session.getAttribute("cart");
        if (listCartItems == null) {
            listCartItems = new ArrayList<>();
            session.setAttribute("cart", listCartItems);
        }
        System.out.println("Before adding: " + listCartItems);
        boolean itemExists = false;
        for (CartModel cartItem : listCartItems) {
            if (cartItem.equals(item)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            listCartItems.add(item);
        }

        System.out.println("After adding: " + listCartItems);
        session.setAttribute("cart", listCartItems);
        response.sendRedirect(request.getHeader("referer"));
    }
    @Override
    public void updateCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartModel> cart = (List<CartModel>) session.getAttribute("cart");
        if (cart == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cart not found");
            return;
        }

        Long productId = Long.parseLong(request.getParameter("productId"));
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            if (quantity < 1) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantity must be at least 1");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quantity format");
            return;
        }

        for (CartModel item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                break;
            }
        }

        session.setAttribute("cart", cart);
        response.sendRedirect(request.getHeader("referer"));
    }

    @Override
    public void removeCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartModel> cart = (List<CartModel>) session.getAttribute("cart");
        if (cart == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Long productId = Long.parseLong(request.getParameter("productId"));
        cart.removeIf(item -> item.getProductId().equals(productId));

        session.setAttribute("cart", cart);
        response.sendRedirect(request.getHeader("referer"));

    }

    @Override
    public void clearCart(HttpServletRequest request, HttpServletResponse response) {

    }


    @Override
    public void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartModel> listCartItems = (List<CartModel>) session.getAttribute("cart");
        System.out.println("Before displaying: " + listCartItems);

        if (listCartItems == null || listCartItems.isEmpty()) {
            request.setAttribute("errorMessage", "Giỏ hàng của bạn đang trống.");
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }

        List<CartModelHelper> listCartDisplay = new ArrayList<>();
        AtomicLong totalAmount = new AtomicLong(0);

        try {
            for (CartModel cartItem : listCartItems) {
                ProductModel product = productService.findProductById(cartItem.getProductId());
                if (product == null) {
                    System.out.println("Product not found: " + cartItem.getProductId());
                    continue;
                }
                if (cartItem.getVariantId() == null) {
                    totalAmount.addAndGet(product.getPrice() * cartItem.getQuantity());
                    listCartDisplay.add(new CartModelHelper(product, cartItem.getQuantity(), null));
                } else {
                    List<ProductVariant> listVariant = productService.getProductVariantsByProductId(cartItem.getProductId());
                    ProductVariant variant = listVariant.stream()
                            .filter(v -> v.getId().equals(cartItem.getVariantId()))
                            .findFirst()
                            .orElse(null);
                    if (variant != null) {
                        totalAmount.addAndGet((long) (variant.getPrice() * cartItem.getQuantity()));
                        listCartDisplay.add(new CartModelHelper(product, cartItem.getQuantity(), variant));
                    } else {
                        System.out.println("Variant not found for product: " + cartItem.getProductId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi xử lý giỏ hàng: " + e.getMessage());
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }

        List<CouponModel> discountCodes = couponService.findAvailableCoupons();
        System.out.println("Discount codes: " + discountCodes);
        request.setAttribute("discountCodes", discountCodes);

        String discountCode = (String) session.getAttribute("discountCode");
        long discountAmount = 0;
        if (discountCode != null && !discountCodes.isEmpty()) {
            for (CouponModel coupon : discountCodes) {
                if (coupon.getCode().equals(discountCode)) {
                    discountAmount = calculateDiscount(totalAmount.get(), coupon);
                    break;
                }
            }
        }

        request.setAttribute("listCartDisplay", listCartDisplay);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("discountCodes", discountCodes);
        request.setAttribute("finalAmount", totalAmount.get() - discountAmount);

        System.out.println("Forwarding to /frontend/shopping_cart.jsp");
        request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
    }

    private long calculateDiscount(long totalAmount, CouponModel coupon) {
        if ("percentage".equals(coupon.getDiscountType())) {
            return totalAmount * coupon.getDiscountValue() / 100;
        } else if ("fixed".equals(coupon.getDiscountType())) {
            return coupon.getDiscountValue();
        }
        return 0;
    }
    public void getCartCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<CartModel> cart = (List<CartModel>) session.getAttribute("cart");

        System.out.println("Cart Session: " + (cart == null ? "null" : cart.size())); // Debug

        int count = (cart == null) ? 0 : cart.size();
        response.setContentType("application/json");
        response.getWriter().write("{\"count\":" + count + "}");
    }
    @Override
    public void applyDiscountCode(HttpServletRequest request, HttpServletResponse response, String discountCode) throws IOException {
        HttpSession session = request.getSession();

        // Lấy thông tin giỏ hàng để kiểm tra điều kiện
        List<CartModel> cartItems = (List<CartModel>) session.getAttribute("cart");
        if (cartItems == null || cartItems.isEmpty()) {
            session.setAttribute("discountError", "Giỏ hàng trống, không thể áp dụng voucher");
            response.sendRedirect("/gio-hang");
            return;
        }

        // Tính tổng giá trị giỏ hàng
        long totalAmount = calculateTotalAmount(cartItems);

        CouponModel coupon = couponDAO.findCouponByCode(discountCode);
        if (coupon == null) {
            session.setAttribute("discountError", "Mã giảm giá không tồn tại");
            response.sendRedirect("/gio-hang");
            return;
        }

        // Kiểm tra các điều kiện
        if (!coupon.getIsAvailable()) {
            session.setAttribute("discountError", "Mã giảm giá không khả dụng");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (LocalDateTime.now().isBefore(coupon.getStartDate())) {
            session.setAttribute("discountError", "Mã giảm giá chưa có hiệu lực");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (LocalDateTime.now().isAfter(coupon.getEndDate())) {
            session.setAttribute("discountError", "Mã giảm giá đã hết hạn");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (totalAmount < coupon.getMinOrderValue()) {
            session.setAttribute("discountError", "Đơn hàng chưa đạt giá trị tối thiểu để áp dụng voucher");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (couponDAOImpl.getRemainingQuantity(discountCode) <= 0) {
            session.setAttribute("discountError", "Mã giảm giá đã hết lượt sử dụng");
            response.sendRedirect("/gio-hang");
            return;
        }

        // Nếu tất cả điều kiện đều hợp lệ
        session.setAttribute("discountCode", discountCode);
        session.removeAttribute("discountError"); // Xóa thông báo lỗi nếu có

        response.sendRedirect("/gio-hang");
    }

    private long calculateTotalAmount(List<CartModel> cartItems) {
        long total = 0;
        for (CartModel item : cartItems) {
            ProductModel product = productService.findProductById(item.getProductId());
            if (product != null) {
                if (item.getVariantId() == null) {
                    total += product.getPrice() * item.getQuantity();
                } else {
                    List<ProductVariant> variants = productService.getProductVariantsByProductId(item.getProductId());
                    for (ProductVariant variant : variants) {
                        if (variant.getId().equals(item.getVariantId())) {
                            total += variant.getPrice() * item.getQuantity();
                            break;
                        }
                    }
                }
            }
        }
        return total;
    }
}

