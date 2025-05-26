package org.igye.sandbox.examplewebapp.impl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.igye.sandbox.examplewebapp.App;
import org.igye.sandbox.examplewebapp.StatefulWebController;
import org.igye.sandbox.examplewebapp.controllers.TextFormatController;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.Optional;

public class AppImpl implements App {
    private static App app;

    private final Context context;
    private final List<StatefulWebController> controllers;

    public AppImpl() {
        try {
            this.context = InitialContext.doLookup("java:comp/env");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        this.controllers = List.of(
                new TextFormatController()
        );
    }

    @SneakyThrows
    @Override
    public String getStrProp(String name) {
        return (String) context.lookup(name);
    }

    @SneakyThrows
    @Override
    public void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String path) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/" + path + ".jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    public Optional<StatefulWebController> lookupController(String path) {
        return controllers.stream()
                .filter(ctrl -> path.equals(ctrl.getPath()))
                .findFirst();
    }

    public static App getInstance() {
        if (app == null) {
            synchronized (AppImpl.class) {
                if (app == null) {
                    app = new AppImpl();
                }
            }
        }
        return app;
    }
}
