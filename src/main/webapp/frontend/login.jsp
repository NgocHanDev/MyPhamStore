<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/tablib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Đăng nhập</title>
  <meta content="width=device-width, initial-scale=1.0" name="viewport" />

  <!-- Include CSS -->
  <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/bootstrap.css" />
  <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/font-awesome.css" />
  <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/style.css" />
  <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/login.css?v=20250410" />
</head>

<body>

<!-- Main Content -->
<div class="main-content">
  <div class="company__info">
    <a href="<c:url value='/trang-chu' />">
      <img src="<%= request.getContextPath() %>/static/images/logo/logo3.svg" alt="Logo" />
    </a>
  </div>

  <div class="login_form">
    <div class="container-fluid">
      <h1>Đăng nhập</h1>

      <c:if test="${!empty message}">
        <div class="alert alert-warning" role="alert">${message}</div>
      </c:if>

      <form id="form-login" action="<c:url value='/login?action=login' />" method="post">
        <input type="text" name="email" id="email" class="form__input" placeholder="Email" required />
        <input type="password" name="password" id="password" class="form__input" placeholder="Mật khẩu" required />
        <div id="message" class="alert alert-danger" role="alert" hidden></div>
        <input type="submit" value="Đăng nhập" class="btn" />
      </form>

      <hr>
      <p style="text-align: center">
        Hoặc đăng nhập bằng
      </p>
      <div style="display: flex; justify-content: center; gap: 10px; margin-bottom: 20px;">
        <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/MyPhamStore/login&response_type=code&client_id=39478411393-b755esrk8a9suhtqv16o3onsl0p8qd9a.apps.googleusercontent.com&approval_prompt=force" class="btn btn-social btn-google">
          <i class="fa fa-google"></i> Google
        </a>

        <a href="<c:url value='/login-facebook' />" class="btn btn-social btn-facebook">
          <i class="fa fa-facebook"></i> Facebook
        </a>
      </div>

      <p style="text-align: center">Bạn quên mật khẩu?
        <a style="color: #0000cc" href="<%= request.getContextPath() %>/forgot-password">Quên mật khẩu</a>
      </p>
      <p style="text-align: center">Bạn chưa có tài khoản?
        <a style="color: #0000cc" href="<%= request.getContextPath() %>/register?action=register">Đăng ký</a>
      </p>
    </div>
  </div>
</div>

</body>
</html>
