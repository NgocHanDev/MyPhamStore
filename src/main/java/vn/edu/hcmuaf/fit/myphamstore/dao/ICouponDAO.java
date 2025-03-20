package vn.edu.hcmuaf.fit.myphamstore.dao;

import vn.edu.hcmuaf.fit.myphamstore.model.CategoryModel;
import vn.edu.hcmuaf.fit.myphamstore.model.CouponModel;
import vn.edu.hcmuaf.fit.myphamstore.model.SlideModel;

import java.util.List;
import java.util.Set;

public interface ICouponDAO extends GenericDAO<CouponModel> {
    CouponModel getCouponDetail(Long id);
    CouponModel findCouponById(Long id);

    List<CouponModel> findAvailableCoupons();
    CouponModel findCouponByCode(String code);
}
