package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.dao.IAddressDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.AddressModel;

import java.time.LocalDateTime;
import java.util.List;
@ApplicationScoped
public class AddressDAOImpl implements IAddressDAO {


    @Override
    public List<AddressModel> findByUserId(Long userId) {
        String sql = "SELECT * FROM address WHERE user_id = :user_id AND is_active = 1";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("user_id", userId)
                    .mapToBean(AddressModel.class)
                    .list());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public boolean checkAddressIsExist(AddressModel addressModel, Long userId) {
        //get all address of user
        List<AddressModel> addressList = findByUserId((long) Integer.parseInt(userId.toString()));
        for (AddressModel address : addressList) {
            if (address.getRecipientName().equals(addressModel.getRecipientName()) &&
                    address.getRecipientPhone().equals(addressModel.getRecipientPhone()) &&
                    address.getCity().equals(addressModel.getCity()) &&
                    address.getDistrict().equals(addressModel.getDistrict()) &&
                    address.getWard().equals(addressModel.getWard()) &&
                    address.getNote().equals(addressModel.getNote())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AddressModel findAddressById(Long addressId) {
        String sql = "SELECT * FROM address WHERE id = :id";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("id", addressId)
                    .mapToBean(AddressModel.class)
                    .one());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long save(AddressModel entity) {
        String sql = "INSERT INTO address (user_id, recipient_name, recipient_phone, city_id, district_id, ward_code, city, district, ward, note, is_default, is_active, created_at, updated_at) " +
                "VALUES(:user_id, :recipient_name, :recipient_phone, :city_id, :district_id, :ward_code, :city, :district, :ward, :note, :is_default, :is_active, :created_at, :updated_at)";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createUpdate(sql)
                    .bind("user_id", entity.getUserId())
                    .bind("recipient_name", entity.getRecipientName()   == null ? "" : entity.getRecipientName())
                    .bind("recipient_phone", entity.getRecipientPhone() == null ? "" : entity.getRecipientPhone())
                    .bind("city_id", entity.getCityId() == null ? 0 : entity.getCityId())
                    .bind("district_id", entity.getDistrictId() == null ? 0 : entity.getDistrictId())
                    .bind("ward_code", entity.getWardCode() == null ? 0 : entity.getWardCode())
                    .bind("city", entity.getCity()  == null ? "" : entity.getCity())
                    .bind("district", entity.getDistrict()  == null ? "" : entity.getDistrict())
                    .bind("ward", entity.getWard() == null ? "" : entity.getWard())
                    .bind("note", entity.getNote() == null ? "" : entity.getNote())
                    .bind("is_default", entity.getIsDefault() == null ? 0 : entity.getIsDefault())
                    .bind("is_active", entity.getIsActive() == null ? 1 : entity.getIsActive())
                    .bind("created_at", entity.getCreatedAt() == null ? LocalDateTime.now() : entity.getCreatedAt())
                    .bind("updated_at", entity.getUpdatedAt() == null ? LocalDateTime.now() : entity.getUpdatedAt())
                    .executeAndReturnGeneratedKeys()
                    .mapTo(Long.class)
                    .one());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AddressModel update(AddressModel entity) {
        String sql = "UPDATE address SET recipient_name = :recipient_name, recipient_phone = :recipient_phone,city_id = :city_id, district_id = :district_id, ward_code = :ward_code, city = :city, district = :district, ward = :ward, note = :note, is_default = :is_default, is_active = :is_active, updated_at = :updated_at WHERE id = :id";
        try {
            JDBIConnector.getJdbi().withHandle(handle -> handle.createUpdate(sql)
                    .bind("recipient_name", entity.getRecipientName())
                    .bind("recipient_phone", entity.getRecipientPhone())
                    .bind("city_id", entity.getCityId())
                    .bind("district_id", entity.getDistrictId())
                    .bind("ward_code", entity.getWardCode())
                    .bind("city", entity.getCity())
                    .bind("district", entity.getDistrict())
                    .bind("ward", entity.getWard())
                    .bind("note", entity.getNote())
                    .bind("is_default", entity.getIsDefault())
                    .bind("is_active", entity.getIsActive())
                    .bind("updated_at", entity.getUpdatedAt())
                    .bind("id", entity.getId())
                    .execute());
            return entity;
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public void delete(AddressModel entity) {
        String sql = "UPDATE address SET is_active = 0 WHERE id = :id";
        try {
            int rows = JDBIConnector.getJdbi().withHandle(handle -> {
                handle.getConnection().setAutoCommit(false);
                int affectedRows = handle.createUpdate(sql)
                        .bind("id", entity.getId())
                        .execute();
                handle.commit();
                return affectedRows;
            });
            System.out.println("Đã cập nhật is_active = 0 cho id: " + entity.getId() + ", affected rows: " + rows);
            if (rows == 0) {
                System.out.println("No rows updated for id: " + entity.getId() + ". Check if ID exists.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa address: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public List<AddressModel> findAll(String keyword, int currentPage, int pageSize, String orderBy) {
        return List.of();
    }

    @Override
    public Long getTotalPage(int numOfItems) {
        return 0L;
    }
}
