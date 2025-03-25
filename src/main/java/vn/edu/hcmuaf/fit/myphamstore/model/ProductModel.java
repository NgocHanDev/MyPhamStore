package vn.edu.hcmuaf.fit.myphamstore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class ProductModel extends BaseModel{

    private String name;
    private Long price;
    private Long costPrice;
    private Integer stock;
    private Integer soldQuantity;
    private String description;
    private Boolean isAvailable;
    private String thumbnail;
    private Long categoryId;
    private Long brandId;


}
