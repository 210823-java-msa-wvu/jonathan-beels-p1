package com.revature.repositories.jdbc;

import com.revature.models.Employee;
import com.revature.repositories.EmployeeRepo;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeJDBC implements EmployeeRepo{
    ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

    @Override
    public Employee getById(Integer id) {
        try (Connection conn = cu.getConnection()) {
            String sql ="select * from employee where id = ?";

            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs =ps.executeQuery();

            if (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("user_name"),
                        rs.getString("pass_word"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("remaining_allowance"),
                        rs.getInt("pending"),
                        rs.getInt("awarded"),
                        rs.getInt("supervisor"),
                        rs.getInt("department_id"),
                        rs.getBoolean("ben_co")
                );
                return emp;
            }

        }

        catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Employee getByUsername(String username) {
        try (Connection conn = cu.getConnection()) {
            String sql ="select * from employee where user_name = ?";

            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs =ps.executeQuery();

            if (rs.next()) {
                System.out.println("Flag exist");
                Employee emp = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("user_name"),
                        rs.getString("pass_word"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("remaining_allowance"),
                        rs.getInt("pending"),
                        rs.getInt("awarded"),
                        rs.getInt("supervisor"),
                        rs.getInt("department_id"),
                        rs.getBoolean("ben_co")
                );
                return emp;
            }

        }

        catch(SQLException e) {
            e.printStackTrace();
        }

        System.out.println("flag null");
        return null;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = cu.getConnection()) {
            String sql ="select * from employee";

            PreparedStatement ps =conn.prepareStatement(sql);

            ResultSet rs =ps.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("user_name"),
                        rs.getString("pass_word"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("remaining_allowance"),
                        rs.getInt("pending"),
                        rs.getInt("awarded"),
                        rs.getInt("supervisor"),
                        rs.getInt("department_id"),
                        rs.getBoolean("ben_co")
                );

                employees.add(emp);
            }
            return employees;

        }

        catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isDS(Integer id) {
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from employee where supervisor = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean isDH(Integer id) {
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from department where department_head = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        }

        catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void update(Employee employee) {
        try (Connection conn = cu.getConnection()) {
            String sql = "update employee set employee_id = ?, first_name = ?, last_name = ?, remaining_allowance = ?, " +
                    "pending = ?, awarded = ?, username = ?, password = ?, supervisor = ?, ben_co = ?, department_id = ? where employee_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getFirstName());
            ps.setString(3, employee.getLastName());
            ps.setInt(4, employee.getRemaining());
            ps.setInt(5, employee.getPending());
            ps.setInt(6, employee.getAwarded());
            ps.setString(7, employee.getUsername());
            ps.setString(8, employee.getPassword());
            ps.setInt(9, employee.getSupervisor());
            ps.setBoolean(10, employee.getBenCo());
            ps.setInt(11, employee.getDepartment());
            ps.setInt(12, employee.getId());

            int i = ps.executeUpdate();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {

    }
}
