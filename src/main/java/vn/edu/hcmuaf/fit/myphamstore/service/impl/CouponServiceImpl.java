package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.dao.ICouponDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.CouponModel;
import vn.edu.hcmuaf.fit.myphamstore.service.ICouponService;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CouponServiceImpl implements ICouponService {
    @Inject
    private ICouponDAO couponDAO;

    @Inject
    private LoggingService log;
    private final String CLASS_NAME = "COUPON-SERVICE";

    @Override
    public CouponModel findCouponById(Long id) {
        return couponDAO.findCouponById(id);
    }

    @Override
    public List<CouponModel> getCouponsWithPaging(String keyword, int currentPage, int pageSize, String orderBy) {
        if (keyword != null && !keyword.isEmpty()) {
            keyword = keyword.trim();
        }
        log.info(CLASS_NAME, "Lấy danh sách coupon");
        return couponDAO.findAll(keyword, currentPage, pageSize, orderBy);
    }
    public CouponModel applyCoupon(String couponCode, Double totalAmount) {
        CouponModel coupon = couponDAO.findCouponByCode(couponCode);
        if (coupon == null || !coupon.getIsAvailable() || coupon.getEndDate().isBefore(LocalDateTime.now())) {
            return null;
        }
        if (totalAmount < coupon.getMinOrderValue()) {
            return null;
        }
        log.info(CLASS_NAME, "Áp dụng coupon thành công: " + couponCode);
        return coupon;
    }

    @Override
    public CouponModel findCouponByCode(String couponCode) {
        return couponDAO.findCouponByCode(couponCode);
    }

    @Override
    public Long getTotalPage(int numOfItem) {
        return couponDAO.getTotalPage(numOfItem);
    }

    @Override
    public void displayCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info(CLASS_NAME, "Hiển thị danh sách coupon");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/coupon/coupon-management.jsp");
        String keyword = request.getParameter("keyword");
        String orderBy = request.getParameter("orderBy");
        int currentPage = Integer.parseInt(request.getParameter("currentPage")==null?"1": request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize") == null?"5": request.getParameter("pageSize"));

        List<CouponModel> coupons = this.getCouponsWithPaging(keyword, currentPage, pageSize, orderBy);
        Long totalPages = this.couponDAO.getTotalPage(5);

        request.setAttribute("coupons", coupons);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyword", keyword);
        request.setAttribute("orderBy", orderBy);
        dispatcher.forward(request, response);
    }

    @Override
    public void stopBuying(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        //tến hành cập nhật trạng thái sản phẩm
        CouponModel couponModel = CouponModel.builder().id(id).build();
        couponModel.setIsAvailable(false);
        CouponModel isSuccess = couponDAO.update(couponModel);
        System.out.println(isSuccess);

        if (isSuccess == null) {
            log.info(CLASS_NAME, "Có lỗi xảy ra khi cập nhật coupon id: " + id);
            request.setAttribute("message", "Có lỗi xảy ra");
        } else {
            log.info(CLASS_NAME, "Cập nhật coupon thành công id: " + id);
            request.setAttribute("message", "Cập nhật thành công id: " + id);
            this.displayCoupon(request, response);
        }
    }

    @Override
    public void startBuying(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        //tến hành cập nhật trạng thái sản phẩm
        CouponModel couponModel = CouponModel.builder().id(id).build();
        couponModel.setIsAvailable(true);
        CouponModel isSuccess = couponDAO.update(couponModel);
        if (isSuccess == null) {
            log.info(CLASS_NAME, "Có lỗi xảy ra khi cập nhật coupon id: " + id);
            request.setAttribute("message", "Có lỗi xảy ra");
        } else {
            request.setAttribute("message", "Cập nhật thành công id: " + id);
            log.info(CLASS_NAME, "Cập nhật coupon thành công id: " + id);
            this.displayCoupon(request, response);
        }
    }

    @Override
    public void addCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/coupon/add-coupon.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void updateCoupon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/coupon/add-coupon.jsp");
        Long id = Long.parseLong(request.getParameter("id"));
        CouponModel coupon = couponDAO.getCouponDetail(id);
        System.out.println(coupon);
        if (coupon == null) {
            log.info(CLASS_NAME, "Có lỗi xảy ra khi lấy coupon id: " + id);
            request.setAttribute("message", "Có lỗi xảy ra");
        } else {
            log.info(CLASS_NAME, "Lấy coupon thành công id: " + id);
            request.setAttribute("message", "Lấy coupon thành công id: " + id);
        }
        request.setAttribute("coupon", coupon);
        dispatcher.forward(request, response);
    }


    @Override
    public List<CouponModel> findAvailableCoupons() {
        log.info(CLASS_NAME, "Lấy danh sách coupon khả dụng");
        String sql = "SELECT * FROM coupon WHERE is_available = 1 AND end_date >= NOW()";
        List<CouponModel> availableCoupons = couponDAO.query(sql, CouponModel.class);
        if (availableCoupons == null || availableCoupons.isEmpty()) {
            log.info(CLASS_NAME, "Không có coupon khả dụng");
            return List.of();
        } else {
            log.info(CLASS_NAME, "Có " + availableCoupons.size() + " coupon khả dụng");
            return availableCoupons;
        }
    }
}
