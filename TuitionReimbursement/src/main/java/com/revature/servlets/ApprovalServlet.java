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

public class ApprovalServlet extends HttpServlet {
    ObjectMapper om = new ObjectMapper();
    EmployeeServices empService = new EmployeeServices();
    EmployeeJDBC employeeJDBC = new EmployeeJDBC();
    RequestServices requestServices = new RequestServices();
    private Logger log = LogManager.getLogger(ApprovalServlet.class);

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PUT received");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        om.setDateFormat(df);
        ReimburseRequest temp = new ReimburseRequest();
        Employee emp = new Employee();
        temp = om.readValue(request.getReader(), ReimburseRequest.class);

        System.out.println(temp.toString());
        ReimburseRequest r = requestServices.searchById(temp.getId());
        if (r != null) {
            if (temp.getAdditionalInfo() != null) {
                r.setAdditionalInfo(temp.getAdditionalInfo());
                if (requestServices.updateRequest(r)) {
                    Employee emp2 = employeeJDBC.getById(r.getId());
                    emp2.setPending(emp2.getPending() - r.getAmount());
                    emp2.setRemaining(emp2.getRemaining() + r.getAmount());
                    employeeJDBC.update(emp2);
                    log.info("Information Requested");
                    response.setStatus(200);
                } else {
                    response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Couldn't make request");
                }
            }
            else if (temp.getRejected() != null) {
                System.out.println(temp.getRejected());
                r.setRejected(temp.getRejected());
                if (requestServices.updateRequest(r)) {
                    log.info("Request rejected");
                    System.out.println("Request rejected");
                    response.setStatus(200);
                } else {
                    response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Couldn't reject request");
                }
            }
            else {
                System.out.println("Approval");
                Cookie[] cookies = request.getCookies();
                Boolean dh = false;
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("departmentHead")) {
                        dh = om.readValue(cookies[i].getValue(), Boolean.class);
                        break;
                    }
                }
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("employeeId")) {
                        int empId = Integer.parseInt(cookies[i].getValue());
                        emp = employeeJDBC.getById(empId);
                        Employee emp2 = employeeJDBC.getById(r.getEmployeeId());
                        System.out.println(emp2.getSupervisor());
                        System.out.println(emp.getId());
                        if (emp2.getSupervisor() == emp.getId()) {
                            System.out.println(temp.getDsApproval());
                            r.setDsApproval(temp.getDsApproval());
                            requestServices.updateRequest(r);
                            if (r.getDsApproval()) {
                                System.out.println("DS Approves");
                                log.info("Direct Supervisor approved");
                                if (r.getDsApproval() && r.getDhApproval() && r.getBenCoApproval()) {
                                    emp2.setAwarded(emp2.getAwarded() + r.getAmount());
                                    emp2.setPending(emp2.getPending() - r.getAmount());
                                    emp2.setRemaining(emp2.getRemaining() - r.getAmount());
                                }
                            } else {
                                log.info("Direct Supervisor rejected");
                                emp2.setPending(emp2.getPending() - r.getAmount());
                                emp2.setRemaining(emp2.getRemaining() + r.getAmount());
                                employeeJDBC.update(emp2);
                            }
                        }

                        if (dh) {
                            if (emp2.getDepartment() == emp.getDepartment()) {
                                r.setDhApproval(temp.getDhApproval());
                                requestServices.updateRequest(r);
                                if (r.getDsApproval()) {
                                    System.out.println("DH Approves");
                                    log.info("Department Head approved");
                                    if (r.getDsApproval() && r.getDhApproval() && r.getBenCoApproval()) {
                                        emp2.setAwarded(emp2.getAwarded() + r.getAmount());
                                        emp2.setPending(emp2.getPending() - r.getAmount());
                                        emp2.setRemaining(emp2.getRemaining() - r.getAmount());


                                    }
                                } else {
                                    log.info("Department Head rejected");
                                    emp2.setPending(emp2.getPending() - r.getAmount());
                                    emp2.setRemaining(emp2.getRemaining() + r.getAmount());
                                    employeeJDBC.update(emp2);
                                }
                            }
                        }

                        if (emp.getBenCo()) {
                            r.setBenCoApproval(temp.getBenCoApproval());
                            requestServices.updateRequest(r);
                            if (r.getDsApproval()) {
                                log.info("Benefits Coordinator approved");
                                if (r.getDsApproval() && r.getDhApproval() && r.getBenCoApproval()) {
                                    emp2.setAwarded(emp2.getAwarded() + r.getAmount());
                                    emp2.setPending(emp2.getPending() - r.getAmount());
                                    emp2.setRemaining(emp2.getRemaining() - r.getAmount());


                                }
                            } else {
                                log.info("Benefits Coordinator rejected");
                                emp2.setPending(emp2.getPending() - r.getAmount());
                                emp2.setRemaining(emp2.getRemaining() + r.getAmount());
                                employeeJDBC.update(emp2);
                            }
                        }
                        response.setStatus(200);
                        break;
                    }
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Request not found");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, JsonProcessingException {
        System.out.println("Recieved Request");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        om.setDateFormat(df);
        Cookie[] cookies = request.getCookies();
        boolean dh = false;
        boolean ds = false;
        boolean benCo = false;
        Integer id = null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("departmentHead")) {
                dh = om.readValue(cookies[i].getValue(), Boolean.class);
            }
            else if (cookies[i].getName().equals("directSupervisor")) {
                ds = om.readValue(cookies[i].getValue(), Boolean.class);
            }
            else if (cookies[i].getName().equals("benCo")) {
                benCo = om.readValue(cookies[i].getValue(), Boolean.class);
            }
            else if (cookies[i].getName().equals("employeeId")) {
                id = om.readValue(cookies[i].getValue(),Integer.class);
            }
        }

        try {
            if (dh) {
                System.out.println("Getting list");
                List<ReimburseRequest> requestList = requestServices.getDHRequests(id);
                String reqJSON = om.writeValueAsString(requestList);
                System.out.println("Writing Response");
                response.getWriter().write(reqJSON);
                System.out.println(reqJSON);
            }
            else if (ds) {
                System.out.println("Getting list");
                List<ReimburseRequest> requestList = requestServices.getDSRequests(id);
                String reqJSON = om.writeValueAsString(requestList);
                System.out.println("Writing Response");
                response.getWriter().write(reqJSON);
                System.out.println(reqJSON);
            }
            else if (benCo) {
                System.out.println("Getting list");
                List<ReimburseRequest> requestList = requestServices.getAllRequests();
                String reqJSON = om.writeValueAsString(requestList);
                System.out.println("Writing Response");
                response.getWriter().write(reqJSON);
                System.out.println(reqJSON);
            }

            response.setStatus(200);
            System.out.println("Response Sent");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
