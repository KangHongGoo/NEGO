<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
test test 

<form action= "/attach/fileupload" method="post" enctype="multipart/form-data">
	
	파일 선택 : <input type="file" name="file"/>
	<br/>

	<input type="submit" value="파일 저장"/>

</form>

</body>
</html>