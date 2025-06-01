package vn.edu.hcmuaf.fit.myphamstore.dao;


import vn.edu.hcmuaf.fit.myphamstore.model.CartModel;

public interface ICartDAO extends GenericDAO<CartModel>{
    CartModel getCartByUserId(int userId);

}

