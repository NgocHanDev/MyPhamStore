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
	<div class="ss_inner_title_wrapper">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="ss_inner_title_cont_wrapper">
						<ul>
							<li><a href="#"><i class="fa fa-home"></i></a>&nbsp;&nbsp; ></li>
							<li>Sản Phẩm&nbsp;&nbsp; ></li>
							<li>Loại sản phẩm&nbsp;&nbsp; </li>
							<!-- <li>Lighting, Electronics</li> -->
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- ss inner title Wrapper End -->
	<!-- CC pc sidebar accordion Start -->
	<div class="cc_pc_accordion_main_wrapper">
		<div class="container">
			<div class="row">
				<div class="col-lg-3 col-md-3 hidden-sm hidden-xs">
					<div class="cc_pc_first_accordion_wrapper cc_pc_second_accordion_wrapper2">
						<form id="filterForm" method="get" action="/danh-muc">
							<div class="cc_pc_accordion">
								<ul id="accordion1" class="accordion">
									<li class="default open">
										<div class="link cc_product_heading">Loại sản phẩm<i class="fa fa-chevron-down"></i></div>
										<ul class="submenu">
											<c:forEach var="category" items="${categories}">
												<li>

													<input type="checkbox" id="category${category.id}" name="selectedCategories" value="${category.id}"<c:if test="${fn:contains(selectedCategories, category.id.toString())}">checked</c:if>>
													<label for="category${category.id}">${category.name}</label>
												</li>
											</c:forEach>
										</ul>
									</li>
								</ul>
							</div>
							<div class="cc_pc_accordion">
								<ul id="accordion4" class="accordion">
									<li class="default open">
										<div class="link cc_product_heading">Thương Hiệu<i class="fa fa-chevron-down"></i></div>
										<ul class="submenu">
											<c:forEach var="brand" items="${brands}">
												<li>
													<input type="checkbox" id="brand${brand.id}" name="selectedBrands" value="${brand.id}" <c:if test="${fn:contains(selectedBrands, brand.id)}">checked</c:if>>
													<label for="brand${brand.id}">${brand.name}</label>
												</li>
											</c:forEach>
										</ul>
									</li>
								</ul>
							</div>
							<div class="cc_pc_accordion">
								<ul id="accordion3" class="accordion">
									<li class="default open">
										<div class="link cc_product_heading">Giá<i class="fa fa-chevron-down"></i></div>
										<ul class="submenu price-range">
											<li class="range">
												<div id="range-price" class="range-box"></div>
												<input type="text" id="price" name="priceRange" readonly style="border:0; color:#f6931f; font-weight:bold;" value="${priceRange}">
											</li>
										</ul>
									</li>
								</ul>
							</div>
							<button type="submit" class="btn btn-primary">Lọc sản phẩm</button>
						</form>
					</div>
					<c:if test="${noProductsFound}">
						<p>Không có sản phẩm nào phù hợp với các điều kiện lọc.</p>
					</c:if>
				</div>
				<div class="col-lg-9  col-md-9 col-sm-12 col-xs-12 sidebar2_main_wrapper">
					<div class="sidebar_widget2">
						<div class="filter-area">
							<div class="showpro">
								<p><span>Hiển thị 1-9</span> trên 256 sản phẩm</p>
							</div>
							<select>
								<option>Sắp xếp theo</option>
								<option>Thương Hiệu</option>
								<option>Loại Sản Phẩm</option>
								<option>Giá</option>
							</select>
							<div class="list-grid">
								<ul class="list-inline nav nav-pills">
									<li class="active"><a data-toggle="pill" href="#grid"><i class="fa fa-th-large"></i></a>
									</li>
									<li><a data-toggle="pill" href="#list"><i class="fa fa-list"></i></a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="tab-content">
								<div id="grid" class="tab-pane fade in active">
									<div class="row " style="column-gap: 20px;">
										<c:forEach var="product" items="${products}">
											<div class="col-lg-4 col-md-6 col-sm-6 col-xs-12 prs_upcom_slide_first">
												<div class="ss_featured_products_box">
													<div class="ss_featured_products_box_img">
														<span class="ss_tag">mới</span>
														<span class="ss_offer">giảm 20%</span>
														<img src="${product.thumbnail}" alt="Product" class="img-responsive">
													</div>
													<div class="ss_feat_prod_cont_heading_wrapper">
														<h4><a class="limited-text" href="product_detail.jsp">${product.name}</a></h4>
														<del class="price">${product.price}</del>
														<ins class="price">${product.price - (product.price * 0.2)}</ins>
													</div>
													<div class="ss_featured_products_box_footer">
														<a href="/chi-tiet-san-pham?id=${product.id}">Chi tiết sản phẩm</a>
														<fieldset class="rating">
															<input type="radio" name="rating${product.id}" value="5" />
															<label class="full" title="5 stars"></label>
															<input type="radio" name="rating${product.id}" value="4 and a half" />
															<label class="half" title="4.5 stars"></label>
															<input type="radio" name="rating${product.id}" value="4" />
															<label class="full" title="4 stars"></label>
															<input type="radio" name="rating${product.id}" value="3 and a half" />
															<label class="half" title="3.5 stars"></label>
															<input type="radio" name="rating${product.id}" value="3" />
															<label class="full" title="3 stars"></label>
															<input type="radio" name="rating${product.id}" value="2 and a half" />
															<label class="half" title="2.5 stars"></label>
															<input type="radio" name="rating${product.id}" value="2" />
															<label class="full" title="2 stars"></label>
															<input type="radio" name="rating${product.id}" value="1 and a half" />
															<label class="half" title="1.5 stars"></label>
															<input type="radio" name="rating${product.id}" value="1" />
															<label class="full" title="1 star"></label>
															<input type="radio" name="rating${product.id}" value="half" />
															<label class="half" title="0.5 stars"></label>
														</fieldset>
														<ul>
															<form method="post" action="/gio-hang">
																<input type="hidden" name="action" value="add">
																<input type="hidden" name="productId" value="${product.id}">
																<button type="submit" class="ss_btn">Thêm vào giỏ</button>
															</form>

															<li><a href="#" class="fa fa-heart" data-product-id="${product.id}" aria-hidden="true"></a></li>

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
					<div class="col-lg-12 col-md-12 hidden-sm hidden-xs">
						<!-- blog_pagination_section start -->
						<div class="pager_wrapper gc_blog_pagination">
							<ul class="pagination">
								<c:if test="${currentPage > 1}">
									<li><a href="?currentPage=${currentPage - 1}&pageSize=${pageSize}&keyword=${keyword}&orderBy=${orderBy}&priceRange=${priceRange}">Previous</a></li>
								</c:if>
								<c:forEach var="i" begin="1" end="${totalPages}">
									<li class="<c:if test='${i == currentPage}'>active</c:if>">
										<a href="?currentPage=${i}&pageSize=${pageSize}&keyword=${keyword}&orderBy=${orderBy}&priceRange=${priceRange}">${i}</a>
									</li>
								</c:forEach>
								<c:if test="${currentPage < totalPages}">
									<li><a href="?currentPage=${currentPage + 1}&pageSize=${pageSize}&keyword=${keyword}&orderBy=${orderBy}&priceRange=${priceRange}">Next</a></li>
								</c:if>
							</ul>
						</div>
						<!-- blog_pagination_section end -->
					</div>
				</div>
				<div class="col-lg-3 col-md-3 visible-sm visible-xs">
					<div class="cc_pc_first_accordion_wrapper cc_pc_second_accordion_wrapper2">
						<div class="cc_pc_accordion">
							<ul id="accordion8" class="accordion">
								<li class="default open">
									<div class="link cc_product_heading">Loại sản phẩm<i class="fa fa-chevron-down"></i>
									</div>
									<ul class="submenu">
									</ul>
								</li>
							</ul>
						</div>
					</div>
					<div class="cc_pc_first_accordion_wrapper cc_pc_second_accordion_wrapper">
						<div class="cc_pc_accordion">
							<ul id="accordion9" class="accordion">
								<li class="default open">
									<div class="link cc_product_heading">Giá<i class="fa fa-chevron-down"></i>
									</div>
									<ul class="submenu price-range">
										<li class="range">
											<div id="range-price2" class="range-box"></div> <span>tới</span>
											<input type="text" id="price2" class="price-box" readonly/>
										</li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
					<div class="cc_pc_first_accordion_wrapper cc_pc_second_accordion_wrapper">
						<div class="cc_pc_accordion">
							<ul id="accordion10" class="accordion">
								<li class="default open">
									<div class="link cc_product_heading">Thương hiệu<i class="fa fa-chevron-down"></i>
									</div>
									<ul class="submenu">

									</ul>
								</li>
							</ul>
						</div>
					</div>
					<div class="ss_addver_sidebar ss_addver_sidebar_inner">
						<a href="#">
							<img src="images/content/add1.jpg" alt="Add">
						</a>
					</div>
					<div class="col-lg-12 col-md-12 visible-sm visible-xs">
						<!-- blog_pagination_section start -->
						<div class="pager_wrapper gc_blog_pagination">

						</div>
						<!-- blog_pagination_section end -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- CC pc sidebar accordion End -->
	<!-- Footer Wrapper Start -->

	<%@include file="component/footer.jsp"%>


	<!-- Footer Wrapper End -->
	<script>
		$(function() {
			var Accordion = function(el, multiple) {
				this.el = el || {};
				this.multiple = multiple || false;

				// Private variables
				var links = this.el.find('.link');
				// Event
				links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
			}

			Accordion.prototype.dropdown = function(e) {
				var $el = e.data.el;
				var $this = $(this),
						$next = $this.next();

				$next.slideToggle();
				$this.parent().toggleClass('open');

				if (!e.data.multiple) {
					$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
				}
			}

			var accordion = new Accordion($('#accordion1, #accordion4'), false);
		});
	</script>
	<script>
		const header = document.getElementById("header");
		const footer = document.getElementById("footer");
		const nav = document.getElementById("nav");

		fetch('header.jsp')
			.then(response => {
				return response.text()
			})
			.then(data => {
				header.innerHTML = data;
			});
		fetch('footer.jsp')
			.then(response => {
				return response.text()
			})
			.then(data => {
				footer.innerHTML = data;
			});
		fetch('nav.jsp')
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
		$(function() {
			var Accordion = function(el, multiple) {
				this.el = el || {};
				this.multiple = multiple || false;

				// Private variables
				var links = this.el.find('.link');
				// Event
				links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
			}

			Accordion.prototype.dropdown = function(e) {
				var $el = e.data.el;
				var $this = $(this),
						$next = $this.next();

				$next.slideToggle();
				$this.parent().toggleClass('open');

				if (!e.data.multiple) {
					$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
				}
			}

			var accordion = new Accordion($('#accordion1, #accordion4'), false);
		});

		$( "#range-price" ).slider({
			range: true,
			min: 0,
			max: 1_000_000,
			values: [ 0, 1_000_000 ],
			slide: function( event, ui ) {
				$( "#price" ).val( ui.values[ 0 ] + "đ" + " - " + ui.values[ 1 ] + "đ");
			}
		});

		$( "#price" ).val( $( "#range-price" ).slider( "values", 0 ) + "đ" +
				" - " + $( "#range-price" ).slider( "values", 1 ) + "đ");

		$( "#range-price2" ).slider({
			range: true,
			min: 0,
			max: 1_000_000,
			values: [ 0, 1_000_000 ],
			slide: function( event, ui ) {
				$( "#price2" ).val( ui.values[ 0 ] + "đ" + " - " + ui.values[ 1 ] + "đ");
			}
		});

		$( "#price2" ).val( $( "#range-price2" ).slider( "values", 0 ) + "đ" +
				" - " + $( "#range-price2" ).slider( "values", 1 ) + "đ");
	</script>
	<script>
		document.getElementById('filterForm').addEventListener('submit', function(event) {
			// Get selected categories and brands
			const selectedCategories = document.querySelectorAll('input[name="selectedCategories"]:checked');
			const selectedBrands = document.querySelectorAll('input[name="selectedBrands"]:checked');
			const priceRange = document.getElementById('price').value;

			// Check if at least one category or brand is selected
			if (selectedCategories.length === 0 && selectedBrands.length === 0 && !priceRange) {
				alert('Please select at least one filter option.');
				event.preventDefault(); // Prevent form submission
			}
		});
	</script>

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

	<!--main js file end-->
</body>

</html>