package vn.iotstar.dao;

import vn.iotstar.entity.User;

public interface IUserDao {

    /**
     * Tìm user theo username. Trả về null nếu không tồn tại.
     */
    User findByUsername(String username);

    /**
     * Tìm user theo id.
     */
    User findById(Integer id);

    /**
     * Kiểm tra username đã tồn tại trong DB chưa.
     */
    boolean existsByUsername(String username);

    /**
     * Xác thực đăng nhập: tìm user khớp username + password.
     */
    User checkLogin(String username, String password);

    /**
     * Thêm mới user (đăng ký).
     */
    User save(User user);

    /**
     * Cập nhật thông tin user (dùng cho chức năng cập nhật profile).
     */
    User update(User user);
}
