package vn.edu.hcmuaf.fit.myphamstore.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.common.RoleType;
import vn.edu.hcmuaf.fit.myphamstore.model.RoleModel;
import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;

import java.io.IOException;
import java.util.List;

public class AutFilter implements Filter {
    private ServletContext context;
    private String[] requireLogin ;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        requireLogin = new String[]{
                "/change-password",
                "/profile",
                "/order-history",
                "/order-detail",
                "/logout",
                "/checkout",
                "/wishlist",
                "/admin"
        };
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String currentURL = req.getRequestURI();
        UserModel user = (UserModel) req.getSession().getAttribute("user");
        if(user == null){//chua login
            for (String url : requireLogin){
                if(currentURL.contains(url)){
                    System.out.println("Require login");
                    res.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
            }
        }else{//da login
            //check role admin
            List<RoleModel> roles = user.getRoles();
            boolean isAdmin = false;
            for (RoleModel role : roles){
                if(role.getName().equals(RoleType.ADMIN)){
                    isAdmin = true;
                    break;
                }
            }
            if(!isAdmin && currentURL.contains("/admin")){
                res.sendRedirect(req.getContextPath() + "/trang-chu");
                return;
            }
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }
}
