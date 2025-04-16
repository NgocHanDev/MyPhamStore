<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="main-header-three" style="background: linear-gradient(to right, #c084fc, #ff69b4);">
		<div class="ss_categories_box ss-box-two">
			<nav>
				<ul class="list-group vertical-menu yamm make-absolute nav vertical-menu-three">
					<li class="list-group-item button-dropdown" ><a href="javascript:void(0)" class="dropdown-toggle sidebar-three" style="background: #2e9ad0"><span><i class="fa fa-list-ul" style="background: rgba(11,11,11,0.3)"></i>Danh mục sản phẩm</span></a>
						<ul class="dropdown-menu">
							<li class="yamm-tfw menu-item menu-item-has-children animate-dropdown dropdown">
								<div class="wpb_wrapper">
									<div class="wpb_text_column wpb_content_element">
										<div class="wpb_wrapper">
											<ul>
												<c:forEach var="category" items="${categories}">
													<li class="nav-title">${category.name}</li>
												</c:forEach>
											</ul>
										</div>
									</div>
								</div>
								<ul role="menu" class=" dropdown-menu">
									<li class="menu-item animate-dropdown menu-item-object-static_block">
										<div class="yamm-content">
<%--											<div class="row bg-yamm-content bg-yamm-content-bottom bg-yamm-content-right">--%>
<%--												<div class="col-sm-12">--%>
<%--													<div class="vc_column-inner ">--%>
<%--														<div class="wpb_wrapper">--%>
<%--															<div class="wpb_single_image wpb_content_element vc_align_left">--%>
<%--																<figure class="wpb_wrapper vc_figure">--%>
<%--																	<div class="vc_single_image-wrapper vc_box_border_grey">--%>
<%--																		<img src="images/content/mens.jpg" class="vc_single_image-img attachment-full" alt="">--%>
<%--																	</div>--%>
<%--																</figure>--%>
<%--															</div>--%>
<%--														</div>--%>
<%--													</div>--%>
<%--												</div>--%>
<%--											</div>--%>
											<div class="row">
												<div class="col-sm-12">
													<div class="vc_column-inner">
														<div class="wpb_wrapper">
															<div class="wpb_text_column wpb_content_element">
																<div class="wpb_wrapper">
																	<ul>
																		<c:forEach var="category" items="${categories}">
																			<li class="nav-title">${category.name}</li>
																		</c:forEach>
																	</ul>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</li>
								</ul>
							</li>
						</ul>
					</li>
				</ul>
			</nav>

		</div>
		<!-- Slider Wrapper Start -->
		<div class="ss_main_slider_wrapper ss-slider-two">
			<div class="ss_header_wrapper header-wrapper-three">
				<div class="cd-dropdown-wrapper">
					<a class="house_toggle" href="#0">
						<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" id="Capa_1" x="0px" y="0px" viewBox="0 0 512 512" style="enable-background:new 0 0 512 512;" xml:space="preserve">
							<g>
								<g>
									<path d="M481.508,210.336L68.414,38.926c-17.403-7.222-37.064-4.045-51.309,8.287C2.86,59.547-3.098,78.551,1.558,96.808    L38.327,241h180.026c8.284,0,15.001,6.716,15.001,15.001c0,8.284-6.716,15.001-15.001,15.001H38.327L1.558,415.193    c-4.656,18.258,1.301,37.262,15.547,49.595c14.274,12.357,33.937,15.495,51.31,8.287l413.094-171.409    C500.317,293.862,512,276.364,512,256.001C512,235.638,500.317,218.139,481.508,210.336z" fill="#019bea"/>
								</g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
							<g>
							</g>
						</svg>
					</a>
					<nav class="cd-dropdown">
						<h2><a href="#">big basket</a></h2>
						<a href="#0" class="cd-close">Close</a>
						<ul class="cd-dropdown-content">
<%--							<li class="has-children">	<a href="#">Mens</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Womens</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Furniture</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Cooking</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Accessories</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Fashion</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Clocks</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Lighting</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Toys</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Sports</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Hand Made</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Electronics</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
<%--							<!-- .has-children -->--%>
<%--							<li class="has-children">	<a href="#">Cars</a>--%>
<%--								<ul class="cd-secondary-dropdown is-hidden">--%>
<%--									<li class="go-back"><a href="#0">Menu</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">T-Shart</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Belts</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Wallets</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Hates & Caps</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Sunglasses</a>--%>
<%--									</li>--%>
<%--									<li><a href="#">Neckties</a>--%>
<%--									</li>--%>
<%--								</ul>--%>
<%--								<!-- .cd-secondary-dropdown -->--%>
<%--							</li>--%>
							<!-- .has-children -->
							<li>	<a href="../contact.jsp">More Category</a>
							</li>
						</ul>
						<!-- .cd-dropdown-content -->
					</nav>
					<!-- .cd-dropdown -->
				</div>
				<div class="prs_menu_main_wrapper">
					<div class="search-three" style="background: linear-gradient(to right, #c084fc, #ff69b4)">
						<div class="ss_search_box search-box-three visible-xl visible-lg visible-md hidden-sm">
							<!-- Search form -->
							<form action="/danh-muc" method="get">
								<!-- Dropdown danh sách loại sản phẩm -->
								<select name="selectedCategories">
									<option value="">Loại sản phẩm</option>
									<!-- Lặp qua danh sách categories -->
									<c:forEach var="category" items="${categories}">
										<option value="${category.id}">${category.name}</option>
									</c:forEach>
								</select>
								<span>
									<!-- Ô nhập liệu -->
           							 <input type="text" name="keyword" placeholder="Tìm sản phẩm...">
									<!-- Nút tìm kiếm -->
            						<button type="submit" style="background: #b0bec8">
               						 <i class="fa fa-search" aria-hidden="true"></i>
           							 </button>
        						</span>
							</form>

						</div>
					</div>

					<nav class="navbar navbar-default hidden-xl hidden-lg hidden-md visible-sm">
						<div id="dl-menu" class="xv-menuwrapper responsive-menu">
							<div class="ss_menu_btn dl-trigger" id="menu_click">
								<div class="bar1"></div>
								<div class="bar2"></div>
								<div class="bar3"></div>
							</div>
							<div class="clearfix"></div>
							<ul class="dl-menu" id="dl-menu-three">
								<li class="parent"><a href="#">Home &nbsp;<i class="fa fa-angle-down" aria-hidden="true"></i></a>
									<ul class="lg-submenu">
										<li><a href="index.html">Home-I</a>
										</li>
										<li><a href="index2.html">Home-II</a>
										</li>
										<li><a href="index3.html">Home-III</a>
										</li>
									</ul>
								</li>
								<li class="parent"><a href="#">Pages &nbsp;<i class="fa fa-angle-down" aria-hidden="true"></i></a>
									<ul class="lg-submenu">
										<li><a href="404.html">404</a>
										</li>
										<li><a href="../shopping_cart.jsp">Shopping-Cart</a>
										</li>
										<li><a href="checkout.jsp">Checkout</a>
										</li>
									</ul>
								</li>
								<li class="parent"><a href="#">Shop &nbsp;<i class="fa fa-angle-down" aria-hidden="true"></i></a>
									<ul class="lg-submenu">
										<li><a href="product.html">Shop</a>
										</li>
										<li><a href="product_sidebar.html">Shop-Sidebar</a>
										</li>
										<li><a href="product_sidebar_right.html">Shop-Sidebar-Right</a>
										</li>
										<li><a href="product_single.html">Shop-Single</a>
										</li>
									</ul>
								</li>
								<li class="parent"><a href="#">Blog &nbsp;<i class="fa fa-angle-down" aria-hidden="true"></i></a>
									<ul class="lg-submenu">
										<li><a href="blog_categoris.html">Blog-Category</a>
										</li>
										<li><a href="blog_categoris_left.html">Blog-Category-Left</a>
										</li>
										<li><a href="blog_single.html">Blog-Single</a>
										</li>
										<li><a href="blog_single_left.html">Blog-Single-Left</a>
										</li>
									</ul>
								</li>
								<li class="parent megamenu"><a href="#">Shotcodes &nbsp;<i class="fa fa-angle-down" aria-hidden="true"></i></a>
									<ul class="lg-submenu">
										<li><a>Accessories</a>
											<ul class="lg-submenu">
												<li class="ar_left"><a href="#">Electronics</a>
												</li>
												<li class="ar_left"><a href="#">Furniture</a>
												</li>
												<li class="ar_left"><a href="#">Accessories</a>
												</li>
												<li class="ar_left"><a href="#">Divided</a>
												</li>
												<li class="ar_left"><a href="#">Everyday Fashion</a>
												</li>
												<li class="ar_left"><a href="#">Modern Classic</a>
												</li>
												<li class="ar_left"><a href="#">Party</a>
												</li>
											</ul>
										</li>
										<li><a>Computers</a>
											<ul class="lg-submenu">
												<li class="ar_left"><a href="#"> Internet Devices</a>
												</li>
												<li class="ar_left"><a href="#"> Monitors</a>
												</li>
												<li class="ar_left"><a href="#"> Memory Cards</a>
												</li>
												<li class="ar_left"><a href="#">Printers</a>
												</li>
												<li class="ar_left"><a href="#">Networking</a>
												</li>
												<li class="ar_left"><a href="#">Computer Accessories</a>
												</li>
												<li class="ar_left"><a href="#">Software</a>
												</li>
											</ul>
										</li>
										<li><a>Software</a>
											<ul class="lg-submenu">
												<li class="ar_left"><a href="#">Speakers</a>
												</li>
												<li class="ar_left"><a href="#">Entertainment Systems</a>
												</li>
												<li class="ar_left"><a href="#"> Media Players</a>
												</li>
											</ul>
										</li>
										<li><a>Home Audio</a>
											<ul class="lg-submenu">
												<li class="ar_left"><a href="#">Home Theater Systems</a>
												</li>
												<li class="ar_left"><a href="#">Amplifiers</a>
												</li>
												<li class="ar_left"><a href="#">Speakers</a>
												</li>
												<li class="ar_left"><a href="#">CD Turntables </a>
												</li>
												<li class="ar_left"><a href="#">High-Resolution</a>
												</li>
												<li class="ar_left"><a href="#">HD TVs</a>
												</li>
											</ul>
										</li>
										<li>
											<div class="cc_navi_banner_wraper">
												<a href="#">
													<img src="../static/images/content/n1.png" alt="nav_banner">
												</a>
											</div>
											<div class="cc_navi_banner_wraper">
												<a href="#">
													<img src="../static/images/content/n2.png" alt="nav_banner">
												</a>
											</div>
										</li>
									</ul>
								</li>
								<li class="parent"><a href="../contact.jsp">Contact</a>
								</li>
								<li class="parent visible-xs"><a href="#">Cart &nbsp;<i class="fa fa-angle-down" aria-hidden="true"></i></a>
									<ul class="lg-submenu">
										<li>
											<div class="ss_cart_inner_main_section">
												<div class="ss_cart_img_wrapper">
<%--													<img src="../static/images/header/cart_img.jpg" alt="cart_img">--%>
												</div>
												<div class="ss_cart_img_cont_wrapper">
													<h3><a href="#">Product item</a></h3>
													<h4>Product category</h4>
													<span>x</span>
												</div>
											</div>
										</li>
										<li>
											<div class="ss_cart_inner_main_section">
												<div class="ss_cart_img_wrapper">
													<img src="../static/images/header/cart_img.jpg" alt="cart_img">
												</div>
												<div class="ss_cart_img_cont_wrapper">
													<h3><a href="#">Product item</a></h3>
													<h4>Product category</h4>
													<span>x</span>
												</div>
											</div>
										</li>
									</ul>
								</li>
							</ul>
						</div>
						<!-- /dl-menuwrapper -->
					</nav>
				</div>
				<div class="ss_right_menu ss-right-two">
					<ul>
						<li class="ss_menu_toggle_text"><a href="#">Menu</a>
						</li>
					</li>
					<li class="ss_menu_toggle_text2"><a href="#">Tất cả dịch vụ</a>
						<li class="ss_menu_toggle_text2"><a href="#">Giảm 20% cho hôm nay</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
