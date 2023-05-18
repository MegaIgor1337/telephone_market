<%@ page import="java.util.List" %>
<%@ page import="dto.CommentDto" %>
<%@ page import="entity.enums.CommentStatus" %><%--
  Created by IntelliJ IDEA.
  User: tawer
  Date: 21.04.2023
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Comments </title>
</head>
<body>
<a href="${pageContext.request.contextPath}/menu">
    <button type="button">Back to menu</button>
</a>
<%
    List<CommentDto> userComments = (List<CommentDto>) session.getAttribute("userComments");
    int i = 0;
    if (userComments != null && !userComments.isEmpty()) {
        for (CommentDto comment : userComments) {
            i++;
%>
<p>
    <%=i%>)
    <%= comment.getComment() %>
</p>
<p><%=comment.getCommentStatus()%>
</p>

<form method="post" action="${pageContext.request.contextPath}/userComments">
    <input type="hidden" name="commentId" value="<%=comment.getId()%>"/>

    <button type="submit" value="Delete">Delete</button>
</form>

<%
    }
} else {
%>
<p>You do not nave comments</p>
<%
    }
%>

</body>
</html>
