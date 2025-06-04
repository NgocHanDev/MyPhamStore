<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
Created by IntelliJ IDEA.
User: cucsh
Date: 12/7/2024
Time: 10:10 AM
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Thể loại</title>
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
<style>
	.limited-text {
		display: -webkit-box; /* Sử dụng flexbox ẩn nội dung */
		-webkit-box-orient: vertical; /* Đặt hướng box theo chiều dọc */
		overflow: hidden; /* Ẩn phần văn bản bị tràn */
		text-overflow: ellipsis; /* Thêm dấu "..." */
		-webkit-line-clamp: 2; /* Giới hạn số dòng hiển thị */
		line-height: 1.5; /* Đặt chiều cao dòng để tính dòng */
		max-height: calc(1.5em * 2); /* Chiều cao tối đa tương ứng với 2 dòng */
	}
</style>
<body>
<!-- preloader Start -->
<div id="preloader">
	<div id="status">
		<img src="../static/images/header/preloader.gif" id="preloader_image" alt="loader">
	</div>
</div>
<!-- Top Scroll Start -->
<a href="javascript:" id="return-to-top"><i class="fa fa-angle-up"></i></a>
<!-- Header Wrapper Start -->
<%@include file="component/nav.jsp"%>
<%@include file="component/header.jsp"%>
<!-- Header Wrapper End -->
<!-- ss inner title Wrapper Start -->

<div class="cc_pc_accordion_main_wrapper">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="ss_inner_title_cont_wrapper">
					<ul>
						<li>
							<a href="#"><i class="fa fa-home"></i></a>&nbsp;&nbsp; >
						</li>
						<li>Sản phẩm&nbsp;&nbsp; ></li>
						<li>Sản phẩm yêu thích</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col-lg-9  col-md-9 col-sm-12 col-xs-12 sidebar2_main_wrapper">
			<div class="ss_latest_products product-three-service">
<%--				<div class="owl-carousel owl-theme">--%>
<%--					<c:forEach var="product" items="${productsWishlist}">--%>
<%--						<div class="item">--%>
<%--							<div class="ss_featured_products_box">--%>
<%--								<div class="ss_featured_products_box_img">--%>
<%--									<span class="ss_tag">Mới</span>--%>
<%--									<span class="ss_offer">Giảm 20%</span>--%>
<%--									<img src="${product.thumbnail != null ? product.thumbnail : '/path/to/default/image.jpg'}" alt="Product" class="img-responsive">--%>
<%--								</div>--%>
<%--								<div class="ss_feat_prod_cont_heading_wrapper">--%>
<%--									<h4>--%>
<%--										<a class="limited-text" href="<c:url value='/chi-tiet-san-pham?id=${product.id}' />">${product.name}</a>--%>
<%--									</h4>--%>
<%--									<del>${product.price}</del>--%>
<%--									<ins>${product.price - (product.price * 0.2)}</ins>--%>
<%--								</div>--%>
<%--								<div class="ss_featured_products_box_footer">--%>
<%--									<ul style="display: flex; padding-top: 15px; justify-content: center;">--%>
<%--										<form method="post" action="/gio-hang">--%>
<%--											<input type="hidden" name="action" value="add">--%>
<%--											<input type="hidden" name="productId" value="${product.id}">--%>
<%--											<button type="submit" class="ss_btn">Thêm vào giỏ</button>--%>
<%--										</form>--%>
<%--										<form method="post" action="<c:url value='/wishlist' />">--%>
<%--											<input type="hidden" name="action" value="remove">--%>
<%--											<input type="hidden" name="productId" value="${product.id}">--%>
<%--											<button type="submit" class="remove-wishlist-btn">Xóa</button>--%>
<%--										</form>--%>
<%--									</ul>--%>
<%--								</div>--%>
<%--							</div>--%>
<%--						</div>--%>
<%--					</c:forEach>--%>
<%--				</div>--%>
	<div class="ss_latest_products" style="">
		<div class="owl-carousel owl-theme">
			<c:forEach var="product" items="${productsWishlist}">
				<div class="item">
					<div class="ss_featured_products_box">
						<div class="ss_featured_products_box_img">
							<span class="ss_tag">mới</span>
							<img src="${product.thumbnail}" alt="${product.name}" class="img-responsive">
						</div>
						<div class="ss_feat_prod_cont_heading_wrapper">
							<h4><a class="limited-text" href="<c:url value="/product-detail?id=${product.id}" />">${product.name}</a></h4>
							<del class="price">${product.price}</del>
							<ins class="price">${product.price - (product.price * 0.2)}</ins>
						</div>
						<div class="ss_featured_products_box_footer">
							<ul style="display: flex; padding-top: 15px; justify-content: center;">
								<form method="post" action="/gio-hang">
									<input type="hidden" name="action" value="add">
									<input type="hidden" name="productId" value="${product.id}">
									<button type="submit" class="ss_btn">Thêm vào giỏ</button>
								</form>
								<form method="post" action="<c:url value='/wishlist' />">
									<input type="hidden" name="action" value="remove">
									<input type="hidden" name="productId" value="${product.id}">
									<button type="submit" class="remove-wishlist-btn">Xóa</button>
								</form>
							</ul>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
				<!-- Phân trang -->
				<div class="col-lg-12 col-md-12 hidden-sm hidden-xs">
					<div class="pager_wrapper gc_blog_pagination">
						<ul class="pagination">
							<c:if test="${currentPage > 1}">
								<li><a href="?currentPage=${currentPage - 1}&pageSize=6">Previous</a></li>
							</c:if>
							<c:forEach var="i" begin="1" end="${totalPages}">
								<li class="${i == currentPage ? 'active' : ''}">
									<a href="?currentPage=${i}&pageSize=4">${i}</a>
								</li>
							</c:forEach>
							<c:if test="${currentPage < totalPages}">
								<li><a href="?currentPage=${currentPage + 1}&pageSize=6">Next</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="component/footer.jsp"%>

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
	$(window).on("load", function() {
		var wow = new WOW({
			boxClass: 'wow',
			animateClass: 'animated',
			offset: 0,
			mobile: true,
			live: true
		});
		wow.init();
	});
	$(".owl-carousel").owlCarousel({
		loop: false, // Tắt vòng lặp
	});



</script>

</script>
</body>
</html>