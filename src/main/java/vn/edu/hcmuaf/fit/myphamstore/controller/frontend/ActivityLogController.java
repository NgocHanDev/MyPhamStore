package vn.edu.hcmuaf.fit.myphamstore.controller.frontend;

import vn.edu.hcmuaf.fit.myphamstore.dao.IActivityLogDAO;
import vn.edu.hcmuaf.fit.myphamstore.dao.daoimpl.ActivityLogDAOImpl;
import vn.edu.hcmuaf.fit.myphamstore.model.ActivityLogModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/activity-log")
public class ActivityLogController extends HttpServlet {
    private final IActivityLogDAO activityLogDAO = new ActivityLogDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            List<ActivityLogModel> activityLogs = activityLogDAO.findAll();
            activityLogs.forEach(System.out::println); // In ra console

            response.getWriter().write("Activity logs have been printed to the server console.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred while retrieving activity logs.");
        }
    }
}
