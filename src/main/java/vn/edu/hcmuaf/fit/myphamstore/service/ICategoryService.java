package vn.edu.hcmuaf.fit.myphamstore.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.model.CategoryModel;

import java.io.IOException;
import java.util.List;

public interface ICategoryService {
    CategoryModel findCategoryById(Long id);
    List<CategoryModel> findCategoriesParent(Long childId);
    List<CategoryModel> pagingCategory(String keyword,int currentPage, int pageSize, String orderBy);
    Long getTotalPage(int numOfItem);

    List<CategoryModel> getAllCategories();

    void displayCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void stopBuying(HttpServletRequest request, HttpServletResponse response);

    void startBuying(HttpServletRequest request, HttpServletResponse response);

    void addCategory(HttpServletRequest request, HttpServletResponse response);

    void updateCategory(HttpServletRequest request, HttpServletResponse response);

    List<CategoryModel> pagingCategory(String keyword, int currentPage, int pageSize, String orderBy, List<Long> selectedBrands);
}
