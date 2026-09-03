package vn.iotstar.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

/**
 * Servlet quản lý Trang cá nhân (User Profile):
 *  - GET  /user/profile  -> hiển thị thông tin cá nhân
 *  - POST /user/profile  -> cập nhật họ tên / số điện thoại / ảnh đại diện
 */
@WebServlet(name = "ProfileController", urlPatterns = { "/user/profile" })
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1 MB
        maxFileSize = 1024 * 1024 * 10,       // 10 MB
        maxRequestSize = 1024 * 1024 * 50     // 50 MB
)
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User sessionUser = getLoggedInUser(req);
        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy dữ liệu mới nhất từ DB (đề phòng dữ liệu session cũ)
        User freshUser = userService.findById(sessionUser.getId());
        req.setAttribute("user", freshUser);
        req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User sessionUser = getLoggedInUser(req);
        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");

        // Lấy bản ghi mới nhất từ DB để cập nhật (tránh mất dữ liệu các trường khác)
        User user = userService.findById(sessionUser.getId());
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        user.setFullName(fullName);
        user.setPhone(phone);

        // Xử lý upload ảnh đại diện mới (nếu có)
        Part filePart = req.getPart("avatarFile");
        if (filePart != null && filePart.getSize() > 0) {
            String savedPath = saveUploadedFile(req, filePart);
            user.setImages(savedPath);
        }

        User updated = userService.updateProfile(user);

        // Tự động làm mới dữ liệu người dùng trong HttpSession
        HttpSession session = req.getSession();
        session.setAttribute(Constant.SESSION_USER, updated);

        req.setAttribute("success", "Cập nhật thông tin thành công!");
        req.setAttribute("user", updated);
        req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
    }

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
        String newFileName = "avatar_" + UUID.randomUUID() + extension;

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, uploadPath.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
        }

        return Constant.UPLOAD_URL + "/" + newFileName;
    }

    private User getLoggedInUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute(Constant.SESSION_USER);
    }
}
