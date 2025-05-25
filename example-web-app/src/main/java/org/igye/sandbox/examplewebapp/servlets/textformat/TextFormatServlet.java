package org.igye.sandbox.examplewebapp.servlets.textformat;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.igye.sandbox.examplewebapp.App;
import org.igye.sandbox.examplewebapp.impl.AppImpl;

public class TextFormatServlet extends HttpServlet {
    public static final String FORMAT_TEXT_URL = "format_text";
    public static final String ATTR_TEXT_TO_FORMAT = "TEXT_TO_FORMAT";
    private App app;

    public TextFormatServlet() {
        app = AppImpl.getInstance();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("ctx", TextFormatCtx.builder().build());
        app.forwardToJsp(req, resp, FORMAT_TEXT_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String textToFormat = req.getParameter(ATTR_TEXT_TO_FORMAT);
        req.setAttribute(
                "ctx",
                TextFormatCtx.builder()
                        .textToFormat(textToFormat)
                        .formattedText(StringUtils.join(textToFormat.split("\s+"), "\n"))
                        .build()
        );
        app.forwardToJsp(req, resp, FORMAT_TEXT_URL);
    }
}