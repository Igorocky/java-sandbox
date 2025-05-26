package org.igye.sandbox.examplewebapp.impl;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
public class TextFormatCtx {
    @Builder.Default
    private String textToFormat = "";
    @Builder.Default
    @With
    private String formattedText = "";
}
