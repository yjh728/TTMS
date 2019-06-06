<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<form action="add" method="post" enctype="multipart/form-data">
    剧目名：<input type="text" name="play_name"><br>
    剧目类型：<input type="text" name="play_type"><br>
    剧目地区：<input type="text" name="area"><br>
    剧目时长：<input type="number" name="duration"><br>
    剧目上映日期：<input type="date" name="start_date"><br>
    剧目下映日期：<input type="date" name="end_date"><br>
    剧目价格：<input type="number" name="price"><br>

    文件：<input type="file" name="username">
    <input type="submit" value="上传">
</form>
</body>
</html>
