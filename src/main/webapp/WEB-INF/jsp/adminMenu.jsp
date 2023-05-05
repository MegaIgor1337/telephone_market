<%@ page import="java.util.List" %>
<%@ page import="dto.CommentDto" %><%--
  Created by IntelliJ IDEA.
  User: tawer
  Date: 02.05.2023
  Time: 0:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
1)
<a href="${pageContext.request.contextPath}/moderateComments">

  Moderate comments (<%=request.getSession().getAttribute("sizeModerateComments")%>)
</a><br/>
2)
<a href="${pageContext.request.contextPath}/comments">
  View all comments
</a><br/>
3)
<a href="${pageContext.request.contextPath}/profileMenu">
  View profile
</a><br/>
4)
<a href="${pageContext.request.contextPath}/products">
  View all users
</a><br/>
5)
<a href="${pageContext.request.contextPath}/addComment">
  View all products
</a><br/>
6)
<a href="${pageContext.request.contextPath}/logout">
  View promo codes
</a><br/>
7)
<a href="${pageContext.request.contextPath}/logout">
  Log out
</a><br/>

</body>
</html>
