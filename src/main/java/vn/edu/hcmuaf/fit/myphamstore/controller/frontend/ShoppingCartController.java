package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.myphamstore.service.ICartService;
import vn.edu.hcmuaf.fit.myphamstore.service.IProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ShoppingCartController", value = "/gio-hang")
public class ShoppingCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private ICartService cartService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int totalQuantity = cartService.getTotalQuantity(session);
        response.setContentType("application/json");
        response.getWriter().write("{\"totalQuantity\": " + totalQuantity + "}");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            cartService.addToCart(request, response);
        } else if ("updateCart".equals(action)) {
            cartService.updateCart(request, response);
        } else if ("remove".equals(action)) {
            cartService.removeCartItem(request, response);
        } else if ("clear".equals(action)) {
            cartService.clearCart(request, response);
        }else {
            cartService.displayCart(request, response);
        }

    }

}