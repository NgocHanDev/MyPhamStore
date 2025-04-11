<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Quên mật khẩu</title>
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
<%--    <link rel="stylesheet" type="text/css" href="../static/css/change-pass.css" />--%>
    <!-- favicon links -->
    <link rel="shortcut icon" type="image/png" href="../static/images/header/favicon.png" />
    <link
            rel="stylesheet"
            type="text/css"
            href="../static/css/responsive.css"
    />
    <link rel="stylesheet" type="text/css" href="../static/css/style.css" />
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
                  <div class="panel-body">

                    <form action="forgot-password" method="post">
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
                        <div class="form-group">
                          <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-envelope color-blue"></i></span>

                            <input id="email" name="email" placeholder="Địa chỉ email" class="form-control" type="email" oninvalid="setCustomValidity('Vui lòng nhập email hợp lệ!')" onchange="try{setCustomValidity('')}catch(e){}" required="">
                          </div>
                        </div>
                        <div class="form-group">
                          <input class="btn btn-lg btn-primary btn-block" value="Gửi cho tôi email" type="submit">
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
