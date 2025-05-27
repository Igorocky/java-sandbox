package org.igye.sandbox.examplewebapp.impl;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class TextFormatCtx {
    private String textToFormat;
    private Optional<String> formattedText;
}
