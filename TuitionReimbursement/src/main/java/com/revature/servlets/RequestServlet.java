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

public class RequestServlet extends HttpServlet{
    private Logger log = LogManager.getLogger(LoginServlet.class);
    private EmployeeServices empService = new EmployeeServices();
    private RequestServices requestServices = new RequestServices();
    private ObjectMapper om = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ReimburseRequest req = new ReimburseRequest();

        req = om.readValue(request.getReader(),ReimburseRequest.class);

        System.out.println(req.toString());
        if (requestServices.createRequest(req) != null) {
            response.sendRedirect("menu.html");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee does not exist");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ReimburseRequest req = new ReimburseRequest();

        req = om.readValue(request.getReader(), ReimburseRequest.class);

        System.out.println(req.toString());
        if (requestServices.updateRequest(req)) {
            response.sendRedirect("menu.html");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee does not exist");
        }
    }
}
