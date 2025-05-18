package vn.edu.hcmuaf.fit.myphamstore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class AddressModel extends BaseModel {
    /*
    CREATE TABLE `address` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int, //không cần lấy ra vì ko thể từ địa chỉ truy vấn ra user nào được
  `recipient_name` nvarchar(255) NOT NULL,
  `recipient_phone` varchar(15) NOT NULL,
  `city` nvarchar(255) NOT NULL,
  `district` nvarchar(255) NOT NULL,
  `ward` nvarchar(255) NOT NULL,
  `note` nvarchar(255),
  `is_default` boolean DEFAULT false,
  `is_active` boolean DEFAULT true,
  `created_at` datetime DEFAULT (now()),
  `updated_at` datetime DEFAULT (now())
);
     */
    private Long userId;
    private String recipientName;
    private String recipientPhone;
    private Integer cityId;
    private Integer districtId;
    private Integer wardCode;
    private String city;
    private String district;
    private String ward;
    private String note;
    private Boolean isDefault;
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddressModel that = (AddressModel) o;
        return Objects.equals(recipientName, that.recipientName)
                && Objects.equals(recipientPhone, that.recipientPhone)
                && Objects.equals(city, that.city)
                && Objects.equals(district, that.district)
                && Objects.equals(ward, that.ward) &&
                Objects.equals(note, that.note);
    }
}
