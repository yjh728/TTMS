<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<form action="user/register" method="post">
    账号：<input type="text" name="username"><br/>
    密码：<input type="password" name="password"><br/>
    身份：<br><input type="radio" name="identity" value="售票员">售票员<br>
    <input type="radio" name="identity" value="经理">经理<br>
    <input type="radio" name="identity" value="用户">用户<br>
    密保问题：<input type="text" name="question"><br/>
    密保答案：<input type="text" name="answer"><br/>
    <input type="submit" value="注册">
</form>
</body>
</html>
