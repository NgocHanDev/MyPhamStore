<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/common/tablib.jsp"%>
<!DOCTYPE html>
<html lang="en" style="scroll-behavior: smooth;">
<head>
  <meta charset="utf-8" />
  <title>Chi tiết sản phẩm</title>
  <meta content="width=device-width, initial-scale=1.0" name="viewport" />
  <meta name="description" content="big basket" />
  <meta name="keywords" content="big basket, Ecommerce, Store, Mall, online Shopping" />
  <meta name="author" content="" />
  <meta name="MobileOptimized" content="320" />
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
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
  <link rel="shortcut icon" type="image/png" href="../static/images/header/favicon.png" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <style>
    .btc_shop_sin_pro_icon_wrapper i {
      font-family: FontAwesome !important;
      color: gray; /* Màu mặc định */
      font-size: 24px;
      cursor: pointer;
      transition: color 0.2s ease-in-out; /* Hiệu ứng mượt khi đổi màu */
    }

    .btc_shop_sin_pro_icon_wrapper i.fa-star {
      color: gold !important; /* Màu khi được chọn */
    }
    #variants{
      border-radius: 4px;
      padding: 2px;
      border: none;
      transform: translateX(-6px);
    }
    #variants:hover{
    border: 1px solid #0091DC;
    }
  </style>
</head>
<body>
<div id="preloader">
  <div id="status">
    <img src="../static/images/header/preloader.gif" id="preloader_image" alt="loader" />
  </div>
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
            <li>
              <a href="#"><i class="fa fa-home"></i></a>&nbsp;&nbsp; >
            </li>
            <li>Sản phẩm&nbsp;&nbsp; ></li>
            <li><c:out value="${brand.name}"/>&nbsp;&nbsp; ></li>
            <li><c:out value="${product.name}"/></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="cc_ps_top_product_main_wrapper">
  <div class="container">
    <div class="row">
      <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
        <div class="video_img_section_wrapper">
          <div class="cc_ps_top_slider_section">
            <div class="owl-carousel owl-theme">
              <c:forEach var="productImage" items="${productImages}">
              <div class="item" data-hash="zero">
                  <img class="small img-responsive" src="<c:out value="${productImage.url}"/>" alt="Product Image">
              </div>
              </c:forEach>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
        <div class="btc_shop_single_prod_right_section">
          <h1><a href="#">${product.name}"</h1>
            <div class="btc_shop_single_prod_right_section_cont">
            <p>
              <c:out value="${reviewCount} "/> Đánh Giá <span><a ></a></span>
            </p>
              <p>Đã bán: <c:out value="${soldQuantity}"/> sản phẩm</p>

              <c:set var="variants" value="${variants}"/>
              <c:if test="${not empty variants}">
                <h5>Chọn biến thể:</h5>
                <select id="variants" onchange="changeVariant(this)">
<%--                  mặt định sản phẩm gốc--%>
                  <option value="" selected
                          data-price="${product.price}"
                          data-stock="${product.stock}">sản phẩm gốc - ${product.price}(Còn: ${product.stock})
                  </option>
<%--  các biến thể--%>
                  <c:forEach items="${variants}" var="variant">
                    <option value="${variant.id}"
                            data-price="${variant.price}"
                            data-stock="${variant.stock}">
                        ${variant.name} - ${variant.price}
                      (Còn: ${variant.stock})
                    </option>
                  </c:forEach>
                </select>
              </c:if>
            <div class="ss_featured_products_box_img_list_cont ss_featured_products_box_img_list_cont_single">

<%--              <p class="shop_pera"><c:out value="${product.brandName}"/></p>--%>
              <del><c:out value="${product.costPrice}"/>đ</del> <ins id="product-price"><c:out value="${product.price}"/>đ</ins>
<%--              <h5>--%>
<%--                Giới Thiệu:--%>
<%--                <c:out value="${product.description}"/>--%>
<%--              </h5>--%>
            </div>

          </div>
          <div class="btc_shop_prod_quanty_bar">
            <div class="row">
              <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 full_width">
                <div class="cc_ps_quantily_info">
                    <div class="select_number">
                      <button onclick="changeQty(1); return false;" class="increase">
                        <i class="fa fa-plus"></i>
                      </button>
                      <input type="text" name="quantity" value="1" size="2" id="input-quantity" class="form-control" />
                      <button onclick="changeQty(0); return false;" class="decrease">
                        <i class="fa fa-minus"></i>
                      </button>
                    </div>
                    <input type="hidden" name="product_id" />
                </div>
              </div>

              <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="cc_ps_deliv_main_wrapper">
                    <div class="cc_ps_bottom_cont_heading_wrapper">
                      <p>Policys :</p>
                    </div>
                </div>
              </div>
              <form method="post" action="/gio-hang" onsubmit="submitAddCart(event)">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" id="productId" value="${product.id}">
                <input type="hidden" name="variantId" id="variantId" value="">
                <button type="submit"  class="ss_btn">Thêm vào giỏ</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- CC ps top product Wrapper End -->
<!-- accordion section start -->
<div class="accordion_section">
  <div class="container">
    <div class="row">
      <div class="col-lg-12 col-md-9 col-sm-12 col-xs-12">
        <div class="panel-group" id="accordionFourLeft">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion_oneLeft" href="#description"
                   aria-expanded="false">
                  Mô Tả
                </a>
              </h4>
            </div>
            <div id="description" class="panel-collapse collapse in" aria-expanded="false" role="tablist">
              <div class="panel-body">
               ${product.description}
              </div>
              <!-- end of panel-body -->
            </div>
          </div>
          <!-- recommend product-->
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion_oneLeft" href="#related-products"
                   aria-expanded="false">
                  Sản phẩm cùng thể loại
                </a>
              </h4>
            </div>
            <div id="related-products" class="panel-collapse collapse in" aria-expanded="true" role="tablist">
              <div class="ss_latest_products_wrapper" style="padding-bottom: 0;">
                <div class="container">
                  <div class="row">
                    <div class="col-lg-12">
                      <div class="ss_latest_products">
                        <div class="owl-carousel owl-theme" id="related-products-carousel">
                          <c:forEach var="product" items="${sameCateProducts}">
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
                                  <form method="post" action="<c:url value='/gio-hang' />">
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
          <!-- /.panel-default -->
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion_oneLeft" href="#review"
                   aria-expanded="false">
                  Đánh giá
                </a>
              </h4>
            </div>
            <div id="review" class="panel-collapse collapse in" aria-expanded="false" role="tablist">
              <div class="panel-body">
                <div class="col-lg-6 col-md-6 col-sm-12">
                  <form action="<c:url value='/product-detail' />" method="post">
                  <div class="btc_shop_single_prod_right_section shop_product_single_head">
                    <%
                      String error = request.getParameter("error");
                      if ("notPurchased".equals(error)) {
                    %>
                    <div class="alert alert-warning">Bạn chưa mua sản phẩm này nên không thể đánh giá.</div>
                    <%
                      }
                    %>
                    <h1>Thêm đánh giá của bạn</h1>
                    <input type="hidden" name="action" value="addReview" />
                    <input type="hidden" name="productId" value="${product.id}" />
                    <input type="hidden" name="userId" value="${user.id}" />
                    <div class="btc_shop_sin_pro_icon_wrapper">
                      <i class="fa fa-star-o" data-value="1"></i>
                      <i class="fa fa-star-o" data-value="2"></i>
                      <i class="fa fa-star-o" data-value="3"></i>
                      <i class="fa fa-star-o" data-value="4"></i>
                      <i class="fa fa-star-o" data-value="5"></i>
                    </div>
                    <input type="hidden" name="rating" id="rating" value="0"> <!-- Input ẩn để lưu số sao -->

                  </div>
                  <div class="text-accordion shop_pdt_form">
                    <div class="row">
                      <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="contect2_form1">
                          <input type="text" name="userName" placeholder="Your Name"  value="${user.fullName}"/>
                          <i class="fa fa-user"></i>


                        </div>
                      </div>
                      <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="contect2_form1">
                          <input type="text" name="userName" placeholder="Your Name" value="${user.email}" />
                          <i class="fa fa-user"></i>

                        </div>
                      </div>
                      <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="contect2_form4">
                          <textarea  name="comment" rows="4" placeholder=" Your comment"></textarea><i
                                class="fa fa-question-circle"></i>
                        </div>
                      </div>
                      <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="pdt_single_page_btn">
                          <div class="shop_btn_wrapper">
                            <ul>
                              <li><button class="btn btn-primary" type="submit">Đánh Giá</button></li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                    </form>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-12">
                  <div
                          class="btc_shop_single_prod_right_section shop_product_single_head shop_product_single_head_respon">
                    <h1>Đánh giá sản phẩm</h1>
                    <h4><c:out value="${total}⭐ "/><span>đánh giá tổng</span></h4>
                  </div>
                  <div class="text-accordion shop_pdt_form">
                    <div class="progress_section">
                      <div class="progress-wrapper">
                        <div class="progress-item">
                          <span class="progress-title">5 sao</span>
                          <span class="progress-percent pull-right">
                              <c:out value="${total5}% "/></span>
                          <div class="progress">
                            <div class="progress-bar progress-bar-dealy" role="progressbar" aria-valuenow="${total5}"
                                 aria-valuemin="0" aria-valuemax="100" style="width: ${total5}%;"></div>
                          </div>
                          <!-- /.progress -->
                        </div>
                        <!-- /.progress-item -->
                        <div class="progress-item">
                          <span class="progress-title">4 sao</span>
                          <span class="progress-percent pull-right">
                              <c:out value="${total4}% "/></span>
                          <div class="progress">
                            <div class="progress-bar progress-bar-success progress-bar-dealy" role="progressbar"
                                 aria-valuenow="${total4}" aria-valuemin="0" aria-valuemax="100" style="width: ${total4}%;"></div>
                          </div>
                          <!-- /.progress -->
                        </div>
                        <!-- /.progress-item -->
                        <div class="progress-item">
                          <span class="progress-title">3 sao </span>
                          <span class="progress-percent pull-right">
                               <c:out value="${total3} "/>%</span>
                          <div class="progress">
                            <div class="progress-bar progress-bar-danger progress-bar-dealy" role="progressbar"
                                 aria-valuenow="${total3}" aria-valuemin="0" aria-valuemax="100" style="width: ${total3}%;"></div>
                          </div>
                          <!-- /.progress -->
                        </div>
                        <!-- /.progress-item -->
                        <div class="progress-item">
                          <span class="progress-title">2 sao</span>
                          <span class="progress-percent pull-right">
                              <c:out value="${total2} "/>%</span>
                          <div class="progress">
                            <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="${total2}"
                                 aria-valuemin="0" aria-valuemax="100" style="width: ${total2}%;"></div>
                          </div>
                          <!-- /.progress -->
                        </div>
                        <!-- /.progress-item -->
                        <div class="progress-item">
                          <span class="progress-title">1 sao</span>
                          <span class="progress-percent pull-right">
                               <c:out value="${total1} "/>%</span>
                          <div class="progress">
                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="${total1} "
                                 aria-valuemin="0" aria-valuemax="100" style="width: ${total1}%;"></div>
                          </div>
                          <!-- /.progress -->
                        </div>
                      </div>
                      <!-- /.progress-wrapper -->
                    </div>
                  </div>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12">
                  <div class="comment_box_blog">
                    <div class="sp_comment1_wrapper cmnt_wraper_2">
                      <c:forEach var="review" items="${reviews}">
                        <c:set var="matchedUser" value="" />
                        <c:forEach var="user" items="${users}">
                          <c:if test="${user.id == review.userId}">
                            <c:set var="matchedUser" value="${user}" />
                          </c:if>
                        </c:forEach>

                        <div class="sp_comment1_img text-center">
                          <c:choose>
                            <c:when test="${not empty matchedUser.avatar}">
                              <img src="data:image/png;base64,${matchedUser.avatar}" alt="Avatar" class="user-avatar" width="70" height="70">

                            </c:when>
                            <c:otherwise>
                              <i class="fa fa-user" style="font-size: 70px;" aria-hidden="true"></i>
                            </c:otherwise>
                          </c:choose>
                        </div>
                        <div class="sp_comment1_cont">
                          <h3><c:out value="${matchedUser.fullName}"/></h3>
                          <p>
                            <span><c:out value="${review.createdAt}"/></span>
                          </p>
                          <p>
                            <span><c:out value="${review.rating}"/>⭐</span>
                          </p>
                          <p>
                            <c:out value="${review.comment}"/>
                          </p>
                        </div>
                      </c:forEach>

                    </div>
                  </div>
                </div>
              </div>
              <!-- end of panel-body -->
            </div>
          </div>
          <!--end of /.panel-group-->
        </div>
      </div>
    </div>
  </div>
</div>
<!-- accordion section end -->

  <%@include file="component/footer.jsp"%>

<%--<script>--%>
<%--  const submitAddCart = (e) =>{--%>
<%--    e.preventDefault();--%>
<%--    //--%>
<%--    const productId = document.getElementById("productId").value;--%>
<%--    const  select = document.getElementById('variants');--%>
<%--    const variantId = select.options[select.selectedIndex].value--%>

<%--    const url = '/gio-hang?action=add&variantId='+variantId+'&productId='+productId--%>

<%--    fetch(url, {--%>
<%--      method: 'POST'--%>
<%--    }).then(()=>{--%>
<%--      window.location.reload();--%>
<%--    })--%>
<%--  }--%>

<%--</script>--%>

<script>
  const changeVariant = (select) =>{
    const selectedOption = select.options[select.selectedIndex];
    const id = selectedOption.id;
    const price = parseInt(selectedOption.getAttribute("data-price"));
    const stock = selectedOption.getAttribute("data-stock");

    document.getElementById('variantId').value = id
    document.getElementById('product-price').textContent = price+'đ'
  }
</script>
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
  <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
  <script src="../static/js/demo/cart.js"></script>
<script>
  document.addEventListener("DOMContentLoaded", function () {
    console.log("Script chạy!");

    const stars = document.querySelectorAll(".btc_shop_sin_pro_icon_wrapper i");
    const ratingInput = document.getElementById("rating");

    function updateStars(rating) {
      // Cập nhật giá trị input ẩn
      ratingInput.value = rating;
      console.log("Đánh giá:", rating);

      // Reset tất cả sao về trạng thái mặc định
      stars.forEach((star, index) => {
        if (index < rating) {
          star.classList.remove("fa-star-o");
          star.classList.add("fa-star");
        } else {
          star.classList.remove("fa-star");
          star.classList.add("fa-star-o");
        }
      });
    }

    stars.forEach(star => {
      star.addEventListener("click", function () {
        const rating = parseInt(this.getAttribute("data-value"), 10);
        console.log("Click sao:", rating);
        updateStars(rating);
      });
    });

    // Đặt mặc định là 1 sao
    updateStars(1);
  });
  $(document).ready(function () {
    // Initialize Owl Carousel for Related Products
    $("#related-products-carousel").owlCarousel({
      loop: true,
      margin: 15,
      nav: true,
      dots: true,
      autoplay: true,
      autoplayTimeout: 5000,
      autoplayHoverPause: true,
      responsive: {
        0: {
          items: 1,
        },
        576: {
          items: 2,
        },
        768: {
          items: 3,
        },
        992: {
          items: 4,
        },
      },
    });

    // Add to Cart Toast Notification
    $("form[action='/gio-hang']").on("submit", function (e) {
      e.preventDefault();
      const form = $(this);
      $.ajax({
        url: form.attr("action"),
        method: "POST",
        data: form.serialize(),
        success: function () {
          Toastify({
            text: "Sản phẩm đã được thêm vào giỏ hàng!",
            duration: 3000,
            backgroundColor: "#28a745",
            className: "toastify-success",
          }).showToast();
        },
        error: function () {
          Toastify({
            text: "Có lỗi xảy ra khi thêm vào giỏ hàng.",
            duration: 3000,
            backgroundColor: "#dc3545",
            className: "toastify-error",
          }).showToast();
        },
      });
    });

    // Add to Wishlist Toast Notification
    $("form[action='/wishlist']").on("submit", function (e) {
      e.preventDefault();
      const form = $(this);
      $.ajax({
        url: form.attr("action"),
        method: "POST",
        data: form.serialize(),
        success: function () {
          Toastify({
            text: "Sản phẩm đã được thêm vào danh sách yêu thích!",
            duration: 3000,
            backgroundColor: "#28a745",
            className: "toastify-success",
          }).showToast();
        },
        error: function () {
          Toastify({
            text: "Có lỗi xảy ra khi thêm vào danh sách yêu thích.",
            duration: 3000,
            backgroundColor: "#dc3545",
            className: "toastify-error",
          }).showToast();
        },
      });
    });
  });
</script>


</body>
</html>
