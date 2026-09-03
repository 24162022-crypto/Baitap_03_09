package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Integer id) {
        return categoryDao.findById(id);
    }

    @Override
    public Category insert(Category category) {
        if (category.getStatus() == null) {
            category.setStatus(1);
        }
        return categoryDao.save(category);
    }

    @Override
    public Category update(Category category) {
        return categoryDao.update(category);
    }

    @Override
    public boolean delete(Integer id) {
        return categoryDao.deleteById(id);
    }
}
