package vn.iotstar.service.impl;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;

public class UserServiceImpl implements IUserService {

    private final IUserDao userDao = new UserDaoImpl();

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public User login(String username, String password) {
        return userDao.checkLogin(username, password);
    }

    @Override
    public User register(User user) {
        // Có thể bổ sung mã hoá mật khẩu (BCrypt) tại đây nếu yêu cầu bảo mật cao hơn
        if (user.getRoleId() == null) {
            user.setRoleId(2); // Mặc định role user thường
        }
        return userDao.save(user);
    }

    @Override
    public User updateProfile(User user) {
        return userDao.update(user);
    }
}
