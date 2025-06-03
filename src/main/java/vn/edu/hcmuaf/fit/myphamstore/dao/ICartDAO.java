package vn.edu.hcmuaf.fit.myphamstore.dao;


import vn.edu.hcmuaf.fit.myphamstore.model.CartHeaderModel;
import vn.edu.hcmuaf.fit.myphamstore.model.CartModel;

import java.util.List;

public interface ICartDAO extends GenericDAO<CartModel>{
    Long createCartForUser(Long userId);
    CartHeaderModel getCartByUserId(Long userId);

    List<CartModel> getCartItemsByCartId(Long id);
}

