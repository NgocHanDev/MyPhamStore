<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@include file="/common/tablib.jsp"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
    />
    <title>Trang chủ admin</title>
    <link rel="stylesheet" href="../static/css/admin.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap"
      rel="stylesheet"
    />
    <!--srart theme style -->
    <link rel="stylesheet" type="text/css" href="../static/css/bootstrap.css" />
    <link
      rel="stylesheet"
      type="text/css"
      href="../static/css/font-awesome.css"
    />
    <link
      rel="stylesheet"
      type="text/css"
      href="../static/css/owl.carousel.css"
    />
    <link rel="stylesheet" type="text/css" href="../static/css/fonts.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/camera.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/style.css" />
    <link
      rel="stylesheet"
      type="text/css"
      href="../static/css/responsive.css"
    />
    <!-- favicon links -->
    <link
      rel="shortcut icon"
      type="image/png"
      href="../static/images/header/favicon.png"
    />
    <link
      rel="stylesheet"
      href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
    <link rel="stylesheet" href="../static/css/category-management.css" />
  </head>

  <body>
    <div class="dashboard">
      <div class="sidebar " id="sidebar">
        <%@include file="/admin/components/admin-sidebar.jsp"%>
      </div>
      <!-- end sidebar -->
      <!-- start header -->
      <div class="main-content" id="header" style="margin-left: 0; height: fit-content;">
        <%@include file="/admin/components/admin-header.jsp"%>
      </div>
      <!-- Main Content -->
      <main class="main-content row" style="margin-top: 76px">
        <!--style="display: flex; position: relative; height: min-content;"> -->

        <div class="left-col">
          <div class="stats">
            <div class="stat-box">
              <p>Nhân viên</p>
              <div class="employee-avatars">
                <div class="employee-avatar">AVT</div>
                <div class="employee-avatar">AVT</div>
                <div class="employee-avatar">AVT</div>
                <span class="extra-avatars">+24</span>
              </div>
            </div>
            <div class="stat-box">
              <p>Tổng sản phẩm</p>
              <select>
                <option>Ngày</option>
                <option>Tuần</option>
                <option>Tháng</option>
                <option>Năm</option>
              </select>
              <p class="Fduct-stats">
                <span class="added-products">+150 SP</span>
                <span class="removed-products">-10 SP</span>
              </p>
            </div>
            <div class="stat-box">
              <p>Tổng nhãn hàng</p>
              <div class="employee-avatars">
                <div class="employee-avatar">AVT</div>
                <div class="employee-avatar">AVT</div>
                <div class="employee-avatar">AVT</div>
                <span class="extra-avatars">+24</span>
              </div>
            </div>
          </div>
          <section class="recent-orders" style="width: 100%">
            <h2>Đơn hàng gần đây</h2>
            <a href="#" class="btn btn-primary">Xem tất cả đơn hàng </a>
            <style>
              table {
                border-collapse: collapse;
                width: 100%;
              }

              th,
              td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
              }
            </style>

            <table>
              <tr>
                <th>Mã đơn hàng</th>
                <th>Khách hàng</th>
                <th>Trạng thái</th>
                <th>Ngày</th>
                <th>Tổng tiền</th>
              </tr>
              <tr>
                <td>001</td>
                <td>Nguyen Van A</td>
                <td>Đã giao</td>
                <td>10/11/2024</td>
                <td>500,000đ</td>
              </tr>
              <tr>
                <td>002</td>
                <td>Tran Thi B</td>
                <td>Đang xử lý</td>
                <td>10/11/2024</td>
                <td>200,000đ</td>
              </tr>
            </table>
          </section>
        </div>
        <!-- Revenue Section -->
        <div class="rightbar" style="width: 100vw">
          <div class="right-col" style="padding: 10">
            <div class="statistical">
              <div id="pieChart" style="height: 50vh"></div>
              <div class="chart-container" style="height: 50vh">
                <div id="columChart"></div>
              </div>
            </div>
          </div>
        </div>
      </main>

      <label for="sidebar" class="body-label" id="body-label"></label>
    </div>
    <script src="../static/js/bootstrap.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
    <script src="../static/js/admin.js"></script>
    <script src="../path/to/local/canvasjs.min.js"></script>
    <script src="../static/js/category-management.js"></script>
  </body>
</html>
