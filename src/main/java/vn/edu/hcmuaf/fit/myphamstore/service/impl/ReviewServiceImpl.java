package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.dao.IReviewDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.ReviewModel;
import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;
import vn.edu.hcmuaf.fit.myphamstore.service.IReviewService;

import java.io.IOException;
import java.util.List;

public class ReviewServiceImpl implements IReviewService {
    @Inject
    private IReviewDAO reviewDAO;

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
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String productId = request.getParameter("productId");
        String userId = request.getParameter("userId");
        System.out.println("userId: " + userId);
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        ReviewModel review = new ReviewModel();
        review.setProductId(Long.parseLong(productId));
        review.setUserId(Long.parseLong(userId));
        review.setRating(rating);
        review.setComment(comment);

        reviewDAO.saveReview(review,Long.parseLong(userId),Long.parseLong(productId));

        response.sendRedirect(request.getContextPath() + "/product-detail?id=" + productId);
    }
    }

