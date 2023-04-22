<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add comment</title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/comments">
    <label for="comment">Write comment:</label>
    <br/>
    <textarea id="comment" name="comment" rows="5" cols="40"></textarea>
    <br>
    <button type="submit">Send</button>
    <a href="${pageContext.request.contextPath}/menu">
        <button type="button">Back to menu</button>
    </a>
</form>
</body>
</html>
