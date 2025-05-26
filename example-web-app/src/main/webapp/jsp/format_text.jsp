<%@ page import="org.igye.sandbox.examplewebapp.impl.TextFormatCtx" %>
<%@ page import="org.apache.commons.text.StringEscapeUtils" %>
<%@ page import="static org.igye.sandbox.examplewebapp.impl.TextFormatController.ATTR_TEXT_TO_FORMAT" %>
<%
    TextFormatCtx ctx = (TextFormatCtx) request.getAttribute("ctx");
%>
<form method="post">
    <h5>Text to format:</h5>
    <textarea name="<%=ATTR_TEXT_TO_FORMAT%>" cols="100" rows="10"><%=StringEscapeUtils.escapeHtml4(ctx.getTextToFormat())%></textarea>
    <br>
    <button type="submit">Format</button>
</form>
<br>
<h5>Formatted text:</h5>
<pre><%=StringEscapeUtils.escapeHtml4(ctx.getFormattedText())%></pre>
