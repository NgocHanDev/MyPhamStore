package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.dao.ICouponDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.CouponModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.rmi.server.LogStream.log;

public class CouponDAOImpl implements ICouponDAO {

    @Override
    public CouponModel getCouponDetail(Long id) {
        String sql = "select * from coupon where id=?";
        try{
            return JDBIConnector.getJdbi().withHandle(h-> h.select(sql, id).mapToBean(CouponModel.class).one());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CouponModel findCouponById(Long id) {
        String query = "SELECT * FROM coupon WHERE id = :id";
        try {
            CouponModel result = JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(query)
                    .bind("id", id)
                    .mapToBean(CouponModel.class)
                    .one());
            return result;
        } catch (Exception e) {
            log("category not found");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CouponModel> findAvailableCoupons() {
        String sql = "SELECT * FROM coupon WHERE is_available = 1 AND start_date <= NOW() AND end_date >= NOW()";
        try {
            return JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createQuery(sql)
                            .mapToBean(CouponModel.class)
                            .list()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    @Override
    public Long save(CouponModel entity) {
        String sql = "INSERT INTO coupon (  code, min_order_value, discount_type,discount_value,max_discount_value,start_date,end_date,current_usage,max_usage, created_at, is_available) " +
                "VALUES (  :code, :min_order_value, :discount_type,:discount_value,:max_discount_value,:start_date,:end_date,:current_usage,:max_usage, :created_at,is_available)";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> {
                // Thực hiện câu lệnh INSERT và lấy id tự động sinh
                return handle.createUpdate(sql)
                        .bind("code", entity.getCode().trim())
                        .bind("min_order_value", entity.getMinOrderValue())
                        .bind("discount_type", entity.getDiscountType().toString())
                        .bind("discount_value", entity.getDiscountValue())
                        .bind("max_discount_value", entity.getMaxDiscountValue())
                        .bind("start_date", entity.getStartDate())
                        .bind("end_date", entity.getEndDate())
                        .bind("current_usage", entity.getCurrentUsage())
                        .bind("max_usage", entity.getMaxUsage())
                        .bind("created_at", LocalDateTime.now())
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
    public CouponModel update(CouponModel entity) {
        CouponModel couponExisted = findCouponById(entity.getId());
        if (couponExisted == null) {
            log("Coupon not found");
            return null;
        }

        String sql = "UPDATE coupon SET  code = :code,min_order_value = :min_order_value,discount_type=:discount_type" +
                ",discount_value=:discount_value,max_discount_value=:max_discount_value,start_date=:start_date,end_date=:end_date,current_usage=:current_usage" +
                ",max_usage=:max_usage, created_at=:created_at,is_available = :isAvailable WHERE id = :id";
        try {
            int result = JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createUpdate(sql)
                        .bind("code", entity.getCode() == null ? couponExisted.getCode() : entity.getCode().trim())
                        .bind("min_order_value", entity.getMinOrderValue() == null ? couponExisted.getMinOrderValue() : entity.getMinOrderValue())
                        .bind("discount_type", entity.getDiscountType() == null ? couponExisted.getDiscountType().toString() : entity.getDiscountType().toString())
                        .bind("discount_value", entity.getDiscountValue() == null ? couponExisted.getDiscountValue() : entity.getDiscountValue())
                        .bind("max_discount_value", entity.getMaxDiscountValue() == null ? couponExisted.getMaxDiscountValue() : entity.getMaxDiscountValue())
                        .bind("start_date", entity.getStartDate() == null ? couponExisted.getStartDate() : entity.getStartDate())
                        .bind("end_date", entity.getEndDate() == null ? couponExisted.getEndDate() : entity.getEndDate())
                        .bind("current_usage", entity.getCurrentUsage() == null ? couponExisted.getCurrentUsage() : entity.getCurrentUsage())
                        .bind("max_usage", entity.getMaxUsage() == null ? couponExisted.getMaxUsage() : entity.getMaxUsage())
                        .bind("created_at", couponExisted.getCreatedAt())
                        .bind("isAvailable", entity.getIsAvailable() == null ? couponExisted.getIsAvailable() : entity.getIsAvailable())
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
    public void delete(CouponModel entity) {

    }

    @Override
    public List<CouponModel> findAll(String keyword, int currentPage, int pageSize, String orderBy) {
        // Sàng lọc dữ liệu đầu vào
        if (currentPage < 1) currentPage = 1;

        // Tránh SQL Injection bằng cách kiểm tra cột hợp lệ
        List<String> allowedColumns = Arrays.asList("id", "brand_id", "code", "min_order_value", "discount_type","discount_value","max_discount_value","start_date","end_date","current_usage","created_at","is_available");
        if (!allowedColumns.contains(orderBy)) {
            orderBy = "id";
        }

        // Xây dựng câu lệnh SQL
        String sql = "SELECT * FROM coupon ";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += "WHERE CONCAT(id, code, min_order_value, discount_type,discount_value,max_discount_value,start_date,end_date,current_usage,max_usage, created_at, is_available) LIKE :keyword ";
        }
        sql += "ORDER BY " + orderBy + " " +
                "LIMIT :limit " +
                "OFFSET :offset";
        // Sử dụng JDBI để thực hiện truy vấn
        int finalCurrentPage = currentPage;
        String finalSql = sql;


        List<CouponModel> couponModels = JDBIConnector.getJdbi().withHandle(handle -> {
            // Tạo truy vấn và gán các tham số
            var query = handle.createQuery(finalSql)
                    .bind("limit", pageSize)
                    .bind("offset", (finalCurrentPage - 1) * pageSize);

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.bind("keyword", "%" + keyword + "%");
            }

            // Ánh xạ kết quả truy vấn thành đối tượng UserModel
            return query.mapToBean(CouponModel.class).list();
        });
        return couponModels;
    }

    @Override
    public Long getTotalPage(int numOfItems) {
        String query = "SELECT COUNT(*) FROM coupon";

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
    public CouponModel findCouponByCode(String code) {
        String sql = "SELECT * FROM coupon WHERE code = :code";
        try {
            return JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createQuery(sql)
                            .bind("code", code)
                            .mapToBean(CouponModel.class)
                            .one()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public int getRemainingQuantity(String code) {
        String sql = "SELECT max_usage - current_usage FROM coupon WHERE code = :code";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("code", code)
                    .mapTo(Integer.class)
                    .one());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public double getDiscount(String code) {
        String sql = "SELECT discount_value FROM coupon WHERE code = :code";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("code", code)
                    .mapTo(Double.class)
                    .one());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
