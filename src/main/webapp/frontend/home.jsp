<%@include file="/common/tablib.jsp"%>
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
<!--<![endif]-->

<head>
	<meta charset="utf-8" />
	<title>Trang chủ</title>
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
	<!-- Top Scroll End -->
	<!-- Header Wrapper Start -->
	 <%@include file="component/nav.jsp"%>
	 <%@include file="component/header.jsp"%>
	<!-- Header Wrapper End -->
	


	<!-- Slider Wrapper End -->
	<!-- <div class="ss_top_slider_wrapper">
		<div id="theme-main-banner">
				<div data-src="../static/images/slider/slider1.jpg">
				</div>
		</div>
	</div> -->
	<div class="bs-example" style="margin-top: 210px">
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Carousel indicators -->
			<ol class="carousel-indicators">
				<c:forEach var="slide" items="${slides}" varStatus="status">
					<li data-target="#myCarousel" data-slide-to="${status.index}" class="${status.first ? 'active' : ''}"></li>
				</c:forEach>
			</ol>

			<!-- Wrapper for carousel items -->
			<div class="carousel-inner">
				<c:forEach var="slide" items="${slides}" varStatus="status">
					<div class="item ${status.first ? 'active' : ''}">
						<img src="${slide.image}" width="100%" class="img-fluid" alt="Slide ${status.index + 1}">
					</div>
				</c:forEach>
			</div>

			<!-- Carousel controls -->
			<a class="carousel-control left" href="#myCarousel" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left"></span>
			</a>
			<a class="carousel-control right" href="#myCarousel" data-slide="next">
				<span class="glyphicon glyphicon-chevron-right"></span>
			</a>
		</div>
	</div>
	<!-- Slider Wrapper End -->


	<!-- Service Wrapper Start -->


	<div class="ss_services_wrapper" style="padding-bottom: 0;">
		<div class="container custom-container">
			<div class="row">
				<div class="col-lg-3 col-md-3 col-sm-12">
					<div class="service-img">
						<img src="https://file.hstatic.net/1000006063/file/banner_thuong_hieu_noi_bat_pc_489_x_955_76d4bb5104914396b78f2db70ae4c231.png" alt="" height="500px">
					</div>
				</div>
				<div class="col-lg-9 col-md-9 col-sm-12 col-12">
					<div class="row">
						<div class="ss_latest_products product-three-service">
							<div class="owl-carousel owl-theme">
								<c:forEach var="product" items="${products}">
									<div class="item">
										<div class="ss_featured_products_box">
											<div class="ss_featured_products_box_img">
												<span class="ss_tag">mới</span>
												<span class="ss_offer">giảm 20%</span>
												<img src="${product.thumbnail}" alt="Product" class="img-responsive">
											</div>
											<div class="ss_feat_prod_cont_heading_wrapper">
												<h4><a class="limited-text" href="<c:url value="/product-detail?id=${product.id}"  />">${product.name}</a></h4>

												<del class="price">${product.price}</del>
												<ins class="price">${product.price - (product.price * 0.2)}</ins>
											</div>
											<div class="ss_featured_products_box_footer">
												<fieldset class="rating">
													<input type="radio" name="rating" value="5" />
													<label class="full" title="5 stars"></label>
													<input type="radio" name="rating" value="4 and a half" />
													<label class="half" title="4.5 stars"></label>
													<input type="radio" name="rating" value="4" />
													<label class="full" title="4 stars"></label>
													<input type="radio" name="rating" value="3 and a half" />
													<label class="half" title="3.5 stars"></label>
													<input type="radio" name="rating" value="3" />
													<label class="full" title="3 stars"></label>
													<input type="radio" name="rating" value="2 and a half" />
													<label class="half" title="2.5 stars"></label>
													<input type="radio" name="rating" value="2" />
													<label class="full" title="2 stars"></label>
													<input type="radio" name="rating" value="1 and a half" />
													<label class="half" title="1.5 stars"></label>
													<input type="radio" name="rating" value="1" />
													<label class="full" title="1 star"></label>
													<input type="radio" name="rating" value="half" />
													<label class="half" title="0.5 stars"></label>
												</fieldset>
												<ul style="    display: flex
;
    padding-top: 15px;
    justify-content: center;">
													<form method="post" action="/gio-hang">
														<input type="hidden" name="action" value="add">
														<input type="hidden" name="productId" value="${product.id}">
														<button type="submit" class="ss_btn">Thêm vào giỏ</button>
													</form>
													<li>
														<form method="post" action="<c:url value='/wishlist' />">
															<input type="hidden" name="action" value="add">
															<input type="hidden" name="productId" value="${product.id}">
															<button type="submit" class="fa fa-heart" aria-hidden="true"></button>
														</form>
													</li>
												</ul>
											</div>

										</div>
									</div>

								</c:forEach>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Service Wrapper End -->
	<!-- Featured Products Wrapper Start -->
	<div class="ss_featured_products_wrapper" style="padding-top: 0;">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="ss_featured_products_tab">
						<div class="ss_heading">
							<h3>Danh Mục Hot</h3>
						</div>
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#best" aria-controls="best" role="tab" data-toggle="tab">Da Mặt</a></li>
							<li role="presentation"><a href="#hot" aria-controls="hot" role="tab" data-toggle="tab">Body</a></li>
							<li role="presentation"><a href="#trand" aria-controls="trand" role="tab" data-toggle="tab">Trang Điểm</a></li>
						</ul>
					</div>
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane fade in active" id="best">
							<div class="ss_featured_products">
								<div class="owl-carousel owl-theme">
									<c:if test="${empty skinCare}">
										<p>Không có sản phẩm nào để hiển thị trong danh mục này.</p>
									</c:if>
									<c:forEach var="product" items="${skinCare}">
										<div class="item">
											<div class="ss_featured_products_box">
												<div class="ss_featured_products_box_img">
													<span class="ss_tag">Mới</span>
													<span class="ss_offer">Giảm 20%</span>
													<img src="${product.thumbnail != null ? product.thumbnail : '/path/to/default/image.jpg'}" alt="Product" class="img-responsive">
												</div>
												<div class="ss_feat_prod_cont_heading_wrapper">
													<h4>
														<a class="limited-text" href="<c:url value='/product-detail?id=${product.id}' />">${product.name}</a>
													</h4>

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
														<li>
															<form method="post" action="<c:url value='/wishlist' />">
																<input type="hidden" name="action" value="add">
																<input type="hidden" name="productId" value="${product.id}">
																<button type="submit" class="fa fa-heart" aria-hidden="true"></button>
															</form>
														</li>
													</ul>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
						<div role="tabpanel" class="tab-pane fade" id="hot">
							<div class="ss_featured_products">
								<div class="owl-carousel owl-theme">
									<c:if test="${empty bodyCare}">
										<p>Không có sản phẩm nào để hiển thị trong danh mục này.</p>
									</c:if>
									<c:forEach var="product" items="${bodyCare}">
										<div class="item">
											<div class="ss_featured_products_box">
												<div class="ss_featured_products_box_img">
													<span class="ss_tag">Mới</span>
													<span class="ss_offer">Giảm 20%</span>
													<img src="${product.thumbnail != null ? product.thumbnail : '/path/to/default/image.jpg'}" alt="Product" class="img-responsive">
												</div>
												<div class="ss_feat_prod_cont_heading_wrapper">
													<h4>
														<a class="limited-text" href="<c:url value='/product-detail?id=${product.id}' />">${product.name}</a>
													</h4>
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
														<li>
															<form method="post" action="<c:url value='/wishlist' />">
																<input type="hidden" name="action" value="add">
																<input type="hidden" name="productId" value="${product.id}">
																<button type="submit" class="fa fa-heart" aria-hidden="true"></button>
															</form>
														</li>
													</ul>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
						<div role="tabpanel" class="tab-pane fade" id="trand">
							<div class="ss_featured_products">
								<div class="owl-carousel owl-theme">
									<c:if test="${empty makeUp}">
										<p>Không có sản phẩm nào để hiển thị trong danh mục này.</p>
									</c:if>
									<c:forEach var="product" items="${makeUp}">
										<div class="item">
											<div class="ss_featured_products_box">
												<div class="ss_featured_products_box_img">
													<span class="ss_tag">Mới</span>
													<span class="ss_offer">Giảm 20%</span>
													<img src="${product.thumbnail != null ? product.thumbnail : '/path/to/default/image.jpg'}" alt="Product" class="img-responsive">
												</div>
												<div class="ss_feat_prod_cont_heading_wrapper">
													<h4>
														<a class="limited-text" href="<c:url value='/product-detail?id=${product.id}' />">${product.name}</a>
													</h4>
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
														<li>
															<form method="post" action="<c:url value='/wishlist' />">
																<input type="hidden" name="action" value="add">
																<input type="hidden" name="productId" value="${product.id}">
																<button type="submit" class="fa fa-heart" aria-hidden="true"></button>
															</form>
														</li>
													</ul>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

		<div class="ss_latest_products_wrapper" style="padding-bottom: 0;">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">
						<div class="ss_featured_products_tab">
							<div class="ss_heading">
								<h3>Sản Phẩm Mới Ra Mắt</h3>
							</div>
						</div>
						<div class="ss_latest_products">
							<div class="owl-carousel owl-theme">
								<c:forEach var="product" items="${latestProducts}">
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
												<ul style="    display: flex
;
    padding-top: 15px;
    justify-content: center;">
													<form method="post" action="/gio-hang">
														<input type="hidden" name="action" value="add">
														<input type="hidden" name="productId" value="${product.id}">
														<button type="submit" class="ss_btn">Thêm vào giỏ</button>
													</form>

													<li>
														<form method="post" action="<c:url value='/wishlist' />">
															<input type="hidden" name="action" value="add">
															<input type="hidden" name="productId" value="${product.id}">
															<button type="submit" class="fa fa-heart" aria-hidden="true"></button>
														</form>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>

	<!-- Latest Products Wrapper End -->
	
	
	
	<!-- Brandlogo Wrapper Start -->
	<div class="ss_brandlogo_wrapper">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="ss_heading_box">
						<div class="ss_heading">
							<h3>Các Thương Hiệu</h3>
						</div>
					</div>
					<div class="ss_brandlogo_slider">
						<div class="owl-carousel owl-theme">
							<c:forEach var="brand" items="${brands}">
								<div class="item">
									<div class="ss_featured_products_box">
										<div class="ss_featured_products_box_img">
											<span class="ss_tag">mới</span>
											<a href="/danh-muc?selectedBrands=${brand.id}">
												<img src="${brand.logo != null ? brand.logo : '/path/to/default/image.jpg'}" alt="Brand Logo"/>
											</a>
										</div>
									</div>
								</div>
							</c:forEach>
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
	<!--main js file end-->
	<!-- demo feature -->
	 <script src="../static/js/demo/login-label.js"></script>

	<script>
		function formatPrice(price, currencySymbol, delimiter) {
			return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, delimiter) + ' ' + currencySymbol;
		}

		document.addEventListener("DOMContentLoaded", function() {
			document.querySelectorAll(".price").forEach(function(element) {
				let price = parseFloat(element.textContent.replace(/[^0-9.-]+/g,""));
				element.textContent = formatPrice(price, "VND", ".");
			});
		});
	</script>

</body>
</html>