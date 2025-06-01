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
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

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
    @Inject
    private LoggingService log;

    private static final String LOGGER_NAME = "CART-SERVICE";

    @Override
    public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = Long.parseLong(request.getParameter("productId"));
        String variantIdParam = request.getParameter("variantId");
        Long variantId = (variantIdParam == null || variantIdParam.isBlank()) ? null : Long.parseLong(variantIdParam);

        int quantity = Integer.parseInt(request.getParameter("quantity") == null ? "1" : request.getParameter("quantity"));
        ProductModel product = productService.findProductById(productId);
        if (product == null) {
            log.error(LOGGER_NAME, "Không tìm thấy sản phẩm với ID: " + productId);
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
            log.info(LOGGER_NAME, "Khởi tạo giỏ hàng mới cho phiên làm việc.");
        }

        boolean itemExists = false;
        for (CartModel cartItem : listCartItems) {
            if (cartItem.equals(item)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                itemExists = true;
                log.info(LOGGER_NAME, "Cập nhật số lượng sản phẩm ID: " + productId + ", số lượng mới: " + cartItem.getQuantity());
                break;
            }
        }
        if (!itemExists) {
            listCartItems.add(item);
            log.info(LOGGER_NAME, "Thêm sản phẩm mới vào giỏ hàng, ID: " + productId + ", số lượng: " + quantity);
        }

        session.setAttribute("cart", listCartItems);
        log.info(LOGGER_NAME, "Hoàn tất thêm sản phẩm vào giỏ hàng, tổng số mục: " + listCartItems.size());
        response.sendRedirect(request.getHeader("referer"));
    }

    @Override
    public void updateCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartModel> cart = (List<CartModel>) session.getAttribute("cart");
        if (cart == null) {
            log.error(LOGGER_NAME, "Không tìm thấy giỏ hàng trong phiên làm việc.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Cart not found\"}");
            return;
        }

        Long productId = Long.parseLong(request.getParameter("productId"));
        Integer quantity;
        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity < 1) {
                log.warn(LOGGER_NAME, "Số lượng không hợp lệ khi cập nhật giỏ hàng: " + quantity);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Số lượng tối thiểu phải bằng 1\"}");
                return;
            }
        } catch (NumberFormatException e) {
            log.error(LOGGER_NAME, "Định dạng số lượng không hợp lệ: " + request.getParameter("quantity"));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Invalid quantity format\"}");
            return;
        }

        for (CartModel item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                log.info(LOGGER_NAME, "Cập nhật giỏ hàng, sản phẩm ID: " + productId + ", số lượng mới: " + quantity);
                break;
            }
        }

        session.setAttribute("cart", cart);
        log.info(LOGGER_NAME, "Hoàn tất cập nhật giỏ hàng.");
        response.getWriter().write("{\"status\":\"success\"}");
    }

    @Override
    public void removeCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartModel> cart = (List<CartModel>) session.getAttribute("cart");
        if (cart == null) {
            log.error(LOGGER_NAME, "Không tìm thấy giỏ hàng để xóa mục.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Cart not found\"}");
            return;
        }

        Long productId = Long.parseLong(request.getParameter("productId"));
        int initialSize = cart.size();
        cart.removeIf(item -> item.getProductId().equals(productId));

        if (cart.size() < initialSize) {
            log.info(LOGGER_NAME, "Xóa sản phẩm khỏi giỏ hàng, ID: " + productId);
            session.setAttribute("cart", cart);
            response.getWriter().write("{\"status\":\"success\"}");
        } else {
            log.warn(LOGGER_NAME, "Không tìm thấy sản phẩm để xóa, ID: " + productId);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Product not found\"}");
        }

        session.setAttribute("cart", cart);
        log.info(LOGGER_NAME, "Hoàn tất xóa mục khỏi giỏ hàng, số mục còn lại: " + cart.size());
    }

    @Override
    public void clearCart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<CartModel> cart = (List<CartModel>) session.getAttribute("cart");
        if (cart != null) {
            cart.clear();
            session.setAttribute("cart", cart);
            log.info(LOGGER_NAME, "Xóa toàn bộ giỏ hàng.");
        } else {
            log.warn(LOGGER_NAME, "Giỏ hàng đã trống hoặc không tồn tại khi yêu cầu xóa.");
        }
    }

    @Override
    public void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<CartModel> listCartItems = (List<CartModel>) session.getAttribute("cart");
        log.info(LOGGER_NAME, "Hiển thị giỏ hàng, số mục: " + (listCartItems == null ? 0 : listCartItems.size()));

        if (listCartItems == null || listCartItems.isEmpty()) {
            log.warn(LOGGER_NAME, "Giỏ hàng trống khi hiển thị.");
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
                    log.error(LOGGER_NAME, "Không tìm thấy sản phẩm khi hiển thị giỏ hàng, ID: " + cartItem.getProductId());
                    continue;
                }
                if (cartItem.getVariantId() == null) {
                    totalAmount.addAndGet(product.getPrice() * cartItem.getQuantity());
                    listCartDisplay.add(new CartModelHelper(product, cartItem.getQuantity(), null));
                    log.info(LOGGER_NAME, "Thêm sản phẩm vào danh sách hiển thị, ID: " + cartItem.getProductId());
                } else {
                    List<ProductVariant> listVariant = productService.getProductVariantsByProductId(cartItem.getProductId());
                    ProductVariant variant = listVariant.stream()
                            .filter(v -> v.getId().equals(cartItem.getVariantId()))
                            .findFirst()
                            .orElse(null);
                    if (variant != null) {
                        totalAmount.addAndGet((long) (variant.getPrice() * cartItem.getQuantity()));
                        listCartDisplay.add(new CartModelHelper(product, cartItem.getQuantity(), variant));
                        log.info(LOGGER_NAME, "Thêm biến thể sản phẩm vào danh sách hiển thị, ID: " + cartItem.getProductId() + ", Variant ID: " + cartItem.getVariantId());
                    } else {
                        log.error(LOGGER_NAME, "Không tìm thấy biến thể sản phẩm, ID: " + cartItem.getProductId() + ", Variant ID: " + cartItem.getVariantId());
                    }
                }
            }
        } catch (Exception e) {
            log.error(LOGGER_NAME, "Lỗi khi xử lý hiển thị giỏ hàng: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi khi xử lý giỏ hàng: " + e.getMessage());
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }

        List<CouponModel> discountCodes = couponService.findAvailableCoupons();
        log.info(LOGGER_NAME, "Tải danh sách mã giảm giá, số lượng: " + discountCodes.size());
        request.setAttribute("discountCodes", discountCodes);

        String discountCode = (String) session.getAttribute("discountCode");
        long discountAmount = 0;
        if (discountCode != null && !discountCodes.isEmpty()) {
            for (CouponModel coupon : discountCodes) {
                if (coupon.getCode().equals(discountCode)) {
                    discountAmount = calculateDiscount(totalAmount.get(), coupon);
                    log.info(LOGGER_NAME, "Áp dụng mã giảm giá: " + discountCode + ", số tiền giảm: " + discountAmount);
                    break;
                }
            }
        }

        request.setAttribute("listCartDisplay", listCartDisplay);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("discountCodes", discountCodes);
        request.setAttribute("finalAmount", totalAmount.get() - discountAmount);

        log.info(LOGGER_NAME, "Chuyển tiếp đến trang giỏ hàng, tổng tiền: " + totalAmount.get() + ", giảm giá: " + discountAmount);
        request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
    }

    private long calculateDiscount(long totalAmount, CouponModel coupon) {
        if ("percentage".equals(coupon.getDiscountType())) {
            long discount = totalAmount * coupon.getDiscountValue() / 100;
            log.info(LOGGER_NAME, "Tính giảm giá theo phần trăm, giá trị: " + discount);
            return discount;
        } else if ("fixed".equals(coupon.getDiscountType())) {
            log.info(LOGGER_NAME, "Tính giảm giá cố định, giá trị: " + coupon.getDiscountValue());
            return coupon.getDiscountValue();
        }
        log.warn(LOGGER_NAME, "Loại giảm giá không xác định: " + coupon.getDiscountType());
        return 0;
    }

    @Override
    public void getCartCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<CartModel> cart = (List<CartModel>) session.getAttribute("cart");

        int count = (cart == null) ? 0 : cart.size();
        log.info(LOGGER_NAME, "Lấy số lượng mục trong giỏ hàng: " + count);

        response.setContentType("application/json");
        response.getWriter().write("{\"count\":" + count + "}");
    }

    @Override
    public void applyDiscountCode(HttpServletRequest request, HttpServletResponse response, String discountCode) throws IOException {
        HttpSession session = request.getSession();
        List<CartModel> cartItems = (List<CartModel>) session.getAttribute("cart");
        if (cartItems == null || cartItems.isEmpty()) {
            log.warn(LOGGER_NAME, "Giỏ hàng trống khi áp dụng mã giảm giá: " + discountCode);
            session.setAttribute("discountError", "Giỏ hàng trống, không thể áp dụng voucher");
            response.sendRedirect("/gio-hang");
            return;
        }

        long totalAmount = calculateTotalAmount(cartItems);
        log.info(LOGGER_NAME, "Tính tổng giá trị giỏ hàng khi áp dụng mã giảm giá: " + totalAmount);

        CouponModel coupon = couponDAO.findCouponByCode(discountCode);
        if (coupon == null) {
            log.error(LOGGER_NAME, "Không tìm thấy mã giảm giá: " + discountCode);
            session.setAttribute("discountError", "Mã giảm giá không tồn tại");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (!coupon.getIsAvailable()) {
            log.warn(LOGGER_NAME, "Mã giảm giá không khả dụng: " + discountCode);
            session.setAttribute("discountError", "Mã giảm giá không khả dụng");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (LocalDateTime.now().isBefore(coupon.getStartDate())) {
            log.warn(LOGGER_NAME, "Mã giảm giá chưa có hiệu lực: " + discountCode);
            session.setAttribute("discountError", "Mã giảm giá chưa có hiệu lực");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (LocalDateTime.now().isAfter(coupon.getEndDate())) {
            log.warn(LOGGER_NAME, "Mã giảm giá đã hết hạn: " + discountCode);
            session.setAttribute("discountError", "Mã giảm giá đã hết hạn");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (totalAmount < coupon.getMinOrderValue()) {
            log.warn(LOGGER_NAME, "Đơn hàng chưa đạt giá trị tối thiểu để áp dụng mã giảm giá: " + discountCode);
            session.setAttribute("discountError", "Đơn hàng chưa đạt giá trị tối thiểu để áp dụng voucher");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (couponDAOImpl.getRemainingQuantity(discountCode) <= 0) {
            log.warn(LOGGER_NAME, "Mã giảm giá đã hết lượt sử dụng: " + discountCode);
            session.setAttribute("discountError", "Mã giảm giá đã hết lượt sử dụng");
            response.sendRedirect("/gio-hang");
            return;
        }

        session.setAttribute("discountCode", discountCode);
        session.removeAttribute("discountError");
        log.info(LOGGER_NAME, "Áp dụng mã giảm giá thành công: " + discountCode);

        response.sendRedirect("/gio-hang");
    }

    private long calculateTotalAmount(List<CartModel> cartItems) {
        long total = 0;
        for (CartModel item : cartItems) {
            ProductModel product = productService.findProductById(item.getProductId());
            if (product != null) {
                if (item.getVariantId() == null) {
                    total += product.getPrice() * item.getQuantity();
                    log.info(LOGGER_NAME, "Tính tổng cho sản phẩm ID: " + item.getProductId() + ", giá: " + product.getPrice() + ", số lượng: " + item.getQuantity());
                } else {
                    List<ProductVariant> variants = productService.getProductVariantsByProductId(item.getProductId());
                    for (ProductVariant variant : variants) {
                        if (variant.getId().equals(item.getVariantId())) {
                            total += variant.getPrice() * item.getQuantity();
                            log.info(LOGGER_NAME, "Tính tổng cho biến thể ID: " + item.getVariantId() + ", giá: " + variant.getPrice() + ", số lượng: " + item.getQuantity());
                            break;
                        }
                    }
                }
            } else {
                log.error(LOGGER_NAME, "Không tìm thấy sản phẩm khi tính tổng, ID: " + item.getProductId());
            }
        }
        log.info(LOGGER_NAME, "Tổng giá trị giỏ hàng: " + total);
        return total;
    }
}