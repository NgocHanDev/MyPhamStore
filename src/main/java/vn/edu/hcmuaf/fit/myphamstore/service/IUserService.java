package vn.edu.hcmuaf.fit.myphamstore.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.edu.hcmuaf.fit.myphamstore.model.UserModel;

import java.io.IOException;
import java.util.List;


public interface IUserService {
    List<UserModel> getUsersWithPaging(String keyword,int currentPage, int pageSize, String orderBy);
    boolean checkLogin(String email, String password);
    UserModel findUserByEmail(String email);
    void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
    void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
    void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
    void displayListUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
    UserModel findUserById(Long id);
    void lockUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void detailUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void unlockUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void verifyOtp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    boolean verifyOTPHash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void profile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    boolean updateUserPassword(UserModel user);

    void addAddress(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void uploadAvatar(HttpServletRequest request, HttpServletResponse response, Part filePart) throws IOException;

    void showAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException;

    boolean forgotPassword(String email, String otp);
}
