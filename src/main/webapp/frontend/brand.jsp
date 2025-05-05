<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <title>Thương Hiệu</title>
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
    <link rel="stylesheet" type="text/css" href="../static/css/jquery-ui.min.css" />
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
    <!-- Slider Wrapper Start -->
    <!-- Slider Wrapper End -->
    <!-- ss inner title Wrapper Start -->
    <div class="ss_inner_title_wrapper">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="ss_inner_title_cont_wrapper">
                        <ul>
                            <li><a href="#"><i class="fa fa-home"></i></a>&nbsp;&nbsp; ></li>
                            <li>Sản Phẩm&nbsp;&nbsp; ></li>
                            <li>Thương hiệu&nbsp;&nbsp; </li>
                            <!-- <li>Lighting, Electronics</li> -->

                        </ul>
                        <div class="row" style="margin-top: 50px;">
                            <div class="row" style="margin-top: 50px;">
                                <c:forEach var="brand" items="${brands}">
                                    <a class="col-md-2 col-sm-3 col-xs-4" href="${pageContext.request.contextPath}/danh-muc?selectedBrands=${brand.id}" style="margin-top: 20px;">
                                        <img src="${brand.logo}" alt="${brand.name}" class="img-responsive">
                                    </a>
                                </c:forEach>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- ss inner title Wrapper End -->


    <!-- Brand Wrapper Start -->


    <!-- Brand end -->

    <!-- Footer Wrapper Start -->
    <%@include file="component/footer.jsp"%>
    <!-- Footer Wrapper End -->
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
    <script src="../static/js/jquery-ui.min.js"></script>
    <script src="../static/js/custom.js"></script>
    <script>
        $(window).on("load", function () {
            var wow = new WOW({
                boxClass: 'wow',
                animateClass: 'animated',
                offset: 0,
                mobile: true,
                live: true
            });
            wow.init();
        });

        $(function () {
            var Accordion = function (el, multiple) {
                this.el = el || {};
                this.multiple = multiple || false;

                // Variables privadas
                var links = this.el.find('.link');
                // Evento
                links.on('click', { el: this.el, multiple: this.multiple }, this.dropdown)
            }

            Accordion.prototype.dropdown = function (e) {
                var $el = e.data.el;
                $this = $(this),
                    $next = $this.next();

                $next.slideToggle();
                $this.parent().toggleClass('open');

                if (!e.data.multiple) {
                    $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
                };
            }

            var accordion = new Accordion($('#accordion2, #accordion3, #accordion4, #accordion5, #accordion6, #accordion7, #accordion8, #accordion9, #accordion10, #accordion11, #accordion12, #accordion13'), false);
        });

        $("#range-price").slider({
            range: true,
            min: 50_000,
            max: 10_000_000,
            values: [50_000, 10_000_000],
            slide: function (event, ui) {
                $("#price").val(ui.values[0] + "đ" + " - " + ui.values[1] + "đ");
            }
        });

        $("#price").val($("#range-price").slider("values", 0) + "đ" +
            " - " + $("#range-price").slider("values", 1) + "đ");

        $("#range-price2").slider({
            range: true,
            min: 50_000,
            max: 10_000_000,
            values: [50_000, 10_000_000],
            slide: function (event, ui) {
                $("#price2").val(ui.values[0] + "đ" + " - " + ui.values[1] + " đ");
            }
        });

        $("#price2").val($("#range-price2").slider("values", 0) + "đ" +
            " - " + $("#range-price2").slider("values", 1) + "đ");
    </script>
    <!--main js file end-->
</body>

</html>