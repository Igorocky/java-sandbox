package org.igye.sandbox.examplewebapp.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.igye.sandbox.examplewebapp.App;
import org.igye.sandbox.examplewebapp.impl.AppImpl;

import java.io.IOException;

public class HelloServlet extends HttpServlet {
    private App app;

    public HelloServlet() {
        app = AppImpl.getInstance();
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //        response.getWriter().write("<h1>Hello, Jenkins with WAR!</h1>");
        request.setAttribute("globalStr", app.getStrProp("global.string"));
        request.setAttribute("localStr", app.getStrProp("local.string"));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("");
        requestDispatcher.forward(request, response);
    }
}