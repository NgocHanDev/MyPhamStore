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
            <c:choose>
            <c:when test="${not empty address}">
            <div class="form-group col-md-6 col-sm-12 col-xs-12">
            <select name="city" id="city"  >
                <option value="${address.city}" selected>${address.city}</option>
                <!-- Thêm các tùy chọn khác từ danh sách tỉnh/thành -->
              </select>
            </div>
            <div class="form-group col-md-6 col-sm-12 col-xs-12">

            <select name="district" id="district">
                <option value="${address.district}" selected>${address.district}</option>
                <!-- Thêm các tùy chọn khác từ danh sách quận/huyện -->
              </select>
            </div>
            <div class="form-group col-md-6 col-sm-12 col-xs-12">

            <select name="ward" id="ward">
                <option value="${address.ward}" selected>${address.ward}</option>
                <!-- Thêm các tùy chọn khác từ danh sách phường/xã -->
              </select>
            </div>
              <div class="form-group col-md-6 col-sm-6 col-xs-12">
              <input type="text" name="note" value="${address.note}">
                </div>
              <div class="form-group col-md-6 col-sm-6 col-xs-12">
                <div class="field-label">Ghi chú của khách hàng </div>
                <input type="text" name="customerNote"  placeholder="ghi chú của bạn" required >
              </div>
              <a href="#" onclick="showNewAddressForm()">Sử dụng địa chỉ khác</a>
            </c:when>
            <c:otherwise>
            <div class="form-group col-md-6 col-sm-12 col-xs-12">
              <div class="field-label">Thành phố/Tỉnh thành </div>
              <%--              <input type="text" name="city"  placeholder="nhập tên thành phố/tỉnh thành" value="${address.city}" required >--%>
              <select name="city" id="city" required >
                <option value="${address.city}" selected>Vui lòng chọn Tỉnh/thành phố</option>
              </select>
            </div>

            <!--Form Group-->
            <div class="form-group col-md-6 col-sm-12 col-xs-12">
              <div class="field-label">Quận/huyện </div>
              <%-- <input type="text" name="district"   placeholder="nhập tên quận/huyện" value="${address.district}" required > --%>
              <select name="district" id="district" required disabled>
                <option value="${address.district}" selected>Vui lòng chọn  Quận/huyện</option>
              </select>
            </div>

            <!--Form Group-->
            <div class="form-group col-md-6 col-sm-6 col-xs-12">
              <div class="field-label">Phường/xã</div>
              <%-- <input type="text" name="ward"  placeholder="nhập tên phường/xã" value="${address.ward}"  required>--%>
              <select name="ward" id="ward" required disabled>
                <option value="${address.ward}" selected>Vui lòng chọn  Phường/xã</option>
              </select>

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
            </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
      <input type="hidden" id="submit-fee-cost" name="submit-fee-cost">
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
          <th>Loại</th>
          <th>Giá</th>
          <th>Số lượng</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listCartDisplay}" var="cart">
          <c:set var="variant" value="${cart.variant}"/>
          <tr>
            <td>${cart.product.name}</td>
            <td>
              <c:choose>
                <c:when test="${variant != null}">${variant.name}</c:when>
                <c:otherwise>Sản phẩm gốc</c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:choose>
                <c:when test="${variant != null}">${variant.price}đ</c:when>
                <c:otherwise>${cart.product.price}đ</c:otherwise>
              </c:choose>
            </td>
            <td class="total-quantity">${cart.quantity}</td>
          </tr>
        </c:forEach>
        <tr>
          <td>Số tiền được giảm</td>
          <td></td>
          <td >${discountPrice}</td>
        </tr>
        <tr>
          <td>Phí Vận Chuyển</td>
          <td></td>
          <td id="fee-cost"></td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
          <td>Tổng: </td>
          <td></td>
          <input id="sub-total-amount" type="hidden" value="${totalAmount - discountPrice}">
          <td id="total-amount"></td>
        </tr>
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
                <input type="radio" name="payment-group" id="payment-2" value="bank_transfer" checked>
                <label for="payment-2"><strong>Thanh toán chuyển khoản </strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>
            <li>
              <div class="radio-option">
                <input type="radio" name="payment-group" id="payment-1" value="cod">
                <label for="payment-1"><strong>Cod</strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>

            <li>
              <div class="radio-option">
                <input type="radio" name="payment-group" id="payment-3" value="momo">
                <label for="payment-3"><strong>MOMO</strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>
            <li>
              <div class="radio-option">
                <input type="radio" name="payment-group" id="payment-4" value="vnpay">
                <label for="payment-4"><strong>VnPay</strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>
            <li>
              <div class="radio-option">
                <input type="radio" name="payment-group" id="payment-5" value="paypal">
                <label for="payment-5"><strong>PayPal</strong><span class="small-text">Thực hiện thanh toán trực tiếp vào tài khoản ngân hàng của chúng tôi. Vui lòng sử dụng Mã Đơn Hàng của bạn làm tham chiếu thanh toán. Đơn hàng của bạn sẽ không được gửi cho đến khi số tiền thanh toán được ghi nhận vào tài khoản của chúng tôi.</span></label>
              </div>
            </li>
          </ul>
        </div>

        <div class="lower-box text-right">
          <div class="shop_btn_wrapper checkout_btn">
            <ul>
              <li><a id="submit-order">Đặt hàng </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    <!--End Payment Box-->
  </div>

</div>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <!--        <button type="button" class="close" data-dismiss="modal">&times;</button>-->
        <h4 class="modal-title">Thanh toán chuyển khoản</h4>
      </div>
      <div class="modal-body">
        <div class="text-center" id="img-qr">
<%--          create img element--%>
        </div>
        <div class="text-center">
          <div>
            <strong>Hêt hạn thanh toán trong: </strong><span id="countdown">09:59</span>
          </div>
          <div>
            <strong>Trạng thái: </strong> <span class="paid-status">đang chờ thanh toán</span> <span class="fa fa-circle-o-notch spinner text-primary" ></span>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" id="btn-close-modal" class="btn btn-default  " data-dismiss="modal" >Đóng</button>
      </div>
    </div>

  </div>
</div>
<!--End CheckOut Page-->
<%@include file="component/footer.jsp"%>

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
<script src="../static/js/address.js"></script>
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
<%--<script src="../static/js/demo/checkout.js"></script>--%>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>

<%--<script>--%>
<%--  document.getElementById("submit-order").addEventListener("click", (e)=>{--%>
<%--    e.preventDefault();--%>
<%--    console.log("submit order");--%>
<%--    Toastify({--%>
<%--      text: "Đặt hàng thành công \n Đơn hàng của bạn sẽ sớm được giao!",--%>
<%--      duration: 5000,--%>
<%--      newWindow: true,--%>
<%--      close: true,--%>
<%--      gravity: "top", // `top` or `bottom`--%>
<%--      position: "center", // `left`, `center` or `right`--%>
<%--      stopOnFocus: true, // Prevents dismissing of toast on hover--%>
<%--      style: {--%>
<%--        //   background: "linear-gradient(to right, #00b09b, #96c93d)",--%>
<%--      },--%>
<%--      onClick: function(){} // Callback after click--%>
<%--    }).showToast();--%>
<%--    setTimeout(()=>{--%>
<%--      //submit form--%>
<%--      document.querySelector(".checkout-form").submit();--%>
<%--    }, 1000);--%>
<%--  })--%>
<%--</script>--%>
<script>

  document.getElementById("submit-order").addEventListener("click", (e) => {

    e.preventDefault();

    // Lấy giá trị radio được chọn
    let selectedPayment = $('input[name="payment-group"]:checked').val();
    if (selectedPayment === 'bank_transfer' || selectedPayment === 'momo' || selectedPayment === 'vnpay' || selectedPayment === 'paypal') {
      // Nếu là thanh toán chuyển khoản, hiển thị modal
      $('#myModal').modal('show');
      //create qr code
      const totalAmount = parseInt($('#total-amount').text().replace(' đ', '').trim());
      const descriptionInput = generateRandomString(12);
      //create img qr code element
      let qrCodeUrl = 'https://img.vietqr.io/image/BIDV-V3CASSPHUOCHAI-print.png?amount='+totalAmount+'&addInfo='+descriptionInput;
      // Tạo phần tử img
      let qrCodeImg = $('<img>').attr({
        src: qrCodeUrl,
        alt: 'qr',
        width: '70%'
      });

      $('#img-qr').append(qrCodeImg);


      let time = 600;
      let $countdown = $('#countdown');

      let interval = setInterval(function() {
        time--;
        let minutes = Math.floor(time / 60).toString().padStart(2, '0');
        let seconds = (time % 60).toString().padStart(2, '0');
        $countdown.text(minutes+":"+seconds);

        if (time <= 0) {
          $countdown.text('QR đã hết hạn!');
          $('.spinner').hide();
          $('.paid-status').text('Đã hết hạn thanh toán!')
          clearInterval(interval);
        }
      }, 1000);

      //call api check payment status
      let checkPaymentStatus = setInterval(() => {
        $.ajax({
          url: 'https://script.googleusercontent.com/macros/echo?user_content_key=AehSKLhWMwJXwW1qQT-lb2wGPftMQqOKJtBVFyFH6xfJIMLWwedFPxc-WpkE6Vu1HXQoi2gxvvbemIdqBpDFPJMF8V1VZTgrLsFTHbxAP0ZRsEN4s3eMMyFaFJyBdLJwByrUA4mbXuXmk7lcdUsI94VYIgKV3c8BAP7SNO4aIwjdMrADtQyl3tHP_6tVGUpNlK29fdop3CYtsdkq1AQ_N3mHEExH5xkhStphUstO5qsOkoTfZkSn1108X_Ij1c_NSvm5SmBrGg9p3PNvdyVMR4qAazkTVInjSA&lib=MefAGJO-K_E0-PprtpWJOobDt6-Ehs5Nh',
          type: 'GET',
          success: function(response) {
            const data = response.data[1];
            const price = data['Số tiền'];
            const description = data['Mô tả'];

            if(price >= totalAmount && description.includes(descriptionInput)) {
              clearInterval(checkPaymentStatus);
              clearInterval(interval);
              $('.spinner').hide();
              $('#btn-close-modal').removeClass('hidden');
              $('.paid-status').text('Đã thanh toán thành công!')
              // Hiển thị thông báo thành công
              Toastify({
                text: "Thanh toán thành công!\nĐơn hàng của bạn sẽ sớm được giao!",
                duration: 5000,
                newWindow: true,
                close: true,
                gravity: "top",
                position: "center",
                stopOnFocus: true,
                onClick: function(){}
              }).showToast();
              setTimeout(() => {
                document.querySelector(".checkout-form").submit();
              }, 3000);

            }else{
              console.log('Chưa thanh toán');
            }
          },
          error: function() {
            console.error("Error checking payment status");
          }
        });
      }, 3000);
    } else {
      // Hiển thị thông báo thành công
      Toastify({
        text: "Thanh toán thành công!\nĐơn hàng của bạn sẽ sớm được giao!",
        duration: 5000,
        newWindow: true,
        close: true,
        gravity: "top",
        position: "center",
        stopOnFocus: true,
        onClick: function(){}
      }).showToast();
      setTimeout(() => {
        document.querySelector(".checkout-form").submit();
      }, 3000);
    }
  });

  const generateRandomString = (length) => {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'; // Ký tự cho phép
    let result = '';
    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
  }
</script>

</body>

</html>