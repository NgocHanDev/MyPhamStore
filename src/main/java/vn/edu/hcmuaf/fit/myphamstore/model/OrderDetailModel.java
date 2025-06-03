package vn.edu.hcmuaf.fit.myphamstore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class OrderDetailModel extends BaseModel{
    private Long orderId;
    private Long productId;
    private Integer quantity;
//    private Long variantId;
    private Double totalPrice;
}
