package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.constant.Iconstant;
import vn.edu.hcmuaf.fit.myphamstore.entity.GoogleAccount;
import vn.edu.hcmuaf.fit.myphamstore.service.IUserService;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Inject
    private IUserService userService;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        String action = request.getParameter("action");
//        String code = request.getParameter("code");
//
//        if ("googleLogin".equals(action) && code != null) {
//            System.out.println("Google OAuth Code received: " + code);
//            try {
//                String accessToken = GoogleLogin.getToken(code);
//                GoogleAccount googleAccount = GoogleLogin.getUserInfo(accessToken);
//
//                request.getSession().setAttribute("googleUser", googleAccount);
//                response.sendRedirect(request.getContextPath() + "/trang-chu");
//                return;
//            } catch (Exception e) {
//                e.printStackTrace();
//                response.sendRedirect(request.getContextPath() + "/login?error=google_login_failed");
//                return;
//            }
//        }
//
//        // Mặc định hiển thị trang login
        request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

         if ("googleLogin".equalsIgnoreCase(action)) {
            // Redirect to Google's OAuth2 authorization URL
            String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth" +
                    "?client_id=" + Iconstant.GOOGLE_CLIENT_ID +
                    "&redirect_uri=" + Iconstant.GOOGLE_REDIRECT_URI  +
                    "&response_type=code" +
                    "&scope=email%20profile";
            response.sendRedirect(googleAuthUrl);
        } else if("facebookLogin".equalsIgnoreCase(action)) {
        }else {
             userService.login(request, response);
         }
    }
}
