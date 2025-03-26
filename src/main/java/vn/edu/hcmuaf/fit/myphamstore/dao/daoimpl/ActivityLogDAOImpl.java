package vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl;

import vn.edu.hcmuaf.fit.myphamstore.dao.IActivityLogDAO;
import vn.edu.hcmuaf.fit.myphamstore.model.ActivityLogModel;
import vn.edu.hcmuaf.fit.myphamstore.common.JDBIConnector;

import java.util.List;

public class ActivityLogDAOImpl implements IActivityLogDAO {

    @Override
    public void save(ActivityLogModel activityLog) {
        String sql = "INSERT INTO activity_logs (user_id, level, action, old_data, new_data, location, created_at) " +
                "VALUES (:user_id, :level, :action, :old_data, :new_data, :location, :created_at)";
        try {
            JDBIConnector.getJdbi().useHandle(handle -> handle.createUpdate(sql)
                    .bind("user_id", activityLog.getUserId())
                    .bind("level", activityLog.getLevel())
                    .bind("action", activityLog.getAction())
                    .bind("old_data", activityLog.getOldData())
                    .bind("new_data", activityLog.getNewData())
                    .bind("location", activityLog.getLocation())
                    .bind("created_at", activityLog.getCreatedAt())
                    .execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ActivityLogModel findById(int id) {
        String sql = "SELECT * FROM activity_logs WHERE id = :id";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .bind("id", id)
                    .mapToBean(ActivityLogModel.class)
                    .one());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ActivityLogModel> findAll() {
        String sql = "SELECT * FROM activity_logs ORDER BY created_at DESC";
        try {
            return JDBIConnector.getJdbi().withHandle(handle -> handle.createQuery(sql)
                    .mapToBean(ActivityLogModel.class)
                    .list());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public void update(ActivityLogModel activityLog) {
        String sql = "UPDATE activity_log SET user_id = :user_id, level = :level, action = :action, old_data = :old_data, new_data = :new_data, location = :location, created_at = :created_at WHERE id = :id";
        try {
            JDBIConnector.getJdbi().useHandle(handle -> handle.createUpdate(sql)
                    .bind("user_id", activityLog.getUserId())
                    .bind("level", activityLog.getLevel())
                    .bind("action", activityLog.getAction())
                    .bind("old_data", activityLog.getOldData())
                    .bind("new_data", activityLog.getNewData())
                    .bind("location", activityLog.getLocation())
                    .bind("created_at", activityLog.getCreatedAt())
                    .bind("id", activityLog.getId())
                    .execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM activity_log WHERE id = :id";
        try {
            JDBIConnector.getJdbi().useHandle(handle -> handle.createUpdate(sql)
                    .bind("id", id)
                    .execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}