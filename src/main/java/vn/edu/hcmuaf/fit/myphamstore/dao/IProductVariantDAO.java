package vn.edu.hcmuaf.fit.myphamstore.dao;

import vn.edu.hcmuaf.fit.myphamstore.model.ProductModel;
import vn.edu.hcmuaf.fit.myphamstore.model.ProductVariant;

import java.util.List;

public interface IProductVariantDAO extends GenericDAO<ProductVariant> {
    List<ProductVariant> findAllByProduct(ProductModel productModel);

    ProductVariant findById(Long variantId);
}
