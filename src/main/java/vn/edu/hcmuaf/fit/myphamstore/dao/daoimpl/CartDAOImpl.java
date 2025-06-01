package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.dao.ICartDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.CartHeaderModel;
import vn.edu.hcmuaf.fit.myphamstore.model.CartModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.rmi.server.LogStream.log;

public class CartDAOImpl implements ICartDAO {
    @Override
    public Long save(CartModel entity) {
        String sql = "INSERT INTO cart_item (cart_id,  product_id, variant_id,quantity, created_at, updated_at) " +
                "VALUES (:cart_id, :product_id, :variant_id,:quantity, :createdAt, :updatedAt)";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> {
                // Thực hiện câu lệnh INSERT và lấy id tự động sinh
                return handle.createUpdate(sql)
                        .bind("cart_id",entity.getCardId())
                        .bind("product_id", entity.getProductId())
                        .bind("variant_id", entity.getVariantId())
                        .bind("quantity", entity.getQuantity())
                        .bind("createdAt", LocalDateTime.now())
                        .bind("updatedAt", LocalDateTime.now())
                        .executeAndReturnGeneratedKeys("id") // Lấy giá trị khóa chính tự động sinh
                        .mapTo(Long.class) // Ánh xạ giá trị trả về thành Long
                        .one();
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CartModel update(CartModel entity) {
        CartModel cartModel = findCartById(entity.getId());
        String sql = "UPDATE cart_item SET product_id = :product_id, variant_id = :variant_id,quantity = :quantity , updated_at = :updatedAt WHERE id = :id";
        try {
            int result = JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createUpdate(sql)
                        .bind("product_id", entity.getProductId() == null ? cartModel.getProductId() : entity.getProductId())
                        .bind("variant_id", entity.getVariantId() == null ? cartModel.getVariantId() : entity.getVariantId())
                        .bind("quantity", entity.getQuantity() == null ? cartModel.getQuantity() : entity.getQuantity())
                        .bind("updatedAt", LocalDateTime.now())
                        .bind("id", entity.getId())
                        .execute();
            });

            if(result > 0){
                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private CartModel findCartById(Long id) {
        String query = "SELECT * FROM cart_item WHERE id = :id";
        try {
            CartModel result = JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(query)
                    .bind("id", id)
                    .mapToBean(CartModel.class)
                    .one());
            return result;
        } catch (Exception e) {
            log("cart not found");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(CartModel entity) {

    }

    @Override
    public List<CartModel> findAll(String keyword, int currentPage, int pageSize, String orderBy) {
        // Sàng lọc dữ liệu đầu vào
        if (currentPage < 1) currentPage = 1;

        // Tránh SQL Injection bằng cách kiểm tra cột hợp lệ
        List<String> allowedColumns = Arrays.asList("id", "name", "logo","is_available","created_at", "updated_at");
        if (!allowedColumns.contains(orderBy)) {
            orderBy = "id";
        }

        // Xây dựng câu lệnh SQL
        String sql = "SELECT * FROM brand ";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += "WHERE CONCAT(id, name, logo,is_available, created_at, updated_at) LIKE :keyword ";
        }
        sql += "ORDER BY " + orderBy + " " +
                "LIMIT :limit " +
                "OFFSET :offset";
        // Sử dụng JDBI để thực hiện truy vấn
        int finalCurrentPage = currentPage;
        String finalSql = sql;


        List<CartModel> cartModels = JDBIConnector.getJdbi().withHandle(handle -> {
            // Tạo truy vấn và gán các tham số
            var query = handle.createQuery(finalSql)
                    .bind("limit", pageSize)
                    .bind("offset", (finalCurrentPage - 1) * pageSize);

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.bind("keyword", "%" + keyword + "%");
            }

            // Ánh xạ kết quả truy vấn thành đối tượng UserModel
            return query.mapToBean(CartModel.class).list();
        });
        return cartModels;
    }

    @Override
    public Long getTotalPage(int numOfItems) {
        String query = "SELECT COUNT(*) FROM brand";

        try {
            // Dùng withHandle để thực hiện câu lệnh SQL
            Long totalUser = JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createQuery(query)
                        .mapTo(Long.class)  // Ánh xạ kết quả thành kiểu Long
                        .one();  // Chỉ lấy một kết quả duy nhất
            });

            // Tính toán số trang
            if (totalUser != null) {
                long countPage = totalUser / numOfItems;
                if (totalUser % numOfItems != 0) {
                    countPage++;
                }
                return countPage;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Long createCartForUser(Long userId) {
        String sql = "INSERT INTO cart (user_id) VALUES (:user_id)";
        try {
            return JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind("user_id", userId)
                            .executeAndReturnGeneratedKeys("id") // Lấy id tự động sinh
                            .mapTo(Long.class)
                            .one()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CartHeaderModel getCartByUserId(Long userId) {
        String query = "SELECT * FROM cart WHERE id = :id";
        try {
            CartHeaderModel result = JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(query)
                    .bind("id", userId)
                    .mapToBean(CartHeaderModel.class)
                    .one());
            return result;
        } catch (Exception e) {
            log("cart not found");
            e.printStackTrace();
        }
        return null;
    }
}
