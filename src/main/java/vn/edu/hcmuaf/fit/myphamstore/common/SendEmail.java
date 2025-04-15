    package vn.edu.hcmuaf.fit.myphamstore.common;
    
    import jakarta.mail.*;
    import jakarta.mail.internet.InternetAddress;
    import jakarta.mail.internet.MimeMessage;
    import lombok.extern.slf4j.Slf4j;
    import vn.edu.hcmuaf.fit.myphamstore.model.AddressModel;
    import vn.edu.hcmuaf.fit.myphamstore.model.CartModelHelper;
    import vn.edu.hcmuaf.fit.myphamstore.model.OrderModel;
    import vn.edu.hcmuaf.fit.myphamstore.model.ProductVariant;

    import java.text.NumberFormat;
    import java.time.format.DateTimeFormatter;
    import java.util.Date;
    import java.util.List;
    import java.util.Locale;
    import java.util.Properties;

    @Slf4j
    public class SendEmail {
        private static final String EMAIL = "hanrepository@gmail.com";
        private static final String PASSWORD = "nfssbtsafpmatbaq";
    
        public static boolean sendEmail(String to, String otp) {
            log.info("Sending email to: {}",to);
            System.out.println("Sending email to: " + to);
            String tieuDe = "Email xác nhận tài khoản";
            String noiDung = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            background-color: #f4f4f4;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "        }\n" +
                    "        .email-container {\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 20px auto;\n" +
                    "            background-color: #ffffff;\n" +
                    "            border: 1px solid #ddd;\n" +
                    "            border-radius: 8px;\n" +
                    "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                    "            overflow: hidden;\n" +
                    "        }\n" +
                    "        .email-header {\n" +
                    "            background-color: #007bff;\n" +
                    "            color: #ffffff;\n" +
                    "            text-align: center;\n" +
                    "            padding: 20px;\n" +
                    "        }\n" +
                    "        .email-header h1 {\n" +
                    "            margin: 0;\n" +
                    "            font-size: 24px;\n" +
                    "        }\n" +
                    "        .email-body {\n" +
                    "            padding: 20px;\n" +
                    "            color: #333333;\n" +
                    "        }\n" +
                    "        .email-body p {\n" +
                    "            margin: 10px 0;\n" +
                    "            line-height: 1.6;\n" +
                    "        }\n" +
                    "        .email-body a {\n" +
                    "            display: inline-block;\n" +
                    "            margin-top: 20px;\n" +
                    "            padding: 10px 20px;\n" +
                    "            background-color: #007bff;\n" +
                    "            color: #ffffff;\n" +
                    "            text-decoration: none;\n" +
                    "            border-radius: 5px;\n" +
                    "            font-size: 16px;\n" +
                    "        }\n" +
                    "        .email-body a:hover {\n" +
                    "            background-color: #0056b3;\n" +
                    "        }\n" +
                    "        .email-footer {\n" +
                    "            text-align: center;\n" +
                    "            padding: 10px;\n" +
                    "            font-size: 12px;\n" +
                    "            color: #777;\n" +
                    "            border-top: 1px solid #ddd;\n" +
                    "            background-color: #f9f9f9;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"email-container\">\n" +
                    "        <!-- Header -->\n" +
                    "        <div class=\"email-header\">\n" +
                    "            <h1>Kích hoạt tài khoản</h1>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <!-- Body -->\n" +
                    "        <div class=\"email-body\">\n" +
                    "            <p>Chào bạn,</p>\n" +
                    "            <p>Cảm ơn bạn đã đăng ký tài khoản tại <strong>Website của chúng tôi</strong>.</p>\n" +
                    "            <p>Vui lòng nhấn vào nút bên dưới để kích hoạt tài khoản của bạn:</p>\n" +
                    "            <a href=\"http://localhost:8080/register?action=verify&register&otp="+otp+"&email="+to+"  \">Kích hoạt tài khoản</a>\n" +
                    "            <p>Nếu bạn không thực hiện đăng ký, vui lòng bỏ qua email này.</p>\n" +
                    "            <p>Trân trọng,</p>\n" +
                    "            <p><strong>Đội ngũ hỗ trợ</strong></p>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <!-- Footer -->\n" +
                    "        <div class=\"email-footer\">\n" +
                    "            &copy; 2025 Website của chúng tôi. Tất cả các quyền được bảo lưu.\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";
    
            // Properties : khai báo các thuộc tính
            MimeMessage msg = getMimeMessage();

            try {
                // Kiểu nội dung
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
    
                // Người gửi
    
                // Người nhận
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
    
                // Tiêu đề email
                msg.setSubject(tieuDe);
    
                // Quy đinh ngày gửi
                msg.setSentDate(new Date());
    
                // Quy định email nhận phản hồi
                // msg.setReplyTo(InternetAddress.parse(from, false))
    
                // Nội dung
                msg.setContent(noiDung, "text/HTML; charset=UTF-8");
    
                // Gửi email
                Transport.send(msg);
                log.info("Send email successful to: {}", to);
                return true;
            } catch (Exception e) {
                log.info("Send email fail to: {}", to);
                e.printStackTrace();
                return false;
            }
        }
        public static void notifyOrderToUser(String toEmail, OrderModel order, List<CartModelHelper> listCartDisplay, AddressModel address){
            log.info("Send email notify order to user: {}", toEmail);
            // Sử dụng NumberFormat với locale cho Việt Nam
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
    

            StringBuilder items = new StringBuilder();
            for (CartModelHelper cartItem : listCartDisplay) {
                ProductVariant variant = cartItem.getVariant();
                String price = (variant != null) ? numberFormat.format(variant.getPrice()) : numberFormat.format(cartItem.getProduct().getPrice());
                items.append("<tr>\n")
                        .append("                    <td>").append(cartItem.getProduct().getName()).append("</td>\n")
                        .append("                    <td>").append((variant == null)?"sản phẩm gốc":variant.getName()).append("</td>\n")
                        .append("                    <td>").append(cartItem.getQuantity()).append("</td>\n")
                        .append("                    <td>").append(price).append(" VNĐ</td>\n")
                        .append("                </tr>\n");
            }
    
            String template = "<!DOCTYPE html>\n" +
                    "<html lang=\"vi\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Đặt Hàng Thành Công</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            background-color: #e3f2fd;\n" +
                    "            padding: 20px;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            max-width: 600px;\n" +
                    "            margin: auto;\n" +
                    "            background: #ffffff;\n" +
                    "            padding: 20px;\n" +
                    "            border-radius: 20px;\n" +
                    "            box-shadow: 0px 4px 20px rgba(0, 123, 255, 0.2);\n" +
                    "        }\n" +
                    "        h2 {\n" +
                    "            text-align: center;\n" +
                    "            color: #007bff;\n" +
                    "            border-bottom: 3px solid #90caf9;\n" +
                    "            padding-bottom: 10px;\n" +
                    "        }\n" +
                    "        h3 {\n" +
                    "            color: #0056b3;\n" +
                    "            border-left: 5px solid #90caf9;\n" +
                    "            padding-left: 10px;\n" +
                    "        }\n" +
                    "        .table-container {\n" +
                    "            border-radius: 12px;\n" +
                    "            overflow: hidden;\n" +
                    "            margin-bottom: 20px;\n" +
                    "        }\n" +
                    "        table {\n" +
                    "            width: 100%;\n" +
                    "            border-collapse: collapse;\n" +
                    "        }\n" +
                    "        table, th, td {\n" +
                    "            border: none;\n" +
                    "            padding: 12px;\n" +
                    "            text-align: left;\n" +
                    "        }\n" +
                    "        th {\n" +
                    "            background-color: #bbdefb;\n" +
                    "            color: #0d47a1;\n" +
                    "        }\n" +
                    "        td {\n" +
                    "            background-color: #e3f2fd;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <h2>Đặt Hàng Thành Công</h2>\n" +
                    "        \n" +
                    "        <div class=\"table-container\">\n" +
                    "            <table>\n" +
                    "                <tr><td><strong>Ghi Chú:</strong></td><td>"+order.getNote()+"</td></tr>\n" +
                    "                <tr><td><strong>Phương Thức Thanh Toán:</strong></td><td>"+order.getPaymentMethod().name()+"</td></tr>\n" +
                    "                <tr><td><strong>Phí Vận Chuyển:</strong></td><td>"+numberFormat.format(order.getShippingFee())+" VNĐ</td></tr>\n" +
                    "                <tr><td><strong>Tổng Tiền:</strong></td><td>"+numberFormat.format(order.getTotalPrice() + order.getShippingFee())+" VNĐ</td></tr>\n" +
                    "                <tr><td><strong>Ngày Đặt Hàng:</strong></td><td>"+order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"</td></tr>\n" +
                    "            </table>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <h3>Thông Tin Giao Hàng</h3>\n" +
                    "        <div class=\"table-container\">\n" +
                    "            <table>\n" +
                    "                <tr><td><strong>Người Nhận:</strong></td><td>"+address.getRecipientName()+"</td></tr>\n" +
                    "                <tr><td><strong>Số Điện Thoại:</strong></td><td>"+address.getRecipientPhone()+"</td></tr>\n" +
                    "                <tr><td><strong>Thành Phố:</strong></td><td>"+address.getCity()+"</td></tr>\n" +
                    "                <tr><td><strong>Quận/Huyện:</strong></td><td>"+address.getDistrict()+"</td></tr>\n" +
                    "                <tr><td><strong>Phường/Xã:</strong></td><td>"+address.getWard()+"</td></tr>\n" +
                    "                <tr><td><strong>Ghi Chú Địa Chỉ:</strong></td><td>"+address.getNote()+"</td></tr>\n" +
                    "            </table>\n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <h3>Chi Tiết Đơn Hàng</h3>\n" +
                    "        <div class=\"table-container\">\n" +
                    "            <table>\n" +
                    "                <tr>\n" +
                    "                    <th>Tên Sản Phẩm</th>\n" +
                    "                    <th>Loại</th>\n"+
                    "                    <th>Số Lượng</th>\n" +
                    "                    <th>Đơn Giá</th>\n" +
                    "                </tr>\n" +
                    items +
                    "                \n" +
                    "            </table>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";
    
            String tieuDe = "Thông báo đặt đơn hàng #" + order.getId();
            // Properties : khai báo các thuộc tính
            MimeMessage msg = getMimeMessage();

            try {
                // Kiểu nội dung
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                // Người gửi
    
                // Người nhận
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
    
                // Tiêu đề email
                msg.setSubject(tieuDe, "UTF-8");
    
                // Quy đinh ngày gửi
                msg.setSentDate(new Date());
    
                // Quy định email nhận phản hồi
                // msg.setReplyTo(InternetAddress.parse(from, false))
    
                // Nội dung
                msg.setContent(template, "text/HTML; charset=UTF-8");
    
                // Gửi email
                Transport.send(msg);
                log.info("Send email successful to: " + toEmail);
            } catch (Exception e) {
                log.info("Send email fail to: " + toEmail);
                e.printStackTrace();
            }
        }

        private static MimeMessage getMimeMessage() {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
            props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // create Authenticator
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // TODO Auto-generated method stub
                    return new PasswordAuthentication(EMAIL, PASSWORD);
                }
            };

            // Phiên làm việc
            Session session = Session.getInstance(props, auth);

            // Tạo một tin nhắn
            MimeMessage msg = new MimeMessage(session);
            return msg;
        }
        public static boolean forgotPassword(String to, String otp) {
            log.info("Sending password reset email to: {}", to);
            String tieuDe = "Khôi phục mật khẩu tài khoản";
            String noiDung = "<!DOCTYPE html>\n" +
                    "<html lang=\"vi\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <style>\n" +
                    "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 0; margin: 0; }\n" +
                    "        .email-container { max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px;\n" +
                    "            border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }\n" +
                    "        .email-header { background-color: #007bff; color: #ffffff; text-align: center; padding: 20px; }\n" +
                    "        .email-body { padding: 20px; color: #333333; text-align: center; }\n" +
                    "        .email-body p { margin: 10px 0; line-height: 1.6; }\n" +
                    "        .reset-link { display: inline-block; margin-top: 20px; padding: 10px 20px;\n" +
                    "            background-color: #007bff; color: #ffffff; text-decoration: none;\n" +
                    "            border-radius: 5px; font-size: 16px; }\n" +
                    "        .reset-link:hover { background-color: #0056b3; }\n" +
                    "        .email-footer { text-align: center; padding: 10px; font-size: 12px;\n" +
                    "            color: #777; border-top: 1px solid #ddd; background-color: #f9f9f9; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"email-container\">\n" +
                    "        <div class=\"email-header\">\n" +
                    "            <h1>Yêu cầu khôi phục mật khẩu</h1>\n" +
                    "        </div>\n" +
                    "        <div class=\"email-body\">\n" +
                    "            <p>Xin chào,</p>\n" +
                    "            <p>Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản của mình.</p>\n" +
                    "            <p>Vui lòng nhấn vào nút bên dưới để đặt lại mật khẩu:</p>\n" +
                    "            <a class=\"reset-link\" href=\"http://localhost:8080/reset-password?otp=" + otp + "&email=" + to + "\">Đặt lại mật khẩu</a>\n" +
                    "            <p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"email-footer\">\n" +
                    "            &copy; 2025 Website của chúng tôi. Tất cả các quyền được bảo lưu.\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";

            MimeMessage msg = getMimeMessage();
            try {
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
                msg.setSubject(tieuDe, "UTF-8");
                msg.setSentDate(new Date());
                msg.setContent(noiDung, "text/HTML; charset=UTF-8");

                Transport.send(msg);
                log.info("Password reset email sent successfully to: {}", to);
                return true;
            } catch (Exception e) {
                log.error("Failed to send password reset email to: {}", to, e);
                return false;
            }
        }

    }
