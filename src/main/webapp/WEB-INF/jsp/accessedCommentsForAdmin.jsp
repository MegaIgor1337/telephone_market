<%@ page import="java.util.List" %>
<%@ page import="dto.CommentDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Comments </title>
</head>
<body>

<a href="${pageContext.request.contextPath}/admin">
    <button type="button">Back to menu</button>
</a>
<%
    List<CommentDto> accessedComments = (List<CommentDto>) session.getAttribute("accessedComments");
    int i = 0;
    if (accessedComments != null && !accessedComments.isEmpty()) {
        for (CommentDto comment : accessedComments) {
            i++;
%>
<p>
        <%=i%>)
        <%= comment.getComment() %>
<form method="post" action="${pageContext.request.contextPath}/accessedComments">
    <input type="hidden" name="commentId" value="<%=comment.getId()%>"/>
    <button type="submit" value="Delete">Delete</button>
</form>
</p>
<%
    }
} else {
%>
<p>There are not comments</p>
<%
    }
%>
</body>
</html>
