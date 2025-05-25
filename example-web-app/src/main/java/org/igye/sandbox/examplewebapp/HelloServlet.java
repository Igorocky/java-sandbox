package org.igye.sandbox.examplewebapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.igye.sandbox.examplewebapp.impl.AppConfigImpl;

import java.io.IOException;

public class HelloServlet extends HttpServlet {
    private AppConfig appConfig;

    public HelloServlet() {
        appConfig = AppConfigImpl.getInstance();
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //        response.getWriter().write("<h1>Hello, Jenkins with WAR!</h1>");
        request.setAttribute("globalStr", appConfig.getStrProp("global.string"));
        request.setAttribute("localStr", appConfig.getStrProp("local.string"));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("");
        requestDispatcher.forward(request, response);
    }
}