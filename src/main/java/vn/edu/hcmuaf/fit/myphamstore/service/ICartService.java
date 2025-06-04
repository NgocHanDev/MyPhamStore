package vn.edu.hcmuaf.fit.myphamstore.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.model.CartHeaderModel;
import vn.edu.hcmuaf.fit.myphamstore.model.CartModel;
import vn.edu.hcmuaf.fit.myphamstore.model.CouponModel;
import java.io.IOException;
import java.util.List;

public interface ICartService {
    void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void updateCart(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void removeCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void getCartCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    CartHeaderModel getCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    List<CartModel> getCartList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void applyDiscountCode(HttpServletRequest request, HttpServletResponse response, String discountCode) throws IOException;
    Long createCartForUser(Long id);
    List<CouponModel> getAvailableCoupons();
}
