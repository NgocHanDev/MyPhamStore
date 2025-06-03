package vn.edu.hcmuaf.fit.myphamstore.dao;

import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;

public interface IUserDAO extends GenericDAO<UserModel> {
    boolean checkLogin(String username, String password);
    boolean checkEmailExist(String email);
    boolean checkPhoneExist(String phone);
    UserModel getUserByEmail(String email);
    UserModel getUserById(Long id);
    boolean updateUserPassword(UserModel user);
    Long getUserIdByEmailAndPassword(String email, String password);

    void updateAvatar(Long id, String newAvatarUrl);

    String getAvatar(int userId);
    Long saveGoogleUser(UserModel user);
}
