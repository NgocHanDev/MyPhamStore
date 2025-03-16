package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.myphamstore.model.CartModel;
import vn.edu.hcmuaf.fit.myphamstore.model.CartModelHelper;
import vn.edu.hcmuaf.fit.myphamstore.model.CouponModel;
import vn.edu.hcmuaf.fit.myphamstore.model.ProductModel;
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
    @Override
    public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = Long.parseLong(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity") == null ? "1" : request.getParameter("quantity"));
        ProductModel product = productService.findProductById(productId);
        Long brandId = product.getBrandId();

        CartModel item = CartModel.builder()
                .productId(productId)
                .quantity(quantity)
                .brandId(brandId)
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
                }
                totalAmount.addAndGet(product.getPrice() * cartItem.getQuantity());
                listCartDisplay.add(new CartModelHelper(product, cartItem.getQuantity()));
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "An error occurred while processing your cart.");
            request.getRequestDispatcher("/frontend/shopping_cart.jsp").forward(request, response);
            return;
        }

        List<CouponModel> discountCodes= couponService.findAvailableCoupons();
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

        String discountCode = request.getParameter("discountCode");
        boolean isDiscountValid = false;
        long discountAmount = 0;
        if (discountCode != null) {
            for (CouponModel coupon : discountCodes) {
                if (coupon.getCode().equals(discountCode)) {
                    isDiscountValid = true;
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
        request.setAttribute("isDiscountValid", isDiscountValid);


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
    public int getTotalQuantity(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartModel> cart = (List<CartModel>) session.getAttribute("cart");
        if (cart == null) {
            return 0;
        }
        return cart.stream().mapToInt(CartModel::getQuantity).sum();
    }
}
