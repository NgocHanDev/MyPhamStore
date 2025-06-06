<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/common/tablib.jsp"%>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Thêm nhãn hàng</title>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!--srart theme style -->
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/animate.css"
    />
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/bootstrap.css"
    />
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/font-awesome.css"
    />
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/owl.carousel.css"
    />
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/owl.theme.default.css"
    />
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/magnific-popup.css"
    />
    <link rel="stylesheet" type="text/css" href="../../static/css/fonts.css" />
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/dl-menu.css"
    />
    <link rel="stylesheet" type="text/css" href="../../static/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../../static/css/camera.css" />
    <link rel="stylesheet" type="text/css" href="../../static/css/style.css" />
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/responsive.css"
    />
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/sidebar.css"
    />
    <!-- favicon links -->
    <link
      rel="shortcut icon"
      type="image/png"
      href="../../static/images/header/favicon.png"
    />
    <link
      rel="stylesheet"
      href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css"
    />
    <link rel="stylesheet" href="../../static/css/brand-management.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
  </head>
  <body>
    <div class="dashboard">
      <!-- Sidebar -->
      <div class="sidebar " id="sidebar">
        <%@include file="/admin/components/admin-sidebar.jsp"%>
      </div>
      <!-- end sidebar -->
      <!-- start header -->
      <div class="main-content" id="header" style="margin-left: 0; height: fit-content;">
        <%@include file="/admin/components/admin-header.jsp"%>
      </div>
      <!-- end header -->

      <!-- Main Content -->
      <main class="main-content row my-table-custom">
        <!--style="display: flex; position: relative; height: min-content;"> -->
        <div class=" " style="width: 100%">
          <h1 class="text-center" style="margin-bottom: 20px; margin-top: 85px">
            Thêm Nhãn Hàng Mới
          </h1>
          <form
            class=""
            action="#"
            method="post"
            style="padding: 0 100px 0 100px; margin-left: 100px"
          >
            <div class="row">
              <div class="col-lg-12">
                <div class="input-group">
                  <span class="input-group-addon" id="basic-addon1"
                    >Tên Nhãn hàng</span
                  >
                  <input
                    type="text"
                    class="form-control"
                    placeholder="Tên nhãn hàng..."
                    aria-describedby="basic-addon1"
                  />
                </div>
              </div>
            </div>
            <div class="row" style="margin-top: 20px">
              <div class="col-lg-6 row">
                <div class="col-lg-12">
                  <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1"
                      >Hình Ảnh</span
                    >
                    <input
                      id="imageInput"
                      type="file"
                      class="form-control"
                      placeholder="Hình ảnh sản phẩm..."
                      aria-describedby="basic-addon1"
                      accept="image/*"
                    />
                  </div>
                </div>
              </div>
              <div class="col-lg-6">
                <img src="" alt="image" width="100%" id="output" hidden />
              </div>
            </div>
            <div style="margin-top: 20px" class="text-center">
              <button
                class="btn-lg btn-danger"
                style="width: 200px"
                type="button"
                onclick="history.back()"
              >
                Huỷ bỏ
              </button>
              <button
                class="btn-lg btn-primary"
                style="width: 200px"
                type="submit"
              >
                Thêm Mới
              </button>
            </div>
          </form>
        </div>
      </main>
      <label for="sidebar" class="body-label" id="body-label"></label>
    </div>
    <script>
      document
        .getElementById("imageInput")
        .addEventListener("change", function (event) {
          const file = event.target.files[0]; // Lấy tệp được chọn
          if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
              document.getElementById("output").hidden = false;
              document
                .getElementById("output")
                .setAttribute("src", e.target.result);
            };
            reader.readAsDataURL(file); // Chuyển đổi tệp thành Base64
          } else {
            alert("No file selected!");
          }
        });
    </script>
    <script src="../../static/js/bootstrap.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
    <script src="../../static/js/admin.js"></script>
    <script src="../../static/js/category-management.js"></script>
  </body>
</html>
