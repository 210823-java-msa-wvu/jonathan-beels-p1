package com.revature.services;

import com.revature.models.Employee;
import com.revature.models.ReimburseRequest;
import com.revature.repositories.jdbc.RequestJDBC;
import com.revature.repositories.EmployeeRepo;
import com.revature.repositories.jdbc.EmployeeJDBC;

import java.util.List;

public class EmployeeServices {
    EmployeeRepo empRepo = new EmployeeJDBC();

    public boolean login(String username, String password) {
        Employee emp = empRepo.getByUsername(username);

        if (emp != null) {
            if (username.equals(emp.getUsername()) && password.equals(emp.getPassword())) {
                return true;
            }
        }

        return false;
    }

    public List<Employee> getAllEmployees() {
        return empRepo.getAll();
    }

    public Integer calculateAwarded(Integer id) {
        RequestJDBC requestRepo = new RequestJDBC();

        List<ReimburseRequest> list = requestRepo.getAll();
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEmployeeId() == id) {
                if (list.get(i).getBenCoApproval() && list.get(i).getDhApproval() && list.get(i).getDsApproval()) {
                    sum += list.get(i).getAmount();
                }
            }
        }

        return sum;
    }
}
