package vn.edu.hcmuaf.fit.myphamstore.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class FacebookAccount {
    private String id;
    private String name;
    private String email;
    private String picture;


}
