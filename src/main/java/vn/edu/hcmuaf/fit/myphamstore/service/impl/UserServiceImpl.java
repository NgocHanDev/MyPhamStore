package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import vn.edu.hcmuaf.fit.myphamstore.common.*;
import vn.edu.hcmuaf.fit.myphamstore.constant.Iconstant;
import vn.edu.hcmuaf.fit.myphamstore.dao.*;
import vn.edu.hcmuaf.fit.myphamstore.exception.UserNotActiveException;
import vn.edu.hcmuaf.fit.myphamstore.model.*;
import vn.edu.hcmuaf.fit.myphamstore.service.IUserService;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class UserServiceImpl implements IUserService {
    @Inject
    private IUserDAO userDAO;
    @Inject
    private IRoleDAO roleDAO;
    @Inject
    private IOtpDAO otpDAO;
    @Inject
    private IAddressDAO addressDAO;
    @Inject
    private ICartDAO cartDAO;
    @Inject
    LoggingService logger;


    @Override
    public List<UserModel> getUsersWithPaging(String keyword, int currentPage, int pageSize, String orderBy) {
        if (keyword != null && !keyword.isEmpty()) {
            keyword = keyword.trim();
        }
        return userDAO
                .findAll(keyword, currentPage, pageSize, orderBy)
                .stream().map(u ->{
                    u.setRoles(roleDAO.findListRoleByUserId(u.getId()));
                    return u;
                }).collect(Collectors.toList());
    }

    @Override
    public boolean checkLogin(String email, String password) {

        return userDAO.checkLogin(email, password);
    }

    @Override
    public UserModel findUserByEmail(String email) {
        if(email == null || email.isBlank()) {
            return null;
        }
        try{
            UserModel user = userDAO.getUserByEmail(email);
            if(user != null) {
                user.setRoles(roleDAO.findListRoleByUserId(user.getId()));
            }
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        boolean verified = verifyRecaptcha(gRecaptchaResponse);

        if (!verified) {
            request.setAttribute("message", "Vui lòng xác minh không phải robot!");
            request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng nhập email và mật khẩu!");
            request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
            return;
        }

        email = email.trim();
        password = password.trim();
        List<CartModel> cartItems = null   ;

        try {
            boolean isAuthenticated = this.checkLogin(email, password);
            if (isAuthenticated) {
                logger.info("USER-SERVICE", String.format("Login %s successfully", email));
                UserModel user = this.findUserByEmail(email); // Gọi thêm phương thức này
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    if (user.getRoles() != null && user.getRoles().stream().anyMatch(r -> (r.getName().equalsIgnoreCase(RoleType.ADMIN)) || (r.getName().equalsIgnoreCase(RoleType.EMPLOYEE)))) {
                        response.sendRedirect(request.getContextPath() + "/admin");
                    } else {
                        CartHeaderModel cartHeaderModel = cartDAO.getCartByUserId(user.getId());

                        if (cartHeaderModel == null) {
                            Long id = cartDAO.createCartForUser(user.getId());
                            cartItems = cartDAO.getCartItemsByCartId(id);
                        }else {
                            cartItems = cartDAO.getCartItemsByCartId(cartHeaderModel.getId());
                        }

                        session.setAttribute("cartItems", cartItems);
                        response.sendRedirect(request.getContextPath() + "/trang-chu");
                    }
                }
            } else {
                logger.warn( "USER-SERVICE", String.format("Login %s fails", email));
                request.setAttribute("message", "Sai email hoặc mật khẩu!");
                request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
            }
        } catch (UserNotActiveException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Đã xảy ra lỗi, vui lòng thử lại sau!");
            request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/trang-chu");
    }

    @Override
    public void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    @Override
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = null;
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        email = email.trim();
        String password = request.getParameter("password");
        password = password.trim();
        String confirmPassword = request.getParameter("rePassword");
        String phone  = request.getParameter("phone");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        String gender = request.getParameter("gender");
        //check email tồn tại
        if(userDAO.checkEmailExist(email)) {
            request.setAttribute("message", "Email đã tồn tại trong hệ thống!");
            dispatcher = request.getRequestDispatcher("/frontend/register.jsp");
            dispatcher.forward(request, response);
            return;
        }
        //check phone number tồn tại
        if(userDAO.checkPhoneExist(phone)) {
            request.setAttribute("message", "Số đện thoại đã tồn tại trong hệ thống!");
            dispatcher = request.getRequestDispatcher("/frontend/register.jsp");
            dispatcher.forward(request, response);
            return;
        }
        //check pass
        if(!password.equalsIgnoreCase(confirmPassword)) {
            request.setAttribute("message", "Mật khẩu không trùng khớp!");
            dispatcher = request.getRequestDispatcher("/frontend/register.jsp");
            dispatcher.forward(request, response);
            return;
        }
        //tiến hành đăng kí
        UserModel user = UserModel.builder()
                .fullName(fullName)
                .gender(Gender.valueOf(gender))
                .dateOfBirth(dateOfBirth)
                .email(email)
                .phone(phone)
                .status(UserStatus.NONE)
                .password(password.trim())
                .avatar(null).build();
        Long savedUserId = userDAO.save(user);
        if( savedUserId!= null && savedUserId  > 0){
            roleDAO.setRoleToUser(RoleType.CUSTOMER, savedUserId);
        }
        String otp = otpDAO.generateOtp();
        otpDAO.saveOtp(email, otp);

//         Sử dụng ExecutorService để gửi email bất đồng bộ
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
            String finalEmail = email;
            executorService.submit(() -> {
                SendEmail.sendEmail(finalEmail, otp);
                log.info("USER-SERVICE", String.format("Gửi email thành công: %s", finalEmail));
        });
        executorService.shutdown(); // Đóng ExecutorService sau khi gửi
        request.setAttribute("message", "Đăng ký thành công, Vui lòng kiểm tra email để kích hoạt tài khoản");
        request.getRequestDispatcher("/frontend/register.jsp").forward(request, response);
    }

    @Override
    public void displayListUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/customer/customer-management.jsp");
        String keyword = request.getParameter("keyword");
        String orderBy = request.getParameter("orderBy");
        int currentPage = Integer.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "10" : request.getParameter("pageSize"));

        List<UserModel> users = this.getUsersWithPaging(keyword, currentPage, pageSize, orderBy)
                .stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(r -> r.getName().equalsIgnoreCase(RoleType.CUSTOMER.toString())))
                .collect(Collectors.toList());
        Long totalPages = this.userDAO.getTotalPage(pageSize);
        // Gửi danh sách sản phẩm đến trang JSP
        request.setAttribute("users", users);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyword", keyword);
        request.setAttribute("orderBy", orderBy);
        dispatcher.forward(request, response);
    }

    @Override
    public UserModel findUserById(Long id) {
        if(id == null) {
            return null;
        }
        try{
            UserModel user = userDAO.getUserById(id);
            if(user != null) {
                user.setRoles(roleDAO.findListRoleByUserId(user.getId()));
            }
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void lockUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        UserModel user = UserModel.builder().id(id).build();
        user.setStatus(UserStatus.INACTIVE);
        UserModel isSuccess = userDAO.update(user);

        if (isSuccess != null) {
            request.setAttribute("mesage","Cập nhật thành công id: "+id);
        }else {
            request.setAttribute("message", "Cập nhật thất bại");
        }

        this.displayListUsers(request,response);
    }

    @Override
    public void detailUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        UserModel userModel = null;
        List<RoleModel> roles = roleDAO.findAllRoles();
        if(id != null) {
            userModel = this.findUserById(id);
        }
        if(userModel != null) {
            request.setAttribute("availableRoles", roles);
            request.setAttribute("user", userModel);
            request.getRequestDispatcher("/admin/customer/user-detail.jsp").forward(request, response);
        }
    }

    @Override
    public void unlockUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        UserModel user = UserModel.builder().id(id).build();
        user.setStatus(UserStatus.ACTIVE);
        UserModel isSuccess = userDAO.update(user);

        if (isSuccess != null) {
            request.setAttribute("mesage","Cập nhật thành công id: "+id);
        }else {
            request.setAttribute("message", "Cập nhật thất bại");
        }

        this.displayListUsers(request,response);
    }

    @Override
    public void verifyOtp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String otp = request.getParameter("otp");
        Boolean verify = otpDAO.verifyOtp(email.trim(), otp.trim());
        if(verify) {
            UserModel user = userDAO.getUserByEmail(email);
            user.setStatus(UserStatus.ACTIVE);
            userDAO.update(user);
            request.setAttribute("message", "Kích hoạt tài khoản thành công");
        }else {
            request.setAttribute("message", "Mã OTP không chính xác");
            request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/frontend/login.jsp").forward(request, response);
    }
    @Override
    public boolean verifyOTPHash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String otp = request.getParameter("otp");
        return otpDAO.verifyOtpHash(email.trim(), otp.trim());
    }
    @Override
    public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String fullName = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String dob = request.getParameter("dob");
        String addressIdParam = request.getParameter("address");
        String[] addressesToDelete = request.getParameterValues("deleteAddressIds");// Get array of address IDs to delete
        System.out.println("addressesToDelete: " + Arrays.toString(addressesToDelete));
        // Cập nhật thông tin người dùng
        user.setFullName(fullName);
        user.setGender(Gender.valueOf(gender));
        user.setEmail(email);
        user.setPhone(phone);
        user.setDateOfBirth(LocalDate.parse(dob));

        // Xử lý xóa địa chỉ
        List<AddressModel> addresses = addressDAO.findByUserId(user.getId());
        if (addressesToDelete != null) {
            for (String addressIdStr : addressesToDelete) {
                try {
                    Long addressIdToDelete = Long.parseLong(addressIdStr);
                    AddressModel address = addressDAO.findAddressById(addressIdToDelete);
                    if (address != null && address.getUserId().equals(user.getId())) {
                        addressDAO.delete(address);
                    }
                } catch (NumberFormatException e) {
                    // Log error if needed
                    continue;
                }
            }
        }

        // Cập nhật địa chỉ mặc định
        if (addressIdParam != null && !addressIdParam.isEmpty()) {
            try {
                Long addressId = Long.parseLong(addressIdParam);
                for (AddressModel addr : addresses) {
                    if (addr.getIsActive()) { // Only update active addresses
                        addr.setIsDefault(addr.getId().equals(addressId));
                        addressDAO.update(addr);
                    }
                }
            } catch (NumberFormatException e) {
                // Log error if needed
            }
        }

        // Cập nhật thông tin người dùng trong database
        UserModel isUpdated = userDAO.update(user);

        if (isUpdated != null) {
            request.getSession().setAttribute("successMessage", "Cập nhật hồ sơ và địa chỉ thành công!");
        } else {
            request.getSession().setAttribute("errorMessage", "Cập nhật hồ sơ thất bại!");
        }
        response.sendRedirect(request.getContextPath() + "/profile");
    }

    @Override
    public void profile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Giả sử thông tin user đã được lưu trong session sau khi đăng nhập
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        List<AddressModel>  addresss = addressDAO.findByUserId(user.getId());

        if (user == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
            response.sendRedirect("/login");
            return;
        }

        // Đưa thông tin user vào request để hiển thị trên JSP
        request.setAttribute("user", user);
        request.setAttribute("addresss", addresss);
        try {
            request.getRequestDispatcher("/frontend/profile.jsp").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateUserPassword(UserModel user) {
        return userDAO.updateUserPassword(user);
    }

    @Override
    public void addAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        String recipientName = user.getFullName();
        String recipientPhone = user.getPhone();
        Integer cityId = Integer.parseInt(request.getParameter("cityId"));
        Integer districtId = Integer.parseInt(request.getParameter("districtId"));
        Integer wardCode = Integer.parseInt(request.getParameter("wardCode"));
        System.out.println("cityId: " + cityId);
        System.out.println("districtId: " + districtId);
        System.out.println("wardCode: " + wardCode);
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String note = request.getParameter("note");
        Boolean isDefault = Boolean.parseBoolean(request.getParameter("setDefault"));

        AddressModel address = AddressModel.builder()
                .userId(user.getId())
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
//                .cityId(cityId)
//                .districtId(districtId)
//                .wardCode(wardCode)
                .city(city)
                .district(district)
                .ward(ward)
                .note(note)
                .isDefault(isDefault)
                .isActive(true)
                .build();

        Long savedAddress = addressDAO.save(address);
        if (isDefault) {
            List<AddressModel> addresses = addressDAO.findByUserId(user.getId());
            for (AddressModel addr : addresses) {
                if (!addr.getId().equals(savedAddress)) {
                    addr.setIsDefault(false);
                    addressDAO.update(addr);
                }
            }
        }
        if (savedAddress != null) {
            response.getWriter().write("{\"success\": true}");
        } else {
            response.getWriter().write("{\"success\": false, \"message\": \"Failed to add address.\"}");

        }
    }

    @Override
    public void uploadAvatar(HttpServletRequest request, HttpServletResponse response, Part filePart) throws IOException {
        UserModel user = (UserModel) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Đọc dữ liệu ảnh và chuyển thành Base64
        InputStream inputStream = filePart.getInputStream();
        byte[] imageData = inputStream.readAllBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageData);

        // Lưu Base64 vào database
        userDAO.updateAvatar(user.getId(), base64Image);

        // Cập nhật session
        user.setAvatar(base64Image);
        request.getSession().setAttribute("user", user);

        response.sendRedirect("profile");
    }

    @Override
    public void showAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String base64Image = userDAO.getAvatar(userId);

        if (base64Image != null) {
            response.setContentType("text/plain"); // Trả về chuỗi Base64
            response.getWriter().write(base64Image);
        }
    }

    @Override
    public boolean forgotPassword(String email, String otp) {
        otp = otpDAO.generateOtp();
        UserModel user = userDAO.getUserByEmail(email);
        String hashedOtp = PasswordUtils.hashPassword(otp.trim());
        System.out.println("boolean so pass"+PasswordUtils.verifyPassword(otp.trim(), hashedOtp));
        if (user != null) {
            otpDAO.saveOtp(email,otp.trim());
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                SendEmail.forgotPassword(email, hashedOtp);
            });
            executorService.shutdown();
            return true;
        }
        return false;
    }

    @Override
    public Long saveGoogle(UserModel user) {
        return userDAO.saveGoogleUser(user);
    }

    @Override
    public void updateGoogle(UserModel user) {
        userDAO.update(user);
    }

    @Override
    public void updateRoles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get user id from request
        int userId = Integer.parseInt(request.getParameter("id"));
        //get roles from request
        List<RoleModel> roles = Arrays.stream(request.getParameterValues("roles"))
                .map(roleName -> roleDAO.findRoleByName(roleName.trim()))
                .toList();

        //update roles to user
        roleDAO.updateRolesToUser(roles, (long) userId);
        response.sendRedirect(request.getContextPath() + "/admin/users?action=displayDetail&id="+ userId);
    }

    public Long authenticate(String email, String password) {
        UserModel user = userDAO.getUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user.getId();
        }
        return null;
    }
    private boolean verifyRecaptcha(String gRecaptchaResponse) throws IOException {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "secret=" + URLEncoder.encode(Iconstant.SECRET_KEY_CAPTCHA, "UTF-8") +
                "&response=" + URLEncoder.encode(gRecaptchaResponse, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setDoOutput(true);

        // Gửi dữ liệu
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(params);
            wr.flush();
        }

        // Đọc phản hồi
        StringBuilder responseStr = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                responseStr.append(line);
            }
        }

        // Dùng Gson parse JSON
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseStr.toString(), JsonObject.class);
        return jsonObject.get("success").getAsBoolean();
    }
}
