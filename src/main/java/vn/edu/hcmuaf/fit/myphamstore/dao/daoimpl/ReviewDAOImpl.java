package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.dao.IReviewDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.ReviewModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.rmi.server.LogStream.log;

public class ReviewDAOImpl implements IReviewDAO {
    @Override
    public ReviewModel findReviewById(Long id) {
        String query = "SELECT * FROM review WHERE id = :id";
        try {
            ReviewModel result = JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(query)
                    .bind("id", id)
                    .mapToBean(ReviewModel.class)
                    .one());
            return result;
        } catch (Exception e) {
            log("category not found");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReviewModel getReviewDetail(Long id) {
        String sql = "select * from review where id=?";
        try{
            return JDBIConnector.getJdbi().withHandle(h-> h.select(sql, id).mapToBean(ReviewModel.class).one());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReviewModel> getAllReviewsByProductId(Long id) {
        String sql = "SELECT * FROM review WHERE product_id = :id order by created_at desc";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("id", id)
                    .mapToBean(ReviewModel.class)
                    .list());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Long save(ReviewModel entity) {
        String sql = "INSERT INTO review ( user_id, product_id,rating,comment, created_at, updated_at) " +
                "VALUES ( :userId, :productId,:rating,:comment, :createdAt, :updatedAt)";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> {
                // Thực hiện câu lệnh INSERT và lấy id tự động sinh
                return handle.createUpdate(sql)
                        .bind("user_id", entity.getUserId())
                        .bind("product_id", entity.getProductId())
                        .bind("rating", entity.getRating())
                        .bind("comment", entity.getComment())
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
    public Long saveReview(ReviewModel entity, Long userId, Long productId) {
        String sql = "INSERT INTO review (user_id, product_id, rating, comment, created_at, updated_at) " +
                "VALUES (:userId, :productId, :rating, :comment, :createdAt, :updatedAt)";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createUpdate(sql)
                        .bind("userId", userId)  // Đúng tên tham số trong SQL
                        .bind("productId", productId)  // Đúng tên tham số trong SQL
                        .bind("rating", entity.getRating())
                        .bind("comment", entity.getComment())
                        .bind("createdAt", LocalDateTime.now())
                        .bind("updatedAt", LocalDateTime.now())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Long.class)
                        .one();
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ReviewModel update(ReviewModel entity) {
        ReviewModel reviewExisted = findReviewById(entity.getId());
        if (reviewExisted == null) {
            log("Contact not found");
            return null;
        }


        String sql = "UPDATE review SET user_id = :userId, product_id = :productId,rating = :rating,comment=:comment , updated_at = :updatedAt WHERE id = :id";
        try {
            int result = JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createUpdate(sql)
                        .bind("userId", entity.getUserId())
                        .bind("productId", entity.getProductId())
                        .bind("rating", entity.getRating() == null ? reviewExisted.getRating() : entity.getRating())
                        .bind("comment", entity.getComment()== null ? reviewExisted.getComment() : entity.getComment())
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

    @Override
    public void delete(ReviewModel entity) {

    }

    @Override
    public List<ReviewModel> findAll(String keyword, int currentPage, int pageSize, String orderBy) {
        // Sàng lọc dữ liệu đầu vào
        if (currentPage < 1) currentPage = 1;

        // Tránh SQL Injection bằng cách kiểm tra cột hợp lệ
        List<String> allowedColumns = Arrays.asList("id", "user_id", "product_id","rating","comment","created_at", "updated_at");
        if (!allowedColumns.contains(orderBy)) {
            orderBy = "id";
        }

        // Xây dựng câu lệnh SQL
        String sql = "SELECT * FROM review ";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += "WHERE CONCAT(id, user_id, product_id,rating,comment, created_at, updated_at) LIKE :keyword ";
        }
        sql += "ORDER BY " + orderBy + " " +
                "LIMIT :limit " +
                "OFFSET :offset";
        // Sử dụng JDBI để thực hiện truy vấn
        int finalCurrentPage = currentPage;
        String finalSql = sql;


        List<ReviewModel> reviewModels = JDBIConnector.getJdbi().withHandle(handle -> {
            // Tạo truy vấn và gán các tham số
            var query = handle.createQuery(finalSql)
                    .bind("limit", pageSize)
                    .bind("offset", (finalCurrentPage - 1) * pageSize);

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.bind("keyword", "%" + keyword + "%");
            }

            // Ánh xạ kết quả truy vấn thành đối tượng UserModel
            return query.mapToBean(ReviewModel.class).list();
        });
        return reviewModels;
    }

    @Override
    public Long getTotalPage(int numOfItems) {
        String query = "SELECT COUNT(*) FROM review";

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
}
