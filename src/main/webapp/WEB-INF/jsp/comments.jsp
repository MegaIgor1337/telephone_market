<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Comments</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/userComments">
    <button type="button"> View your comments </button>
</a>
<a href="${pageContext.request.contextPath}/menu">
    <button type="button">Back to menu</button>
</a>
<a href="${pageContext.request.contextPath}/addComment">
    <button type="button">Add comment</button>
</a>
<h1>Comments:</h1>
<ul>
    <c:if test="${not empty requestScope.comments}">
        <c:forEach var="comment" items="${requestScope.comments}" varStatus="counter">
            <li>
                <b>${counter.count}: <span
                        style="color: gray">${comment.userDto.name} - </span></b> ${fn:toLowerCase(comment.comment)}
            </li>
        </c:forEach>
    </c:if>
</ul>
</body>
</html>
