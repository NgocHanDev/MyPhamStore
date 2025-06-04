package vn.edu.hcmuaf.fit.myphamstore.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.model.CouponModel;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ICouponService {
    CouponModel findCouponById(Long id);


    List<CouponModel> getCouponsWithPaging(String keyword, int currentPage, int pageSize, String orderBy);

    CouponModel findCouponByCode(String couponCode);

    Long getTotalPage(int numOfItem);

    void displayCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void stopBuying(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void startBuying(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void addCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void updateCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    List<CouponModel> findAvailableCoupons();

    CouponModel applyCoupon(String couponCode, Double totalAmount);
}
