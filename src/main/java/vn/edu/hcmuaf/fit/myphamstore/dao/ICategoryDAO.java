package vn.edu.hcmuaf.fit.myphamstore.dao;

import vn.edu.hcmuaf.fit.myphamstore.model.CategoryModel;

import java.util.List;

public interface ICategoryDAO {
    Long save(CategoryModel entity);

    CategoryModel update(CategoryModel entity);

    void delete(CategoryModel entity);

    List<CategoryModel> findAll(String keyword, int currentPage, int pageSize, String orderBy);

    List<CategoryModel> findAll(String keyword, int currentPage, int pageSize, String orderBy, List<Long> selectedBrands);
    Long getTotalPage(int numOfItem);
    CategoryModel findCategoryById(Long id);
    List<CategoryModel> getAllCategories();
}