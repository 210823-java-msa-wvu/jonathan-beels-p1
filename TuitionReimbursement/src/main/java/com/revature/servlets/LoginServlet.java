package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.repositories.EmployeeRepo;
import com.revature.repositories.jdbc.EmployeeJDBC;
import com.revature.services.EmployeeServices;
import com.revature.models.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private Logger log = LogManager.getLogger(LoginServlet.class);
    private EmployeeServices empService = new EmployeeServices();
    private ObjectMapper om = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Employee emp = new Employee();

        emp = om.readValue(request.getReader(), Employee.class);

        String username = emp.getUsername();
        String password = emp.getPassword();
        System.out.println("Username: " + username + " Password: " + password);
        if (empService.login(username, password)) {
            System.out.println("flag 1");
            EmployeeJDBC employeeRepo = new EmployeeJDBC();
            emp = employeeRepo.getByUsername(username);

            System.out.println("flag 2");
            Boolean isDS = employeeRepo.isDS(emp.getId());
            Boolean isDH = employeeRepo.isDH(emp.getId());

            System.out.println("flag 3");
            Cookie user = new Cookie("employee", emp.toJSON());
            Cookie ds = new Cookie("directSupervisor", isDS.toString());
            Cookie dh = new Cookie("departmentHead", isDH.toString());

            System.out.println("flag 4");
            user.setMaxAge(60 * 60 * 24);
            ds.setMaxAge(60 * 60 * 24);
            dh.setMaxAge(60 * 60 * 24);

            System.out.println("flag 5");
            response.addCookie(user);
            System.out.println("flag 5.1");
            response.addCookie(ds);
            System.out.println("flag 5.2");
            response.addCookie(dh);

            System.out.println("flag 6");
            response.sendRedirect("menu.html");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid login credentials");
        }
    }
}
