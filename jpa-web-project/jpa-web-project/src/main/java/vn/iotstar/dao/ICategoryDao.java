package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.Category;

public interface ICategoryDao {

    /**
     * Lấy toàn bộ danh sách danh mục.
     */
    List<Category> findAll();

    /**
     * Tìm danh mục theo id.
     */
    Category findById(Integer id);

    /**
     * Thêm mới danh mục.
     */
    Category save(Category category);

    /**
     * Cập nhật danh mục.
     */
    Category update(Category category);

    /**
     * Xóa danh mục theo id.
     */
    boolean deleteById(Integer id);
}
