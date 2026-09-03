<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thêm Danh mục</title>
</head>
<body>

<h2>Thêm danh mục mới</h2>

<c:if test="${not empty error}">
    <p class="msg error">${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/admin/category/insert"
      method="post" enctype="multipart/form-data" class="form-card">

    <div class="form-group">
        <label>Tên danh mục <span class="required">*</span></label>
        <input type="text" name="categoryName" required>
    </div>

    <div class="form-group">
        <label>Ảnh đại diện (nhập URL)</label>
        <input type="text" name="imageUrl" placeholder="https://example.com/image.jpg">
    </div>

    <div class="form-group">
        <label>Hoặc tải ảnh lên từ máy</label>
        <input type="file" name="imageFile" accept="image/*">
    </div>

    <div class="form-group">
        <label>Trạng thái</label>
        <select name="status">
            <option value="1" selected>Hoạt động</option>
            <option value="0">Ngừng hoạt động</option>
        </select>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">Lưu</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/categories">Hủy</a>
    </div>
</form>

</body>
</html>
