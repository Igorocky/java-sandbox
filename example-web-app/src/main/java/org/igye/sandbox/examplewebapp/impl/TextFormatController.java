package org.igye.sandbox.examplewebapp.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.igye.sandbox.examplewebapp.StatefulWebController;

import java.util.Optional;

public class TextFormatController implements StatefulWebController<TextFormatCtx, Boolean, TextFormatCtx> {
    public static final String ATTR_TEXT_TO_FORMAT = "TEXT_TO_FORMAT";

    @Override
    public String getPath() {
        return "format_text";
    }

    @Override
    public TextFormatCtx restoreState(HttpServletRequest req) {
        String textToFormat = req.getParameter(ATTR_TEXT_TO_FORMAT);
        return TextFormatCtx.builder()
                .textToFormat(textToFormat == null ? "" : textToFormat)
                .formattedText("")
                .build();
    }

    @Override
    public Optional<Boolean> decodeAction(HttpServletRequest req, TextFormatCtx state) {
        return Optional.of(true);
    }

    @Override
    public TextFormatCtx updateState(TextFormatCtx state, Boolean action) {
        return state.withFormattedText(StringUtils.join(state.getTextToFormat().split("\\s+"), "\n"));
    }

    @Override
    public void saveState(TextFormatCtx state) {

    }

    @Override
    public Pair<TextFormatCtx, String> renderState(TextFormatCtx state) {
        return Pair.of(state, "format_text");
    }
}
