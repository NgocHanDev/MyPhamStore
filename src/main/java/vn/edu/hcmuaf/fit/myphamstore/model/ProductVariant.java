package vn.edu.hcmuaf.fit.myphamstore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class ProductVariant extends BaseModel{
    private Long id;
    private Long productId;
    private String name;
    private Double price;
    private Double costPrice;
    private Integer stock;
    private Integer soldQuantity;
    private Boolean isAvailable;
}
