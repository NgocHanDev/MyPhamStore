package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.common.RoleType;
import vn.edu.hcmuaf.fit.myphamstore.dao.IRoleDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.RoleModel;

import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class RoleDAOImpl implements IRoleDAO {
    @Override
    public List<RoleModel> findListRoleByUserId(Long userId) {
        String sql = "SELECT r.* FROM user AS u " +
                "JOIN user_has_role AS u_r ON u.id = u_r.user_id " +
                "JOIN role AS r ON r.id = u_r.role_id " +
                "WHERE u.id = :userId";

       try {
            List<RoleModel> roles = JDBIConnector.getJdbi().withHandle(handle ->
                    handle.createQuery(sql)
                            .bind("userId", userId)
                            .mapToBean(RoleModel.class)
                            .list()
            );
            return roles;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Trả về danh sách rỗng thay vì null để tránh lỗi NPE
        }
    }

    @Override
    public void setRoleToUser(String roleType, Long userId) {
        RoleModel roleModel = this.findRoleByName(roleType);
        String sql = "INSERT INTO user_has_role (role_id, user_id) VALUES (:role_id, :user_id)";
        try{
            JDBIConnector.getJdbi().useHandle(handle ->{
                handle.createUpdate(sql)
                        .bind("role_id", roleModel.getId())
                        .bind("user_id", userId)
                        .execute()
                        ;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public RoleModel findRoleByName(String roleType) {
        String sql = "SELECT * FROM role WHERE name = :roleName";
        try{
            return JDBIConnector.getJdbi().withHandle(handle ->{
                return handle.createQuery(sql)
                        .bind("roleName", roleType.trim())
                        .mapToBean(RoleModel.class)
                        .one();
            });
        }catch (Exception e){
        return null;
        }
    }

    @Override
    public List<RoleModel> findAllRoles() {
        return JDBIConnector.getJdbi().withHandle(handle -> {
            String sql = "SELECT * FROM role";
            return handle.createQuery(sql)
                    .mapToBean(RoleModel.class)
                    .list();
        });
    }

    @Override
    public void updateRolesToUser(List<RoleModel> roles, Long userId) {
        String deleteSql = "DELETE FROM user_has_role WHERE user_id = :userId";
        String insertSql = "INSERT INTO user_has_role (role_id, user_id) VALUES (:role_id, :user_id)";

        try {
            JDBIConnector.getJdbi().useHandle(handle -> {
                // Xóa tất cả các vai trò hiện tại của người dùng
                handle.createUpdate(deleteSql)
                        .bind("userId", userId)
                        .execute();

                // Thêm các vai trò mới
                for (RoleModel role : roles) {
                    handle.createUpdate(insertSql)
                            .bind("role_id", role.getId())
                            .bind("user_id", userId)
                            .execute();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
