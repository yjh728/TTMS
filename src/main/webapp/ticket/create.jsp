<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<form action="create" method="post">
    演出计划id：<input type="text" name="plan_id"><br>
    是否重新生成：<br><input type="radio" name="recreate" value="true">是<br>
    <input type="radio" name="recreate" value="false">否<br>
    <input type="submit" value="生成票">
</form>
</body>
</html>
