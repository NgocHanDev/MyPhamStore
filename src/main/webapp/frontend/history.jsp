<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.myphamstore.model.OrderModel" %>
<%@ page import="vn.edu.hcmuaf.fit.myphamstore.model.ProductModel" %>
<%@ page import="vn.edu.hcmuaf.fit.myphamstore.model.OrderDetailModel" %>
<%@ page import="java.util.Map" %>
<%@include file="/common/tablib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch Sử Mua Hàng</title>
    <link rel="stylesheet" type="text/css" href="../static/css/history.css" />
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
    <!-- favicon links -->
    <link rel="shortcut icon" type="image/png" href="../static/images/header/favicon.png" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

</head>
<body>
<%--<div id="preloader">--%>
<%--    <div id="status">--%>
<%--        <img src="../static/images/header/preloader.gif" id="preloader_image" alt="loader">--%>
<%--    </div>--%>
<%--</div>--%>
<!-- Top Scroll Start -->
<a href="javascript:" id="return-to-top"><i class="fa fa-angle-up"></i></a>
<!-- Top Scroll End -->
<%@include file="component/nav.jsp"%>
<%@include file="component/header.jsp"%>
<!-- Header Wrapper Start -->
<div class="ss_inner_title_wrapper">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="ss_inner_title_cont_wrapper">
                    <ul>
                        <li>
                            <a href="#"><i class="fa fa-home"></i></a>&nbsp;&nbsp; >
                        </li>
                        <li>Lịch sử đơn hàng</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content -->
<div class="container" >
    <div style="height: 50px; background-color: white;z-index: 10;; width: 100vw;"></div>
    <div class="container px-4 mt-4">
        <h1>Lịch sử đơn hàng  </h1>
        <%List<OrderModel> orders = (List<OrderModel>) request.getAttribute("orderHistory");
            Map<Long, List<OrderDetailModel>> orderDetailsMap = (Map<Long, List<OrderDetailModel>>) request.getAttribute("orderDetailsMap");
            Map<Long, List<ProductModel>> productsMap = (Map<Long, List<ProductModel>>) request.getAttribute("productsMap");
        %>
        <hr class="mt-0 mb-4">

        <div class="row d-flex">
            <!-- Profile picture section -->
            <div class="col-md-8">
                <div class="card mb-4">

                    <%
                        if (orders != null && !orders.isEmpty()) {
                            for (OrderModel order : orders) {
                    %>
                    <div class="order">
                        <div class="order-header">
                            <span class="order-date"><i class="fas fa-calendar-alt"></i> <%= order.getOrderDate() %></span>
                            <span class="order-status <%= order.getStatus() %>">

                <i class="fas fa-check-circle"></i> <%if ("PENDING".equalsIgnoreCase(String.valueOf(order.getStatus()))){
                    out.print("Đang chờ xử lý");
                            } else if ("SHIPPING".equalsIgnoreCase(String.valueOf(order.getStatus()))){
                                out.print("Đang giao hàng");
                            } else if ("DELIVERED".equalsIgnoreCase(String.valueOf(order.getStatus()))){
                                out.print("Đã giao hàng");
                            } else if ("CANCELLED".equalsIgnoreCase(String.valueOf(order.getStatus()))){
                                out.print("Đã hủy");
                            } else if ("CONFIRMED".equalsIgnoreCase(String.valueOf(order.getStatus()))){
                                out.print("Đã xác nhận");
                            }
                            %>
                                <% if ("PENDING".equalsIgnoreCase(order.getStatus()) || "CONFIRMED".equalsIgnoreCase(order.getStatus())) { %>
    <form method="post" action="<%= request.getContextPath() + "/order-history" %>" onsubmit="return confirm('Bạn có chắc muốn hủy đơn hàng này không?');">
        <input type="hidden" name="orderId" value="<%= order.getId() %>"/>
        <button type="submit" class="btn btn-danger mt-2">Hủy đơn hàng</button>
    </form>
<% } %>

            </span>
                        </div>
                        <div class="order-details">
                            <%
                                List<OrderDetailModel> orderDetails = orderDetailsMap.get(order.getId());
                                List<ProductModel> products = productsMap.get(order.getId());

                                if (orderDetails != null && products != null) {
                                    for (OrderDetailModel orderDetail : orderDetails) {
                                        for (ProductModel product : products) {
                                            if (product.getId() == orderDetail.getProductId()) {
                            %>
                            <div class="product">
                                <img src="<%= product.getThumbnail() %>" alt="<%= product.getName() %>">
                                <div class="product-info">
                                    <h3><%= product.getName() %></h3>
                                    <p>Số lượng: <%= orderDetail.getQuantity() %></p>
                                    <p>Giá: <%= product.getPrice() %> VNĐ</p>
                                </div>
                            </div>
                            <%
                                        }
                                    }
                                }
                            } else {
                            %>
                            <p>Không có sản phẩm nào trong đơn hàng này.</p>
                            <%
                                }
                            %>
                        </div>
                        <div class="order-total">
                            <strong>Tổng cộng: <%= order.getTotalPrice() %> VNĐ</strong>
                        </div>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <p>Không có đơn hàng nào.</p>
                    <%
                        }
                    %>


                </div>
                </div>
            </div>
            </div>
        </hr>
</div>
    </div>
</div>
<!-- end content -->
<!-- Footer Wrapper End -->
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

</body>
</html>
