package com.revature.repositories;

import com.revature.models.Employee;
import com.revature.utils.ConnectionUtil;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeRepo extends CrudRepo<Employee>{

    @Override
    Employee getById(Integer id);

    Employee getByUsername(String username);

    @Override
    List<Employee> getAll();

    void update(Employee employee);

    void delete(Integer id);
}
