package com.revature.repositories.jdbc;

import com.revature.models.ReimburseRequest;
import com.revature.repositories.RequestRepo;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RequestJDBC implements RequestRepo {
    ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

    @Override
    public ReimburseRequest add(ReimburseRequest request) {
        if ((request.getEmployeeId() != null) && (request.getAmount() != null) && (request.getDescription() != null)
        && (request.getEventDate() != null) && (request.getEventTime() != null) && (request.getEventType() != null)
        && (request.getJustification() != null) && (request.getGradingFormat() != null)) {
            try (Connection conn = cu.getConnection()) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                String sql = "insert into request (employee_id, amount, event_type, " +
                        "event_date, event_time, event_location, description, grading_format_type, " +
                        "grade, justification, urgent) " +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning *";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, request.getEmployeeId());
                System.out.println("Flag0");
                ps.setInt(2, request.getAmount());
                System.out.println("Flag1");
                ps.setString(3, request.getEventType());
                System.out.println("Flag2");
                ps.setDate(4, Date.valueOf(df.format(request.getEventDate())));
                System.out.println("Flag4");
                ps.setString(5, request.getEventTime() + ":00");
                System.out.println("Flag5");
                ps.setString(6, request.getLocation());
                System.out.println("Flag6");
                ps.setString(7, request.getDescription());
                System.out.println("Flag7");
                ps.setString(8, request.getGradingFormat());
                System.out.println("Flag8");
                ps.setString(9, request.getGrade());
                System.out.println("Flag9");
                ps.setString(10, request.getJustification());
                System.out.println("Flag10");
                ps.setBoolean(11, request.getUrgent());
                System.out.println("Flag11");

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    request.setId(rs.getInt("request_id"));

                    return request;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public ReimburseRequest getById(Integer id) {
        System.out.println("Flag2");
        try (Connection conn = cu.getConnection()) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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
                        rs.getString("event_time"),
                        rs.getString("event_location"),
                        rs.getString("description"),
                        rs.getString("grading_format_type"),
                        rs.getString("grade"),
                        rs.getBoolean("direct_supervisor_approval"),
                        rs.getBoolean("department_head_approval"),
                        rs.getBoolean("benco_approval"),
                        rs.getString("justification"),
                        rs.getBoolean("urgent"),
                        rs.getBoolean("additional_info"),
                        rs.getBoolean("rejected")
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
                        rs.getString("event_time"),
                        rs.getString("event_location"),
                        rs.getString("description"),
                        rs.getString("grading_format_type"),
                        rs.getString("grade"),
                        rs.getBoolean("direct_supervisor_approval"),
                        rs.getBoolean("department_head_approval"),
                        rs.getBoolean("benco_approval"),
                        rs.getString("justification"),
                        rs.getBoolean("urgent"),
                        rs.getBoolean("additional_info"),
                        rs.getBoolean("rejected")
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
                        rs.getString("event_time"),
                        rs.getString("event_location"),
                        rs.getString("description"),
                        rs.getString("grading_format_type"),
                        rs.getString("grade"),
                        rs.getBoolean("direct_supervisor_approval"),
                        rs.getBoolean("department_head_approval"),
                        rs.getBoolean("benco_approval"),
                        rs.getString("justification"),
                        rs.getBoolean("urgent"),
                        rs.getBoolean("additional_info"),
                        rs.getBoolean("rejected")
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

    public List<ReimburseRequest> getBySupervisor() {
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
                        rs.getString("event_time"),
                        rs.getString("event_location"),
                        rs.getString("description"),
                        rs.getString("grading_format_type"),
                        rs.getString("grade"),
                        rs.getBoolean("direct_supervisor_approval"),
                        rs.getBoolean("department_head_approval"),
                        rs.getBoolean("benco_approval"),
                        rs.getString("justification"),
                        rs.getBoolean("urgent"),
                        rs.getBoolean("additional_info"),
                        rs.getBoolean("rejected")
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

    public List<ReimburseRequest> getByDS(Integer id) {
        try (Connection conn = cu.getConnection()) {
            String sql = "select r.request_id, r.employee_id, r.amount, r.event_type, r.event_date, r.event_time, r.event_location, r.description, r.grading_format_type, r.grade, r.direct_supervisor_approval, \n" +
                    "r.department_head_approval, r.benco_approval, r.justification, r.urgent, r.additional_info, r.rejected from request r \n" +
                    "join employee e on r.employee_id = e.employee_id where e.supervisor = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            List<ReimburseRequest> requests = new ArrayList<>();
            while (rs.next()) {
                ReimburseRequest request = new ReimburseRequest(
                        rs.getInt("request_id"),
                        rs.getInt("employee_id"),
                        rs.getInt("amount"),
                        rs.getString("event_type"),
                        rs.getDate("event_date"),
                        rs.getString("event_time"),
                        rs.getString("event_location"),
                        rs.getString("description"),
                        rs.getString("grading_format_type"),
                        rs.getString("grade"),
                        rs.getBoolean("direct_supervisor_approval"),
                        rs.getBoolean("department_head_approval"),
                        rs.getBoolean("benco_approval"),
                        rs.getString("justification"),
                        rs.getBoolean("urgent"),
                        rs.getBoolean("additional_info"),
                        rs.getBoolean("rejected")
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

    public List<ReimburseRequest> getByDH(Integer id) {
        try (Connection conn = cu.getConnection()) {
            String sql = "select r.request_id, r.employee_id, r.amount, r.event_type, r.event_date, r.event_time, r.event_location, r.description, r.grading_format_type, r.grade, r.direct_supervisor_approval, \n" +
                    "r.department_head_approval, r.benco_approval, r.justification, r.urgent, r.additional_info, r.rejected from request r \n" +
                    "join employee e on r.employee_id = e.employee_id \n" +
                    "join department d on e.department_id = d.department_id where d.department_head = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            List<ReimburseRequest> requests = new ArrayList<>();
            while (rs.next()) {
                ReimburseRequest request = new ReimburseRequest(
                        rs.getInt("request_id"),
                        rs.getInt("employee_id"),
                        rs.getInt("amount"),
                        rs.getString("event_type"),
                        rs.getDate("event_date"),
                        rs.getString("event_time"),
                        rs.getString("event_location"),
                        rs.getString("description"),
                        rs.getString("grading_format_type"),
                        rs.getString("grade"),
                        rs.getBoolean("direct_supervisor_approval"),
                        rs.getBoolean("department_head_approval"),
                        rs.getBoolean("benco_approval"),
                        rs.getString("justification"),
                        rs.getBoolean("urgent"),
                        rs.getBoolean("additional_info"),
                        rs.getBoolean("rejected")
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

    public void update(ReimburseRequest request) {
        System.out.println(request.toString());
        try (Connection conn = cu.getConnection()) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String sql = "update request set request_id= ?, employee_id = ?, amount = ?, " +
                    "event_type = ?, event_date = ?, event_time = ?, event_location = ?, " +
                    "description = ?, grading_format_type = ?, grade = ?, " +
                    "direct_supervisor_approval = ?, department_head_approval = ?, " +
                    "benco_approval = ?, justification = ?, additional_info = ?, rejected =? where request_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, request.getId());
            ps.setInt(2, request.getEmployeeId());
            ps.setInt(3, request.getAmount());
            ps.setString(4, request.getEventType());
            ps.setDate(5, Date.valueOf(df.format(request.getEventDate())));
            ps.setString(6, request.getEventTime());
            ps.setString(7, request.getLocation());
            ps.setString(8, request.getDescription());
            ps.setString(9, request.getGradingFormat());
            ps.setString(10, request.getGrade());
            ps.setBoolean(11, request.getDsApproval());
            ps.setBoolean(12, request.getDhApproval());
            ps.setBoolean(13, request.getBenCoApproval());
            ps.setString(14, request.getJustification());
            ps.setBoolean(15, request.getAdditionalInfo());
            ps.setBoolean(16, request.getRejected());
            ps.setInt(17, request.getId());

            ps.executeUpdate();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        try (Connection conn = cu.getConnection()) {
            String sql = "delete from request where request_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.execute();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
