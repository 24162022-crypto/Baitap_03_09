package vn.iotstar.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.utils.Constant;

/**
 * Servlet quản lý Danh mục (Admin):
 *  - GET  /admin/categories        -> danh sách
 *  - GET  /admin/category/add      -> form thêm mới
 *  - POST /admin/category/insert   -> xử lý thêm mới (URL ảnh hoặc upload file)
 *  - GET  /admin/category/delete   -> xóa theo id
 */
@WebServlet(name = "CategoryController", urlPatterns = {
        "/admin/categories",
        "/admin/category/add",
        "/admin/category/insert",
        "/admin/category/delete"
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1 MB
        maxFileSize = 1024 * 1024 * 10,       // 10 MB
        maxRequestSize = 1024 * 1024 * 50     // 50 MB
)
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String uri = req.getServletPath();

        switch (uri) {
            case "/admin/categories" -> showList(req, resp);
            case "/admin/category/add" -> req.getRequestDispatcher("/views/admin/category_form.jsp").forward(req, resp);
            case "/admin/category/delete" -> deleteCategory(req, resp);
            default -> resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String uri = req.getServletPath();
        if ("/admin/category/insert".equals(uri)) {
            doInsert(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/views/admin/categories.jsp").forward(req, resp);
    }

    private void doInsert(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String categoryName = req.getParameter("categoryName");
        String imageUrl = req.getParameter("imageUrl"); // Ảnh nhập qua URL
        String statusParam = req.getParameter("status");

        if (categoryName == null || categoryName.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập tên danh mục!");
            req.getRequestDispatcher("/views/admin/category_form.jsp").forward(req, resp);
            return;
        }

        String finalImagePath = null;

        // Ưu tiên file upload nếu có, nếu không thì dùng URL ảnh nhập tay
        Part filePart = req.getPart("imageFile");
        if (filePart != null && filePart.getSize() > 0) {
            finalImagePath = saveUploadedFile(req, filePart);
        } else if (imageUrl != null && !imageUrl.isBlank()) {
            finalImagePath = imageUrl.trim();
        }

        Category category = new Category();
        category.setCategoryName(categoryName.trim());
        category.setImages(finalImagePath);
        category.setStatus(statusParam != null ? Integer.parseInt(statusParam) : Constant.STATUS_ACTIVE);

        categoryService.insert(category);

        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                Integer id = Integer.parseInt(idParam);
                categoryService.delete(id);
            } catch (NumberFormatException ignored) {
                // id không hợp lệ -> bỏ qua
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    /**
     * Lưu file upload vào thư mục uploads trên server, trả về đường dẫn URL
     * tương đối để lưu vào CSDL.
     */
    private String saveUploadedFile(HttpServletRequest req, Part filePart) throws IOException {
        String uploadDirPath = req.getServletContext().getRealPath("/" + Constant.UPLOAD_DIR);
        Path uploadPath = Paths.get(uploadDirPath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String submittedFileName = filePart.getSubmittedFileName();
        String extension = "";
        int dotIndex = submittedFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = submittedFileName.substring(dotIndex);
        }
        String newFileName = "cat_" + UUID.randomUUID() + extension;

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, uploadPath.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
        }

        return Constant.UPLOAD_URL + "/" + newFileName;
    }

    private boolean isAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return false;
        }
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        return user != null && user.getRoleId() != null && user.getRoleId() == Constant.ROLE_ADMIN;
    }
}
