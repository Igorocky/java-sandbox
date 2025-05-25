package org.igye.sandbox.examplewebapp.servlets.textformat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextFormatCtx {
    @Builder.Default
    private String textToFormat = "";
    @Builder.Default
    private String formattedText = "";
}
