package vn.edu.hcmuaf.fit.myphamstore.dao;

import vn.edu.hcmuaf.fit.myphamstore.model.ProductModel;

import java.util.List;

public interface IProductDAO extends GenericDAO<ProductModel> {

    List<ProductModel> getFilteredProducts(String keyword, String[] selectedCategories, String[] selectedBrands, String priceRange, int currentPage, int pageSize, String orderBy);

    ProductModel getProductDetail(Long id);
    List<ProductModel> getProductsByCategory(Long categoryId);
    List<ProductModel> getLatestProductsByCategory(Long categoryId, int limit);
    List<ProductModel> getAllProducts();
    List<ProductModel> findVariantsByProductId(Long productId);
    List<ProductModel> getLatestProducts();
     Long countAllProducts();

    List<ProductModel> findProduct(Long productId);

    Integer getSoldQuantityByProductId(Long id);

    void increaseSoldQuantity(Long productId, Integer quantity);
}
