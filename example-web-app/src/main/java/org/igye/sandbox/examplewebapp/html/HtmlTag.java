package org.igye.sandbox.examplewebapp.html;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HtmlTag implements HtmlElem {
    private final String name;
    private final Map<String, String> attrs;
    private final List<? extends HtmlElem> children;

    public HtmlTag(String name, Map<String, String> attrs, List<? extends HtmlElem> children) {
        this.name = name;
        this.attrs = attrs;
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(name);
        if (attrs != null && !attrs.isEmpty()) {
            attrs.forEach((attrName, attrValue) -> {
                sb.append(" ").append(attrName).append("='").append(attrValue).append("'");
            });
        }
        if (CollectionUtils.isEmpty(children)) {
            sb.append("/>");
        } else {
            sb.append(">").append(
                children.stream()
                    .filter(Objects::nonNull)
                    .map(HtmlElem::toString)
                    .collect(Collectors.joining("\n"))
            );
            sb.append("</").append(name).append(">");
        }
        return sb.toString();
    }
}
