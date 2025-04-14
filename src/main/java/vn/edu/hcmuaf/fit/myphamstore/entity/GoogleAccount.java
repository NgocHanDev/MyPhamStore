package vn.edu.hcmuaf.fit.myphamstore.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class GoogleAccount {
    private String  id, email, name, first_name, given_name, family_name, picture;
    private boolean verified_email;


}
