<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 1/30/2025
  Time: 2:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<!--<![endif]-->

<head>
  <meta charset="utf-8" />
  <title>Thanh toán</title>
  <meta content="width=device-width, initial-scale=1.0" name="viewport" />
  <meta name="Miêu tả" content="big basket" />
  <meta name="keywords" content="Túi lớn, Thương mại điện tử, Cửa hàng, Trung tâm mua sắm, Mua sắm trực tuyến" />
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
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
  <link rel="stylesheet" type="text/css" href="../static/css/sidebar.css" />
  <!-- favicon links -->
  <link rel="shortcut icon" type="image/png" href="../static/images/header/favicon.png" />
</head>

<body>
<!-- preloader Start -->
<div id="preloader">
  <div id="status">
    <img src="images/header/preloader.gif" id="preloader_image" alt="loader">
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
<!-- ss inner title Wrapper Start -->
<div class="ss_inner_title_wrapper">
  <div class="container">
    <div class="row">
      <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="ss_inner_title_cont_wrapper">
          <ul>
            <li><a href="#"><i class="fa fa-home"></i></a>&nbsp;&nbsp; ></li>
            <li>Sản phẩm&nbsp;&nbsp; ></li>
            <li>Mua sắm&nbsp;&nbsp; ></li>
            <li>Thanh toán</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
<!--CheckOut Page-->
<div class="checkout-page">
  <div class="container">

    <div class="panel-group" id="accordionFourLeft">
      <div class="panel panel-default panel-checkout">
        <div class="panel-heading checkout_panel_heading">
          <h4 class="panel-title">
            <i class="fa fa-ticket"></i> Mã giảm giá khả dụng
          </h4>
        </div>
        <div class="panel-body">
          <div class="coupon-container">
            <c:forEach items="${availableCoupons}" var="coupon">
              <div class="coupon-card">
                <div class="coupon-code">
                  <span>${coupon.code}</span>
                  <button class="copy-btn" onclick="copyCoupon('${coupon.code}')">Sao chép mã</button>
                </div>
                <ul class="coupon-details">
                  <li><strong>Số lượng còn lại:</strong> ${coupon.remainingQuantity}</li>
                  <li><strong>Loại giảm giá:</strong> ${coupon.type eq 'percentage' ? 'Phần trăm' : 'Cố định'}</li>
                  <li><strong>Số tiền giảm:</strong> ${coupon.type eq 'percentage' ? coupon.discount + '%' : coupon.discount + 'đ'}</li>
                  <li><strong>Giá trị đơn hàng tối thiểu:</strong> ${coupon.minOrderValue}đ</li>
                  <li><strong>Ngày hết hạn:</strong> ${coupon.expiryDate}</li>
                </ul>
              </div>
            </c:forEach>
          </div>
        </div>
      </div>
      <div class="panel panel-default panel-checkout panel-checkout-2">

        <div id="collapseFiveLefttwo" class="panel-collapse collapse" aria-expanded="false" role="tablist">
          <div class="panel-body">

            <div class="text-accordion">
              <div class="estimate_shiping_Wrapper_cntnt estimate_shiping_Wrapper_cntnt_3">
                <div class="btc_shop_single_prod_right_section shop_product_single_head related_pdt_shop_head related_pdt_shop_head_2">
                  <h1>Đăng nhập bằng tài khoản của bạn : </h1>
                  <div class="shop_pdt_form login_form ckeckpot_form_clr_wrapper">
                    <!--Form Group-->
                    <div class="row">
                      <div class="form-group col-md-6 col-sm-6 col-xs-12">

                        <div class="contect2_form1">
                          <input type="text" placeholder=" Username*"><i class="fa fa-user"></i>
                        </div>

                        <div class="contect2_form1">
                          <input type="password" placeholder=" password*"><i class="fa fa-lock"></i>
                        </div>

                        <div class="remember_box">
                          <label class="control control--checkbox">Hãy nhớ tôi
                            <input type="checkbox">
                            <span class="control__indicator"></span>
                          </label>

                        </div>
                        <div class="shop_btn_wrapper chackout_login_btn">
                          <ul>
                            <li><a href="#">Đăng nhập</a>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

            </div>
          </div>
          <!-- end of panel-body -->
        </div>
      </div>
    </div>

    <!--Checkout Details-->
    <form class="checkout-form" action="<c:url value="/checkout?action=checkout"/>" method="post">

      <div class="row clearfix">
        <!--Column-->
        <div class="column col-lg-6 col-md-6 col-sm-12 col-xs-12">
          <div class="btc_shop_single_prod_right_section related_pdt_shop_head checkout_heading">
            <h1>CHI TIẾT THANH TOÁN </h1>
          </div>
          <div class="row clearfix">

            <!--Form Group-->
            <div class="form-group col-md-12 col-sm-12 col-xs-12">
              <div class="field-label">Tên người nhận</div>
              <input type="text" name="recipientName"  placeholder="nhập tên nguời nhận " value="${user.fullName}" required >
            </div>

            <!--Form Group-->
            <div class="form-group col-md-12 col-sm-12 col-xs-12">
              <div class="field-label">Số điện thoại người nhận <sup>*</sup></div>
              <input type="text" name="recipientPhone"  placeholder="nhập số điện thoại người nhận" value="${user.phone}" required >
            </div>
            <!-- Form Group
            <div class="form-group col-md-12 col-sm-12 col-xs-12">
                <div class="field-label">Tên công ty</div>
                <input type="text" name="field-name" value="" placeholder="">
            </div> -->

            <!--Form Group-->
<%--            <div class="form-group col-md-12 col-sm-12 col-xs-12">--%>
<%--              <div class="field-label">Số điện thoại </div>--%>
<%--              <input type="text" name="field-name" value="0123456789" readonly placeholder="">--%>
<%--            </div>--%>

            <!--Form Group-->
            <div class="form-group col-md-6 col-sm-12 col-xs-12">
              <div class="field-label">Thành phố/Tỉnh thành </div>
              <input type="text" name="city"  placeholder="nhập tên thành phố/tỉnh thành" value="${address.city}" required >
            </div>

            <!--Form Group-->
            <div class="form-group col-md-6 col-sm-12 col-xs-12">
              <div class="field-label">Quận/huyện </div>
              <input type="text" name="district"   placeholder="nhập tên quận/huyện" value="${address.district}" required >
            </div>

            <!--Form Group-->
            <div class="form-group col-md-6 col-sm-6 col-xs-12">
              <div class="field-label">Phường/xã</div>
              <input type="text" name="ward"  placeholder="nhập tên phường/xã" value="${address.ward}"  required>
            </div>

            <!--Form Group-->
            <div class="form-group col-md-6 col-sm-6 col-xs-12">
              <div class="field-label">Chi tiết địa chỉ </div>
              <input type="text" name="note"  placeholder="nhập chi tiết địa chỉ" value="${address.note}" required >
            </div>

            <div class="form-group col-md-6 col-sm-6 col-xs-12">
              <div class="field-label">Ghi chú của khách hàng </div>
              <input type="text" name="customerNote"  placeholder="ghi chú của bạn" required >
            </div>
          </div>
        </div>
      </div>

    </form>
    <!--End Checkout Details-->

    <!--Order Box-->
    <div class="order-box">
      <div class="btc_shop_single_prod_right_section related_pdt_shop_head checkout_heading">
        <h1>Sản phẩm đã đặt hàng của bạn </h1>
      </div>
      <table class="table">
        <thead>
        <tr>
          <th>Sản phẩm</th>
          <th>Giá</th>
          <th>Số lượng</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listCart}" var="cart">
          <tr>
            <td>${cart.product.name}</td>
            <td>${cart.product.price}</td>
            <td>${cart.quantity}</td>
          </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
          <td>Tổng: </td>
          <td>${totalAmount}đ</td>
        <!-- hiển thị tổng -->
        </tfoot>
      </table>
    </div>
    <!--End Order Box-->
<%--    <!-- Coupons Section -->--%>
<%--    <div class="coupons-section">--%>
<%--      <div class="btc_shop_single_prod_right_section related_pdt_shop_head checkout_heading">--%>
<%--        <h1>Available Coupons</h1>--%>
<%--      </div>--%>
<%--      <div class="coupons-list">--%>
<%--        <c:forEach items="${availableCoupons}" var="coupon">--%>
<%--          <div class="coupon-card">--%>
<%--            <div class="coupon-header">--%>
<%--              <h2>${coupon.code}</h2>--%>
<%--              <span class="coupon-expiry">Expires on: ${coupon.endDate}</span>--%>
<%--            </div>--%>
<%--            <div class="coupon-body">--%>
<%--              <p><strong>Discount:</strong> ${coupon.discountValue} ${coupon.discountType}</p>--%>
<%--              <p><strong>Min Order Value:</strong> ${coupon.minOrderValue}</p>--%>
<%--              <p><strong>Max Discount:</strong> ${coupon.maxDiscountValue}</p>--%>
<%--              <p><strong>Remaining Quantity:</strong> ${coupon.maxUsage - coupon.currentUsage}</p>--%>
<%--              <p><strong>Conditions:</strong> ${coupon.conditions}</p>--%>
<%--            </div>--%>
<%--          </div>--%>
<%--        </c:forEach>--%>
<%--      </div>--%>
<%--    </div>--%>
    <!--Payment Box-->
    <div class="payment-box">
      <div class="upper-box">

        <!--Payment Options-->
        <div class="payment-options">
          <ul>
            <li>
              <div class="radio-option">
                <input type="radio" name="payment-group" id="payment-2" checked>
                <label for="payment-2"><strong>Thanh toán chuyển khoản </strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>
            <li>
              <div class="radio-option">
                <input type="radio" name="payment-group" id="payment-1">
                <label for="payment-1"><strong>Cod</strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>

            <li>
              <div class="radio-option">
                <input type="radio" name="payment-group" id="payment-3">
                <label for="payment-3"><strong>MOMO</strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>
            <li>
              <div class="radio-option">
                <input type="radio" name="payment-group" id="payment-4">
                <label for="payment-4"><strong>VnPay</strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>
            <li>
              <div class="radio-option">
                <input type="radio" name="payment-group" id="payment-5">
                <label for="payment-5"><strong>PayPal</strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>
          </ul>
        </div>

      </div>
      <div class="lower-box text-right">
        <div class="shop_btn_wrapper checkout_btn">
          <ul>
            <li><a id="submit-order" href="#">Đặt hàng </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!--End Payment Box-->

  </div>

</div>
<!--End CheckOut Page-->
<%@include file="component/footer.jsp"%>
<script>
  const province = document.getElementById("province");
  const district = document.getElementById("district");
  const ward = document.getElementById("ward");

  fetch('https://provinces.open-api.vn/api/')
          .then(response => {
            return response.json()
          })
          .then(data => {
            data.forEach(element => {
              let option = document.createElement("option");
              option.text = element.name;
              option.value = element.code;
              province.add(option);
            });
          });

  province.addEventListener("change", async (e)=>{
    let code = e.target.value
    const response = await fetch(`https://provinces.open-api.vn/api/p/${code}?depth=2`);
    const data = await response.json();
    district.innerHTML = "<option value='#' selected disabled>Chọn quận huyện</option>";
    data.districts.forEach(element => {
      let option = document.createElement("option");
      option.text = element.name;
      option.value = element.code;
      district.add(option);
    });
  });

  district.addEventListener("change", async (e)=>{
    let code = e.target.value
    const response = await fetch(`https://provinces.open-api.vn/api/d/${code}?depth=2`);
    const data = await response.json();
    ward.innerHTML = "<option value='#' selected disabled>Chọn phường xã</option>";
    data.wards.forEach(element => {
      let option = document.createElement("option");
      option.text = element.name;
      option.value = element.code;
      ward.add(option);
    });
  });

</script>
<script>
  function copyCoupon(code) {
    navigator.clipboard.writeText(code).then(function() {
      alert("Mã giảm giá đã được sao chép: " + code);
    }).catch(function(error) {
      console.error("Lỗi khi sao chép mã: ", error);
    });
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
</script>
<!--main js file end-->
<script src="../static/js/demo/checkout.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<script>
  document.getElementById("submit-order").addEventListener("click", (e)=>{
    e.preventDefault();
    console.log("submit order");
    Toastify({
      text: "Đặt hàng thành công \n Đơn hàng của bạn sẽ sớm được giao!",
      duration: 5000,
      newWindow: true,
      close: true,
      gravity: "top", // `top` or `bottom`
      position: "center", // `left`, `center` or `right`
      stopOnFocus: true, // Prevents dismissing of toast on hover
      style: {
        //   background: "linear-gradient(to right, #00b09b, #96c93d)",
      },
      onClick: function(){} // Callback after click
    }).showToast();
    setTimeout(()=>{
      //submit form
        document.querySelector(".checkout-form").submit();
    }, 1000);
  })
</script>
</body>

</html>
