package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.common.OrderStatus;
import vn.edu.hcmuaf.fit.myphamstore.common.PaymentMethod;
import vn.edu.hcmuaf.fit.myphamstore.dao.IOrderDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.OrderDetailModel;
import vn.edu.hcmuaf.fit.myphamstore.model.OrderModel;

import java.time.LocalDateTime;
import java.util.List;
@ApplicationScoped
public class OrderDAOImpl implements IOrderDAO {
    @Override
    public Long saveOrder(OrderModel orderModel) {
        String sql = "INSERT INTO orders (address_id, user_id, status, shipping_fee, note, payment_method, total_price, order_date, confirmed_at) " +
                "VALUES(:addressId, :userId, :status, :shippingFee, :note, :paymentMethod, :totalPrice, :orderDate, :confirmedAt)";
        try{
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createUpdate(sql)
                    .bind("addressId", orderModel.getAddressId())
                    .bind("userId", orderModel.getUserId())
                    .bind("status", orderModel.getStatus() == null ? OrderStatus.PENDING.name() : orderModel.getStatus().name())
                    .bind("shippingFee", orderModel.getShippingFee() == null ? 0 : orderModel.getShippingFee())
                    .bind("note", orderModel.getNote() == null ? "" : orderModel.getNote())
                    .bind("paymentMethod", orderModel.getPaymentMethod() == null ? PaymentMethod.COD : orderModel.getPaymentMethod())
                    .bind("totalPrice", orderModel.getTotalPrice() == null ? 0 : orderModel.getTotalPrice())
                    .bind("orderDate", orderModel.getOrderDate() == null ? LocalDateTime.now() : orderModel.getOrderDate())
                    .bind("confirmedAt", orderModel.getConfirmedAt() )
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Long.class)
                    .one());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrderDetail(OrderDetailModel orderDetailModel) {
        String sql = "INSERT INTO order_details (order_id, product_id, quantity, total_price) " +
                "VALUES(:orderId, :productId, :quantity, :totalPrice)";
        try{
            JDBIConnector.getJdbi().withHandle(handle -> handle.createUpdate(sql)
                    .bind("orderId", orderDetailModel.getOrderId())
                    .bind("productId", orderDetailModel.getProductId())
                    .bind("quantity", orderDetailModel.getQuantity())
                    .bind("totalPrice", orderDetailModel.getTotalPrice())
                    .execute());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrder(OrderModel orderModel) {

        String sql = "UPDATE orders SET " +
                "address_id = :addressId, " +
                "user_id = :userId, " +
                "status = :status, " +
                "shipping_fee = :shippingFee, " +
                "note = :note, " +
                "payment_method = :paymentMethod, " +
                "total_price = :totalPrice, " +
                "order_date = :orderDate, " +
                "confirmed_at = :confirmedAt WHERE id = :id";
        try{
            JDBIConnector.getJdbi().withHandle(handle -> handle.createUpdate(sql)
                    .bind("addressId", orderModel.getAddressId())
                    .bind("userId", orderModel.getUserId())
                    .bind("status", orderModel.getStatus() == null ? OrderStatus.PENDING.name() : orderModel.getStatus().name())
                    .bind("shippingFee", orderModel.getShippingFee() == null ? 0 : orderModel.getShippingFee())
                    .bind("note", orderModel.getNote() == null ? "" : orderModel.getNote())
                    .bind("paymentMethod", orderModel.getPaymentMethod() == null ? PaymentMethod.COD : orderModel.getPaymentMethod())
                    .bind("totalPrice", orderModel.getTotalPrice() == null ? 0 : orderModel.getTotalPrice())
                    .bind("orderDate", orderModel.getOrderDate() == null ? LocalDateTime.now() : orderModel.getOrderDate())
                    .bind("confirmedAt", orderModel.getConfirmedAt()  == null ? LocalDateTime.now() : orderModel.getConfirmedAt())
                    .bind("id", orderModel.getId())
                    .execute());
        }   catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderModel> findOrderByUserId(Long userId) {
        String sql = "SELECT * FROM orders WHERE user_id = :userId";
        try{
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("userId", userId)
                    .mapToBean(OrderModel.class)
                    .list());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderModel findOrderById(Long orderId) {
        String sql  = "SELECT * FROM orders WHERE id = :orderId";
        try{
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("orderId", orderId)
                    .mapToBean(OrderModel.class)
                    .findFirst()
                    .orElse(null));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderDetailModel> findOrderDetailByOrderId(Long orderId) {
        String sql = "SELECT * FROM order_details WHERE order_id = :orderId";
        try{
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("orderId", orderId)
                    .mapToBean(OrderDetailModel.class)
                    .list());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateOrderDetail(OrderDetailModel orderDetailModel) {
        String sql = "UPDATE order_details SET " +
                "order_id = :orderId, " +
                "product_id = :productId, " +
                "quantity = :quantity, " +
                "total_price = :totalPrice WHERE id = :id";
        try{
            JDBIConnector.getJdbi().withHandle(handle -> handle.createUpdate(sql)
                    .bind("orderId", orderDetailModel.getOrderId())
                    .bind("productId", orderDetailModel.getProductId())
                    .bind("quantity", orderDetailModel.getQuantity())
                    .bind("totalPrice", orderDetailModel.getTotalPrice())
                    .bind("id", orderDetailModel.getId())
                    .execute());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void changeStatus(Long orderId, OrderStatus status) {
        String sql = "UPDATE orders SET status = :status WHERE id = :orderId";
        try {
            int rowsAffected = JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind("status", status.name())
                            .bind("orderId", orderId)
                            .execute()
            );
            if (rowsAffected == 0) {
                throw new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage(), e);
        }
    }

    @Override
    public Long countAllProducts() {
        String query = "SELECT COUNT(*) FROM orders";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(query)
                    .mapTo(Long.class)
                    .one());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderModel> findByUserId(Long userId, int currentPage, int pageSize) {
        String sql = "SELECT * FROM `orders` WHERE user_id = :userId LIMIT :limit OFFSET :offset";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("userId", userId)
                    .bind("limit", pageSize)
                    .bind("offset", (currentPage - 1) * pageSize)
                    .mapToBean(OrderModel.class)
                    .list());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long save(OrderModel entity) {
        return 0L;
    }

    @Override
    public OrderModel update(OrderModel entity) {
        return null;
    }

    @Override
    public void delete(OrderModel entity) {

    }

    @Override
    public List<OrderModel> findAll(String keyword, int currentPage, int pageSize, String orderBy) {
        // Sàng lọc dữ liệu đầu vào
        if (currentPage < 1) currentPage = 1;
        // Tránh SQL Injection bằng cách kiểm tra cột hợp lệ
        if (orderBy == null || !List.of("id", "address_id", "user_id", "status", "shipping_fee", "note", "payment_method", "total_price", "order_date", "confirmed_at").contains(orderBy)) {
            orderBy = "order_date, status";
        }
        // Xây dựng câu lệnh SQL
        String sql = "SELECT * FROM orders ";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += "WHERE id LIKE :keyword OR address_id LIKE :keyword OR user_id LIKE :keyword OR status LIKE :keyword OR shipping_fee LIKE :keyword OR note LIKE :keyword OR payment_method LIKE :keyword OR total_price LIKE :keyword OR order_date LIKE :keyword OR confirmed_at LIKE :keyword ";
        }
        sql += "ORDER BY " + orderBy + " LIMIT :limit OFFSET :offset";
        // Sử dụng JDBI để thực hiện truy vấn
        int finalCurrentPage = currentPage;
        String finalSql = sql;
        List<OrderModel> orders = JDBIConnector.getJdbi().withHandle(handle -> {
            // Tạo truy vấn và gán các tham số
            var query = handle.createQuery(finalSql)
                    .bind("limit", pageSize)
                    .bind("offset", (finalCurrentPage - 1) * pageSize);

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.bind("keyword", "%" + keyword + "%");
            }

            // Ánh xạ kết quả truy vấn thành đối tượng OrderModel
            return query.mapToBean(OrderModel.class).list();
        });

        return orders;
    }

    @Override
    public Long getTotalPage(int numOfItems) {
        String query = "SELECT COUNT(*) FROM orders";

        try {
            // Dùng withHandle để thực hiện câu lệnh SQL
            Long totalOrder = JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createQuery(query)
                        .mapTo(Long.class)  // Ánh xạ kết quả thành kiểu Long
                        .one();  // Chỉ lấy một kết quả duy nhất
            });
            // Tính toán số trang
            if (totalOrder != null) {
                long countPage = totalOrder / numOfItems;
                if (countPage % numOfItems != 0) {
                    countPage++;
                }
                return countPage;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
