package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.entity.FacebookAccount;
import vn.edu.hcmuaf.fit.myphamstore.entity.GoogleAccount;
import vn.edu.hcmuaf.fit.myphamstore.model.*;
import vn.edu.hcmuaf.fit.myphamstore.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeController", value = "/trang-chu")
public class HomeController extends HttpServlet {
    @Inject
    private ISlideService slideService;
    @Inject
    private IProductService productService;
    @Inject
    private IBrandService brandService;
    @Inject
    private ICategoryService categoryService;
    @Inject
    private IUserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("code") != null) {
                String code = request.getParameter("code");
                System.out.println(code.length());
                String scope = request.getParameter("scope");
                System.out.println(scope.length());
                String accessToken = GoogleLogin.getToken(code);
                GoogleAccount googleAccount = GoogleLogin.getUserInfo(accessToken);
//                String accessToken = FaceBookLogin.getToken(code);
//                FacebookAccount googleAccount = FaceBookLogin.getUserInfo(accessToken);
                UserModel userModel = userService.findUserByEmail(googleAccount.getEmail());

                if(userModel != null) {
                    request.getSession().setAttribute("user", userModel);
                }
                else {
                    UserModel user = new UserModel();
                    user.setEmail(googleAccount.getEmail());
                    user.setFullName(googleAccount.getName());
                    user.setAvatar(googleAccount.getPicture());
                    List<RoleModel> roles = new ArrayList<>();
                    RoleModel role = new RoleModel();
                    role.setName("ROLE_USER");
                    role.setDescription("CUSTOMER");
                    roles.add(role);
                    user.setRoles(roles);
                    request.getSession().setAttribute("user", user);
                }
            }
            UserModel user = (UserModel) request.getSession().getAttribute("user");
            if (user != null) {
                request.setAttribute("user", user);
            }
            // Lấy các tham số từ request
            String keyword = request.getParameter("keyword");
            String orderBy = request.getParameter("orderBy");

            // Xử lý giá trị null cho currentPage và pageSize
            int currentPage = 1;
            int pageSize = 5;

            if (request.getParameter("currentPage") != null) {
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            }
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }

            // Lấy danh sách sản phẩm và số trang
            List<ProductModel> products = productService.getProductsWithPaging(keyword, currentPage, pageSize, orderBy);
            Long totalPages = productService.getTotalPage(pageSize);

            // Lấy danh sách sản phẩm mới nhất
            List<ProductModel> latestProducts = productService.getLatestProducts();
            request.setAttribute("latestProducts", latestProducts);

            // Lấy danh sách thương hiệu
            List<BrandModel> brands = brandService.getAllBrands();

            // lấy danh sách các loại sản phẩm
            List<CategoryModel> categories = categoryService.getAllCategories();

            List<ProductModel>skinCare = productService.getProductsByCategory(1);

            List<ProductModel>bodyCare = productService.getProductsByCategory(2);

            List<ProductModel>makeUp = productService.getProductsByCategory(3);

            // Lấy danh sách slide
            List<SlideModel> slides = slideService.findAll();

            request.setAttribute("slides", slides);

            // Set các attribute để gửi đến JSP
            request.setAttribute("products", products);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("keyword", keyword);
            request.setAttribute("orderBy", orderBy);
            request.setAttribute("latestProducts", latestProducts);
            request.setAttribute("brands", brands);
            request.setAttribute("categories", categories);
            request.setAttribute("skinCare", skinCare);
            request.setAttribute("bodyCare", bodyCare);
            request.setAttribute("makeUp", makeUp);

            // Chuyển hướng đến trang JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/frontend/home.jsp");
            dispatcher.forward(request, response);

        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi khởi tạo lớp: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xử lý yêu cầu: " + e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}