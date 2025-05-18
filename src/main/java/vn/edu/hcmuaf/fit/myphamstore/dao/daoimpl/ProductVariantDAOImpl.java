package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.dao.IProductVariantDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.ProductModel;
import vn.edu.hcmuaf.fit.myphamstore.model.ProductVariant;

import java.util.List;

public class ProductVariantDAOImpl implements IProductVariantDAO {
    @Override
    public Long save(ProductVariant entity) {
        String sql = "INSERT INTO product_variant (product_id, name, price, cost_price, stock, sold_quantity, is_available) " +
                "VALUES ( :productId, :name, :price, :costPrice, :stock, :soldQuantity, :isAvailable)";
        try{
         return JDBIConnector.getJdbi().withHandle(handle -> handle.createUpdate(sql)
                .bind("productId", entity.getProductId() == null ? 0 : entity.getProductId())
                 .bind("name", entity.getName() == null ? "" : entity.getName())
                    .bind("price", entity.getPrice() == null ? 0 : entity.getPrice())
                    .bind("costPrice", entity.getCostPrice() == null ? 0 : entity.getCostPrice())
                    .bind("stock", entity.getStock() == null ? 0 : entity.getStock())
                    .bind("soldQuantity", entity.getSoldQuantity() == null ? 0 : entity.getSoldQuantity())
                    .bind("isAvailable", entity.getIsAvailable() == null || entity.getIsAvailable())
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Long.class)
                .one());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductVariant update(ProductVariant entity) {
        return null;
    }

    @Override
    public void delete(ProductVariant entity) {
        String sql = "DELETE FROM product_variant WHERE id = :id";
        try{
            JDBIConnector.getJdbi().withHandle(handle -> handle.createUpdate(sql)
                    .bind("id", entity.getId())
                    .execute());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductVariant> findAll(String keyword, int currentPage, int pageSize, String orderBy) {
        return List.of();
    }

    @Override
    public Long getTotalPage(int numOfItems) {
        return 0L;
    }

    @Override
    public List<ProductVariant> findAllByProduct(ProductModel productModel) {
        String sql = "SELECT * FROM product_variant WHERE product_id = :productId";
        try{
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("productId", productModel.getId())
                    .mapToBean(ProductVariant.class)
                    .list());
        }catch (Exception e){
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public ProductVariant findById(Long id) {
        String sql = "SELECT * FROM product_variant WHERE id = :id";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("id", id)
                    .mapToBean(ProductVariant.class)
                    .one());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
