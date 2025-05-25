package org.igye.sandbox.examplewebapp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface App {
    String getStrProp(String name);
    void forwardToJsp(HttpServletRequest request, HttpServletResponse response, String path);
}
