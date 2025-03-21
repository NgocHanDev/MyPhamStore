package vn.edu.hcmuaf.fit.myphamstore.dao;

import vn.edu.hcmuaf.fit.myphamstore.model.ActivityLogModel;

import java.util.List;

public interface IActivityLogDAO {
    void save(ActivityLogModel activityLog);
    ActivityLogModel findById(int id);
    List<ActivityLogModel> findAll();
    void update(ActivityLogModel activityLog);
    void delete(int id);
}