package com.revature.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class GradeServlet extends HttpServlet{
    private Logger log = LogManager.getLogger(LoginServlet.class);
    private RequestServices reqService = new RequestServices();
    private ObjectMapper om = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("PUT received");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        om.setDateFormat(df);
        ReimburseRequest temp = om.readValue(request.getReader(), ReimburseRequest.class);

        System.out.println("Looking for request");
        ReimburseRequest r = reqService.searchById(temp.getId());
        if (r != null) {
            System.out.println("Request found");
            r.setGrade(temp.getGrade());
            System.out.println("Upating request...");
            reqService.updateRequest(r);
            System.out.println("Update Successful! Redirecting...");
            response.setStatus(200);
        }

        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Request not found");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, JsonProcessingException {
        System.out.println("Recieved Request");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        om.setDateFormat(df);
        try {
            System.out.println("Getting list");
            List<ReimburseRequest> requestList = reqService.getAllRequests();
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
