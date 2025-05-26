package org.igye.sandbox.examplewebapp.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.igye.sandbox.examplewebapp.App;
import org.igye.sandbox.examplewebapp.StatefulWebController;
import org.igye.sandbox.examplewebapp.impl.AppImpl;

import java.util.Optional;

public class ApiDispatcherServlet extends HttpServlet {
    private App app;

    public ApiDispatcherServlet() {
        app = AppImpl.getInstance();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) {
        String path = Optional.ofNullable(req.getPathInfo()).map(s -> s.substring(1)).orElse("");
        StatefulWebController controller = app.lookupController(path).orElseThrow(() -> new RuntimeException(String.format(
                "Cannot find a controller for the path '%s'.", path
        )));
        Object state = controller.restoreState(req);
        Optional<Object> action = controller.decodeAction(req, state);
        Object newState = action.map(act -> controller.updateState(state, act)).orElse(state);
        controller.saveState(newState);
        Pair<Object, String> ctxAndJsp = controller.renderState(newState);
        req.setAttribute("ctx", ctxAndJsp.getLeft());
        app.forwardToJsp(req, resp, ctxAndJsp.getRight());
    }
}