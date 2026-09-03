<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body class="auth-page">
    <div class="auth-box">
        <h2>Đăng ký tài khoản mới</h2>

        <c:if test="${not empty error}">
            <p class="msg error">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-group">
                <label>Tên đăng nhập <span class="required">*</span></label>
                <input type="text" name="username" value="${username}" required autofocus>
            </div>
            <div class="form-group">
                <label>Mật khẩu <span class="required">*</span></label>
                <input type="password" name="password" required>
            </div>
            <div class="form-group">
                <label>Xác nhận mật khẩu <span class="required">*</span></label>
                <input type="password" name="confirmPassword" required>
            </div>
            <div class="form-group">
                <label>Họ và tên</label>
                <input type="text" name="fullName" value="${fullName}">
            </div>
            <div class="form-group">
                <label>Số điện thoại</label>
                <input type="text" name="phone" value="${phone}">
            </div>
            <button type="submit" class="btn btn-primary">Đăng ký</button>
        </form>

        <p class="switch-link">
            Đã có tài khoản?
            <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
        </p>
    </div>
</body>
</html>
