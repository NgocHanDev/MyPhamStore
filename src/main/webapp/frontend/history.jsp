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

                                <% if ("PENDING".equalsIgnoreCase(String.valueOf(order.getStatus()))) { %>
    <button class="cancel-order-btn" onclick="cancelOrder(<%= order.getId() %>)">
        <i class="fas fa-times-circle"></i> Hủy đơn hàng
    </button>
<% } %>

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
<div id="cancelOrderModal" style="display:none; position:fixed; top:0; left:0; width:100vw; height:100vh; background:rgba(0,0,0,0.4); z-index:9999; align-items:center; justify-content:center;">
    <div style="background:#fff; padding:30px; border-radius:8px; max-width:400px; width:90%; position:relative;">
        <span id="closeCancelModal" style="position:absolute; top:10px; right:20px; cursor:pointer; font-size:22px;">&times;</span>
        <h4>Chọn lý do hủy đơn hàng</h4>
        <form id="cancelOrderForm">
            <input type="hidden" name="orderId" id="cancelOrderId">
            <div>
                <label><input type="radio" name="reason" value="Đổi ý" required> Đổi ý</label><br>
                <label><input type="radio" name="reason" value="Đặt nhầm"> Đặt nhầm</label><br>
                <label><input type="radio" name="reason" value="Tìm được giá tốt hơn"> Tìm được giá tốt hơn</label><br>
                <label><input type="radio" name="reason" value="Khác"> Khác</label>
                <input type="text" id="otherReasonInput" name="otherReason" placeholder="Nhập lý do khác" style="display:none; margin-top:8px; width:100%;">
            </div>
            <button type="submit" class="btn btn-danger" style="margin-top:16px;">Xác nhận hủy</button>
        </form>
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
<%--<script>--%>
<%--    function cancelOrder(orderId) {--%>
<%--        if (confirm("Bạn có chắc muốn hủy đơn hàng này không?")) {--%>
<%--            $.ajax({--%>
<%--                url: '/huy-don-hang',--%>
<%--                method: 'POST',--%>
<%--                data: { orderId: orderId },--%>
<%--                success: function(response) {--%>
<%--                    alert(response.message || "Đơn hàng đã được hủy.");--%>
<%--                    location.reload();--%>
<%--                },--%>
<%--                error: function() {--%>
<%--                    alert("Có lỗi xảy ra khi hủy đơn hàng.");--%>
<%--                }--%>
<%--            });--%>
<%--        }--%>
<%--    }--%>
<%--</script>--%>
<script>
    function cancelOrder(orderId) {
        if (confirm("Bạn có chắc muốn hủy đơn hàng này không?")) {
            document.getElementById('cancelOrderId').value = orderId;
            document.getElementById('cancelOrderModal').style.display = 'flex';
        }
    }

    document.getElementById('closeCancelModal').onclick = function() {
        document.getElementById('cancelOrderModal').style.display = 'none';
    };

    document.querySelectorAll('input[name="reason"]').forEach(function(radio) {
        radio.addEventListener('change', function() {
            document.getElementById('otherReasonInput').style.display = this.value === 'Khác' ? 'block' : 'none';
        });
    });

    document.getElementById('cancelOrderForm').onsubmit = function(e) {
        e.preventDefault();
        var orderId = document.getElementById('cancelOrderId').value;
        var reason = document.querySelector('input[name="reason"]:checked').value;
        var otherReason = document.getElementById('otherReasonInput').value;
        var finalReason = (reason === 'Khác') ? otherReason : reason;

        if (reason === 'Khác' && !otherReason.trim()) {
            alert('Vui lòng nhập lý do hủy.');
            return;
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/order-history',
            method: 'POST',
            data: { id: orderId, status: 'CANCELLED', reason: finalReason }, // Gửi đúng tham số id và status
            success: function(response) {
                var btn = document.querySelector('.cancel-order-btn[onclick="cancelOrder(' + orderId + ')"]');
                if (btn) {
                    var orderHeader = btn.closest('.order-header');
                    var statusSpan = orderHeader.querySelector('.order-status');
                    statusSpan.innerHTML = '<i class="fas fa-check-circle"></i> Đã hủy';
                    btn.remove();
                }
                alert(response.message || "Đơn hàng đã được hủy.");
            },
            error: function(xhr, status, error) {
                console.error("Lỗi hủy đơn hàng:", xhr.responseText); // In lỗi chi tiết vào console
                alert("Có lỗi xảy ra khi hủy đơn hàng: " + (xhr.responseJSON?.message || error));
            }
        });
        document.getElementById('cancelOrderModal').style.display = 'none';
    };
</script>
</body>
</html>
