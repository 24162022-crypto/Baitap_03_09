package vn.iotstar.service;

import vn.iotstar.entity.User;

public interface IUserService {

    User findByUsername(String username);

    User findById(Integer id);

    boolean existsByUsername(String username);

    User login(String username, String password);

    User register(User user);

    User updateProfile(User user);
}
