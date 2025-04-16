package vn.edu.hcmuaf.fit.myphamstore.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogModel {
    private Long id;
    private String logLevel;
    private String loggerName;
    private String message;
    private LocalDateTime createdAt;
}
