package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.common.PasswordUtils;
import vn.edu.hcmuaf.fit.myphamstore.dao.IProductDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.BrandModel;
import vn.edu.hcmuaf.fit.myphamstore.model.CategoryModel;
import vn.edu.hcmuaf.fit.myphamstore.model.ProductModel;
import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
@ApplicationScoped
public class ProductDAOImpl implements IProductDAO {
    @Override
    public ProductModel getProductDetail(Long id) {
        String sql = "select * from product where id=?";
        try{
            return JDBIConnector.getJdbi().withHandle(h-> h.select(sql, id).mapToBean(ProductModel.class).one());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductModel> getProductsByCategory(Long categoryId) {
        String sql = "SELECT * FROM product WHERE category_id = :categoryId";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("categoryId", categoryId)
                        .mapToBean(ProductModel.class)
                        .list()
        );
    }

    @Override
    public List<ProductModel> getLatestProductsByCategory(Long categoryId, int limit) {
        String sql = "SELECT * FROM product WHERE category_id = :categoryId ORDER BY created_at DESC LIMIT :limit";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("categoryId", categoryId)
                        .bind("limit", limit)
                        .mapToBean(ProductModel.class)
                        .list()
        );
    }

    @Override
    public Long save(ProductModel entity) {
        return 0L;
    }

    @Override
    public ProductModel update(ProductModel entity) {
        // Kiểm tra sự tồn tại của người dùng
        ProductModel productModel = this.getProductDetail(entity.getId());
        if (productModel == null) return null;

        // SQL để cập nhật thông tin người dùng
        String sql = "UPDATE product SET name = :name, price = :price, cost_price = :costPrice, stock = :stock, " +
                "sold_quantity = :soldQuantity, description = :description, is_available = :isAvailable, thumbnail = :thumbnail, brand_id = :brandId," +
                "category_id = :categoryId, created_at = :createdAt, updated_at = :updatedAt WHERE id = :id";

        try {
            int result = JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createUpdate(sql)
                        .bind("name", entity.getName() != null ? entity.getName().trim() : productModel.getName())
                        .bind("price", entity.getPrice() != null ? entity.getPrice() : productModel.getPrice())
                        .bind("costPrice", entity.getCostPrice() != null ? entity.getCostPrice() : productModel.getCostPrice())
                        .bind("stock", entity.getStock() != null ? entity.getStock() : productModel.getStock())
                        .bind("soldQuantity", entity.getSoldQuantity() != null ? entity.getSoldQuantity() : productModel.getSoldQuantity())
                        .bind("description", entity.getDescription() != null ? entity.getDescription().trim() : productModel.getDescription())
                        .bind("isAvailable", entity.getIsAvailable() != null ? entity.getIsAvailable() : productModel.getIsAvailable())
                        .bind("thumbnail", entity.getThumbnail() != null ? entity.getThumbnail().trim() : productModel.getThumbnail())
                        .bind("brandId", entity.getBrandId() != null ? entity.getBrandId() : productModel.getBrandId())
                        .bind("categoryId", entity.getCategoryId() != null ? entity.getCategoryId() : productModel.getCategoryId())
                        .bind("createdAt", productModel.getCreatedAt())
                        .bind("updatedAt", new Timestamp(System.currentTimeMillis()))
                        .bind("id", productModel.getId())
                        .execute(); // Thực thi câu lệnh UPDATE và trả về số bản ghi bị ảnh hưởng
            });

            if (result > 0) {
                return entity; // Nếu có bản ghi bị cập nhật, trả về đối tượng người dùng đã cập nhật
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Nếu không có bản ghi nào được cập nhật
    }

    @Override
    public void delete(ProductModel entity) {

    }
    @Override
    public List<ProductModel> getAllProducts() {
        String sql = "SELECT * FROM product";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql).mapToBean(ProductModel.class).list()
        );
    }

    @Override
    public List<ProductModel> findVariantsByProductId(Long productId) {
        String sql = "SELECT * FROM product_variant WHERE product_id = :productId";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("product_id", productId)
                        .mapToBean(ProductModel.class)
                        .list()
        );
    }
    @Override
    public List<ProductModel> getLatestProducts() {
        String sql = "SELECT * FROM product ORDER BY created_at DESC LIMIT 5";
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(ProductModel.class)
                        .list()
        );
    }

    @Override
    public Long countAllProducts() {
        String query = "SELECT COUNT(*) FROM product";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createQuery(query)
                        .mapTo(Long.class)
                        .one();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public List<ProductModel> findAll(String keyword, int currentPage, int pageSize, String orderBy) {
        // Sàng lọc dữ liệu đầu vào
        if (currentPage < 1) currentPage = 1;

        // Tránh SQL Injection bằng cách kiểm tra cột hợp lệ
        List<String> allowedColumns = Arrays.asList("id", "name", "price", "stock", "soldQuantity", "description", "isAvailable", "thumbnail", "created_at", "updated_at", "categoryId", "brandId");
        if (!allowedColumns.contains(orderBy)) {
            orderBy = "id";
        }

        // Xây dựng câu lệnh SQL
        String sql = "SELECT * FROM product ";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += "WHERE CONCAT(id, name, price, stock, sold_quantity, description, is_available, thumbnail,  category_id, brand_id,created_at, updated_at) LIKE :keyword ";
        }
        sql += "ORDER BY " + orderBy + " " +
                "LIMIT :limit " +
                "OFFSET :offset";
        // Sử dụng JDBI để thực hiện truy vấn
        int finalCurrentPage = currentPage;
        String finalSql = sql;


        List<ProductModel> products = JDBIConnector.getJdbi().withHandle(handle -> {
            // Tạo truy vấn và gán các tham số
            var query = handle.createQuery(finalSql)
                    .bind("limit", pageSize)
                    .bind("offset", (finalCurrentPage - 1) * pageSize);

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.bind("keyword", "%" + keyword + "%");
            }

            // Ánh xạ kết quả truy vấn thành đối tượng UserModel
            return query.mapToBean(ProductModel.class).list();
        });
        return products;
    }

    @Override
    public Long getTotalPage(int numOfItems) {
        String query = "SELECT COUNT(*) FROM product";

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
