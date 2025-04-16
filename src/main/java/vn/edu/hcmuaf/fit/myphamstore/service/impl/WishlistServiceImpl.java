package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.inject.Inject;
import vn.edu.hcmuaf.fit.myphamstore.dao.IWishlistDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.ProductModel;
import vn.edu.hcmuaf.fit.myphamstore.service.IWishlistService;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.util.List;

public class WishlistServiceImpl implements IWishlistService {
    @Inject
    private IWishlistDAO wishlistDao;
    @Inject
    private LoggingService log;
    private final String CLASS_NAME = "WISHLIST-SERVICE";

    @Override
    public List<ProductModel> getWishlistByUserId(Long userId) {
        log.info(CLASS_NAME, "Lấy danh sách sản phẩm yêu thích của người dùng có id = " + userId);
        return wishlistDao.getWishlistByUserId(userId);
    }


    @Override
    public void addToWishlist(long userId, long productId) {
        if (!isProductInWishlist(userId, productId)) {
            log.info(CLASS_NAME, "Thêm sản phẩm có id = " + productId + " vào danh sách yêu thích của người dùng có id = " + userId);
            wishlistDao.addToWishlist(userId, productId);
        }
    }

    public boolean isProductInWishlist(long userId, long productId) {
        List<ProductModel> wishlist = wishlistDao.getWishlistByUserId(userId);
        log.info(CLASS_NAME, "Kiểm tra sản phẩm có id = " + productId + " có trong danh sách yêu thích của người dùng có id = " + userId);
        for (ProductModel product : wishlist) {
            if (product.getId() == productId) {
                return true;
            }
        }
        return false;
    }


    public List<ProductModel> getWishlistByUserId(Long userId, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        log.info(CLASS_NAME, "Lấy danh sách sản phẩm yêu thích của người dùng có id = " + userId + " với kích thước trang là " + pageSize + " và trang hiện tại là " + currentPage);
        return wishlistDao.getWishlistByUserId(userId, pageSize, offset);
    }

    public int getWishlistCountByUserId(Long userId) {
        log.info(CLASS_NAME, "Lấy số lượng sản phẩm yêu thích của người dùng có id = " + userId);
        return wishlistDao.getWishlistCountByUserId(userId);
    }

    @Override
    public void removeFromWishlist(Long userId, long productId) {
        log.info(CLASS_NAME, "Xóa sản phẩm có id = " + productId + " khỏi danh sách yêu thích của người dùng có id = " + userId);
        wishlistDao.removeFromWishlist(userId, productId);
    }

}