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
//        Long variantId = request.getParameter("variantId").isBlank() ? null : Long.parseLong(request.getParameter("variantId"));
        Long variantId = (request.getParameter("variantId") == null || request.getParameter("variantId").isBlank()) ? null : Long.parseLong(request.getParameter("variantId"));
        int quantity = Integer.parseInt(request.getParameter("quantity") == null ? "1" : request.getParameter("quantity"));
        ProductModel product = productService.findProductById(productId);
        Long brandId = product.getBrandId();


        CartModel item = CartModel.builder()
                .productId(productId)
                .quantity(quantity)
                .brandId(brandId)
                .variantId(variantId)
                .build();

        HttpSession session = request.getSession();
        List<CartModel> listCartItems = session.getAttribute("cart") == null ? new ArrayList<>() : (List<CartModel>) session.getAttribute("cart");
        System.out.println("Before adding: " + listCartItems);
        listCartItems.forEach(cartItem -> {
            if (cartItem.equals(item)) { // Check if the product is already in the cart
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        });
        // If the product is not in the cart, add it
        if (!listCartItems.contains(item)) {
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

        if (quantity < 1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantity must be at least 1");
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
            System.out.println("After displaying: " + listCartDisplay);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while processing your cart.");
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }

        List<CouponModel> discountCodes = couponService.findAvailableCoupons();
        List<Map<String, Object>> simplifiedDiscountCodes = discountCodes.stream()
                .map(coupon -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", coupon.getCode());
                    map.put("discountType", coupon.getDiscountType());
                    map.put("discountValue", coupon.getDiscountValue());
                    return map;
                })
                .collect(Collectors.toList());
        request.setAttribute("discountCodes", simplifiedDiscountCodes);

        String discountCode = (String) session.getAttribute("discountCode");
        long discountAmount = 0;
        if (discountCode != null) {
            for (CouponModel coupon : discountCodes) {
                if (coupon.getCode().equals(discountCode)) {
                    discountAmount = calculateDiscount(totalAmount.get(), coupon);
                    break;
                }
            }
        }

        request.setAttribute("listCartDisplay", listCartDisplay);
        request.setAttribute("totalAmount", totalAmount.get());
        long finalAmount = totalAmount.get() - discountAmount;
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("finalAmount", finalAmount);

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

        int count = cart == null ? 0 : cart.size();
        response.setContentType("application/json");
        response.getWriter().write("{\"count\":" + count + "}");
    }
    @Override
    public void applyDiscountCode(HttpServletRequest request, HttpServletResponse response, String discountCode) throws IOException {
        HttpSession session = request.getSession();
        CouponModel coupon = couponDAO.findByCode(discountCode);
        if (coupon != null && couponDAOImpl.getRemainingQuantity(discountCode) > 0) {
            session.setAttribute("discountAmount", couponDAOImpl.getDiscount(discountCode));
            session.setAttribute("discountCode", discountCode);
            response.sendRedirect("/gio-hang");
        } else {
            session.setAttribute("discountError", "Mã giảm giá không hợp lệ hoặc đã hết hạn.");
            response.sendRedirect("/gio-hang");
        }
    }
}

