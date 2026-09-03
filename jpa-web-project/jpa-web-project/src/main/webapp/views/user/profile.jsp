<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thông tin cá nhân</title>
</head>
<body>

<h2>Thông tin cá nhân</h2>

<c:if test="${not empty success}">
    <p class="msg success">${success}</p>
</c:if>

<div class="profile-box">

    <div class="profile-avatar">
        <c:choose>
            <c:when test="${not empty user.images}">
                <img src="${pageContext.request.contextPath}${user.images}" alt="Avatar" class="avatar-img">
            </c:when>
            <c:otherwise>
                <div class="avatar-placeholder">${fn:substring(user.username, 0, 1)}</div>
            </c:otherwise>
        </c:choose>
    </div>

    <form action="${pageContext.request.contextPath}/user/profile"
          method="post" enctype="multipart/form-data" class="form-card">

        <div class="form-group">
            <label>Tên đăng nhập</label>
            <input type="text" value="${user.username}" disabled>
        </div>

        <div class="form-group">
            <label>Họ và tên</label>
            <input type="text" name="fullName" value="${user.fullName}">
        </div>

        <div class="form-group">
            <label>Số điện thoại</label>
            <input type="text" name="phone" value="${user.phone}">
        </div>

        <div class="form-group">
            <label>Cập nhật ảnh đại diện</label>
            <input type="file" name="avatarFile" accept="image/*">
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Cập nhật</button>
        </div>
    </form>
</div>

</body>
</html>
