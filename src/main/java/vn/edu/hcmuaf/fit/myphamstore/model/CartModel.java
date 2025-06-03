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
public class CartModel extends BaseModel implements Serializable  {
    private Long cardId;
    private Long productId;
    private Integer quantity;
    private Long brandId;
    private Long variantId;
    private Long priceAtAdded;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartModel that = (CartModel) o;
        return productId.equals(that.productId) &&
                (variantId == null ? that.variantId == null : variantId.equals(that.variantId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, variantId);
    }
}
