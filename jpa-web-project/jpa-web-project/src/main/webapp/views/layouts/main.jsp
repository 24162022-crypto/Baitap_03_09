<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%--
    SiteMesh 3 KHÔNG cần khai báo taglib/TLD cho các thẻ <sitemesh:write>.
    Đây là cách chính thức được khuyến nghị (xem QUICKSTART.md của sitemesh3):
    vì không có prefix "sitemesh" nào được khai báo bằng taglib directive,
    JSP engine (Jasper) sẽ coi <sitemesh:write .../> là văn bản HTML thô và
    xuất ra nguyên vẹn; sau đó Filter SiteMesh sẽ đọc HTML trả về và thay thế
    các thẻ này bằng title/head/body thật của trang gốc.
--%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><sitemesh:write property="title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <sitemesh:write property="head" />
</head>
<body>

    <!-- ================= HEADER ================= -->
    <header class="site-header">
        <div class="logo">JPA WEB PROJECT</div>

        <!-- ================= MENU NAVIGATION ================= -->
        <nav class="main-nav">
            <a href="${pageContext.request.contextPath}/admin/categories">Danh mục</a>
            <c:choose>
                <c:when test="${not empty sessionScope.SESSION_USER}">
                    <a href="${pageContext.request.contextPath}/user/profile">
                        Xin chào, ${sessionScope.SESSION_USER.fullName != null ? sessionScope.SESSION_USER.fullName : sessionScope.SESSION_USER.username}
                    </a>
                    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                    <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <!-- ================= NỘI DUNG THAY ĐỔI (BODY) ================= -->
    <main class="site-content">
        <sitemesh:write property="body" />
    </main>

    <!-- ================= FOOTER ================= -->
    <footer class="site-footer">
        <p>&copy; 2026 JPA Web Project - vn.iotstar. Bài tập Jakarta EE / JPA.</p>
    </footer>

</body>
</html>

