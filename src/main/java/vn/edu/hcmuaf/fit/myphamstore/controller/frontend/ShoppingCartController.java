package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.User;
import vn.edu.hcmuaf.fit.myphamstore.dao.ICouponDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.CartHeaderModel;
import vn.edu.hcmuaf.fit.myphamstore.model.CartModel;
import vn.edu.hcmuaf.fit.myphamstore.model.CouponModel;
import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;
import vn.edu.hcmuaf.fit.myphamstore.service.ICartService;
import vn.edu.hcmuaf.fit.myphamstore.service.IProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ShoppingCartController", value = "/gio-hang")
public class ShoppingCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private ICartService cartService;
    @Inject
    private ICouponDAO couponDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("count".equals(action)) {
            HttpSession session = request.getSession();
            UserModel user = (UserModel) session.getAttribute("user");

            int count = 0;
            if (user != null) {
                List<CartModel> listItems = cartService.getCartList(request,response);
                count = listItems.size();
            } else {
                List<CartModel> sessionCart = (List<CartModel>) session.getAttribute("cart");
                if (sessionCart != null) count = sessionCart.size();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"count\": " + count + "}");
        }
        else if ("getAvailableCoupons".equals(action)) {
            List<CouponModel> availableCoupons = cartService.getAvailableCoupons();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < availableCoupons.size(); i++) {
                CouponModel c = availableCoupons.get(i);
                json.append("{")
                        .append("\"id\":").append(c.getId()).append(",")
                        .append("\"code\":\"").append(c.getCode()).append("\",")
                        .append("\"discountType\":\"").append(c.getDiscountType()).append("\",")
                        .append("\"discountValue\":").append(c.getDiscountValue()).append(",")
                        .append("\"minOrderValue\":").append(c.getMinOrderValue())
                        .append("}");
                if (i != availableCoupons.size() - 1) json.append(",");
            }
            json.append("]");

            response.getWriter().write(json.toString());
        }

        else {
            cartService.displayCart(request, response);
}
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
            response.sendRedirect("/gio-hang");
        } else if ("applyDiscount".equals(action)) {
            String discountCode = request.getParameter("discountCode");
            cartService.applyDiscountCode(request, response, discountCode);
        } else {
            cartService.displayCart(request, response);
        }
    }

}