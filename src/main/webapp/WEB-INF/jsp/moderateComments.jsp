<%--
  Created by IntelliJ IDEA.
  User: tawer
  Date: 02.05.2023
  Time: 0:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Moderate Comments</title>
</head>
<body>
<form method="get" action="${pageContext.request.contextPath}/admin">
    <button type="submit" name="button"> Back to menu </button>
</form>
<h1>Comments:</h1>
<ul>
    <c:if test="${not empty requestScope.moderateComments}">
        <c:forEach var="comment" items="${requestScope.moderateComments}" varStatus="counter">
            <li>
                <b>${counter.count}: <span
                        style="color: gray">${comment.userDto.name} - </span></b> ${fn:toLowerCase(comment.comment)}
            </li>
        <form method="post" action="${pageContext.request.contextPath}/moderateComments">
            <input type="hidden" name="commentId" value="${comment.id}"/>
            <button type="submit" value="access" name="button"> Access </button>
            <button type="submit" value="delete" name="button"> Delete </button>
        </form>
        </c:forEach>
    </c:if>
</ul>
</body>
</html>
