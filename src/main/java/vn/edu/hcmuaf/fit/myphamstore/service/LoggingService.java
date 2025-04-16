package vn.edu.hcmuaf.fit.myphamstore.service;

import vn.edu.hcmuaf.fit.myphamstore.common.LogLevel;

public interface LoggingService {
    void info(String loggerName, String message);
    void error(String loggerName, String message);
    void warn(String loggerName, String message);

}
