package vn.iotstar.config;

import jakarta.servlet.annotation.WebFilter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * Filter cấu hình SiteMesh 3 - dùng để "trang trí" (decorate) toàn bộ trang
 * JSP bằng layout chung (main.jsp), ngoại trừ các trang login/register.
 *
 * Đăng ký filter này trong web.xml, ánh xạ url-pattern "/*".
 */
@WebFilter(filterName = "sitemesh", urlPatterns = "/*")
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        // Áp dụng layout chung cho toàn bộ trang .jsp
        builder.addDecoratorPath("/*", "/views/layouts/main.jsp");

        // Loại trừ các trang không cần layout (login, register, các trang admin
        // form nếu muốn để riêng, các resource tĩnh...)
        builder.addExcludedPath("/login");
        builder.addExcludedPath("/register");
        builder.addExcludedPath("/resources/*");
        builder.addExcludedPath("/uploads/*");
    }
}
