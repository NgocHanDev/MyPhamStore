package vn.edu.hcmuaf.fit.myphamstore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CartHeaderModel implements Serializable {
    private Long userId;
}
