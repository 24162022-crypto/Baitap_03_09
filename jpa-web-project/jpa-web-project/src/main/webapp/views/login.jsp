<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body class="auth-page">
    <div class="auth-box">
        <h2>Đăng nhập hệ thống</h2>

        <c:if test="${not empty error}">
            <p class="msg error">${error}</p>
        </c:if>
        <c:if test="${not empty success}">
            <p class="msg success">${success}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label>Tên đăng nhập</label>
                <input type="text" name="username" value="${username}" required autofocus>
            </div>
            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary">Đăng nhập</button>
        </form>

        <p class="switch-link">
            Chưa có tài khoản?
            <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
        </p>
    </div>
</body>
</html>
