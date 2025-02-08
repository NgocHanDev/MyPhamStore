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
    <title>Đổi mật khẩu</title>
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
    <link rel="stylesheet" type="text/css" href="../static/css/change-pass.css" />
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
    <!-- Header Wrapper Start -->
    <%@include file="component/nav.jsp"%>
    <!-- Header Wrapper End -->
    <!-- Main Content -->

    <div class="container-fluid">
      <div class="row main-content bg-success text-center" style="width: 30%">
        <div class="col-md-12 col-xs-12 col-sm-12 login_form">
          <div class="container-fluid">
            <div class="row">
              <h2>Đổi mật khẩu</h2>
            </div>
            <div class="row">
              <form action="/change-password" method="post">
                <div class="form-group">
                  <label for="password">Mật khẩu cũ:</label>
                  <input type="password" id="password" name="password" class="form-control" required>
                </div>
                <div class="form-group">
                  <label for="new-password">Mật khẩu mới:</label>
                  <input type="password" id="new-password" name="new-password" class="form-control" required>
                </div>
                <div class="form-group">
                  <label for="re-new-password">Nhập lại mật khẩu mới:</label>
                  <input type="password" id="re-new-password" name="re-new-password" class="form-control" required>
                </div>
                <div class="row">
                  <input type="submit" value="Đổi mật khẩu" class="btn" />
                </div>
              </form>
              <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
              </c:if>
              <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
              </c:if>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Brandlogo Wrapper End -->
    <%@include file="component/footer.jsp"%>

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
