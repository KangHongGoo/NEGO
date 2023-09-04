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
helloooooooooooo
<br/>
worlddddddddddd
<br/>

<form action="/say" method="post">
	id: <input name="id"><br/>
	pw: <input name="pw"><br/>
	<input type="submit" value="로그인">

</form>



</body>
</html>