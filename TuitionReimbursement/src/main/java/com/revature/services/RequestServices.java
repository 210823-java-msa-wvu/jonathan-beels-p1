package com.revature.services;

import com.revature.models.Employee;
import com.revature.models.ReimburseRequest;
import com.revature.repositories.EmployeeRepo;
import com.revature.repositories.RequestRepo;
import com.revature.repositories.jdbc.EmployeeJDBC;
import com.revature.repositories.jdbc.RequestJDBC;
import java.sql.Date;
import java.time.LocalDate;

import java.util.List;

public class RequestServices {
    EmployeeRepo empRepo = new EmployeeJDBC();
    RequestRepo reqRepo = new RequestJDBC();

    public ReimburseRequest createRequest(ReimburseRequest r) {
        Employee emp = empRepo.getById(r.getEmployeeId());

        if (emp != null) {
            return reqRepo.add(r);
        }

        else {
            System.out.println("Employee Does not Exist");
            return null;
        }
    }

    public ReimburseRequest searchById(Integer id) {
        return reqRepo.getById(id);
    }

    public List<ReimburseRequest> getAllRequests() {
        return reqRepo.getAll();
    }

    public boolean updateRequest(ReimburseRequest r) {
        if (reqRepo.getById(r.getId()) != null) {
            reqRepo.update(r);
            return true;
        }

        else {
            System.out.println("Request does not exist...");
        }
        return false;
    }

    public void deleteRequest(Integer id) {
        if (reqRepo.getById(id) != null) {
            reqRepo.delete(id);
        }

        else {
            System.out.println("Request did not exist");
        }
    }
}
