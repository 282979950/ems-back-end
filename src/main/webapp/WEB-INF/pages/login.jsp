<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/26
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>表具管理系统</title>
</head>
<body>
<form action="login" method="post">
    <input type="text" name="username" value="${username}"><br>
    <input type="password" name="password" value="${password}"> <br>
    <input type="submit" value="登录">
    <p>${message}</p>
</form>
</body>
</html>
