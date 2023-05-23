<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dto.UserDto" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<%UserDto userDto = (UserDto) request.getSession().getAttribute("userDto");%>
<p1>Login:</p1>
<%=userDto.getName()%>
<a href="${pageContext.request.contextPath}/changeLogin">
    <button type="button"> Change name</button>
</a><br/>
<p1>Email:</p1>
<%=userDto.getEmail()%>
<a href="${pageContext.request.contextPath}/changeEmail">
    <button type="button"> Change email</button>
</a><br/>
<p1>Passport number:</p1>
<%=userDto.getPassportNo()%>
<a href="${pageContext.request.contextPath}/changePassportNo">
    <button type="button"> Change passport number</button>
</a><br/>
<p1>Balance: </p1>
<%=userDto.getBalance() + "$"%>
<a href="${pageContext.request.contextPath}/putMoney">
    <button type="button"> Put money </button>
</a><br/>
<a href="${pageContext.request.contextPath}/address">
    <button type="button"> Add delivery address </button>
</a>
<a href="${pageContext.request.contextPath}/changePassword">
    <button type="button"> Change password </button>
</a>
<a href="${pageContext.request.contextPath}/menu">
    <button type="button"> Back to menu </button>
</a>
<br/>
<p1>Your delivery addresses:</p1>
<br/>
<table>
    <thead>
    <tr>
        <th>#</th>
        <th>Country</th>
        <th>City</th>
        <th>Street</th>
        <th>House</th>
        <th>Flat</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="address"  items="${sessionScope.addresses}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td>${address.country}</td>
            <td>${address.city}</td>
            <td>${address.street}</td>
            <td>${address.house}</td>
            <td>${address.flat}</td>
            <td>
                <form method="get" action="${pageContext.request.contextPath}/changeAddress">
                    <input type="hidden" name="addressId" value="${address.id}"/>
                    <input type="submit" value="Change"/>
                </form>
            </td>
            <td>
                <form method="POST" action="${pageContext.request.contextPath}/deleteAddress">
                    <input type="hidden" name="addressId" value="${address.id}"/>
                    <button type="submit" value="Delete">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
