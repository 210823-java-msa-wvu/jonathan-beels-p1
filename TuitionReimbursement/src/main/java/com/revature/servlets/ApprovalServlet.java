package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.ReimburseRequest;
import com.revature.repositories.EmployeeRepo;
import com.revature.repositories.jdbc.EmployeeJDBC;
import com.revature.services.EmployeeServices;
import com.revature.models.Employee;
import com.revature.services.RequestServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApprovalServlet extends HttpServlet {
    ObjectMapper om = new ObjectMapper();
    EmployeeServices empService = new EmployeeServices();
    EmployeeJDBC employeeJDBC = new EmployeeJDBC();
    RequestServices requestServices = new RequestServices();
    private Logger log = LogManager.getLogger(ApprovalServlet.class);

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReimburseRequest temp = new ReimburseRequest();
        Employee emp = new Employee();
        temp = om.readValue(request.getReader(), ReimburseRequest.class);

        ReimburseRequest r = requestServices.searchById(temp.getId());
        if (r != null) {
            Cookie[] cookies = request.getCookies();
            Boolean dh = false;
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("departmentHead")) {
                    dh = om.readValue(cookies[i].getValue(), Boolean.class);
                    break;
                }
            }
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("employee")) {
                    emp = om.readValue(cookies[i].getValue(), Employee.class);
                    Employee emp2 = employeeJDBC.getById(r.getId());

                    if (emp2.getSupervisor() == emp.getId()) {
                        r.setDsApproval(temp.getDsApproval());
                        requestServices.updateRequest(r);
                        if (r.getDsApproval() && r.getDhApproval() && r.getBenCoApproval()) {
                            emp2.setAwarded(emp2.getAwarded() + r.getAmount());
                            emp2.setPending(emp2.getPending() - r.getAmount());
                            emp2.setRemaining(emp2.getRemaining() - r.getAmount());
                            employeeJDBC.update(emp2);
                        }
                        log.info("Direct Supervisor approved");
                    }

                    if (dh) {
                        if (emp2.getDepartment() == emp.getDepartment()) {
                            r.setDhApproval(temp.getDhApproval());
                            requestServices.updateRequest(r);
                            if (r.getDsApproval() && r.getDhApproval() && r.getBenCoApproval()) {
                                emp2.setAwarded(emp2.getAwarded() + r.getAmount());
                                emp2.setPending(emp2.getPending() - r.getAmount());
                                emp2.setRemaining(emp2.getRemaining() - r.getAmount());
                                employeeJDBC.update(emp2);
                            }
                            log.info("Department Head approved");
                        }
                    }

                    if (emp.getBenCo()) {
                        r.setBenCoApproval(temp.getBenCoApproval());
                        requestServices.updateRequest(r);
                        if (r.getDsApproval() && r.getDhApproval() && r.getBenCoApproval()) {
                            emp2.setAwarded(emp2.getAwarded() + r.getAmount());
                            emp2.setPending(emp2.getPending() - r.getAmount());
                            emp2.setRemaining(emp2.getRemaining() - r.getAmount());
                            employeeJDBC.update(emp2);
                        }
                        log.info("Benefits Coordinator approved");
                    }
                    response.sendRedirect("menu.html");
                    break;
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Request not found");
        }
    }
}
