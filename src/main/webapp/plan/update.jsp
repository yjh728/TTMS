<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<form action="update" method="post">
    演出计划id：<input type="number" name="plan_id"><br>
    剧目id：<input type="text" name="play_id"><br>
    演出厅id：<input type="text" name="studio_id"><br>
    播放日期：<input type="date" name="play_date"><br>
    开始时间：<input type="time" name="start_time"><br>
    <input type="submit" value="更新">
</form>
</body>
</html>
