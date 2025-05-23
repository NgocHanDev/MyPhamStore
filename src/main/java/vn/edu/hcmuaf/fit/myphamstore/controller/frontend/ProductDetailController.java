package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import vn.edu.hcmuaf.fit.myphamstore.dao.IProductDAO;
import vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl.ProductDAOImpl;
import vn.edu.hcmuaf.fit.myphamstore.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.service.IProductService;
import vn.edu.hcmuaf.fit.myphamstore.service.IReviewService;
import vn.edu.hcmuaf.fit.myphamstore.service.IUserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductDetailController", value = "/product-detail")
public class ProductDetailController extends HttpServlet {
    @Inject
    private IProductService productService;
    @Inject
    private IReviewService reviewService;
    @Inject
    private IUserService userService;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double totalReview = 0.0;
        double totalReview1 = 0.0;
        double totalReview2= 0.0;
        double totalReview3 = 0.0;
        double totalReview4 = 0.0;
        double totalReview5 = 0.0;
        String productId = request.getParameter("id");
        Long id = Long.parseLong(productId);
        ProductModel product = productService.getProductDetail(id);
        request.setAttribute("product", product);
        BrandModel brand = productService.getBrandById(product.getBrandId());
        request.setAttribute("brand", brand);
        Integer soldQuantity = productService.getSoldQuantityByProductId(id);
        request.setAttribute("soldQuantity", soldQuantity);
        List<ProductImageModel> productImages = productService.getProductImageById(id);
        request.setAttribute("productImages", productImages);
        List<ReviewModel> reviews = productService.getReviewsByProductId(id);
        request.setAttribute("reviewCount", reviews.size());
        request.setAttribute("reviews", reviews);
        List<UserModel> users = new ArrayList<>();
        for (ReviewModel review : reviews) {
            UserModel user = userService.findUserById(review.getUserId());
            users.add(user);
        }
        request.setAttribute("users", users);
        List<ProductVariant> productVariants = productService.getProductVariantsByProductId(id);
        request.setAttribute("variants", productVariants);
        System.out.println(productVariants);
        for (ReviewModel review : reviews) {
            totalReview += review.getRating();
            switch (review.getRating()) {
                case 5:
                    totalReview5 += 1;
                    break;
                case 4:
                    totalReview4 += 1;
                    break;
                case 3:
                    totalReview3 += 1;
                    break;
                case 2:
                    totalReview2 += 1;
                    break;
                case 1:
                    totalReview1 += 1;
                    break;
            }
        }

        int reviewCount = reviews.size();
        if (reviewCount > 0) {
            totalReview = Math.round((totalReview / reviewCount) * 100.0) / 100.0;
            totalReview5 = (totalReview5 / reviewCount) * 100;
            totalReview4 = (totalReview4 / reviewCount) * 100;
            totalReview3 = (totalReview3 / reviewCount) * 100;
            totalReview2 = (totalReview2 / reviewCount) * 100;
            totalReview1 = (totalReview1 / reviewCount) * 100;
        } else {
            totalReview = 0;
            totalReview5 = 0;
            totalReview4 = 0;
            totalReview3 = 0;
            totalReview2 = 0;
            totalReview1 = 0;
        }
        request.setAttribute("total", totalReview);
        request.setAttribute("total1", totalReview1);
        request.setAttribute("total2", totalReview2);
        request.setAttribute("total3", totalReview3);
        request.setAttribute("total4", totalReview4);
        request.setAttribute("total5", totalReview5);
        request.getRequestDispatcher("/frontend/product_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("addReview".equalsIgnoreCase(action)) {
            reviewService.addReview(req, resp);
        } else {
            doGet(req, resp);
        }
    }
}