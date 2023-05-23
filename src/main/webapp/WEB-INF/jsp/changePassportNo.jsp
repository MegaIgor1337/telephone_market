<%--
  Created by IntelliJ IDEA.
  User: tawer
  Date: 23.05.2023
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>changePassportNo</title>
</head>
<body>
<p style="color: red;">
  <%=request.getSession().getAttribute("messagePassportNo")%>
</p>
<a href="${pageContext.request.contextPath}/profileMenu">
  <button type="button">Back to menu</button>
</a>
<form action="${pageContext.request.contextPath}/changePassportNo" method="post">
  <label for="password">Password:
    <input type="password" name="password" id="password" required>
  </label><br>
  <label> New passport number:
    <input type="text" name="newPassportNo" id="newPassportNo">
  </label>
  <label for="submit">
    <input type="submit" value="send" id="submit">
  </label>
</form>
</body>
</html>
