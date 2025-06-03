package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.myphamstore.common.*;
import vn.edu.hcmuaf.fit.myphamstore.dao.IUserDAO;
import vn.edu.hcmuaf.fit.myphamstore.exception.UserNotActiveException;
import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;



@Slf4j
@ApplicationScoped
public class UserDAOImp implements IUserDAO {
    private final Jdbi jdbi = JDBIConnector.getJdbi();
    @Override
    public Long save(UserModel entity) {
        String sql = "INSERT INTO user (email, full_name, phone, date_of_birth, gender, status, created_at, updated_at, avatar, password, last_login) " +
                "VALUES (:email, :fullName, :phone, :dateOfBirth, :gender, :status, :createdAt, :updatedAt, :avatar, :password, :lastLogin)";

        try {
            return JDBIConnector.getJdbi().withHandle(handle -> {
                // Hash password trước khi lưu
                System.out.println("Password before hash: " + entity.getPassword());
                String hashedPassword = PasswordUtils.hashPassword(entity.getPassword()) ;
                System.out.println("Hashed password: " + hashedPassword);
                // Thực hiện câu lệnh INSERT và lấy id tự động sinh
                return handle.createUpdate(sql)
                        .bind("email", entity.getEmail().trim())
                        .bind("fullName", entity.getFullName().trim())
                        .bind("phone", entity.getPhone().trim())
                        .bind("dateOfBirth", entity.getDateOfBirth())
                        .bind("gender", entity.getGender() == null ? null : entity.getGender().name().toUpperCase())
                        .bind("status", entity.getStatus() == null ? UserStatus.NONE.toString() : entity.getStatus().toString().toUpperCase())
                        .bind("createdAt", LocalDateTime.now())
                        .bind("updatedAt", LocalDateTime.now())
                        .bind("avatar", entity.getAvatar())
                        .bind("password", hashedPassword)
                        .bind("lastLogin", LocalDateTime.now())
                        .executeAndReturnGeneratedKeys("id") // Lấy giá trị khóa chính tự động sinh
                        .mapTo(Long.class) // Ánh xạ giá trị trả về thành Long
                        .one();
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserModel update(UserModel entity) {
        // Kiểm tra sự tồn tại của người dùng
        UserModel user = getUserById(entity.getId());
        if (user == null) return null;

        // SQL để cập nhật thông tin người dùng
        String sql = "UPDATE user SET email = :email, full_name = :fullName, phone = :phone, date_of_birth = :dateOfBirth, " +
                "gender = :gender, status = :status, last_login = :lastLogin, avatar = :avatar, password = :password WHERE id = :id";

        try {
            // Dùng withHandle để quản lý kết nối và giao dịch
            int result = JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createUpdate(sql)
                        .bind("email", entity.getEmail() != null ? entity.getEmail().trim() : user.getEmail())
                        .bind("fullName", entity.getFullName() != null ? entity.getFullName().trim() : user.getFullName())
                        .bind("phone", entity.getPhone() != null ? entity.getPhone().trim() : user.getPhone())
                        .bind("dateOfBirth", entity.getDateOfBirth() != null ? Date.valueOf(entity.getDateOfBirth()) : Date.valueOf(user.getDateOfBirth()))
                        .bind("gender", entity.getGender() != null ? entity.getGender().toString().toUpperCase() : user.getGender().toString().toUpperCase())
                        .bind("status", entity.getStatus() != null ? entity.getStatus().name() : user.getStatus().name())
                        .bind("lastLogin", Timestamp.valueOf(user.getLastLogin()))
                        .bind("avatar", entity.getAvatar() != null ? entity.getAvatar() : user.getAvatar())
                        .bind("password", (entity.getPassword() != null && !entity.getPassword().startsWith("$2a$"))
                                ? PasswordUtils.hashPassword(entity.getPassword())
                                : user.getPassword())
                        .bind("id", entity.getId())
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
    public void delete(UserModel entity) {
        entity.setStatus(UserStatus.NONE);
        update(entity);
    }

    /**
     * phương thức phân tranguser
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<UserModel> findAll(String keyword, int currentPage, int pageSize, String orderBy) {
        // Sàng lọc dữ liệu đầu vào
        if (currentPage < 1) currentPage = 1;

        // Tránh SQL Injection bằng cách kiểm tra cột hợp lệ
        List<String> allowedColumns = Arrays.asList("id", "email", "full_name", "phone", "date_of_birth", "gender", "status", "last_login", "created_at", "updated_at", "avatar");
        if (!allowedColumns.contains(orderBy)) {
            orderBy = "id";
        }

        // Xây dựng câu lệnh SQL
        String sql = "SELECT * FROM user ";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += "WHERE CONCAT(id, email, full_name, phone, date_of_birth, gender, status, last_login, created_at, updated_at, avatar) LIKE :keyword ";
        }
        sql += "ORDER BY " + orderBy + " " +
                "LIMIT :limit " +
                "OFFSET :offset";

        // Sử dụng JDBI để thực hiện truy vấn
        int finalCurrentPage = currentPage;
        String finalSql = sql;
        List<UserModel> users = JDBIConnector.getJdbi().withHandle(handle -> {
            // Tạo truy vấn và gán các tham số
            var query = handle.createQuery(finalSql)
                    .bind("limit", pageSize)
                    .bind("offset", (finalCurrentPage - 1) * pageSize);

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.bind("keyword", "%" + keyword + "%");
            }

            // Ánh xạ kết quả truy vấn thành đối tượng UserModel
            return query.mapToBean(UserModel.class).list();
        });

        return users;
    }

    @Override
    public boolean checkLogin(String email, String password) {
        String sql = "SELECT password, status FROM user WHERE email = :email"; // Chỉ lấy cột cần thiết
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> {
                UserModel user = handle.createQuery(sql)
                        .bind("email", email)
                        .mapToBean(UserModel.class)
                        .findOne() // Dùng findOne() thay vì one()
                        .orElse(null);
                log.info(user.toString());
                // Kiểm tra xem user có tồn tại không
                if (user == null) {
                    return false; // Email không tồn tại
                }

                // Kiểm tra trạng thái tài khoản
                if (user.getStatus() == null || user.getStatus() == UserStatus.NONE) {
                    throw new UserNotActiveException("Vui lòng kiểm tra email và xác nhận tài khoản!");
                }
                // Kiểm tra trạng thái tài khoản
                if (user.getStatus() == null || user.getStatus() == UserStatus.INACTIVE) {
                    throw new UserNotActiveException("Tài khoản của bạn đã bị khóa !");
                }

                // Kiểm tra mật khẩu
                if (user.getPassword() == null) {
                    return false; // Mật khẩu không hợp lệ
                }

                return PasswordUtils.verifyPassword(password, user.getPassword());
            });
        } catch (UserNotActiveException e) {
            throw e; // Ném lại ngoại lệ để xử lý ở tầng trên
        } catch (Exception e) {
            e.printStackTrace(); // Nên thay bằng logging framework
            return false; // Xử lý lỗi chung
        }
    }


    @Override
    public boolean checkEmailExist(String email) {
        String sql = "SELECT COUNT(email) FROM user WHERE email = :email";

        try {
            // Dùng withHandle để thực thi truy vấn
            return JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createQuery(sql)
                            .bind("email", email)
                            .mapTo(Integer.class)
                            .one() > 0
            );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkPhoneExist(String phone) {
        return false;
    }

    @Override
    public UserModel getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = :email";
        try {
            return JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createQuery(sql)
                            .bind("email", email)
                            .mapToBean(UserModel.class)
                            .one()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public UserModel getUserById(Long id) {
        try {
            UserModel user = JDBIConnector
                    .getJdbi()
                    .withHandle(handle -> {
                        return handle.select("SELECT * FROM user WHERE id = ?", id).mapToBean(UserModel.class).one();
                    });
            return user;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateUserPassword(UserModel user) {
        String sql = "UPDATE user SET password = ? WHERE id = ?";
        try {
            int result = JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind(0, user.getPassword())
                            .bind(1, user.getId())
                            .execute()
            );
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public Long getUserIdByEmailAndPassword(String email, String password) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id FROM user WHERE email = :email AND password = :password")
                        .bind("email", email)
                        .bind("password", password)
                        .mapTo(Long.class)
                        .findOne()
                        .orElse(null)
        );
    }

    @Override
    public void updateAvatar(Long id, String avatarBase64) {
        String sql = "UPDATE user SET avatar = :avatar WHERE id = :id";
        try {
            JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind("avatar", avatarBase64)
                            .bind("id", id)
                            .execute()
            );
        } catch (Exception e) {
            e.printStackTrace();
    }}

    @Override
    public String getAvatar(int userId) {
        String sql = "SELECT avatar FROM user WHERE id = :id";
        try {
            return JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createQuery(sql)
                            .bind("id", userId)
                            .mapTo(String.class)
                            .findOne()
                            .orElse(null)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long saveGoogleUser(UserModel entity) {
        String sql = "INSERT INTO user (email, full_name, phone, date_of_birth, gender, status, created_at, updated_at, avatar, last_login) " +
                "VALUES (:email, :fullName, :phone, :dateOfBirth, :gender, :status, :createdAt, :updatedAt, :avatar,  :lastLogin)";

        try {
            return JDBIConnector.getJdbi().withHandle(handle -> {
                // Hash password trước khi lưu
                // Thực hiện câu lệnh INSERT và lấy id tự động sinh
                return handle.createUpdate(sql)
                        .bind("email", entity.getEmail().trim())
                        .bind("fullName", entity.getFullName().trim())
                        .bind("phone", entity.getPhone())
                        .bind("dateOfBirth", entity.getDateOfBirth())
                        .bind("gender", entity.getGender() == null ? null : entity.getGender().name().toUpperCase())
                        .bind("status", entity.getStatus() == null ? UserStatus.NONE.toString() : entity.getStatus().toString().toUpperCase())
                        .bind("createdAt", LocalDateTime.now())
                        .bind("updatedAt", LocalDateTime.now())
                        .bind("avatar", entity.getAvatar())
                        .bind("lastLogin", LocalDateTime.now())
                        .executeAndReturnGeneratedKeys("id") // Lấy giá trị khóa chính tự động sinh
                        .mapTo(Long.class) // Ánh xạ giá trị trả về thành Long
                        .one();
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy số lượng page dựa trên số lượng item cần hiển thị
     * @param numOfItems
     * @return
     */
    @Override
    public Long getTotalPage(int numOfItems) {
        String query = "SELECT COUNT(*) FROM user u JOIN user_has_role ur ON u.id = ur.user_id JOIN role r ON ur.role_id = r.id WHERE r.name = :roleName ";

        try {
            // Dùng withHandle để thực hiện câu lệnh SQL
            Long totalUser = JDBIConnector.getJdbi().withHandle(handle -> {
                return handle.createQuery(query)
                        .bind("roleName", "CUSTOMER") // Gắn giá trị cho tham số :roleName
                        .mapTo(Long.class)           // Ánh xạ kết quả thành kiểu Long
                        .one();                      // Chỉ lấy một kết quả duy nhất
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
