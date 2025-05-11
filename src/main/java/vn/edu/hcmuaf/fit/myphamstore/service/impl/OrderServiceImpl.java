package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.common.OrderStatus;
import vn.edu.hcmuaf.fit.myphamstore.dao.IAddressDAO;
import vn.edu.hcmuaf.fit.myphamstore.dao.IOrderDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.*;
import vn.edu.hcmuaf.fit.myphamstore.service.IOrderService;
import vn.edu.hcmuaf.fit.myphamstore.service.IProductService;
import vn.edu.hcmuaf.fit.myphamstore.service.IUserService;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class OrderServiceImpl implements IOrderService {
    @Inject
    private IOrderDAO orderDAO;
    @Inject
    private IUserService userService;
    @Inject
    private IAddressDAO addressDAO;
    @Inject
    private IProductService productService;
    @Inject
    private LoggingService log;
    private final String CLASS_NAME = "ORDER-SERVICE";

    @Override
    public void displayOrders(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/order/order-management.jsp");
        String keyword = request.getParameter("keyword");
        String orderBy = request.getParameter("orderBy");
        int currentPage = Integer.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "10" : request.getParameter("pageSize"));

        List<OrderModel> orders = getOrdersWithPaging(keyword, currentPage, pageSize, orderBy);
        Map<OrderModel, UserModel> orderUserMap = new HashMap<>();
        for (OrderModel order : orders) {
            UserModel user = userService.findUserById(order.getUserId());
            orderUserMap.put(order, user);
        }

        Long totalPages = getTotalPage(pageSize);

        log.info(CLASS_NAME, "Lấy danh sách đơn hàng thành công");
        // Gửi danh sách order đến trang JSP
        request.setAttribute("orderUserMap", orderUserMap);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalItems", orderDAO.countAllProducts());
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyword", keyword);
        request.setAttribute("orderBy", orderBy);
        requestDispatcher.forward(request, resp);
    }

    @Override
    public void displayOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/admin/order/order-detail.jsp");
        Long orderId = Long.parseLong(req.getParameter("id"));

        OrderModel order = getOrderDetails(orderId);
        UserModel user = userService.findUserById(order.getUserId());
        AddressModel address = addressDAO.findAddressById(order.getAddressId());
        List<OrderDetailModel> listOrderDetails = getOrderDetailsByOrderId(orderId);
        Map<OrderDetailModel, ProductModel> orderDetails = new HashMap<>();
        for (OrderDetailModel orderDetail : listOrderDetails) {
            ProductModel product = productService.findProductById(orderDetail.getProductId());
            orderDetails.put(orderDetail, product);
        }
        log.info(CLASS_NAME, "Lấy chi tiết đơn hàng thành công");

        req.setAttribute("order", order);
        req.setAttribute("orderDetails", orderDetails);
        req.setAttribute("address", address);
        req.setAttribute("user", user);
        requestDispatcher.forward(req, resp);
    }

    @Override
    public Long getTotalPage(int numOfItems) {
        return orderDAO.getTotalPage(numOfItems);
    }

    @Override
    public Long getTotalOrder() {
        return 0L;
    }

    @Override
    public OrderModel findOrderById(Long id) {
        return null;
    }

    @Override
    public List<OrderModel> getOrdersWithPaging(String keyword, int currentPage, int pageSize, String orderBy) {
        if (keyword != null && !keyword.isEmpty()) {
            keyword = keyword.trim();
        }

        return orderDAO.findAll(keyword, currentPage, pageSize, orderBy);
    }

    @Override
    public List<OrderDetailModel> getOrderDetailsByOrderId(Long orderId) {
        log.info(CLASS_NAME, "Lấy danh sách chi tiết đơn hàng thành công id: " + orderId);
        return orderDAO.findOrderDetailByOrderId(orderId);
    }


    @Override
    public List<OrderModel> getOrderHistoryByUserId(Long userId, int currentPage, int pageSize) {

        return orderDAO.findByUserId(userId, currentPage, pageSize);
    }

    @Override
    public void changeStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info(CLASS_NAME, "Thay đổi trạng thái đơn hàng");
        Long orderId = Long.parseLong(req.getParameter("id"));
        OrderStatus status = OrderStatus.valueOf(req.getParameter("status"));
        orderDAO.changeStatus(orderId, status);
        if(status == OrderStatus.CONFIRMED){
            log.info(CLASS_NAME, "Xác nhận đơn hàng thành công id: " + orderId);
            OrderModel order = orderDAO.findOrderById(orderId);
            order.setConfirmedAt(LocalDateTime.now());
            orderDAO.update(order);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/orders?action=display");
    }

    @Override
    public List<ProductModel> getProductByOrderDetail(OrderDetailModel orderDetail) {
        log.info(CLASS_NAME, "Lấy sản phẩm trong đơn hàng thành công id: " + orderDetail.getId());
        return productService.findProduct(orderDetail.getProductId());
    }
    @Override
    public List<OrderModel> getOrdersByUserId(Long userId) {
        log.info(CLASS_NAME, "Lấy danh sách đơn hàng của người dùng thành công id: " + userId);
        return orderDAO.findOrderByUserId(userId);
    }

    public OrderModel getOrderDetails(Long orderId) {
        log.info(CLASS_NAME, "Lấy chi tiết đơn hàng thành công id: " + orderId);
        return orderDAO.findOrderById(orderId);
    }
}
