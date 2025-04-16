package vn.edu.hcmuaf.fit.myphamstore.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vn.edu.hcmuaf.fit.myphamstore.common.LogLevel;
import vn.edu.hcmuaf.fit.myphamstore.dao.LogDao;
import vn.edu.hcmuaf.fit.myphamstore.model.LogModel;
import vn.edu.hcmuaf.fit.myphamstore.service.LoggingService;

import java.time.LocalDateTime;

@ApplicationScoped
public class LoggingServiceImpl implements LoggingService {
    @Inject
    private LogDao logDao;

    @Override
    public void info( String loggerName, String message) {
        LogModel logModel = new LogModel();
        logModel.setLoggerName(loggerName);
        logModel.setMessage(message);
        logModel.setLogLevel(LogLevel.INFO.toString());
        logModel.setCreatedAt(LocalDateTime.now());
        logDao.saveLog(logModel);
    }

    @Override
    public void error(String loggerName, String message) {
        LogModel logModel = new LogModel();
        logModel.setLoggerName(loggerName);
        logModel.setMessage(message);
        logModel.setLogLevel(LogLevel.ERROR.toString());
        logModel.setCreatedAt(LocalDateTime.now());
        logDao.saveLog(logModel);
    }

    @Override
    public void warn(String loggerName, String message) {
        LogModel logModel = new LogModel();
        logModel.setLoggerName(loggerName);
        logModel.setMessage(message);
        logModel.setLogLevel(LogLevel.WARN.toString());
        logModel.setCreatedAt(LocalDateTime.now());
        logDao.saveLog(logModel);

    }
}
