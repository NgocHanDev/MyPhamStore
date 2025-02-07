<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/common/tablib.jsp"%>
<!-- Header Wrapper Start -->
<div class="ss_middle_header_wrapper">
    <div class="ss_logo">
        <a href="<c:url value="/trang-chu" />">
            <img src="../static/images/logo/logo.svg" alt="Logo" title="big basket" class="img-responsive">
        </a>
    </div>
    <div class="ss_info_area" style="height: fit-content;">
        <ul>
            <li>
                <div class="ss_search_box hidden-xl hidden-lg hidden-md visible-sm ">
                    <select>
                        <option value="c1">Thể loại</option>
                        <option value="c2">Category 1</option>
                        <option value="c3">Category 2</option>
                        <option value="c4">Category 3</option>
                    </select> <span>
                        <input type="text" placeholder="Search for products">
                        <button><i class="fa fa-search" aria-hidden="true"></i></button>
                    </span>
                </div>
                <nav class="navbar navbar-default visible-lg visible-xl visible-md hidden-sm hidden">
                    <div id="dl-menu" class="xv-menuwrapper responsive-menu menu-three">
                        <div class="clearfix"></div>
                        <ul class="dl-menu">
                            <li class="parent"><a href="/trang-chu">Trang Chủ </a>
                            </li>
                            <li class="parent">
                                <a href="<c:url value="/danh-muc" />">
                                    Tất cả sản phẩm
                                </a>
                            </li>
                            <li class="parent">
                            <a href="<c:url value="/thuong-hieu" />">
                                Thương hiệu
                            </a>
                            </li>
                            <li class="parent">
                                <a href="<c:url value="/lien-he" />">
                                    Liên hệ
                                </a>
                            </li>
                            <li class="parent visible-xs"><a href="/gio-hang">Cart &nbsp;<i
                                        class="fa fa-angle-down" aria-hidden="true"></i></a>
                                <ul class="lg-submenu">
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
                                    <li>
                                        <div class="ss_cart_inner_main_section">
                                            <div class="ss_cart_img_wrapper">
                                                <img src="images/header/cart_img.jpg" alt="cart_img">
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
            </li>
            <li>
                <div class="ss_notification_box">
                    <ul>
                        <li><a href="<c:url value='/wishlist' />"><i class="fa fa-heart" aria-hidden="true"></i> </a></li>
                        <li><a href="#"><i class="fa fa-bell" aria-hidden="true"></i><span>2</span></a>
                        </li>
                    </ul>
                </div>
            </li>
            <li id="" style="height: 82px; display: flex; align-items: center;">
                <c:choose>
                    <c:when test="${empty user}">
                        <!-- Người dùng chưa đăng nhập -->
                        <div class="ss_login_box">
                            <a href="<c:url value='/login?' />">
                                <span>Đăng nhập/Đăng ký</span>
                                <img src="../static/images/header/user_icon.png" alt="Đăng nhập" title="Đăng nhập" class="img-responsive">
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- Người dùng đã đăng nhập -->
                        <div class="dropdown">
                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                    ${user.fullName} <strong>#${user.id}</strong>
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuDivider" style="left: 0; z-index: 111121;">
                                <li><a href="<c:url value='/change-password' />">Đổi mật khẩu</a></li>
                                <li><a href="<c:url value='/profile' />">Thông tin cá nhân</a></li>
                                <li><a href="#">Lịch sử đơn hàng</a></li>
                                <li role="separator" class="divider"></li>
<%--                                <form id="form-login" class="form-group" action=<c:url value="/login?action=logout" /> method="post"/>--%>
<%--                                <li><a href="<c:url value='/login?action=logout' />" methods="post">Đăng xuất</a></li>--%>
<%--                                </form>--%>
<%--                                <li>--%>
<%--                                    <form id="logout-form" action="<c:url value='/login?action=logout' />" method="post">--%>
<%--                                        <button type="submit" style="border: none; background: none; padding: 0; color: inherit; cursor: pointer;">--%>
<%--                                            Đăng xuất--%>
<%--                                        </button>--%>
<%--                                    </form>--%>
<%--                                </li>--%>
                                <li>
                                    <a href="#" onclick="document.getElementById('logout-form').submit();" style="cursor: pointer;">
                                        Đăng xuất
                                    </a>
                                    <form id="logout-form" action="<c:url value='/login?action=logout' />" method="post" style="display: none;"></form>
                                </li>
                            </ul>
                        </div>
                    </c:otherwise>
                </c:choose>
            </li>

            <li>
                <div class="ss_cart_value dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <p>${cart.size()}</p>
                        <a href="<c:url value='/gio-hang' />"><img src="../static/images/header/cart_icon.png" alt="Cart" title="Yêu thích">
                            <span>Giỏ hàng: <ins style="text-transform: lowercase;">đ</ins></span>
                        </a>
                    </a>
                </div>
            </li>
        </ul>
        </a>
    </div>
    </li>
    </ul>
</div>
</div>

<!-- Header Wrapper End -->