package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.dao.ICategoryDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.CategoryModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.rmi.server.LogStream.log;

@Slf4j
@ApplicationScoped
public class CategoryDAOImpl implements ICategoryDAO {
    @Override
    public List<CategoryModel> getAllCategories() {
        String sql = "SELECT * FROM category";
        try {
            return JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createQuery(sql).mapToBean(CategoryModel.class).list()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public CategoryModel findCategoryById(Long id) {
        String query = "SELECT * FROM category WHERE id = :id";
        try {
            CategoryModel result = JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(query)
                    .bind("id", id)
                    .mapToBean(CategoryModel.class)
                    .one());
            return result;
        } catch (Exception e) {
            log("category not found");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long save(CategoryModel entity) {

        String sql = "INSERT INTO category ( parent_id, name, is_available, created_at, updated_at) " +
            "VALUES (:parent_id, :name, :is_available, :createdAt, :updatedAt)";
            try {
            return JDBIConnector.getJdbi().withHandle(handle -> {
            // Thực hiện câu lệnh INSERT và lấy id tự động sinh
            return handle.createUpdate(sql)
                    .bind("parent_id", entity.getParentId())
                    .bind("name", entity.getName().trim())
                    .bind("is_available", entity.getIsAvailable())
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
    public CategoryModel update(CategoryModel entity) {
    CategoryModel categoryExisted = findCategoryById(entity.getId());
        if (categoryExisted == null) {
        log("Contact not found");
            return null;
    }
        System.out.println(categoryExisted);

    String sql = "UPDATE category SET parent_id = :parent_id, name = :name, is_available = :is_available , updated_at = :updatedAt WHERE id = :id";
        try {
        int result = JDBIConnector.getJdbi().withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind("parent_id", entity.getParentId() == null ? categoryExisted.getParentId() : entity.getParentId())
                    .bind("name", entity.getName() == null ? categoryExisted.getName() : entity.getName().trim())
                    .bind("is_available", entity.getIsAvailable() == null ? categoryExisted.getIsAvailable() : entity.getIsAvailable())
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
    public void delete(CategoryModel entity) {

    }



    @Override
    public List<CategoryModel> findAll(String keyword, int currentPage, int pageSize, String orderBy) {
        // Sàng lọc dữ liệu đầu vào
        if (currentPage < 1) currentPage = 1;

        // Tránh SQL Injection bằng cách kiểm tra cột hợp lệ
        List<String> allowedColumns = Arrays.asList("id", "parent_id", "name","description","created_at", "updated_at");
        if (!allowedColumns.contains(orderBy)) {
            orderBy = "id";
        }

        // Xây dựng câu lệnh SQL
        String sql = "SELECT * FROM category ";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += "WHERE CONCAT(id, parent_id, name, description, created_at, updated_at) LIKE :keyword ";
        }
        sql += "ORDER BY " + orderBy + " " +
                "LIMIT :limit " +
                "OFFSET :offset";
        // Sử dụng JDBI để thực hiện truy vấn
        int finalCurrentPage = currentPage;
        String finalSql = sql;


        List<CategoryModel> categoryModels = JDBIConnector.getJdbi().withHandle(handle -> {
            // Tạo truy vấn và gán các tham số
            var query = handle.createQuery(finalSql)
                    .bind("limit", pageSize)
                    .bind("offset", (finalCurrentPage - 1) * pageSize);

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.bind("keyword", "%" + keyword + "%");
            }

            // Ánh xạ kết quả truy vấn thành đối tượng UserModel
            return query.mapToBean(CategoryModel.class).list();
        });
        return categoryModels;
    }

    @Override
    public List<CategoryModel> findAll(String keyword, int currentPage, int pageSize, String orderBy, List<Long> selectedBrands) {
        return null;
    }

    @Override
    public Long getTotalPage(int numOfItems) {
        String query = "SELECT COUNT(*) FROM category";

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
