package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.constant.Iconstant;
import vn.edu.hcmuaf.fit.myphamstore.entity.GoogleAccount;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String code = request.getParameter("code");

        if ("googleLogin".equals(action) && code != null) {
            System.out.println("Google OAuth Code received: " + code);
            try {
                String accessToken = GoogleLogin.getToken(code);
                GoogleAccount googleAccount = GoogleLogin.getUserInfo(accessToken);

                request.getSession().setAttribute("googleUser", googleAccount);
                response.sendRedirect(request.getContextPath() + "/trang-chu");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/login?error=google_login_failed");
                return;
            }
        }

        // Mặc định hiển thị trang login
        request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("googleLogin".equals(action)) {
            // Redirect to Google's OAuth2 authorization URL
            String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth" +
                    "?client_id=" + Iconstant.GOOGLE_CLIENT_ID +
                    "&redirect_uri=" + Iconstant.GOOGLE_REDIRECT_URI +
                    "&response_type=code" +
                    "&scope=email%20profile";
            response.sendRedirect(googleAuthUrl);
        } else {
            // Handle regular login
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Add your login validation logic here (e.g., check against the database)
            if (email.equals("test@example.com") && password.equals("password")) { // Example validation
                // Store user info in session
                request.getSession().setAttribute("user", email);

                // Redirect to homepage
                response.sendRedirect(request.getContextPath() + "/trang-chu");
            } else {
                // Login failed, redirect back to login page with error
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
            }
        }
    }
}
