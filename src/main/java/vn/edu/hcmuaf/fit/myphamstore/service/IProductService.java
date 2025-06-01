package vn.edu.hcmuaf.fit.myphamstore.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.model.*;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    Long getTotalPage(int numOfItems);
    List<ProductModel> getProductsWithPaging(String keyword,int currentPage, int pageSize, String orderBy);
    Long getTotalProduct();
    void stopBuying( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void startBuying( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void displayProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    List<ProductModel> getProductsByCategory(long l);
    List<ProductModel> getLatestProducts();
    ProductModel findProductById(Long id);

    ProductModel getProductDetail(Long id);
    BrandModel getBrandById(Long brandId);

   List<ProductImageModel> getProductImageById(Long id);

    List<ReviewModel> getReviewsByProductId(Long id);

    void executeAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    void executeUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    List<ProductModel> getFilteredProducts(String keyword, List<String> categories, List<String> brands, String priceRange, int currentPage, int pageSize, String orderBy);
    List<ProductVariant> getProductVariantsByProductId(Long id);


    List<ProductModel> findProduct(Long orderDetailId);

    ProductVariant findVariantById(Long variantId);
    Integer getSoldQuantityByProductId(Long id);

    void incrementSoldQuantity(Long productId, Integer quantity);
}
