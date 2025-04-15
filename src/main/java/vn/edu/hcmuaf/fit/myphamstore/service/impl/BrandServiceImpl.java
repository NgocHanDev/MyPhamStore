package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.model.BrandModel;
import vn.edu.hcmuaf.fit.myphamstore.service.IBrandService;
import vn.edu.hcmuaf.fit.myphamstore.dao.IBrandDAO;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.io.IOException;
import java.util.List;

public class BrandServiceImpl implements IBrandService {
    @Inject
    private IBrandDAO brandDAO;
    @Inject
    private LoggingService logger;

    private static final String LOGGER_NAME = "BRAND-SERVICE";

    @Override
    public BrandModel findBrandById(Long id) {
        logger.info(LOGGER_NAME, "Tìm thương hiệu với ID: " + id);
        BrandModel brand = brandDAO.findBrandById(id);
        if (brand == null) {
            logger.warn(LOGGER_NAME, "Không tìm thấy thương hiệu với ID: " + id);
        } else {
            logger.info(LOGGER_NAME, "Tìm thấy thương hiệu: " + brand.getName());
        }
        return brand;
    }

    @Override
    public List<BrandModel> getBrandsWithPaging(String keyword, int currentPage, int pageSize, String orderBy) {
        logger.info(LOGGER_NAME, "Lấy danh sách thương hiệu, từ khóa: " + (keyword != null ? keyword : "không có") +
                ", trang: " + currentPage + ", kích thước: " + pageSize + ", sắp xếp: " + orderBy);
        if (keyword != null && !keyword.isEmpty()) {
            keyword = keyword.trim();
            logger.info(LOGGER_NAME, "Chuẩn hóa từ khóa: " + keyword);
        }
        List<BrandModel> brands = brandDAO.findAll(keyword, currentPage, pageSize, orderBy);
        logger.info(LOGGER_NAME, "Tìm thấy " + brands.size() + " thương hiệu");
        return brands;
    }

    @Override
    public Long getTotalPage(int numOfItem) {
        logger.info(LOGGER_NAME, "Tính tổng số trang với kích thước: " + numOfItem);
        Long totalPages = brandDAO.getTotalPage(numOfItem);
        logger.info(LOGGER_NAME, "Tổng số trang: " + totalPages);
        return totalPages;
    }

    @Override
    public List<BrandModel> getAllBrands() {
        logger.info(LOGGER_NAME, "Lấy tất cả thương hiệu");
        List<BrandModel> brands = brandDAO.getAllBrands();
        logger.info(LOGGER_NAME, "Tìm thấy " + brands.size() + " thương hiệu");
        return brands;
    }

    @Override
    public void displayBrand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(LOGGER_NAME, "Hiển thị trang quản lý thương hiệu");
        String keyword = request.getParameter("keyword");
        String orderBy = request.getParameter("orderBy");
        int currentPage;
        int pageSize;
        try {
            currentPage = Integer.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage"));
            pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "5" : request.getParameter("pageSize"));
            logger.info(LOGGER_NAME, "Tham số: trang=" + currentPage + ", kích thước=" + pageSize + ", từ khóa=" + keyword + ", sắp xếp=" + orderBy);
        } catch (NumberFormatException e) {
            logger.error(LOGGER_NAME, "Lỗi định dạng tham số: " + e.getMessage());
            request.setAttribute("errorMessage", "Tham số không hợp lệ");
            request.getRequestDispatcher("/admin/brand/brand-management.jsp").forward(request, response);
            return;
        }

        try {
            List<BrandModel> brands = this.getBrandsWithPaging(keyword, currentPage, pageSize, orderBy);
            Long totalPages = this.brandDAO.getTotalPage(pageSize);

            request.setAttribute("brands", brands);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("keyword", keyword);
            request.setAttribute("orderBy", orderBy);
            logger.info(LOGGER_NAME, "Chuẩn bị hiển thị " + brands.size() + " thương hiệu, tổng số trang: " + totalPages);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/brand/brand-management.jsp");
            dispatcher.forward(request, response);
            logger.info(LOGGER_NAME, "Chuyển hướng đến trang brand-management.jsp");
        } catch (Exception e) {
            logger.error(LOGGER_NAME, "Lỗi khi hiển thị trang thương hiệu: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi khi tải trang thương hiệu");
            request.getRequestDispatcher("/admin/brand/brand-management.jsp").forward(request, response);
        }
    }

    @Override
    public void stopBuying(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
            logger.info(LOGGER_NAME, "Yêu cầu ngừng kinh doanh thương hiệu với ID: " + id);
        } catch (NumberFormatException e) {
            logger.error(LOGGER_NAME, "ID thương hiệu không hợp lệ: " + request.getParameter("id"));
            request.setAttribute("message", "ID không hợp lệ");
            this.displayBrand(request, response);
            return;
        }

        BrandModel brandModel = BrandModel.builder().id(id).isAvailable(false).build();
        BrandModel isSuccess = brandDAO.update(brandModel);
        if (isSuccess == null) {
            logger.error(LOGGER_NAME, "Thất bại khi ngừng kinh doanh thương hiệu ID: " + id);
            request.setAttribute("message", "Có lỗi khi ngừng kinh doanh thương hiệu");
        } else {
            logger.info(LOGGER_NAME, "Ngừng kinh doanh thương hiệu thành công, ID: " + id);
            request.setAttribute("message", "Ngừng kinh doanh thành công ID: " + id);
        }
        this.displayBrand(request, response);
    }

    @Override
    public void startBuying(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
            logger.info(LOGGER_NAME, "Yêu cầu bắt đầu kinh doanh thương hiệu với ID: " + id);
        } catch (NumberFormatException e) {
            logger.error(LOGGER_NAME, "ID thương hiệu không hợp lệ: " + request.getParameter("id"));
            request.setAttribute("message", "ID không hợp lệ");
            this.displayBrand(request, response);
            return;
        }

        BrandModel brandModel = BrandModel.builder().id(id).isAvailable(true).build();
        BrandModel isSuccess = brandDAO.update(brandModel);
        if (isSuccess == null) {
            logger.error(LOGGER_NAME, "Thất bại khi bắt đầu kinh doanh thương hiệu ID: " + id);
            request.setAttribute("message", "Có lỗi khi bắt đầu kinh doanh thương hiệu");
        } else {
            logger.info(LOGGER_NAME, "Bắt đầu kinh doanh thương hiệu thành công, ID: " + id);
            request.setAttribute("message", "Bắt đầu kinh doanh thành công ID: " + id);
        }
        this.displayBrand(request, response);
    }

    @Override
    public void addBrand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(LOGGER_NAME, "Chuyển hướng đến trang thêm thương hiệu mới");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/brand/add-brand.jsp");
        dispatcher.forward(request, response);
        logger.info(LOGGER_NAME, "Hoàn tất chuyển hướng đến add-brand.jsp");
    }

    @Override
    public void updateBrand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
            logger.info(LOGGER_NAME, "Yêu cầu cập nhật thương hiệu với ID: " + id);
        } catch (NumberFormatException e) {
            logger.error(LOGGER_NAME, "ID thương hiệu không hợp lệ: " + request.getParameter("id"));
            request.setAttribute("errorMessage", "ID không hợp lệ");
            request.getRequestDispatcher("/admin/brand/brand-management.jsp").forward(request, response);
            return;
        }

        BrandModel brand = brandDAO.getBrandDetail(id);
        if (brand == null) {
            logger.warn(LOGGER_NAME, "Không tìm thấy thương hiệu để cập nhật, ID: " + id);
            request.setAttribute("errorMessage", "Không tìm thấy thương hiệu");
            request.getRequestDispatcher("/admin/brand/brand-management.jsp").forward(request, response);
            return;
        }

        request.setAttribute("brand", brand);
        logger.info(LOGGER_NAME, "Chuẩn bị dữ liệu thương hiệu ID: " + id + " để cập nhật");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/brand/add-brand.jsp");
        dispatcher.forward(request, response);
        logger.info(LOGGER_NAME, "Chuyển hướng đến trang cập nhật thương hiệu add-brand.jsp");
    }
}