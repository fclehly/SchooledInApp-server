<%--
  Created by IntelliJ IDEA.
  User: fwz
  Date: 2017/6/8
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File Upload Test</title>
</head>
<body>
<form action="/api/avatars" method="post" enctype="multipart/form-data">
    选择文件:<input type="file" name="avatar"><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
