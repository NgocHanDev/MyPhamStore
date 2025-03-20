package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.model.CouponModel;
import vn.edu.hcmuaf.fit.myphamstore.service.ICheckoutService;
import vn.edu.hcmuaf.fit.myphamstore.service.ICouponService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckoutController", value = "/checkout")
public class CheckoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Inject
    private ICheckoutService checkoutService;
    @Inject
    private ICouponService couponService;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("display".equals(action)) {
            checkoutService.displayCheckout(request, response);
        } else if ("checkout".equals(action)) {
            checkoutService.checkout(request, response);
        }
        // Fetch available coupons and set as request attribute
        List<CouponModel> availableCoupons = couponService.findAvailableCoupons();
        request.setAttribute("availableCoupons", availableCoupons);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}