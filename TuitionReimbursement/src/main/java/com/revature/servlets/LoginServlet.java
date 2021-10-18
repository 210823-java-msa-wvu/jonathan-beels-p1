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
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    private Logger log = LogManager.getLogger(LoginServlet.class);
    private EmployeeServices empService = new EmployeeServices();
    private ObjectMapper om = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Employee emp = new Employee();
        response.setContentType("text/html");

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

            String empString = om.writeValueAsString(emp);

            System.out.println("flag 3");
            Cookie userID = new Cookie("employeeId", emp.getId().toString());
            Cookie benCo = new Cookie("benCo", emp.getBenCo().toString());
            Cookie ds = new Cookie("directSupervisor", isDS.toString());
            Cookie dh = new Cookie("departmentHead", isDH.toString());

            System.out.println("flag 4");
            userID.setMaxAge(60 * 60 * 24);
            benCo.setMaxAge( 60 * 60 * 24);
            ds.setMaxAge(60 * 60 * 24);
            dh.setMaxAge(60 * 60 * 24);

            log.info("Adding Cookies");
            response.addCookie(benCo);
            System.out.println("flag 5.1");
            response.addCookie(ds);
            System.out.println("flag 5.2");
            response.addCookie(dh);
            System.out.println("flag 6");
            response.addCookie(userID);
            System.out.println("Flag 7");
            response.sendRedirect("menu.html");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid login credentials");
        }
    }
}
