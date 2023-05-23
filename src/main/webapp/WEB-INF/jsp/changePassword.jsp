<%--
  Created by IntelliJ IDEA.
  User: tawer
  Date: 23.05.2023
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>changePassword</title>
</head>
<body>
<p style="color: red;">
  <%=request.getSession().getAttribute("messagePassword")%>
</p>
<a href="${pageContext.request.contextPath}/profileMenu">
  <button type="button">Back to menu</button>
</a>
<form action="${pageContext.request.contextPath}/changePassword" method="post">
  <label for="oldPassword">Old password:
    <input type="password" name="oldPassword" id="oldPassword" required>
  </label><br>
  <label> New password:
    <input type="password" name="newPassword" id="newPassword">
  </label>
  <label for="submit">
    <input type="submit" value="send" id="submit">
  </label>
</form>
</body>
</html>
