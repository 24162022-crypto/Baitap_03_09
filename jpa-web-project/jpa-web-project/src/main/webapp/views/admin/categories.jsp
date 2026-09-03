<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý Danh mục</title>
</head>
<body>

<div class="page-header">
    <h2>Quản lý Danh mục</h2>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/category/add">+ Thêm danh mục</a>
</div>

<table class="data-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Ảnh</th>
            <th>Tên danh mục</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="cat" items="${categories}">
            <tr>
                <td>${cat.categoryId}</td>
                <td>
                    <c:if test="${not empty cat.images}">
                        <img src="${cat.images}" alt="${cat.categoryName}" class="thumb">
                    </c:if>
                </td>
                <td>${cat.categoryName}</td>
                <td>
                    <c:choose>
                        <c:when test="${cat.status == 1}">
                            <span class="badge badge-active">Hoạt động</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-inactive">Ngừng</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-danger btn-sm"
                       href="${pageContext.request.contextPath}/admin/category/delete?id=${cat.categoryId}"
                       onclick="return confirm('Bạn có chắc muốn xóa danh mục này?');">
                        Xóa
                    </a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty categories}">
            <tr>
                <td colspan="5" class="empty-row">Chưa có danh mục nào.</td>
            </tr>
        </c:if>
    </tbody>
</table>

</body>
</html>
