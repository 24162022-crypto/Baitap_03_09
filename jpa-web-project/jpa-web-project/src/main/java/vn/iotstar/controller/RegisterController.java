package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(name = "RegisterController", urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");

        // Validate cơ bản
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ thông tin bắt buộc!");
            forwardBack(req, resp, username, fullName, phone);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            forwardBack(req, resp, username, fullName, phone);
            return;
        }

        // Kiểm tra trùng username bằng JPA
        if (userService.existsByUsername(username.trim())) {
            req.setAttribute("error", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác!");
            forwardBack(req, resp, username, fullName, phone);
            return;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setRoleId(Constant.ROLE_USER);

        userService.register(user);

        req.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    private void forwardBack(HttpServletRequest req, HttpServletResponse resp,
                              String username, String fullName, String phone)
            throws ServletException, IOException {
        req.setAttribute("username", username);
        req.setAttribute("fullName", fullName);
        req.setAttribute("phone", phone);
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }
}
