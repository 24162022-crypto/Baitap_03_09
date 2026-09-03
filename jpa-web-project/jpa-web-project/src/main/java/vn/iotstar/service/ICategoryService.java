package vn.iotstar.service;

import java.util.List;

import vn.iotstar.entity.Category;

public interface ICategoryService {

    List<Category> findAll();

    Category findById(Integer id);

    Category insert(Category category);

    Category update(Category category);

    boolean delete(Integer id);
}
