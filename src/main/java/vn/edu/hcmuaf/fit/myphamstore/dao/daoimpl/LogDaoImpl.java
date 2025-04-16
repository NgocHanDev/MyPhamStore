package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;
import vn.edu.hcmuaf.fit.myphamstore.dao.LogDao;
import vn.edu.hcmuaf.fit.myphamstore.model.LogModel;

@ApplicationScoped
public class LogDaoImpl  implements LogDao {

    @Override
    public void saveLog(LogModel log) {
        String sql = "INSERT INTO logs (log_level, logger_name, message, created_at) VALUES (?, ?, ?, ?)";
        try {
            JDBIConnector.getJdbi().useHandle(handle -> handle.createUpdate(sql)
                    .bind(0, log.getLogLevel())
                    .bind(1, log.getLoggerName())
                    .bind(2, log.getMessage())
                    .bind(3, log.getCreatedAt())
                    .execute());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
