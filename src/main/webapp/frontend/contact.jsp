<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
Created by IntelliJ IDEA.
User: cucsh
Date: 12/7/2024
Time: 10:10 AM
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<!-- 
Template Name: big basket
Version: 1.0.0
Author: 
Website: 
Purchase: 
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->

<head>
	<meta charset="utf-8" />
	<title>Liên hệ</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta name="description" content="big basket" />
	<meta name="keywords" content="big basket, Ecommerce, Store, Mall, online Shopping" />
	<meta name="author" content="" />
	<meta name="MobileOptimized" content="320" />
	<!--srart theme style -->
	<link rel="stylesheet" type="text/css" href="../static/css/animate.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/font-awesome.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/owl.carousel.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/owl.theme.default.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/magnific-popup.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/fonts.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/dl-menu.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/camera.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/style.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/responsive.css" />
	<link rel="stylesheet" type="text/css" href="../static/css/sidebar.css" />
	<!-- favicon links -->
	<link rel="shortcut icon" type="image/png" href="../static/images/header/favicon.png" />
</head>

<body>
    <!-- preloader Start -->
	<div id="preloader">
		<div id="status">
			<img src="../static/images/header/preloader.gif" id="preloader_image" alt="loader">
		</div>
	</div>
	<!-- Top Scroll Start -->
	<a href="javascript:" id="return-to-top"><i class="fa fa-angle-up"></i></a>
	<!-- Top Scroll End -->
    <!-- Header Wrapper Start -->
    <%@include file="component/nav.jsp"%>
    <%@include file="component/header.jsp"%>
    <!-- Header Wrapper End -->
    <div class="container" style="padding-bottom: 50px;">
        <h2 class="text-center" style="padding-top:250px;">Liên Hệ</h2>
        <p class="text-center">Nếu bạn có bất kỳ câu hỏi hoặc cần hỗ trợ, hãy liên hệ với chúng tôi qua biểu mẫu dưới đây.</p>
        <div class="row">
            <!-- Contact Form -->
            <div class="col-md-6">
                <form>
                    <div class="form-group">
                        <label for="name">Họ và Tên:</label>
                        <input type="text" class="form-control" id="name" placeholder="Nhập họ và tên" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control" id="email" placeholder="Nhập email" required>
                    </div>
                    <div class="form-group">
                        <label for="subject">Chủ đề:</label>
                        <input type="text" class="form-control" id="subject" placeholder="Nhập chủ đề">
                    </div>
                    <div class="form-group">
                        <label for="message">Nội dung:</label>
                        <textarea class="form-control" id="message" rows="5" placeholder="Nhập nội dung" required></textarea>
                    </div>
                    <div class="text-center"><button type="submit" style="min-width:150px;" class="btn btn-primary">Gửi</button></div>
                </form>
            </div>

            <!-- Contact Information -->
            <div class="col-md-6">
                <h4>Thông Tin Liên Hệ</h4>
                <p><strong>Địa chỉ:</strong> Kp6, Phường Linh Trung, TP Thủ Đức, TP Hồ Chí Minh</p>
                <p><strong>Số điện thoại:</strong> 0123456789 / 0987654321</p>
                <p><strong>Email:</strong> vphanhchinh@hcmuaf.edu.vn</p>
                <h4>Giờ Làm Việc</h4>
                <p>Thứ 2 - Thứ 6: 8:00 - 17:00</p>
                <p>Thứ 7 - Chủ Nhật: Nghỉ</p>
                <h4>Kết Nối Với Chúng Tôi</h4>
                <p>
                    <a href="#" class="btn btn-social-icon btn-facebook"><i class="glyphicon glyphicon-thumbs-up"></i> Facebook</a>
                    <a href="#" class="btn btn-social-icon btn-google"><i class="glyphicon glyphicon-envelope"></i> Google</a>
                </p>
            </div>
        </div>
    </div>
<!-- Brandlogo Wrapper End -->

    <%@include file="component/footer.jsp"%>

</div>
<script>
    const header = document.getElementById("header");
const footer = document.getElementById("footer");
const nav = document.getElementById("nav");

fetch('./header.jsp')
    .then(response => {
        return response.text()
    })
    .then(data => {
        header.innerHTML = data;
    });
fetch('./footer.jsp')
    .then(response => {
        return response.text()
    })
    .then(data => {
        footer.innerHTML = data;
    });
fetch('./nav.jsp')
    .then(response => {
        return response.text()
    }).then(data => {
        nav.innerHTML = data;
    });
</script>
<!--main js file start-->
<script src="../static/js/jquery_min.js"></script>
<script src="../static/js/wow.js"></script>
<script src="../static/js/bootstrap.js"></script>
<script src="../static/js/owl.carousel.js"></script>
<script src="../static/js/modernizr.js"></script>
<script src="../static/js/jquery.magnific-popup.js"></script>
<script src="../static/js/jquery.dlmenu.js"></script>
<script src="../static/js/jquery.sticky.js"></script>
<script src="../static/js/jquery.menu-aim.js"></script>
<script src="../static/js/camera.min.js"></script>
<script src="../static/js/jquery.easing.1.3.js"></script>
<script src="../static/js/jquery.inview.min.js"></script>
<script src="../static/js/custom.js"></script>
</body>

</html>