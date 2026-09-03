package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(name = "LoginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        User user = userService.login(username.trim(), password);

        if (user == null) {
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        // Lưu thông tin user đăng nhập vào session
        HttpSession session = req.getSession();
        session.setAttribute(Constant.SESSION_USER, user);

        // Điều hướng theo role
        if (user.getRoleId() != null && user.getRoleId() == Constant.ROLE_ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } else {
            resp.sendRedirect(req.getContextPath() + "/user/profile");
        }
    }
}
