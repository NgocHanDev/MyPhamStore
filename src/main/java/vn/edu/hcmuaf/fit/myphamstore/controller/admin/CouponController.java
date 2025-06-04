package vn.edu.hcmuaf.fit.myphamstore.controller.admin;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.common.action.AdminAction;
import vn.edu.hcmuaf.fit.myphamstore.model.RoleModel;
import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;
import vn.edu.hcmuaf.fit.myphamstore.service.ICouponService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminCouponController", value = "/admin/coupons")
public class CouponController extends HttpServlet {
    @Inject
    private ICouponService couponService;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        //get user from session
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        //check if user is admin
        List<RoleModel> roles = user.getRoles();
        boolean isAdmin = roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));
        if (!isAdmin) {
            response.sendRedirect(request.getContextPath() + "/403");
            return;
        }
        if (action == null || action.equalsIgnoreCase(AdminAction.DISPLAY) || action.equalsIgnoreCase(AdminAction.SEARCH) || action.isEmpty()) {
            couponService.displayCoupon(request, response);
        } else if (action.equalsIgnoreCase(AdminAction.STOP_BUYING)) {
            couponService.stopBuying( request, response);
        }else if (action.equalsIgnoreCase(AdminAction.START_BUYING)) {
            couponService.startBuying(request, response);
        }else if (action.equalsIgnoreCase(AdminAction.ADD)) {
            couponService.addCoupon(request, response);
        } else if (action.equalsIgnoreCase(AdminAction.EDIT)) {
            couponService.updateCoupon(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
