package vn.edu.hcmuaf.fit.myphamstore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.Objects;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class ActivityLogModel extends BaseModel {
    private int userId;
    private String level;
    private String action;
    private String oldData;
    private String newData;
    private String location;

}
