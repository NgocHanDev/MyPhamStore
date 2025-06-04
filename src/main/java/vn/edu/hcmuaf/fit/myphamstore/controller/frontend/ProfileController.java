package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.edu.hcmuaf.fit.myphamstore.service.IUserService;

import java.io.IOException;

@WebServlet(name = "ProfileController", value = "/profile")
@MultipartConfig(   // Cấu hình upload file
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class ProfileController extends HttpServlet {
    @Inject
    private IUserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equalsIgnoreCase(action)) {
            userService.updateProfile(request, response);
        } else if ("addAddress".equalsIgnoreCase(action)) {
            userService.addAddress(request, response);
        } else if ("showAvatar".equalsIgnoreCase(action)) {
           userService.showAvatar(request, response);
        }else {
            userService.profile(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("uploadAvatar".equalsIgnoreCase(action)) {
            Part filePart = request.getPart("avatar"); // Lấy file từ form
            userService.uploadAvatar(request, response, filePart);
        } else if ("setDefaultAddress".equalsIgnoreCase(action)) {
            userService.setDefaultAddress(request, response);
        } else if ("deleteAddress".equalsIgnoreCase(action)) {
            userService.deleteAddress(request, response);
        } else {
            doGet(request, response);
        }
    }
}
