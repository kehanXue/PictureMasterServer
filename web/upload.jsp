<%--
  Created by IntelliJ IDEA.
  User: kehan
  Date: 2019/9/4
  Time: 下午5:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Please upload your image</title>
</head>
<body>
<h1>Please upload your image</h1>
<form method="post" action="PictureMasterServlet" enctype="multipart/form-data">
    Choose your image:
    <input type="file" name="uploaded_image"/>
    <br/><br/>
    <input type="text" name="convert_type" />
    <input type="submit" value="Upload"/>
</form>
</body>
</html>