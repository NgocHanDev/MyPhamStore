
<%--
Created by IntelliJ IDEA.
User: cucsh
Date: 12/7/2024
Time: 10:10 AM
To change this template use File | Settings | File Templates.
--%>
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
  <title>Giỏ hàng</title>
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
<div id="preloader">
  <div id="status">
<%--    <img--%>
<%--            src="images/header/preloader.gif"--%>
<%--            id="preloader_image"--%>
<%--            alt="loader"--%>
<%--    />--%>
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
            <li>
              <a href="<c:url value="/trang-chu" />"><i class="fa fa-home"></i></a>&nbsp;&nbsp; >
            </li>
            <li>Sản phẩm&nbsp;&nbsp; >
              <a href="<c:url value="/danh-muc" />"></a>&nbsp;&nbsp; >
            </li>
            <li>Mua sắm&nbsp;&nbsp; ></li>
            <li>Giỏ hàng
              <a href="<c:url value="/gio-hang" />"></a>&nbsp;&nbsp; >
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
<!--cart product wrapper Wrapper Start -->
<div class="cart_product_wrapper">
  <div class="container">
    <div class="row">
      <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div
                class="btc_shop_single_prod_right_section shop_product_single_head related_pdt_shop_head"
        >
          <h1>Giỏ hàng của bạn</h1>
        </div>
      </div>
      <c:choose>
        <c:when test="${not empty sessionScope.cart}">
          <div class="shop_cart_page_wrapper">
            <div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
              <div class="table-responsive cart-calculations">
                <table class="table" >
                  <thead class="cart_table_heading">
                  <tr>
                    <th>Sản phẩm</th>

                    <th>Tên sản phẩm</th>
                    <th>&nbsp;</th>
                    <th>Loại</th>
                    <th>Giá</th>

                    <th>Số lượng</th>

                    <th>Tổng cộng:</th>
                    <th>&nbsp;    </th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="i" items="${listCartDisplay}">
                    <c:set var="variant" value="${i.variant}"/>
                    <tr>
                      <td>
                        <div class="table_cart_img">
                          <figure>
                            <img src="${i.product.thumbnail}" alt="${i.product.name}" style="max-height: 80px" />
                          </figure>
                        </div>
                      </td>
                      <td>
                        <div class="table_cart_cntnt">
                          <h1>${i.product.name}</h1>
                        </div>
                      </td>
                      <c:if test="${variant == null}">
                       <td> sản phẩm gốc</td>
                      </c:if>
                      <c:if test="${variant != null}">
                        <td>${variant.name}</td>
                      </c:if>
                      <td class="cart_page_price">
                        <c:choose>
                          <c:when test="${variant != null}">${variant.price}đ</c:when>
                          <c:otherwise>${i.product.price}đ</c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                      <td>
                        <form method="post" action="/gio-hang">
                          <input type="hidden" name="action" value="updateCart" />
                          <input type="hidden" name="productId" value="${i.product.id}" />
                          <input type="number" name="quantity" value="${i.quantity}" min="1" onchange="this.form.submit()" />
                        </form>
                      </td>
                      <td class="cart_page_totl">
                        <c:choose>
                          <c:when test="${variant != null}">${variant.price * i.quantity}đ</c:when>
                          <c:otherwise>${i.product.price * i.quantity}đ</c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <form method="post" action="/gio-hang">
                          <input type="hidden" name="action" value="remove" />
                          <input type="hidden" name="productId" value="${i.product.id}" />
                          <button type="submit" class="remove-btn">
                            <i class="fa fa-trash"></i>
                          </button>
                        </form>
                      </td>
                    </tr>
                  </c:forEach>
                  <tr>
                    <td></td>
                    <td class="shop_btn_wrapper shop_car_btn_wrapper">
                      <ul>
                        <li><a href="/trang-chu">Tiếp tục mua sắm</a></li>
                      </ul>
                    </td>
                    <td></td>
                    <td></td>
                    <td class="cart_btn_cntnt">
                      Tổng cộng: <span>${totalAmount}đ</span>
                    </td>
                    <td></td>
                  </tr>
                  </tbody>

                </table>
              </div>

              <div class="estimate_shiping_Wrapper_cntnt estimate_shiping_Wrapper_cntnt_2">
                <div class="btc_shop_single_prod_right_section shop_product_single_head related_pdt_shop_head related_pdt_shop_head_2">
                  <h1>Nhập mã giảm giá tại đây:</h1>
                  <form method="post" action="/gio-hang">
                    <div class="lr_nl_form_wrapper">
                      <input type="text" name="discountCode" placeholder="Nhập mã giảm giá của bạn" />
                      <button type="submit">Xác nhận</button>
                    </div>
                  </form>

                  <c:if test="${not empty discountCodes}">
                    <h2>Các mã giảm giá:</h2>
                    <ul id="discountCodesList">
                      <c:forEach var="code" items="${discountCodes}" varStatus="status">
                        <c:if test="${status.index < 3}">
                          <li>${code.code} - ${code.discountValue}</li>
                        </c:if>
                      </c:forEach>
                    </ul>
                    <c:if test="${discountCodes.size() > 3}">
                      <button id="seeMoreBtn" onclick="showMoreCodes()">Xem thêm</button>
                    </c:if>
                  </c:if>


                </div>
              </div>

            </div>

            <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
              <div class="shipping_Wrapper">
                <table class="table">
                  <tbody>
                  <div class="cart-summary">
                    <p>Tổng giá: ${totalAmount}đ</p>
                    <c:if test="${discountAmount > 0}">
                      <p>Giảm giá: -${discountAmount}đ</p>
                    </c:if>
                    <p>Thành tiền: ${finalAmount}đ</p>
                  </div>
                </table>
                <div class="shop_btn_wrapper shop_btn_wrapper_shipping">
                  <ul>
                    <li><a href="<c:url value="/checkout?action=display"/> ">Thanh toán</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <h1>Giỏ hàng của bạn đang trống!</h1>
          <td class="shop_btn_wrapper shop_car_btn_wrapper">
            <ul>
              <li><a href="/trang-chu"><h3>>Tiếp tục mua sắm</h3></a></li>
            </ul>
          </td>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
</div>
<!-- cart product wrapper end -->
<!-- Footer Wrapper Start -->
<%@include file="component/footer.jsp"%>
<!-- Footer Wrapper End -->

<script>
  const header = document.getElementById("header");
  const footer = document.getElementById("footer");
  const nav = document.getElementById("nav");

  fetch("./header.jsp")
          .then((response) => {
            return response.text();
          })
          .then((data) => {
            header.innerHTML = data;
          });
  fetch("./footer.jsp")
          .then((response) => {
            return response.text();
          })
          .then((data) => {
            footer.innerHTML = data;
          });
  fetch("./nav.jsp")
          .then((response) => {
            return response.text();
          })
          .then((data) => {
            nav.innerHTML = data;
          });
</script>

<script>
  function showMoreCodes() {
    const list = document.getElementById('discountCodesList');
    list.innerHTML = '';
    <c:forEach var="code" items="${discountCodes}">
    list.innerHTML += `<li>${code.code} - ${code.description}</li>`;
    </c:forEach>
    document.getElementById('seeMoreBtn').style.display = 'none';
  }
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
</script>
<!--main js file end-->
</body>
</html>
