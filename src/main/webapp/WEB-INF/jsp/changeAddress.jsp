
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change address</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/changeAddress" method="post">
    <p>Change address for delivery</p>
    <label for="country">Country:
        <input type="text" name="country" id="country">
    </label><br/>
    <label for="city">City:
        <input type="text" name="city" id="city">
    </label><br/>
    <label for="street">Street:
        <input type="text" name="street" id="street">
    </label><br/>
    <label for="house">House:
        <input type="text" name="house" id="house">
    </label><br/>
    <label for="flat">Flat:
        <input type="text" name="flat" id="flat">
    </label><br/>
    <label for="submit">
        <input type="submit" value="send" id="submit">
    </label>
</form>
</body>
</html>
