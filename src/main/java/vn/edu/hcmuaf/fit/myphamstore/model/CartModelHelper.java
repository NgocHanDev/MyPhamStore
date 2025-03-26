package vn.edu.hcmuaf.fit.myphamstore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class CartModelHelper implements Serializable {
    private ProductModel product;
    private Integer quantity;
    private ProductVariant variant;
    public CartModelHelper(ProductModel product, Integer quantity) {}
}
