<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
    />
    <title>Chi Tiết Khách Hàng</title>

    <link rel="stylesheet" href="../../static/css/admin.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap"
      rel="stylesheet"
    />
    <!--srart theme style -->
    <link
      rel="stylesheet"
      type="text/css"
      href="../../static/css/bootstrap.css"
    />
    <link rel="stylesheet" type="text/css" href="../../static/css/fonts.css" />
    <link
      rel="stylesheet"
      t
      ype="text/css"
      href="../../static/css/responsive.css"
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
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
    <link rel="stylesheet" href="../../static/css/add-employee.css" />
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
          <h1 class="text-center" style="margin-bottom: 20px">Chi Tiết Người Dùng
          </h1>
          <form
            class=""
            action="#"
            method="post"
            style="padding: 0 100px 0 100px"
            onsubmit=" return validateForm()"
          >
            <div class="row">
              <div class="col-md-6 col-xs-12">
                <div class="input-group" style="margin-top: 20px">
                  <label
                    class="input-group-addon"
                    for="fullName"
                    id="basic-addon1"
                    >Tên Nhân Viên</label
                  >
                  <input
                    id="fullName"
                    name="fullName"
                    type="text"
                    class="form-control"
                    placeholder="Tên nhân viên..."
                    aria-describedby="basic-addon1"
                    readonly
                    value="${user.fullName}"
                  />
                </div>
              </div>
              <div class="col-md-6 col-xs-12">
                <div class="input-group" style="margin-top: 20px">
                  <label for="email" class="input-group-addon" id="basic-addon1"
                    >Email</label
                  >
                  <input
                          readonly
                          value="${user.email}"
                    type="text"
                    id="email"
                    name="email"
                    class="form-control"
                    placeholder="email..."
                    aria-describedby="basic-addon1"
                  />
                </div>
              </div>
              <div class="col-md-6 col-xs-12" style="margin-top: 20px">
                <div class="input-group">
                  <label for="gender" class="input-group-addon" id="basic-addon1"
                  >Giới Tính</label
                  >
                  <input
                          type="text"
                          readonly
                          value="${user.gender}"
                          id="gender"
                          name="gender"
                          class="form-control"
                          placeholder="Số điện thoại..."
                          aria-describedby="basic-addon1"
                  />
                </div>
              </div>
              <div class="col-md-6 col-xs-12" style="margin-top: 20px">
                <div class="input-group">
                  <label for="status" class="input-group-addon" id="basic-addon1"
                  >Trạng Thái</label
                  >
                  <input
                          type="text"
                          readonly
                          value="${user.status}"
                          id="status"
                          name="status"
                          class="form-control"
                          placeholder="Số điện thoại..."
                          aria-describedby="basic-addon1"
                  />
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-md-6 col-xs-12" style="margin-top: 20px">
                <div class="input-group">
                  <label for="phone" class="input-group-addon" id="basic-addon1"
                    >Số điện thoại</label
                  >
                  <input
                    type="text"
                    readonly
                    value="${user.phone}"
                    id="phone"
                    name="phone"
                    class="form-control"
                    placeholder="Số điện thoại..."
                    aria-describedby="basic-addon1"
                  />
                </div>
              </div>
              <div class="col-md-6 col-xs-12" style="margin-top: 20px">
                <div class="input-group">
                  <label
                    for="dateOfBirth"
                    class="input-group-addon"
                    id="basic-addon1"
                    >Ngày tháng năm sinh</label
                  >
                  <input
                    id="dateOfBirth"
                    name="dateOfBirth"
                    type="date"
                    class="form-control"
                    placeholder="Ngày tháng năm sinh..."
                    value="${user.dateOfBirth}"
                    readonly
                    aria-describedby="basic-addon1"
                  />
                </div>
              </div>
              <div class="col-md-6 col-xs-12" style="margin-top: 20px">
                <div class="input-group">
                  <label
                          for="lastLogin"
                          class="input-group-addon"
                          id="basic-addon1"
                  >Đăng nhập gần nhất</label
                  >
                  <input
                          id="lastLogin"
                          name="lastLogin"
                          type="datetime-local"
                          class="form-control"
                          placeholder="Ngày tháng năm sinh..."
                          value="${user.lastLogin}"
                          readonly
                          aria-describedby="basic-addon1"
                  />
                </div>
              </div>
              <div class="col-md-6 col-xs-12" style="margin-top: 20px">
                <div class="input-group">
                  <label
                          for="createdAt"
                          class="input-group-addon"
                          id="basic-addon1"
                  >Ngày tạo tài khoản</label
                  >
                  <input
                          id="createdAt"
                          name="createdAt"
                          type="datetime-local"
                          class="form-control"
                          placeholder="Ngày tháng năm sinh..."
                          value="${user.createdAt}"
                          readonly
                          aria-describedby="basic-addon1"
                  />
                </div>
              </div>
              <div class="col-md-6 col-xs-12" style="margin-top: 20px">
                <div class="input-group">
                  <label
                          for="updatedAt"
                          class="input-group-addon"
                          id="basic-addon1"
                  >Ngày cập nhật gần nhất</label
                  >
                  <input
                          id="updatedAt"
                          name="updatedAt"
                          type="datetime-local"
                          class="form-control"
                          placeholder="Ngày tháng năm sinh..."
                          value="${user.updatedAt}"
                          readonly
                          aria-describedby="basic-addon1"
                  />
                </div>
              </div>
            </div>
            <!--  phần chỉnh sửa vai trò -->
            <div class="row" style="margin-top: 20px">
              <div class="col-md-12">
                <div class="input-group">
                  <label for="roles" class="input-group-addon" id="basic-addon1">Vai trò</label>
                  <select multiple id="roles" name="roles" class="form-control">
                    <c:forEach var="availableRole" items="${availableRoles}">
                      <c:set var="isSelected" value="false" />
                      <c:forEach var="userRole" items="${user.roles}">
                        <c:if test="${userRole.name eq availableRole.name}">
                          <c:set var="isSelected" value="true" />
                        </c:if>
                      </c:forEach>
                      <option value="${availableRole.name}" ${isSelected ? 'selected' : ''}>
                        <c:choose>
                          <c:when test="${availableRole.name eq 'ADMIN'}">Quản trị viên</c:when>
                          <c:when test="${availableRole.name eq 'EMPLOYEE'}">Nhân viên</c:when>
                          <c:otherwise>Khách hàng</c:otherwise>
                        </c:choose>
                      </option>
                    </c:forEach>
                  </select>
                </div>
              </div>
            </div>
            <div class="row" style="margin-top: 20px">
              <div class="col-md-12">
                <div class="input-group">
                  <label
                    for="avatar"
                    class="input-group-addon"
                    id="basic-addon1"
                    >Ảnh đại diện</label
                  >
                </div>
              </div>
              <div class="col-md-12" style="text-align: center">
                <img src="${user.avatar}" alt="image" height="300px"  />
              </div>
            </div>
            <div style="margin-top: 20px" class="text-center">
              <button
                class="btn-lg btn-danger"
                style="width: 200px"
                type="button"
                onclick="history.back()"
              >
                Quay lại
              </button>
              <button class="btn-lg btn-primary" style="width: 200px" type="submit" formaction="<c:url value='/admin/users?action=updateRoles&id=${user.id}'/>">Lưu vai trò</button>
            </div>
          </form>
        </div>
      </main>
      <label for="sidebar" class="body-label" id="body-label"></label>
    </div>
<%--    <script>--%>
<%--      const password = document.getElementById("password");--%>
<%--      const retypePass = document.getElementById("retypePass");--%>
<%--      const messOfRetype = document.getElementById("messOfRetype");--%>

<%--      retypePass.addEventListener("keyup", function () {--%>
<%--        console.log("hello");--%>
<%--        if (password.value !== retypePass.value) {--%>
<%--          messOfRetype.hidden = false;--%>
<%--        } else {--%>
<%--          messOfRetype.hidden = true;--%>
<%--        }--%>
<%--      });--%>
<%--    </script>--%>

    <script>
      document
        .getElementById("inputImage")
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
    <script>
      function validateForm() {
        const rolesSelect = document.getElementById("roles");
        const selectedRoles = rolesSelect.selectedOptions.length;
        console.log("Selected roles count: " + selectedRoles);
        if (selectedRoles === 0) {
          alert("Vui lòng chọn ít nhất một vai trò.");
          return false; // Ngăn gửi form
        } else {
          return true; // Cho phép gửi form
        }
      }
    </script>
    <script src="../../static/js/bootstrap.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
    <script src="../../static/js/admin.js"></script>
    <script src="../../static/js/category-management.js"></script>
  </body>
</html>
