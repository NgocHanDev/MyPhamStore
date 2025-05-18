package vn.edu.hcmuaf.fit.myphamstore.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.model.OrderDetailModel;
import vn.edu.hcmuaf.fit.myphamstore.model.OrderModel;
import vn.edu.hcmuaf.fit.myphamstore.model.ProductModel;

import java.io.IOException;
import java.util.List;

public interface IOrderService {
    void displayOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void displayOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    Long getTotalPage(int numOfItems);
    Long getTotalOrder();
    OrderModel findOrderById(Long id);
    List<OrderModel> getOrdersWithPaging(String keyword,int currentPage, int pageSize, String orderBy);

    List<OrderDetailModel> getOrderDetailsByOrderId(Long orderId);
    void update(OrderModel order);


    List<OrderModel> getOrderHistoryByUserId(Long userId, int currentPage, int pageSize);

    void changeStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    List<ProductModel> getProductByOrderDetail(OrderDetailModel orderDetail);

    List<OrderModel> getOrdersByUserId(Long userId);
}
