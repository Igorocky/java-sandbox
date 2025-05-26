package org.igye.sandbox.examplewebapp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface App {
    String getStrProp(String name);

    void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String path);

    Optional<StatefulWebController> lookupController(String path);
}
