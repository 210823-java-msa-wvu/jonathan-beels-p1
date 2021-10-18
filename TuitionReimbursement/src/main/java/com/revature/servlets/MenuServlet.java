package com.revature.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.ReimburseRequest;
import com.revature.repositories.EmployeeRepo;
import com.revature.repositories.RequestRepo;
import com.revature.repositories.jdbc.EmployeeJDBC;
import com.revature.repositories.jdbc.RequestJDBC;
import com.revature.services.EmployeeServices;
import com.revature.models.Employee;
import com.revature.services.RequestServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.print.DocFlavor;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MenuServlet extends HttpServlet{
    private Logger log = LogManager.getLogger(LoginServlet.class);
    private EmployeeServices empService = new EmployeeServices();
    private EmployeeRepo employeeRepo = new EmployeeJDBC();
    private ObjectMapper om = new ObjectMapper();
    private RequestServices requestServices = new RequestServices();
    private RequestRepo requestRepo = new RequestJDBC();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, JsonProcessingException {
        System.out.println("Recieved Request");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        om.setDateFormat(df);
        try {
            System.out.println("Getting list");
            List<ReimburseRequest> requestList = requestServices.getAllRequests();
            String reqJSON = om.writeValueAsString(requestList);
            System.out.println("Writing Response");
            response.getWriter().write(reqJSON);
            System.out.println(reqJSON);


            response.setStatus(200);
            System.out.println("Response Sent");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
