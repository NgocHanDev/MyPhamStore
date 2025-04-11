package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.common.PasswordUtils;
import vn.edu.hcmuaf.fit.myphamstore.dao.IOtpDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;
import vn.edu.hcmuaf.fit.myphamstore.service.IUserService;

import java.io.IOException;

@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {
    @Inject
    private IUserService userService;
    @Inject
    private IOtpDAO otpDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/frontend/reset-password.jsp");
        dispatcher.forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String otp = request.getParameter("otp");
        String newPassword = request.getParameter("new-password");
        String confirmPassword = request.getParameter("confirm-password");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu không khớp!");
            request.getRequestDispatcher("/frontend/reset-password.jsp").forward(request, response);
            return;
        }

        // Kiểm tra OTP trong database hoặc cache
        boolean isValidOTP = userService.verifyOTPHash(request, response);
        System.out.println(isValidOTP);

        if (!isValidOTP) {
            request.setAttribute("errorMessage", "OTP không hợp lệ hoặc đã hết hạn!");
            request.getRequestDispatcher("/frontend/reset-password.jsp").forward(request, response);
            return;
        }

        // Cập nhật mật khẩu mới
        UserModel user = userService.findUserByEmail(email);
        boolean updateSuccess = userService.updateUserPassword(user);

        if (updateSuccess) {
            request.setAttribute("successMessage", "Đặt lại mật khẩu thành công!");
            response.sendRedirect("/login");
        } else {
            request.setAttribute("errorMessage", "Lỗi hệ thống, vui lòng thử lại!");
            request.getRequestDispatcher("/frontend/reset-password.jsp").forward(request, response);
        }
    }
}
