<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/common/tablib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Giỏ hàng</title>
  <meta content="width=device-width, initial-scale=1.0" name="viewport" />
  <meta name="description" content="big basket" />
  <meta name="keywords" content="big basket, Ecommerce, Store, Mall, online Shopping" />
  <meta name="author" content="" />
  <meta name="MobileOptimized" content="320" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/animate.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/font-awesome.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/owl.carousel.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/owl.theme.default.css" />
  <link rel="stylesheet" type _

          ="text/css" href="${pageContext.request.contextPath}/static/css/magnific-popup.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/fonts.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/dl-menu.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/reset.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/camera.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/responsive.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/sidebar.css" />
</head>
<body>
<div id="preloader">
  <div id="status"></div>
</div>
<a href="javascript:" id="return-to-top"><i class="fa fa-angle-up"></i></a>
<%@include file="component/nav.jsp"%>
<%@include file="component/header.jsp"%>
<div class="ss_inner_title_wrapper">
  <div class="container">
    <div class="row">
      <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="ss_inner_title_cont_wrapper">
          <ul>
            <li><a href="<c:url value='/trang-chu' />"><i class="fa fa-home"></i></a> > </li>
            <li>Sản phẩm > <a href="<c:url value='/danh-muc' />"></a> > </li>
            <li>Mua sắm > </li>
            <li>Giỏ hàng <a href="<c:url value='/gio-hang' />"></a> > </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="cart_product_wrapper">
  <div class="container">
    <div class="row">
      <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="btc_shop_single_prod_right_section shop_product_single_head related_pdt_shop_head">
          <h1>Giỏ hàng của bạn</h1>
        </div>
      </div>
      <c:choose>
        <c:when test="${not empty listCartDisplay}">
          <div class="shop_cart_page_wrapper">
            <div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
              <div class="table-responsive cart-calculations">
                <table class="table">
                  <thead class="cart_table_heading">
                  <tr>
                    <th style="background: #2e9ad0; text-align: center; padding: 10px;">
                      <input type="checkbox" id="select-all" />
                    </th>
                    <th style="background: #2e9ad0; text-align: left; padding: 10px;">Sản phẩm</th>
                    <th style="background: #2e9ad0; text-align: center; padding: 10px;">Giá</th>
                    <th style="background: #2e9ad0; text-align: center; padding: 10px;">Số lượng</th>
                    <th style="background: #2e9ad0; text-align: center; padding: 10px;">Tổng cộng</th>
                    <th style="background: #2e9ad0; text-align: center; padding: 10px;">Hành động</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="i" items="${listCartDisplay}">
                    <tr>
                      <td style="text-align: center; padding: 10px;">
                        <input type="checkbox" class="product-checkbox" data-price="${i.product.price * i.quantity}" data-product-id="${i.product.id}" />
                        <input type="hidden" name="productId" value="${i.product.id}" />
                      </td>
                      <td style="text-align: left; padding: 10px;">
                        <img src="${not empty i.product and not empty i.product.thumbnail ? i.product.thumbnail : ''}"
                             alt="${i.product.name}" style="max-height: 80px; margin-right: 10px;" />
                        <span class="product-name">${not empty i.product and not empty i.product.name ? i.product.name : 'Không có tên'}</span>
                      </td>
                      <td style="text-align: center; padding: 10px;" class="price">
                          ${i.product.price}đ
                      </td>
                      <td style="text-align: center; padding: 10px;">
                        <form method="post" action="/gio-hang">
                          <input type="hidden" name="action" value="updateCart" />
                          <input type="hidden" name="productId" value="${i.product.id}" />
                          <input style="height: auto; text-align: center;" type="number" name="quantity" value="${i.quantity}" min="1" />
                        </form>
                      </td>
                      <td style="text-align: center; padding: 10px;" class="price">
                          ${i.product.price * i.quantity}đ
                      </td>
                      <td style="text-align: center; padding: 10px;">
                        <form method="post" action="${pageContext.request.contextPath}/gio-hang">
                          <input type="hidden" name="action" value="remove" />
                          <input type="hidden" name="productId" value="${i.product.id}" />
                          <button type="submit" class="remove-btn"><i class="fa fa-trash"></i></button>
                        </form>
                      </td>
                    </tr>
                  </c:forEach>
                  <tr>
                    <td colspan="5" style="padding: 20px;">
                      <div style="display: flex; justify-content: space-between; align-items: center; font-size: 16px; font-weight: bold;">
                        <div>
                          <p>Tổng sản phẩm: <span id="total-amount">${totalAmount}đ</span></p>
                          <p>Giảm giá: <span id="discount-amount">${discountAmount}đ</span></p>
                          <p>Tổng cộng: <span id="discounted-total">${finalAmount}đ</span></p>
                        </div>
                        <div>
                          <form id="checkout-form" method="post" action="${pageContext.request.contextPath}/checkout?action=display">
                            <input type="hidden" name="selectedItems" id="selectedItemsInput" />
                            <input type="hidden" name="discountPrice" id="discountPrice" />
                            <button type="submit" class="btn btn-primary">Thanh toán</button>
                          </form>
                        </div>
                      </div>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
              <div class="panel-heading checkout_panel_heading">
                <h4 class="panel-title"><i class="fa fa-ticket"></i> Ưu đãi dành cho bạn:</h4>
              </div>
              <div class="panel-body">
                <div class="voucher-container">
                  <c:forEach var="voucher" items="${discountCodes}">
                    <div class="voucher-wrapper">
                      <img src="${pageContext.request.contextPath}/static/images/content/voucher.png"
                           alt="Voucher Background" />
                      <form class="voucher-form" method="post" action="${pageContext.request.contextPath}/gio-hang">
                        <input type="hidden" name="action" value="applyDiscount" />
                        <input type="hidden" name="discountCode" value="${voucher.code}" />
                        <div style="font-size: 14px; font-weight: bold;">Mã: ${voucher.code}</div>
                        <div style="font-size: 13px; color: #444;">Giảm ${voucher.discountValue}${voucher.discountType == 'FIXED' ? 'k' : '%'} - ĐH tối thiểu: ${voucher.minOrderValue}đ</div>
                        <div style="font-size: 12px; color: gray;">HSD: ${voucher.startDate}</div>
                        <div style="font-size: 12px; color: gray;">HSD: ${voucher.endDate}</div>
                        <button type="submit" class="apply-discount-btn">Sử dụng</button>

                      </form>
                    </div>
                  </c:forEach>
                </div>

                <c:if test="${empty discountCodes}">
                  <p style="color: gray; font-style: italic;">Bạn chưa có mã giảm giá nào khả dụng.</p>
                </c:if>
              </div>

              <!-- end voucher-wrapper -->
              </div>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <h1>Giỏ hàng của bạn đang trống!</h1>
          <div class="shop_btn_wrapper shop_car_btn_wrapper">
            <ul>
              <li><a href="/trang-chu"><h3>Tiếp tục mua sắm</h3></a></li>
            </ul>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
<%@include file="component/footer.jsp"%>
<script src="${pageContext.request.contextPath}/static/js/jquery_min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/wow.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/static/js/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/static/js/modernizr.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.magnific-popup.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.dlmenu.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.sticky.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.menu-aim.js"></script>
<script src="${pageContext.request.contextPath}/static/js/camera.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.easing.1.3.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery.inview.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/custom.js"></script>
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
<script>
  $(document).ready(function () {
    $(".apply-discount-btn").click(function (e) {
      e.preventDefault();

      const $form = $(this).closest("form");
      const discountCode = $form.find("input[name='discountCode']").val();

      $.ajax({
        url: "/gio-hang",
        type: "POST",
        dataType: "json",
        data: {
          action: "applyDiscount",
          discountCode: discountCode
        },
        success: function(response) {
          if (response.success) {
            const discountAmount = Number(response.discountAmount);
            const discountedTotal = Number(response.discountedTotal);

            $("#discount-amount").text(discountAmount.toLocaleString("vi-VN") + "đ");
            $("#discounted-total").text(discountedTotal.toLocaleString("vi-VN") + "đ");
            $("#discountPrice").val(discountAmount);

            alert(response.message);
          } else {
            alert(response.message);
          }
        },

        error: function() {
          alert("Có lỗi xảy ra khi áp dụng mã giảm giá.");
        }
      });
    });
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
<script>
  document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".product-name").forEach(function (element) {
      const maxLength = 20;
      if (element.textContent.length > maxLength) {
        element.textContent = element.textContent.substring(0, maxLength) + "...";
      }
    });
  });
</script>
<script>
  function updateTotalAmount() {
    let total = 0;
    $(".product-checkbox:checked").each(function () {
      total += parseInt($(this).data("price"));
    });
    $("#total-amount").text(total.toLocaleString('vi-VN') + "đ");
  }

  function updateSelectedItems() {
    const selectedKeys = [];
    $(".product-checkbox:checked").each(function () {
      const productId = $(this).data("product-id");
      if (productId) {
        selectedKeys.push(productId);
      }
    });
    $("#selectedItemsInput").val(selectedKeys.join(","));
  }

  $(document).ready(function () {
    $("#select-all").on("change", function () {
      $(".product-checkbox").prop("checked", $(this).is(":checked"));
      updateTotalAmount();
      updateSelectedItems();
    });

    $(".product-checkbox").on("change", function () {
      let allChecked = $(".product-checkbox").length === $(".product-checkbox:checked").length;
      $("#select-all").prop("checked", allChecked);
      updateTotalAmount();
      updateSelectedItems();
    });

    $("#checkout-form").on("submit", function (e) {
      updateSelectedItems();
      const selectedItems = $("#selectedItemsInput").val();
      if (!selectedItems) {
        e.preventDefault();
        alert("Vui lòng chọn ít nhất một sản phẩm để thanh toán.");
        return false;
      }
    });

    updateTotalAmount();
    updateSelectedItems();
  });
</script>
<script>
  $(document).ready(function () {
    $("input[name='quantity']").on("change", function (e) {
      e.preventDefault();
      var $input = $(this);
      var newQuantity = parseInt($input.val());
      if (isNaN(newQuantity) || newQuantity < 1) {
        newQuantity = 1;
        $input.val(1);
      }

      var $row = $input.closest("tr");
      var productId = $row.find("input[name='productId']").val();

    $.ajax({
      url: "/gio-hang",
      method: "POST",
      data: {
        action: "updateCart",
        productId: productId,
        quantity: newQuantity
      },
      success: function (response) {
        if (response.status === "success") {
          var pricePerUnit = parseInt($row.find(".price").first().text().replace(/\D/g, ""));
          var totalPrice = pricePerUnit * newQuantity;
          $row.find(".price").last().text(totalPrice.toLocaleString('vi-VN') + "đ");

          $row.find(".product-checkbox").data("price", totalPrice);

          updateTotalAmount();
        } else {
          alert(response.message || "Có lỗi xảy ra khi cập nhật.");
        }
      },
      error: function () {
        alert("Có lỗi xảy ra khi cập nhật số lượng.");
      }
    });
  })});
</script>
<script>
  $(document).on("click", ".remove-btn", function (e) {
    e.preventDefault();
    var $row = $(this).closest("tr");
    var productId = $row.find("input[name='productId']").val();

    $.ajax({
      url: "/gio-hang",
      method: "POST",
      data: {
        action: "remove",
        productId: productId
      },
      success: function (response) {
        if (response.status === "success") {
          $row.remove();
          updateTotalAmount();
          updateCartCount();
        } else {
          alert(response.message || "Có lỗi xảy ra khi xóa sản phẩm.");
        }
      },
      error: function () {
        alert("Có lỗi xảy ra khi xóa sản phẩm.");
      }
    });
  });
  function updateCartCount() {
    $.ajax({
      url: '/gio-hang?action=count',
      method: 'GET',
      dataType: 'json',
      success: function (data) {
        $('#cart-quantity').text(data.count);
      },
      error: function () {
        console.error('Failed to fetch cart count');
      }
    });
  }
</script>
</body>
</html>