package com.revature.servlets;

import com.revature.controllers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
    private Logger log = LogManager.getLogger(RequestHandler.class);

    private Map<String, FrontController> controllerMap;

    {
        controllerMap = new HashMap<String, FrontController>();


    }

    public FrontController handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder uriString = new StringBuilder(request.getRequestURI());

        uriString.replace(0,request.getContextPath().length()+1, "");

        if (uriString.indexOf("html") != -1) {
            request.setAttribute("static", uriString.toString());
            return controllerMap.get(uriString.toString());
        }

        if (uriString.indexOf("/") != -1) {
            request.setAttribute("path", uriString.substring(uriString.indexOf("/") + 1));
            uriString.replace(uriString.indexOf("/"), uriString.length() + 1, "");
        }

        return controllerMap.get(uriString.toString());
    }
}
