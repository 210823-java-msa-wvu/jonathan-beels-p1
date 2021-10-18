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

public class RequestServlet extends HttpServlet{
    private Logger log = LogManager.getLogger(LoginServlet.class);
    private EmployeeServices empService = new EmployeeServices();
    private RequestServices requestServices = new RequestServices();
    private ObjectMapper om = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Post received");
        ReimburseRequest req = new ReimburseRequest();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        om.setDateFormat(df);

        System.out.println("Flag1");
        req = om.readValue(request.getReader(), ReimburseRequest.class);

        System.out.println("Flag2");
        System.out.println(req.toString());
        if (requestServices.createRequest(req) != null) {
            System.out.println("Flag 3");
            response.sendRedirect("menu.html");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee does not exist");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ReimburseRequest temp = new ReimburseRequest();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        om.setDateFormat(df);

        temp = om.readValue(request.getReader(), ReimburseRequest.class);

        ReimburseRequest r = requestServices.searchById(temp.getId());
        if (r != null) {
            System.out.println("Request found");
            if ((temp.getEventTime() != null) || (temp.getEventTime() != "")) {
                r.setEventTime(temp.getEventTime());
            }
            if ((temp.getAmount() != null) || (temp.getAmount() != 0)) {
                r.setAmount(temp.getAmount());
            }
            if ((temp.getEventDate() != null)) {
                r.setEventDate(temp.getEventDate());
            }
            if ((temp.getEventType() != null) || (temp.getEventType() != "")) {
                r.setEventType(temp.getEventType());
            }
            if ((temp.getLocation() != null) || (temp.getLocation() != "")) {
                r.setLocation(temp.getLocation());
            }
            if ((temp.getGradingFormat() != null) || (temp.getGradingFormat() != "")) {
                r.setGradingFormat(temp.getGradingFormat());
            }
            if ((temp.getDescription() != null) || temp.getDescription() != "") {
                r.setDescription(temp.getDescription());
            }
            if ((temp.getJustification() != null) || (temp.getJustification() != "")) {
                r.setJustification(temp.getJustification());
            }
            if ((temp.getUrgent() != null) || (temp.getJustification() != "")) {
                r.setUrgent(temp.getUrgent());
            }

            System.out.println("Upating request...");
            requestServices.updateRequest(r);
            System.out.println("Update Successful! Redirecting...");
            System.out.println(r.toString());
            response.setStatus(200);
        }

        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Employee does not exist");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, JsonProcessingException {
        System.out.println("Get received");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        om.setDateFormat(df);
        try {
            List<ReimburseRequest> requestList = requestServices.getAllRequests();
            String reqJSON = om.writeValueAsString(requestList);
            response.setStatus(200);

            System.out.println("Sending response");
            response.getWriter().write(reqJSON);
            System.out.println("Response sent");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
