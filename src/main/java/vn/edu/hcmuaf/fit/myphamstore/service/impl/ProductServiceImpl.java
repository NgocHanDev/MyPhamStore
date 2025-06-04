package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.myphamstore.dao.*;
import vn.edu.hcmuaf.fit.myphamstore.model.*;
import vn.edu.hcmuaf.fit.myphamstore.service.IProductService;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class ProductServiceImpl implements IProductService {
    //khai báo các hằng số cấu hình
    private static final String UPLOAD_DIRECTORY = "/static/images/products";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB


    @Inject
    private IProductDAO productDAO;
    @Inject
    private IBrandDAO brandDAO;
    @Inject
    private IProductImageDAO productImageDAO;
    @Inject
    private IReviewDAO reviewDAO;
    @Inject
    private ICategoryDAO categoryDAO;
    @Inject
    private IProductVariantDAO productVariantDAO;
    @Inject
    private LoggingService log;
    private final String CLASS_NAME = "PRODUCT-SERVICE";



    @Override
    public Long getTotalPage(int pageSize) {
        return productDAO.countAllProducts() / pageSize;
    }
    @Override
    public List<ProductModel> getProductsWithPaging(String keyword, int currentPage, int pageSize, String orderBy) {
        if (keyword != null && !keyword.isEmpty()) {
            keyword = keyword.trim();
        }
        log.info(CLASS_NAME, "lấy danh sách sản phẩm");
        return productDAO.findAll(keyword, currentPage, pageSize, orderBy);
    }

    @Override
    public Long getTotalProduct() {
        log.info(CLASS_NAME, "lấy tổng số sản phẩm");
        return productDAO.countAllProducts();
    }

    @Override
    public List<ProductModel> getProductsByCategory(long l) {
        log.info(CLASS_NAME, "lấy danh sách sản phẩm theo danh mục id: " + l );
        return productDAO.getProductsByCategory(l);
    }

    @Override
    public List<ProductModel> getLatestProducts() {
        log.info(CLASS_NAME, "lấy danh sách sản phẩm mới nhất");
        return productDAO.getLatestProducts();
    }

    @Override
    public ProductModel findProductById(Long id) {
        log.info(CLASS_NAME, "lấy sản phẩm theo id: " + id);
        return productDAO.getProductDetail(id);
    }

    @Override
    public ProductModel getProductDetail(Long id) {
        ProductModel product = productDAO.getProductDetail(id);
        if (product == null) {
            return null;
        }
        log.info(CLASS_NAME, "lấy chi tiết sản phẩm theo id: " + id);
        return productDAO.getProductDetail(id);
    }

    @Override
    public BrandModel getBrandById(Long brandId) {
        log.info(CLASS_NAME, "lấy thương hiệu theo id: " + brandId);
        return brandDAO.findBrandById(brandId);
    }

    @Override
    public List<ProductImageModel> getProductImageById(Long id) {
        log.info(CLASS_NAME, "lấy danh sách ảnh sản phẩm theo id: " + id);
        return productImageDAO.getProductImageById(id);

    }

    @Override
    public List<ReviewModel> getReviewsByProductId(Long id) {
        log.info(CLASS_NAME, "lấy danh sách đánh giá sản phẩm theo id: " + id);
        return reviewDAO.getAllReviewsByProductId(id);
    }

    @Override
    public void executeAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String[] listImage = request.getParameterValues("images");

        String thumbnail = request.getParameter("thumbnail");
        String name = request.getParameter("productName");
        String stock = request.getParameter("stock");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String costPrice = request.getParameter("costPrice");
        String brandId = request.getParameter("brandId");
        String categoryId = request.getParameter("categoryId");
        log.info(CLASS_NAME, "thêm sản phẩm mới");

        ProductModel productModel = ProductModel.builder()
                .name(name)
                .description(description)
                .price(Long.parseLong(price))
                .costPrice(Long.parseLong(costPrice))
                .brandId(Long.parseLong(brandId))
                .categoryId(Long.parseLong(categoryId))
                .thumbnail(thumbnail)
                .stock(Integer.parseInt(stock))
                .isAvailable(true)
                .build();
        try{
            Long isSuccess = productDAO.save(productModel);
            if (isSuccess == null || isSuccess == 0) {
                log.error(CLASS_NAME, "thêm sản phẩm thất bại");
                request.setAttribute("message", "Có lỗi xảy ra");
            } else {
                //tiến hành lưu ảnh sản phẩm
                for (String image : listImage) {
                    ProductImageModel productImageModel = ProductImageModel.builder()
                            .productId(isSuccess)
                            .url(image)
                            .build();
                    productImageDAO.save(productImageModel);
                }
                log.info(CLASS_NAME, "thêm sản phẩm thành công với id: " + isSuccess);
                request.setAttribute("message", "Thêm sản phẩm thành công");
                this.displayProduct(request, response);
            }
        }catch (Exception e){
            log.error(CLASS_NAME, "thêm sản phẩm thất bại");
            e.printStackTrace();
        }
    }

    @Override
    public void executeUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] listImage = request.getParameterValues("images") != null ? request.getParameterValues("images") : new String[0];
        Long id = Long.parseLong(request.getParameter("id"));
        String thumbnail = request.getParameter("thumbnail") != null ? request.getParameter("thumbnail") : "";
        String name = request.getParameter("productName");
        String stock = request.getParameter("stock");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String costPrice = request.getParameter("costPrice");
        String brandId = request.getParameter("brandId");
        String categoryId = request.getParameter("categoryId");
        log.info(CLASS_NAME, "cập nhật sản phẩm với id: " + id);

        // phần get data products variant ở đây
        String[] variantNames = request.getParameterValues("variantName");
        String[] variantStocks = request.getParameterValues("variantStock");
        String[] variantCostPrices = request.getParameterValues("variantCostPrice");
        String[] variantPrices = request.getParameterValues("variantPrice");

        // Kiểm tra xem dữ liệu có null không
        if (variantNames != null && variantStocks != null && variantCostPrices != null && variantPrices != null) {
            for (int i = 0; i < variantNames.length; i++) {
                String variantName = variantNames[i];
                int variantStock = Integer.parseInt(variantStocks[i]);
                double variantCostPrice = Double.parseDouble(variantCostPrices[i]);
                double variantPrice = Double.parseDouble(variantPrices[i]);

                // Xử lý dữ liệu, có thể lưu vào database
                ProductVariant productVariant = ProductVariant.builder()
                        .productId(id)
                        .name(variantName)
                        .stock(variantStock)
                        .costPrice(variantCostPrice)
                        .price(variantPrice)
                        .isAvailable(true)
                        .build();
                log.info(CLASS_NAME, "cập nhật sản phẩm variant với id: " + id);
                productVariantDAO.save(productVariant);
            }
        }

        ProductModel productModel = ProductModel.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(Long.parseLong(price))
                .costPrice(Long.parseLong(costPrice))
                .brandId(Long.parseLong(brandId))
                .categoryId(Long.parseLong(categoryId))
                .thumbnail(thumbnail)
                .stock(Integer.parseInt(stock))
                .isAvailable(true)
                .soldQuantity(0)
                .build();

        try{
            ProductModel isSuccess = productDAO.update(productModel);
            if (isSuccess == null) {
                request.setAttribute("message", "Có lỗi xảy ra");
                log.error(CLASS_NAME, "cập nhật sản phẩm thất bại với id: " + id);
            } else {
                //xóa ảnh cũ
                List<ProductImageModel> oldImages = productImageDAO.getProductImageById(id);
                for (ProductImageModel image : oldImages) {
                    productImageDAO.delete(image);
                }
                //tiến hành lưu ảnh sản phẩm
                for (String image : listImage) {
                    ProductImageModel productImageModel = ProductImageModel.builder()
                            .productId(id)
                            .url(image)
                            .build();
                    log.info(CLASS_NAME, "cập nhật ảnh sản phẩm với id: " + id);
                    productImageDAO.save(productImageModel);
                }
                request.setAttribute("message", "Cập nhật sản phẩm thành công");
                //refresh lại trang
                this.displayProduct(request, response);
            }
        }catch (Exception e){
            log.error(CLASS_NAME, "cập nhật sản phẩm thất bại với id: " + id);
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductModel> getFilteredProducts(String keyword, List<String> categories, List<String> brands, String priceRange, int currentPage, int pageSize, String orderBy) {
        String[] categoriesArray = categories != null ? categories.toArray(new String[0]) : new String[0];
        String[] brandsArray = brands != null ? brands.toArray(new String[0]) : new String[0];
        log.info(CLASS_NAME, "lấy danh sách sản phẩm theo bộ lọc");
        return productDAO.getFilteredProducts(keyword, categoriesArray, brandsArray, priceRange, currentPage, pageSize, orderBy);
    }

    @Override
    public List<ProductVariant> getProductVariantsByProductId(Long id) {
        try{
            log.info(CLASS_NAME, "lấy danh sách sản phẩm variant theo id: " + id);
            return productVariantDAO.findAllByProduct(ProductModel.builder().id(id).build());
        }catch (Exception e){
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<ProductModel> getMostPopularProducts() {
        return productDAO.getMostPopularProducts();
    }

    @Override
    public List<ProductModel> findProduct(Long productId) {
        log.info(CLASS_NAME, "lấy danh sách sản phẩm theo id: " + productId);
        return productDAO.findProduct(productId);
    }

    @Override
    public ProductVariant findVariantById(Long variantId) {
        return productVariantDAO.findById(variantId);
    }
    @Override
    public Integer getSoldQuantityByProductId(Long id) {
        return productDAO.getSoldQuantityByProductId(id);
    }

    @Override
    public void incrementSoldQuantity(Long productId, Integer quantity) {
        productDAO.increaseSoldQuantity(productId, quantity);
    }

    @Override
    public void decrementSoldQuantity(Long productId, Integer quantity) {
        ProductModel product = findProductById(productId);
        if (product != null) {
            int currentSold = product.getSoldQuantity();
            int newSold = Math.max(0, currentSold - quantity);
            product.setSoldQuantity(newSold);
            productDAO.update(product);
        }
    }


    @Override
    public void stopBuying(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info(CLASS_NAME, "cập nhật trạng thái sản phẩm với id: " + request.getParameter("id"));
        Long id = Long.parseLong(request.getParameter("id"));
        //tến hành cập nhật trạng thái sản phẩm
        ProductModel productModel = ProductModel.builder().id(id).build();
        productModel.setIsAvailable(false);

        ProductModel isSuccess = productDAO.update(productModel);
        if (isSuccess == null) {
            request.setAttribute("message", "Có lỗi xảy ra");
            log.error(CLASS_NAME, "cập nhật trạng thái sản phẩm thất bại với id: " + id);
        } else {
            request.setAttribute("message", "Cập nhật thành công id: " + id);
            log.info(CLASS_NAME, "cập nhật trạng thái sản phẩm thành công với id: " + id);
            this.displayProduct(request, response);
        }
    }

    @Override
    public void startBuying(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        log.info(CLASS_NAME, "cập nhật trạng thái sản phẩm với id: " + id);
        //tến hành cập nhật trạng thái sản phẩm
        ProductModel productModel = ProductModel.builder().id(id).build();
        productModel.setIsAvailable(true);

        ProductModel isSuccess = productDAO.update(productModel);
        if (isSuccess == null) {
            request.setAttribute("message", "Có lỗi xảy ra");
            log.error(CLASS_NAME, "cập nhật trạng thái sản phẩm thất bại với id: " + id);
        } else {
            request.setAttribute("message", "Cập nhật thành công id: " + id);
            log.info(CLASS_NAME, "cập nhật trạng thái sản phẩm thành công với id: " + id);
            this.displayProduct(request, response);
        }
    }

    @Override
    public void displayProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/product/product-management.jsp");
        String keyword = request.getParameter("keyword");
        String orderBy = request.getParameter("orderBy");
        int currentPage = Integer.parseInt(request.getParameter("currentPage") == null ? "1" : request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize") == null ? "10" : request.getParameter("pageSize"));

        List<ProductModel> products = this.getProductsWithPaging(keyword, currentPage, pageSize, orderBy);
        Long totalPages = this.productDAO.getTotalPage(pageSize);
        log.info(CLASS_NAME, "lấy danh sách sản phẩm");
        // Gửi danh sách sản phẩm đến trang JSP
        request.setAttribute("products", products);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalItems", productDAO.countAllProducts());
        System.out.println("currentPage: " + currentPage);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyword", keyword);
        request.setAttribute("orderBy", orderBy);
        dispatcher.forward(request, response);
    }

    @Override
    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/product/add-product.jsp");
        List<BrandModel> brands = brandDAO.getAllBrands();
        List<CategoryModel> categories = categoryDAO.getAllCategories();
        log.info(CLASS_NAME, "lấy danh sách thương hiệu và danh mục sản phẩm");
        request.setAttribute("brands", brands);
        request.setAttribute("categories", categories);
        dispatcher.forward(request, response);
    }

    @Override
    public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/product/add-product.jsp");
        Long id = Long.parseLong(request.getParameter("id"));
        ProductModel product = productDAO.getProductDetail(id);
        List<ProductImageModel> images = productImageDAO.getProductImageById(id);
        List<BrandModel> brands = brandDAO.getAllBrands();
        List<CategoryModel> categories = categoryDAO.getAllCategories();
        List<ProductVariant> productVariants = productVariantDAO.findAllByProduct(product);
        log.info(CLASS_NAME, "lấy danh sách thương hiệu và danh mục sản phẩm");

        request.setAttribute("variants", productVariants);
        request.setAttribute("brands", brands);
        request.setAttribute("categories", categories);
        request.setAttribute("product", product);
        request.setAttribute("images", images);
        dispatcher.forward(request, response);
    }
}
