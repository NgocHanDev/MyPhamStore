package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.fit.myphamstore.dao.ICategoryDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.CategoryModel;
import vn.edu.hcmuaf.fit.myphamstore.service.ICategoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@Slf4j
@ApplicationScoped
public class CategoryServiceImpl implements ICategoryService {
    @Inject
    private ICategoryDAO categoryDAO;

    @Override
    public CategoryModel findCategoryById(Long id) {
        return categoryDAO.findCategoryById(id);
    }

    @Override
    public List<CategoryModel> findCategoriesParent(Long childId) {
        CategoryModel childCategory = categoryDAO.findCategoryById(childId);
        if (childCategory.getParentId() == null) {
            log("Category is parent");
            return null;
        }
        List<CategoryModel> result = new ArrayList<>();
        result.add(childCategory);//them category con vao list
        Long parentId = childCategory.getParentId();
        while (parentId != null) {
            CategoryModel parentCategory = categoryDAO.findCategoryById(parentId);
            if (parentCategory != null) {
                result.add(parentCategory);
                parentId = parentCategory.getParentId();
            } else {
                break;
            }
        }
        return result;
    }

    @Override
    public List<CategoryModel> pagingCategory(String keyword, int currentPage, int pageSize, String orderBy) {
        if (keyword != null && !keyword.isEmpty()) {
            keyword = keyword.trim();
        }
        return this.categoryDAO.findAll(keyword, currentPage, pageSize, orderBy);
    }

    @Override
    public Long getTotalPage(int numOfItem) {
        return categoryDAO.getTotalPage(numOfItem);
    }

    @Override
    public List<CategoryModel> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    @Override
    public void displayCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/category/category-management.jsp");
        String keyword = request.getParameter("keyword");
        String orderBy = request.getParameter("orderBy");
        int currentPage = Integer.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "5" : request.getParameter("pageSize"));
        String[] selectedBrandsParam = request.getParameterValues("selectedBrands");
        List<Long> selectedBrands = selectedBrandsParam != null ? Arrays.stream(selectedBrandsParam).map(Long::parseLong).collect(Collectors.toList()) : null;

        List<CategoryModel> categories = this.pagingCategory(keyword, currentPage, pageSize, orderBy, selectedBrands);
        Long totalPages = this.categoryDAO.getTotalPage(pageSize);

        request.setAttribute("categories", categories);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyword", keyword);
        request.setAttribute("orderBy", orderBy);
        request.setAttribute("selectedBrands", selectedBrands);
        dispatcher.forward(request, response);
    }

    @Override
    public void stopBuying(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void startBuying(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void addCategory(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void updateCategory(HttpServletRequest request, HttpServletResponse response) {

    }
    @Override
    public List<CategoryModel> pagingCategory(String keyword, int currentPage, int pageSize, String orderBy, List<Long> selectedBrands) {
        if (keyword != null && !keyword.isEmpty()) {
            keyword = keyword.trim();
        }
        return this.categoryDAO.findAll(keyword, currentPage, pageSize, orderBy, selectedBrands);
    }
}
