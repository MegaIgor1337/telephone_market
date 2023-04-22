<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="/registration" method="post">
    <label for="name">Name:
        <input type="text" name="name" id="name">
    </label><br/>
    <label for="password">Password:
        <input type="password" name="password" id="password">
    </label><br/>
    <label for="passportNo">Passport number:
        <input type="text" name="passportNo" id="passportNo">
    </label><br/>
    <label for="email">Email:
        <input type="email" name="email" id="email">
    </label><br/>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option label="${role}">${role}</option><br>
        </c:forEach>
    </select><br/>
    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" VALUE="${gender}"> ${gender}
        <br/>
    </c:forEach>
    <input type="submit" value="Send">
    <a href="${pageContext.request.contextPath}/login">
        <button type="button">Back to login</button>
    </a>
</form>
<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span>
            <br>
        </c:forEach>
    </div>
</c:if>
</body>
</html>