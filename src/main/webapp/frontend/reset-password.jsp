<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/tablib.jsp"%>
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
    <title> Đặt lại mật khẩu</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta name="description" content="big basket" />
    <meta
            name="keywords"
            content="big basket, Ecommerce, Store, Mall, online Shopping"
    />
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
    <link rel="stylesheet" type="text/css" href="../static/css/profile.css" />
    <!-- favicon links -->
    <link rel="shortcut icon" type="image/png" href="../static/images/header/favicon.png" />
    <link
            rel="stylesheet"
            type="text/css"
            href="../static/css/responsive.css"
    />
    <link rel="stylesheet" type="text/css" href="../static/css/style2.css" />
    <!-- favicon links -->
    <link
            rel="shortcut icon"
            type="image/png"
            href="../static/images/header/favicon.png"
    />
</head>

<body>
<!-- preloader Start -->
<div id="preloader">
    <div id="status">
        <img
                src="../static/images/header/preloader.gif"
                id="preloader_image"
                alt="loader"
        />
    </div>
</div>
<!-- Top Scroll Start -->
<a href="javascript:" id="return-to-top"><i class="fa fa-angle-up"></i></a>
<!-- Top Scroll End -->
<div id="nav"><%@include file="component/nav.jsp"%></div>
<!-- Main Content -->
<!-- Main Content -->
<%
    // Lấy email từ URL
    String email = request.getParameter("email");
    if (email == null) {
        email = "Không có email"; // Nếu không có email trong URL
    }
%>

<div class="container">
    <div class="row">
        <div class="row">
            <div class="col-md-4 col-md-offset-4" style="position: static">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="text-center">
                            <h3><i class="fa fa-lock fa-4x"></i></h3>
                            <h2 class="text-center">Quên Mật Khẩu?</h2>
                            <p>Bạn có thể đặt lại mật khẩu tại đây.</p>
                            <p><strong>Email của bạn:</strong>
                                <span style="color: blue;"><%= email %></span>
                            </p>
                            <div class="panel-body">

                                <form action="reset-password" method="post">
                                    <% String successMessage = (String) request.getAttribute("successMessage"); %>
                                    <% if (successMessage != null) { %>
                                    <div class="alert alert-success">
                                        <%= successMessage %>
                                    </div>
                                    <% } %>

                                    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                                    <% if (errorMessage != null) { %>
                                    <div class="alert alert-danger">
                                        <%= errorMessage %>
                                    </div>
                                    <% } %>

                                    <fieldset>
                                        <!-- Input ẩn để gửi email kèm theo form -->
                                        <input type="hidden" name="email" value="<%= request.getParameter("email") %>">
                                        <input type="hidden" name="otp" value="<%= request.getParameter("otp") %>">
                                        <div class="form-group">
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                    <i class="glyphicon glyphicon-lock color-blue"></i>
                                                </span>
                                                <input id="new-password" name="new-password" placeholder="Nhập mật khẩu mới" class="form-control" type="password" required>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                    <i class="glyphicon glyphicon-lock color-blue"></i>
                                                </span>
                                                <input id="confirm-password" name="confirm-password" placeholder="Nhập lại mật khẩu" class="form-control" type="password" required>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <input class="btn btn-lg btn-primary btn-block" value="Đặt lại mật khẩu" type="submit">
                                        </div>
                                    </fieldset>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<!-- Brandlogo Wrapper End -->

<div id="footer"><%@include file="component/footer.jsp"%></div>

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
<script>
    function validatePassword() {
        var password = document.getElementById("new-password").value;
        var retypePassword = document.getElementById("confirm-password").value;

        if (password !== retypePassword) {
            alert("Mật khẩu nhập lại không khớp. Vui lòng kiểm tra lại.");
            return false;
        }
        return true;
    }
</script>

</body>
</html>
