package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.User;
import vn.edu.hcmuaf.fit.myphamstore.dao.ICartDAO;
import vn.edu.hcmuaf.fit.myphamstore.dao.ICouponDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.*;

import vn.edu.hcmuaf.fit.myphamstore.service.*;

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
    private LoggingService log;
    @Inject
    private ICartDAO cartDAO;

    private static final String LOGGER_NAME = "CART-SERVICE";

    @Override
    public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = Long.parseLong(request.getParameter("productId"));
        String variantIdParam = request.getParameter("variantId");
        Long variantId = (variantIdParam == null || variantIdParam.isBlank()) ? null : Long.parseLong(variantIdParam);
        double price = 0.0;
        int quantity = Integer.parseInt(request.getParameter("quantity") == null ? "1" : request.getParameter("quantity"));

        ProductModel product = productService.findProductById(productId);
        if (product == null) {
            log.error(LOGGER_NAME, "Không tìm thấy sản phẩm với ID: " + productId);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            return;
        }

        int availableStock;
        if (variantId != null) {
            ProductVariant variant = productService.findVariantById(variantId);
            if (variant == null) {
                log.error(LOGGER_NAME, "Không tìm thấy biến thể sản phẩm với ID: " + variantId);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product variant not found");
                return;
            }
            availableStock = variant.getStock();
        } else {
            availableStock = product.getStock();
        }

        if (quantity > availableStock) {
            log.warn(LOGGER_NAME, "Số lượng đặt vượt quá tồn kho. Sản phẩm ID: " + productId + ", Tồn kho: " + availableStock);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Số lượng yêu cầu vượt quá số lượng tồn kho");
            return;
        }

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        List<CartModel> cartItems = (List<CartModel>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
            session.setAttribute("cartItems", cartItems);
        }

        if (user == null) {
            // Xử lý giỏ hàng tạm thời trong session
            boolean itemExists = false;
            for (CartModel existingItem : cartItems) {
                if (existingItem.getProductId().equals(productId) &&
                        (variantId == null ? existingItem.getVariantId() == null : variantId.equals(existingItem.getVariantId()))) {
                    int newQuantity = existingItem.getQuantity() + quantity;
                    if (newQuantity > availableStock) {
                        log.warn(LOGGER_NAME, "Tổng số lượng yêu cầu vượt quá tồn kho. Sản phẩm ID: " + productId + ", Tồn kho: " + availableStock);
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tổng số lượng trong giỏ hàng vượt quá số lượng tồn kho");
                        return;
                    }
                    existingItem.setQuantity(newQuantity);
                    itemExists = true;
                    log.info(LOGGER_NAME, "Cập nhật số lượng sản phẩm ID: " + productId + ", số lượng mới: " + newQuantity);
                    break;
                }
            }

            if (!itemExists) {
                if(productService.findVariantById(variantId) == null) {
                    price = product.getPrice();
                }
                else {
                    price = productService.findVariantById(variantId).getPrice();
                }
                CartModel newItem = CartModel.builder()
                        .productId(productId)
                        .variantId(variantId)
                        .quantity(quantity)
                        .priceAtAdded(variantId != null ? (long) price : product.getPrice())
                        .build();
                cartItems.add(newItem);
                log.info(LOGGER_NAME, "Thêm sản phẩm mới vào giỏ hàng session, ID: " + productId + ", số lượng: " + quantity);
            }

            session.setAttribute("cartItems", cartItems);
            log.info(LOGGER_NAME, "Hoàn tất thêm sản phẩm vào giỏ hàng session, tổng số mục: " + cartItems.size());
            response.sendRedirect(request.getHeader("referer"));
            return;
        }

        // Xử lý giỏ hàng trong cơ sở dữ liệu
        Long userId = user.getId();
        CartHeaderModel userCart = cartDAO.getCartByUserId(userId);
        if (userCart == null) {
            CartHeaderModel newCart = new CartHeaderModel();
            newCart.setUserId(userId);
            cartDAO.saveCartHeader(newCart);
            userCart = cartDAO.getCartByUserId(userId);
        }

        CartModel newItem = CartModel.builder()
                .cardId(userCart.getId())
                .productId(productId)
                .variantId(variantId)
                .quantity(quantity)
                .brandId(product.getBrandId())
                .build();

        boolean itemExists = false;
        cartItems = cartDAO.getCartItemsByCartId(userCart.getId());
        for (CartModel existingItem : cartItems) {
            if (existingItem.getProductId().equals(productId) &&
                    (variantId == null ? existingItem.getVariantId() == null : variantId.equals(existingItem.getVariantId()))) {
                int newQuantity = existingItem.getQuantity() + quantity;
                if (newQuantity > availableStock) {
                    log.warn(LOGGER_NAME, "Tổng số lượng yêu cầu vượt quá tồn kho. Sản phẩm ID: " + productId + ", Tồn kho: " + availableStock);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tổng số lượng trong giỏ hàng vượt quá số lượng tồn kho");
                    return;
                }
                existingItem.setQuantity(newQuantity);
                cartDAO.update(existingItem);
                itemExists = true;
                log.info(LOGGER_NAME, "Cập nhật số lượng sản phẩm ID: " + productId + ", số lượng mới: " + newQuantity);
                break;
            }
        }

        if (!itemExists) {
            if(productService.findVariantById(variantId) == null) {
                price = product.getPrice();
            }
            else {
                price = productService.findVariantById(variantId).getPrice();
            }
            newItem.setPriceAtAdded(variantId != null ? (long) price : product.getPrice());
            Long newCartItemId = cartDAO.save(newItem);
            newItem.setId(newCartItemId);
            cartItems.add(newItem);
            log.info(LOGGER_NAME, "Thêm sản phẩm mới vào giỏ hàng DB, ID: " + productId + ", số lượng: " + quantity);
        }

        session.setAttribute("cartItems", cartItems);
        session.setAttribute("cart", userCart);
        log.info(LOGGER_NAME, "Hoàn tất thêm sản phẩm vào giỏ hàng, tổng số mục: " + cartItems.size());
        response.sendRedirect(request.getHeader("referer"));
    }
    @Override
    public void updateCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        if (user == null) {
            log.warn(LOGGER_NAME, "Người dùng chưa đăng nhập khi cập nhật giỏ hàng");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Vui lòng đăng nhập để cập nhật giỏ hàng\"}");
            return;
        }

        CartHeaderModel cartHeader = cartDAO.getCartByUserId(user.getId());
        if (cartHeader == null) {
            log.error(LOGGER_NAME, "Không tìm thấy giỏ hàng cho user ID: " + user.getId());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Giỏ hàng không tồn tại\"}");
            return;
        }

        List<CartModel> cartItems = cartDAO.getCartItemsByCartId(cartHeader.getId());
        if (cartItems == null || cartItems.isEmpty()) {
            log.error(LOGGER_NAME, "Giỏ hàng trống cho user ID: " + user.getId());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Giỏ hàng trống\"}");
            return;
        }

        Long productId;
        String variantIdParam = request.getParameter("variantId");
        Long variantId = (variantIdParam == null || variantIdParam.isBlank()) ? null : Long.parseLong(variantIdParam);
        Integer quantity;

        try {
            productId = Long.parseLong(request.getParameter("productId"));
            quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity < 1) {
                log.warn(LOGGER_NAME, "Số lượng không hợp lệ khi cập nhật giỏ hàng: " + quantity);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Số lượng tối thiểu phải bằng 1\"}");
                return;
            }
        } catch (NumberFormatException e) {
            log.error(LOGGER_NAME, "Định dạng không hợp lệ - productId: " + request.getParameter("productId") + ", quantity: " + request.getParameter("quantity"));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Định dạng productId hoặc quantity không hợp lệ\"}");
            return;
        }

        ProductModel product = productService.findProductById(productId);
        if (product == null) {
            log.error(LOGGER_NAME, "Không tìm thấy sản phẩm với ID: " + productId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Sản phẩm không tồn tại\"}");
            return;
        }

        int availableStock;
        if (variantId != null) {
            ProductVariant variant = productService.findVariantById(variantId);
            if (variant == null) {
                log.error(LOGGER_NAME, "Không tìm thấy biến thể sản phẩm với ID: " + variantId);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"status\":\"error\", \"message\":\"Biến thể sản phẩm không tồn tại\"}");
                return;
            }
            availableStock = variant.getStock();
        } else {
            availableStock = product.getStock();
        }

        if (quantity > availableStock) {
            log.warn(LOGGER_NAME, "Số lượng yêu cầu vượt quá tồn kho. Sản phẩm ID: " + productId + ", Tồn kho: " + availableStock);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Số lượng yêu cầu vượt quá số lượng tồn kho\"}");
            return;
        }

        boolean itemUpdated = false;
        for (CartModel item : cartItems) {
            if (item.getProductId().equals(productId) && (variantId == null ? item.getVariantId() == null : variantId.equals(item.getVariantId()))) {
                item.setQuantity(quantity);
                cartDAO.update(item);
                itemUpdated = true;
                log.info(LOGGER_NAME, "Cập nhật giỏ hàng, sản phẩm ID: " + productId + ", variantId: " + (variantId != null ? variantId : "null") + ", số lượng mới: " + quantity);
                break;
            }
        }

        if (!itemUpdated) {
            log.warn(LOGGER_NAME, "Không tìm thấy sản phẩm trong giỏ hàng để cập nhật, productId: " + productId + ", variantId: " + (variantId != null ? variantId : "null"));
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Sản phẩm không có trong giỏ hàng\"}");
            return;
        }

        session.setAttribute("cartItems", cartItems);
        session.setAttribute("cart", cartHeader);
        log.info(LOGGER_NAME, "Hoàn tất cập nhật giỏ hàng, tổng số mục: " + cartItems.size());
        response.getWriter().write("{\"status\":\"success\", \"message\":\"Cập nhật giỏ hàng thành công\"}");
    }

    @Override
    public void removeCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        if (user == null) {
            log.warn(LOGGER_NAME, "Người dùng chưa đăng nhập khi xóa mục giỏ hàng");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Vui lòng đăng nhập\"}");
            return;
        }

        CartHeaderModel cartHeader = cartDAO.getCartByUserId(user.getId());
        if (cartHeader == null) {
            log.error(LOGGER_NAME, "Không tìm thấy giỏ hàng để xóa mục.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Giỏ hàng không tồn tại\"}");
            return;
        }

        List<CartModel> cartItems = cartDAO.getCartItemsByCartId(cartHeader.getId());
        if (cartItems == null) {
            log.error(LOGGER_NAME, "Không tìm thấy giỏ hàng để xóa mục.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Giỏ hàng không tồn tại\"}");
            return;
        }

        Long productId = Long.parseLong(request.getParameter("productId"));
        String variantIdParam = request.getParameter("variantId");
        Long variantId = (variantIdParam == null || variantIdParam.isBlank()) ? null : Long.parseLong(variantIdParam);

        int initialSize = cartItems.size();
        cartItems.removeIf(item -> item.getProductId().equals(productId) &&
                (variantId == null ? item.getVariantId() == null : variantId.equals(item.getVariantId())));
        cartDAO.removeProduct(productId);

        if (cartItems.size() < initialSize) {
            log.info(LOGGER_NAME, "Xóa sản phẩm khỏi giỏ hàng, ID: " + productId + ", variantId: " + (variantId != null ? variantId : "null"));
            session.setAttribute("cartItems", cartItems);
            response.getWriter().write("{\"status\":\"success\", \"message\":\"Xóa sản phẩm thành công\"}");
        } else {
            log.warn(LOGGER_NAME, "Không tìm thấy sản phẩm để xóa, ID: " + productId + ", variantId: " + (variantId != null ? variantId : "null"));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Sản phẩm không tìm thấy trong giỏ hàng\"}");
        }
        log.info(LOGGER_NAME, "Hoàn tất xóa mục khỏi giỏ hàng, số mục còn lại: " + cartItems.size());
    }
    @Override
    public void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        if (user == null) {
            log.warn(LOGGER_NAME, "Người dùng chưa đăng nhập khi cố gắng xóa giỏ hàng");
            response.sendRedirect("/login");
            return;
        }

        // Lấy giỏ hàng của người dùng từ cơ sở dữ liệu
        CartHeaderModel cartHeader = cartDAO.getCartByUserId(user.getId());
        if (cartHeader != null) {
            // Xóa tất cả các mục trong giỏ hàng (cart_items)
            cartDAO.clearCartItems(cartHeader.getId());
            log.info(LOGGER_NAME, "Xóa toàn bộ giỏ hàng trong cơ sở dữ liệu cho user ID: " + user.getId());
        } else {
            log.warn(LOGGER_NAME, "Không tìm thấy giỏ hàng trong cơ sở dữ liệu cho user ID: " + user.getId());
        }

        // Xóa giỏ hàng trong session
        session.removeAttribute("cartItems");
        session.removeAttribute("discountCode");
        session.removeAttribute("discountError");

        log.info(LOGGER_NAME, "Xóa toàn bộ giỏ hàng và các thuộc tính liên quan trong session.");
    }

    @Override
    public void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        List<CartModel> listCartItems = null;
        CartHeaderModel cartHeader = null;

        if (user == null) {
            listCartItems = (List<CartModel>) session.getAttribute("cartItems");
        } else {
            cartHeader = cartDAO.getCartByUserId(user.getId());
            if (cartHeader != null) {
                listCartItems = cartDAO.getCartItemsByCartId(cartHeader.getId());
            }
            if (listCartItems == null || listCartItems.isEmpty()) {
                listCartItems = (List<CartModel>) session.getAttribute("cartItems");
                log.warn(LOGGER_NAME, "Database cart empty, using session cartItems: " + (listCartItems != null ? listCartItems.size() : 0));
            }
        }

        if (listCartItems == null || listCartItems.isEmpty()) {
            log.warn(LOGGER_NAME, "Giỏ hàng trống khi hiển thị.");
            request.setAttribute("errorMessage", "Giỏ hàng của bạn đang trống.");
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }

        List<CartModelHelper> listCartDisplay = new ArrayList<>();
        long totalAmount = calculateTotalAmount(listCartItems); // Sử dụng calculateTotalAmount
        long discountAmount = 0;
        String appliedCouponCode = (String) session.getAttribute("discountCode");
        CouponModel appliedCoupon = null;

        if (appliedCouponCode != null) {
            appliedCoupon = couponDAO.findCouponByCode(appliedCouponCode);
            if (appliedCoupon != null) {
                discountAmount = calculateDiscount(totalAmount, appliedCoupon);
                if (appliedCoupon.getMaxDiscountValue() != null && discountAmount > appliedCoupon.getMaxDiscountValue()) {
                    discountAmount = appliedCoupon.getMaxDiscountValue();
                }
            }
        }

        for (CartModel cartItem : listCartItems) {
            ProductModel product = productService.findProductById(cartItem.getProductId());
            if (product == null) {
                log.error(LOGGER_NAME, "Không tìm thấy sản phẩm khi hiển thị giỏ hàng, ID: " + cartItem.getProductId());
                continue;
            }
            ProductVariant variant = null;
            if (cartItem.getVariantId() != null) {
                variant = productService.findVariantById(cartItem.getVariantId());
            }
            listCartDisplay.add(new CartModelHelper(product, cartItem.getQuantity(), variant));
        }

        List<CouponModel> discountCodes = couponService.findAvailableCoupons().stream()
                .filter(c -> c.getMinOrderValue() <= totalAmount && c.getCurrentUsage() < c.getMaxUsage())
                .collect(Collectors.toList());

        request.setAttribute("listCartDisplay", listCartDisplay);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("discountCodes", discountCodes);
        request.setAttribute("finalAmount", totalAmount - discountAmount);
        request.setAttribute("appliedCouponCode", appliedCouponCode);
        request.setAttribute("cartHeader", cartHeader);

        log.info(LOGGER_NAME, "Chuyển tiếp đến trang giỏ hàng, tổng tiền: " + totalAmount +
                ", giảm giá: " + discountAmount + ", mã giảm giá áp dụng: " + appliedCouponCode);
        request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
    }

    private long calculateDiscount(long totalAmount, CouponModel coupon) {
        if ("percentage".equalsIgnoreCase(coupon.getDiscountType().toString())) {
            long discount = totalAmount * coupon.getDiscountValue() / 100;
            log.info(LOGGER_NAME, "Tính giảm giá theo phần trăm, giá trị: " + discount);
            return discount;
        } else if ("fixed".equalsIgnoreCase(coupon.getDiscountType().toString())) {
            log.info(LOGGER_NAME, "Tính giảm giá cố định, giá trị: " + coupon.getDiscountValue());
            return coupon.getDiscountValue();
        }
        log.warn(LOGGER_NAME, "Loại giảm giá không xác định: " + coupon.getDiscountType());
        return 0;
    }
    @Override
    public void applyDiscountCode(HttpServletRequest request, HttpServletResponse response, String discountCode) throws IOException {
        HttpSession session = request.getSession();
        List<CartModel> cartItems = null;

        try {
            cartItems = getCartList(request, response);
        } catch (ServletException e) {
            log.error(LOGGER_NAME, "Lỗi khi lấy giỏ hàng: " + e.getMessage());
            session.setAttribute("discountError", "Lỗi khi lấy giỏ hàng");
            response.sendRedirect("/gio-hang");
            return;
        }

        if (cartItems == null || cartItems.isEmpty()) {
            log.warn(LOGGER_NAME, "Giỏ hàng trống khi áp dụng mã giảm giá: " + discountCode);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\": false, \"message\": \"Giỏ hàng trống, không thể áp dụng voucher\"}");
            return;
        }

        long total = calculateTotalAmount(cartItems);
        CouponModel coupon = couponDAO.findCouponByCode(discountCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (coupon == null) {
            log.error(LOGGER_NAME, "Không tìm thấy mã giảm giá: " + discountCode);
            response.getWriter().write("{\"success\": false, \"message\": \"Mã giảm giá không hợp lệ\"}");
            return;
        }

        if (!coupon.getIsAvailable()) {
            log.warn(LOGGER_NAME, "Mã giảm giá không khả dụng: " + discountCode);
            session.setAttribute("discountError", "Mã giảm giá không khả dụng");
            response.getWriter().write("{\"success\": false, \"message\": \"Mã giảm giá không khả dụng\"}");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getStartDate())) {
            log.warn(LOGGER_NAME, "Mã giảm giá chưa có hiệu lực: " + discountCode);
            response.getWriter().write("{\"success\": false, \"message\": \"Mã giảm giá chưa có hiệu lực\"}");
            return;
        }

        if (now.isAfter(coupon.getEndDate())) {
            log.warn(LOGGER_NAME, "Mã giảm giá đã hết hạn: " + discountCode);
            response.getWriter().write("{\"success\": false, \"message\": \"Mã giảm giá đã hết hạn\"}");
            return;
        }

        if (total < coupon.getMinOrderValue()) {
            log.warn(LOGGER_NAME, "Đơn hàng chưa đạt giá trị tối thiểu để áp dụng mã giảm giá: " + discountCode);
            response.getWriter().write("{\"success\": false, \"message\": \"Đơn hàng chưa đủ điều kiện áp dụng mã giảm giá\"}");
            return;
        }

        if (couponDAO.getRemainingQuantity(discountCode) <= 0) {
            log.warn(LOGGER_NAME, "Mã giảm giá đã hết lượt sử dụng: " + discountCode);
            response.getWriter().write("{\"success\": false, \"message\": \"Mã giảm giá đã hết lượt sử dụng\"}");
            return;
        }

        long discountAmount = calculateDiscount(total, coupon);
        if (coupon.getMaxDiscountValue() != null && coupon.getMaxDiscountValue() > 0 && discountAmount > coupon.getMaxDiscountValue()) {
            discountAmount = coupon.getMaxDiscountValue();
        }

        long discountedTotal = total - discountAmount;

        session.setAttribute("discountCode", discountCode);
        session.setAttribute("discountAmount", discountAmount);
        session.setAttribute("discountedTotal", discountedTotal);
        session.removeAttribute("discountError");
        log.info(LOGGER_NAME, "Áp dụng mã giảm giá thành công: " + discountCode);

        String json = String.format(
                "{\"success\": true, \"discountedTotal\": %d, \"discountAmount\": %d, \"message\": \"Áp dụng mã giảm giá thành công\"}",
                discountedTotal, discountAmount
        );
        response.getWriter().write(json);

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
    public CartHeaderModel getCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        return cartDAO.getCartByUserId(user.getId());
    }

    @Override
    public List<CartModel> getCartList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartHeaderModel cart = getCart(request, response);
        List<CartModel> listItems = cartDAO.getCartItemsByCartId(cart.getId());
        return  listItems;
    }



    @Override
    public Long createCartForUser(Long id) {
        return  cartDAO.createCartForUser(id);
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
                    ProductVariant variant = productService.findVariantById(item.getVariantId());
                    if (variant != null) {
                        total += variant.getPrice() * item.getQuantity();
                        log.info(LOGGER_NAME, "Tính tổng cho biến thể ID: " + item.getVariantId() + ", giá: " + variant.getPrice() + ", số lượng: " + item.getQuantity());
                    } else {
                        log.error(LOGGER_NAME, "Không tìm thấy biến thể ID: " + item.getVariantId());
                    }
                }
            } else {
                log.error(LOGGER_NAME, "Không tìm thấy sản phẩm khi tính tổng, ID: " + item.getProductId());
            }
        }
        log.info(LOGGER_NAME, "Tổng giá trị giỏ hàng: " + total);
        return total;
    }
    @Override
    public List<CouponModel> getAvailableCoupons() {
        return couponService.findAvailableCoupons();
    }
}