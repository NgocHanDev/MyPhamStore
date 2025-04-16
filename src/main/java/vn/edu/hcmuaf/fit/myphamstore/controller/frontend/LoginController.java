package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.constant.Iconstant;
import vn.edu.hcmuaf.fit.myphamstore.entity.GoogleAccount;
import vn.edu.hcmuaf.fit.myphamstore.entity.FacebookAccount;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String code = request.getParameter("code");


        if ("googleLogin".equals(action) && code != null) {
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
        } else if ("facebookLogin".equals(action) && code != null) {
            try {
                String accessToken = FaceBookLogin.getToken(code);
                FacebookAccount facebookAccount = FaceBookLogin.getUserInfo(accessToken);

                request.getSession().setAttribute("facebookUser", facebookAccount);
                response.sendRedirect(request.getContextPath() + "/trang-chu");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/login?error=facebook_login_failed");
                return;
            }
        }

        request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("googleLogin".equals(action)) {
            String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth" +
                    "?client_id=" + Iconstant.GOOGLE_CLIENT_ID +
                    "&redirect_uri=" + Iconstant.GOOGLE_REDIRECT_URI +
                    "&response_type=code" +
                    "&scope=email%20profile";
            response.sendRedirect(googleAuthUrl);
        } else if ("facebookLogin".equals(action)) {
            String facebookAuthUrl = "https://www.facebook.com/v22.0/dialog/oauth" +
                    "?client_id=" + Iconstant.FACEBOOK_CLIENT_ID +
                    "&redirect_uri=" + Iconstant.FACEBOOK_REDIRECT_URI +
                    "&response_type=code" +
                    "&scope=email,public_profile";
            response.sendRedirect(facebookAuthUrl);
        } else {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if (email.equals("test@example.com") && password.equals("password")) {
                request.getSession().setAttribute("user", email);

                response.sendRedirect(request.getContextPath() + "/trang-chu");
            } else {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
            }
        }
    }
}