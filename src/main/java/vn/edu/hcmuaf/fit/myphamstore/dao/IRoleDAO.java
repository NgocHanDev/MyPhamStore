package vn.edu.hcmuaf.fit.myphamstore.dao;

import vn.edu.hcmuaf.fit.myphamstore.common.RoleType;
import vn.edu.hcmuaf.fit.myphamstore.model.RoleModel;

import java.util.List;

public interface IRoleDAO {
    List<RoleModel> findListRoleByUserId(Long userId);
    void setRoleToUser(String roleType, Long userId);
    RoleModel findRoleByName(String roleType);
    List<RoleModel> findAllRoles();
    void updateRolesToUser(List<RoleModel> roles, Long userId);
}
