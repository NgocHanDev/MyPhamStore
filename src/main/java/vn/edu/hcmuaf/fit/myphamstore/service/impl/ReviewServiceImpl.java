package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.dao.IReviewDAO;
import vn.edu.hcmuaf.fit.myphamstore.dao.IUserDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.*;
import vn.edu.hcmuaf.fit.myphamstore.service.IOrderService;
import vn.edu.hcmuaf.fit.myphamstore.service.IReviewService;
import vn.edu.hcmuaf.fit.myphamstore.service.IUserService;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.io.IOException;
import java.util.List;

public class ReviewServiceImpl implements IReviewService {
    @Inject
    private IReviewDAO reviewDAO;
    @Inject
    private IOrderService orderService;
    @Inject
    private IUserDAO userDAO;
    @Inject
    private LoggingService log;
    private final String CLASS_NAME = "REVIEW-SERVICE";

    @Override
    public ReviewModel findReviewById(Long id) {
        return reviewDAO.findReviewById(id);
    }

    @Override
    public ReviewModel getReviewDetail(Long id) {
        return reviewDAO.getReviewDetail(id);
    }

    @Override
    public List<ReviewModel> getAllReviewsByProductId(Long id) {
        return reviewDAO.getAllReviewsByProductId(id);
    }

    @Override
    public List<ReviewModel> pagingReview(String keyword, int currentPage, int pageSize, String orderBy) {
        return reviewDAO.findAll(keyword, currentPage, pageSize, orderBy);
    }

    @Override
    public void addReview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info(CLASS_NAME, "Thêm đánh giá sản phẩm");
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        boolean isPurchased = false;
        String productId = request.getParameter("productId");
        String userId = request.getParameter("userId");
        List<OrderModel> orders = orderService.getOrdersByUserId(Long.parseLong(userId));
        for (OrderModel order : orders) {
            List<OrderDetailModel> orderDetailModels = orderService.getOrderDetailsByOrderId(order.getId());
            for (OrderDetailModel detail : orderDetailModels) {
                List<ProductModel> productModels = orderService.getProductByOrderDetail(detail);
                for (ProductModel productModel : productModels) {
                    if (productModel.getId().equals(Long.parseLong(productId))) {
                        log.info(CLASS_NAME, "Người dùng đã mua sản phẩm này");
                        // Đặt cờ để đánh dấu đã mua
                        isPurchased = true;
                        break;
                    }
                }
            }
        }
        if (!isPurchased) {
            log.info(CLASS_NAME, "Người dùng chưa mua sản phẩm này");
            response.sendRedirect(request.getContextPath() + "/product-detail?id=" + productId + "&error=notPurchased");
            return;
        }


        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        ReviewModel review = new ReviewModel();
        review.setProductId(Long.parseLong(productId));
        review.setUserId(Long.parseLong(userId));
        review.setRating(rating);
        review.setComment(comment);

        reviewDAO.saveReview(review,Long.parseLong(userId),Long.parseLong(productId));
        log.info(CLASS_NAME, "Thêm đánh giá sản phẩm thành công cho sản phẩm có id: " + productId);

        response.sendRedirect(request.getContextPath() + "/product-detail?id=" + productId);
    }
    }

