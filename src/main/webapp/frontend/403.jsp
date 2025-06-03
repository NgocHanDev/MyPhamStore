<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
    <title>403 Forbidden</title>
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
    <link
            rel="stylesheet"
            type="text/css"
            href="../static/css/font-awesome.css"
    />
    <link
            rel="stylesheet"
            type="text/css"
            href="../static/css/owl.carousel.css"
    />
    <link
            rel="stylesheet"
            type="text/css"
            href="../static/css/owl.theme.default.css"
    />
    <link
            rel="stylesheet"
            type="text/css"
            href="../static/css/magnific-popup.css"
    />
    <link rel="stylesheet" type="text/css" href="../static/css/fonts.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/dl-menu.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/camera.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/style.css" />
    <link
            rel="stylesheet"
            type="text/css"
            href="../static/css/responsive.css"
    />
    <link rel="stylesheet" type="text/css" href="../static/css/sidebar.css" />
    <!-- favicon links -->
    <link
            rel="shortcut icon"
            type="image/png"
            href="../static/images/header/favicon.png"
    />
</head>

<body>
<!-- preloader Start -->
<%--    <div id="preloader">--%>
<%--      <div id="status">--%>
<%--        <img--%>
<%--          src="../static/images/header/preloader.gif"--%>
<%--          id="preloader_image"--%>
<%--          alt="loader"--%>
<%--        />--%>
<%--      </div>--%>
<%--    </div>--%>
<!-- Top Scroll Start -->
<a href="javascript:" id="return-to-top"><i class="fa fa-angle-up"></i></a>
<!-- Top Scroll End -->
<!-- Header Wrapper Start -->
<div id="nav"><%@include file="component/nav.jsp"%></div>
<div id="header"><%@include file="component/header.jsp"%></div>
<!-- Header Wrapper End -->
<!-- ss inner title Wrapper Start -->
<div class="ss_inner_title_wrapper">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="ss_inner_title_cont_wrapper">
                    <ul>
                        <li>
                            <a href="#"><i class="fa fa-home"></i></a>   >
                        </li>
                        <li>403</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- jp 403 error wrapper start -->
<div class="error_page">
    <div class="container">
        <div class="row">
            <div
                    class="col-md-10 col-md-offset-1 col-sm-12 col-xs-12 text-center"
            >
                <div class="error_page_cntnt">
                    <h2>
                        <span>4</span>
                        <span>0</span>
                        <span>3</span>
                    </h2>
                    <h3>Xin lỗi, bạn không có quyền truy cập trang này!</h3>
                    <p>
                        Rất tiếc, bạn không có quyền truy cập vào tài nguyên này. Vui lòng kiểm tra lại thông tin đăng nhập hoặc liên hệ với quản trị viên để được hỗ trợ. Bạn có thể quay lại
                        <a href="home.jsp">Trang chủ</a> để tiếp tục khám phá trang web của chúng tôi.
                    </p>

                    <div class="error_page_mail_wrapper">
                        <a href="#"
                        ><i class="fa fa-envelope" aria-hidden="true"></i
                        ></a>
                        <input type="email" placeholder="Địa chỉ email *" />
                        <button type="submit" class="btn_send_email">Gửi Email</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- jp 403 error wrapper end -->

<!-- Footer Wrapper Start -->
<div id="footer"><%@include file="component/footer.jsp"%></div>
<!-- Footer Wrapper End -->
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
    $(window).on("load", function () {
        var wow = new WOW({
            boxClass: "wow",
            animateClass: "animated",
            offset: 0,
            mobile: true,
            live: true,
        });
        wow.init();
    });

    // CountDown Js
    var deadline = "December 31 2018 23:59:59 GMT+0530";
    function time_remaining(endtime) {
        var t = Date.parse(endtime) - Date.parse(new Date());
        var seconds = Math.floor((t / 1000) % 60);
        var minutes = Math.floor((t / 1000 / 60) % 60);
        var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
        var days = Math.floor(t / (1000 * 60 * 60 * 24));
        return {
            total: t,
            days: days,
            hours: hours,
            minutes: minutes,
            seconds: seconds,
        };
    }
    function run_clock(id, endtime) {
        var clock = document.getElementById(id);

        // get spans where our clock numbers are held
        var days_span = clock.querySelector(".days");
        var hours_span = clock.querySelector(".hours");
        var minutes_span = clock.querySelector(".minutes");
        var seconds_span = clock.querySelector(".seconds");

        function update_clock() {
            var t = time_remaining(endtime);

            // update the numbers in each part of the clock
            days_span.innerHTML = t.days;
            hours_span.innerHTML = ("0" + t.hours).slice(-2);
            minutes_span.innerHTML = ("0" + t.minutes).slice(-2);
            seconds_span.innerHTML = ("0" + t.seconds).slice(-2);

            if (t.total <= 0) {
                clearInterval(timeinterval);
            }
        }
        update_clock();
        var timeinterval = setInterval(update_clock, 1000);
    }
    run_clock("clockdiv", deadline);
</script>
</body>
</html>