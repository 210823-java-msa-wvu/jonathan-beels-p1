package com.revature.repositories.jdbc;

import com.revature.models.ReimburseRequest;
import com.revature.repositories.RequestRepo;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestJDBC implements RequestRepo {
    ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

    @Override
    public ReimburseRequest add(ReimburseRequest request) {
        try (Connection conn = cu.getConnection()) {
            String sql = "insert into request (employee_id, amount, event_type, " +
                    "event_date, event_time, event_location, description, grading_format_type, " +
                    "grade, direct_supervisor_approval, department_head_approval, benco_approval, justification) " +
                    "values (?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning *";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, request.getEmployeeId());
            ps.setInt(2, request.getAmount());
            ps.setString(3, request.getEventType());
            ps.setDate(4, request.getEventDate());
            ps.setTime(5, request.getEventTime());
            ps.setString(6, request.getLocation());
            ps.setString(7, request.getDescription());
            ps.setString(8, request.getGradingFormat());
            ps.setString(9, request.getGrade());
            ps.setBoolean(10, request.getDsApproval());
            ps.setBoolean(11, request.getDhApproval());
            ps.setBoolean(12, request.getBenCoApproval());
            ps.setString(13, request.getJustification());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setId(rs.getInt("request_id"));

                return request;
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ReimburseRequest getById(Integer id) {
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from request where request_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ReimburseRequest request = new ReimburseRequest(
                        rs.getInt("request_id"),
                        rs.getInt("employee_id"),
                        rs.getInt("amount"),
                        rs.getString("event_type"),
                        rs.getDate("event_date"),
                        rs.getTime("event_time"),
                        rs.getString("event_location"),
                        rs.getString("description"),
                        rs.getString("grading_format_type"),
                        rs.getString("grade"),
                        rs.getBoolean("direct_supervisor_approval"),
                        rs.getBoolean("department_head_approval"),
                        rs.getBoolean("benco_approval"),
                        rs.getString("justification")
                );

                return request;
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ReimburseRequest> getByEmployee(Integer employeeId) {
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from request where employee_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            List<ReimburseRequest> requests = new ArrayList<>();
            while (rs.next()) {
                ReimburseRequest request = new ReimburseRequest(
                        rs.getInt("request_id"),
                        rs.getInt("employee_id"),
                        rs.getInt("amount"),
                        rs.getString("event_type"),
                        rs.getDate("event_date"),
                        rs.getTime("event_time"),
                        rs.getString("event_location"),
                        rs.getString("description"),
                        rs.getString("grading_format_type"),
                        rs.getString("grade"),
                        rs.getBoolean("direct_supervisor_approval"),
                        rs.getBoolean("department_head_approval"),
                        rs.getBoolean("benco_approval"),
                        rs.getString("justification")
                );

                requests.add(request);
            }
            return requests;
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ReimburseRequest> getAll() {
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from request";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            List<ReimburseRequest> requests = new ArrayList<>();
            while (rs.next()) {
                ReimburseRequest request = new ReimburseRequest(
                        rs.getInt("request_id"),
                        rs.getInt("employee_id"),
                        rs.getInt("amount"),
                        rs.getString("event_type"),
                        rs.getDate("event_date"),
                        rs.getTime("event_time"),
                        rs.getString("event_location"),
                        rs.getString("description"),
                        rs.getString("grading_format_type"),
                        rs.getString("grade"),
                        rs.getBoolean("direct_supervisor_approval"),
                        rs.getBoolean("department_head_approval"),
                        rs.getBoolean("benco_approval"),
                        rs.getString("justification")
                );

                requests.add(request);
            }
            return requests;
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
