package vn.edu.hcmuaf.fit.myphamstore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class CartModel  implements Serializable {
    private Long productId;
    private Integer quantity;
    private Long brandId;
    private Long variantId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartModel cartModel = (CartModel) o;
        return Objects.equals(productId, cartModel.productId) && Objects.equals(variantId, cartModel.variantId);
    }
}
