<%--
  Created by IntelliJ IDEA.
  User: tawer
  Date: 23.05.2023
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>changeLogin</title>
</head>
<body>
<p style="color: red;">
    <%=request.getSession().getAttribute("messageLogin")%>
</p>
<a href="${pageContext.request.contextPath}/profileMenu">
    <button type="button">Back to menu</button>
</a>
<form action="${pageContext.request.contextPath}/changeLogin" method="post">
    <label for="password">Password:
        <input type="password" name="password" id="password" required>
    </label><br>
    <label> New Login:
        <input type="text" name="newLogin" id="newLogin">
    </label>
    <label for="submit">
        <input type="submit" value="send" id="submit">
    </label>
</form>
</body>
</html>
