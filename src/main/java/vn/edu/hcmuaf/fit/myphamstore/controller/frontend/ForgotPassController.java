package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.dao.IOtpDAO;
import vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl.OtpDAOImpl;
import vn.edu.hcmuaf.fit.myphamstore.service.IUserService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/forgot-password")
public class ForgotPassController extends HttpServlet {
    @Inject
    private IUserService userService;
    @Inject
    private IOtpDAO otpDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/frontend/forgot-password.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        System.out.println(email);
        // Tạo mã OTP
        String otp = "";

        // Lưu OTP vào database hoặc cache (Redis, session...)
        boolean emailSent = userService.forgotPassword(email, otp);

        if (emailSent) {
            request.setAttribute("successMessage", "Email đặt lại mật khẩu đã được gửi!");
        } else {
            request.setAttribute("errorMessage", "Không thể gửi email, vui lòng thử lại.");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/frontend/forgot-password.jsp");
        dispatcher.forward(request, response);
    }
}