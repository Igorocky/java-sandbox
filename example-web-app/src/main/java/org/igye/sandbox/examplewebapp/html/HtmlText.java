package org.igye.sandbox.examplewebapp.html;

public class HtmlText implements HtmlElem {
    private final String text;

    public HtmlText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
