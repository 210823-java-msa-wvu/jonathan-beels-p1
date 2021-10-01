package com.revature.repositories;

import com.revature.models.ReimburseRequest;
import com.revature.utils.ConnectionUtil;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface RequestRepo extends CrudRepo<ReimburseRequest> {

    ReimburseRequest add(ReimburseRequest request);

    @Override
    ReimburseRequest getById(Integer id);

    List<ReimburseRequest> getByEmployee(Integer employeeId);

    @Override
    List<ReimburseRequest> getAll();

    void update(ReimburseRequest request);

    void delete(Integer id);
}
