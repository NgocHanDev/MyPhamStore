package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.model.OrderDetailModel;
import vn.edu.hcmuaf.fit.myphamstore.model.OrderModel;
import vn.edu.hcmuaf.fit.myphamstore.model.ProductModel;
import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;
import vn.edu.hcmuaf.fit.myphamstore.service.IOrderService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "OrderHistoryController", value = "/order-history")
public class OrderHistoryController extends HttpServlet {
    @Inject
    private IOrderService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long userId = user.getId();
        int currentPage = 1;
        int pageSize = 10;

        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }
        if (request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }

        List<OrderModel> orderHistory = orderService.getOrderHistoryByUserId(userId, currentPage, pageSize);
        Map<Long, List<OrderDetailModel>> orderDetailsMap = new HashMap<>();
        Map<Long, List<ProductModel>> productsMap = new HashMap<>();

        for (OrderModel order : orderHistory) {
            List<OrderDetailModel> orderDetails = orderService.getOrderDetailsByOrderId(order.getId());
            orderDetailsMap.put(order.getId(), orderDetails);

            List<ProductModel> products = new ArrayList<>();
            for (OrderDetailModel orderDetail : orderDetails) {
                List<ProductModel> productList = orderService.getProductByOrderDetail(orderDetail);
                products.addAll(productList);  // Lưu tất cả sản phẩm thay vì ghi đè
            }
            productsMap.put(order.getId(), products);
        }
        request.setAttribute("orderHistory", orderHistory);
        request.setAttribute("orderDetailsMap", orderDetailsMap);
        request.setAttribute("productsMap", productsMap);

        request.getRequestDispatcher("/frontend/history.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getServletPath();
        if ("/order-history".equalsIgnoreCase(action)) {
            try {
                orderService.changeStatus(req, resp);
            }
            catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"message\": \"Có lỗi xảy ra khi hủy đơn hàng.\"}");
            }
        }

    }
}