<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<form action="update" method="post">
    座位id：<input type="number" name="seat_id"><br>
    座位状态：<br><input type="radio" name="seat_status" value="GOOD">好<br>
    <input type="radio" name="seat_status" value="BROKE">坏<br>
    <input type="radio" name="seat_status" value="NONE">无<br>
    <input type="submit" value="查看">
</form>
</body>
</html>