<%--
  Created by IntelliJ IDEA.
  User: tawer
  Date: 23.05.2023
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>change email</title>
</head>
<body>
<p style="color: red;">
  <%=request.getSession().getAttribute("messageEmail")%>
</p>
<a href="${pageContext.request.contextPath}/profileMenu">
  <button type="button">Back to menu</button>
</a>
<form action="${pageContext.request.contextPath}/changeEmail" method="post">
  <label for="password">Password:
    <input type="password" name="password" id="password" required>
  </label><br>
  <label> New email:
    <input type="text" name="newEmail" id="newEmail">
  </label>
  <label for="submit">
    <input type="submit" value="send" id="submit">
  </label>
</form>
</body>
</html>
