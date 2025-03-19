package vn.edu.hcmuaf.fit.myphamstore.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ICartService {
    void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void updateCart(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void removeCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void clearCart(HttpServletRequest request, HttpServletResponse response);
    void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void getCartCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
