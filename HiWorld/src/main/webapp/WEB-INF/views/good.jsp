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
	여기는 good.jsp입니다
	<br/>
	방금 줄을 바꾸었습니다.
	<br/>
	html에서는
	<br/>
	엔터를 친다고 줄이 바뀌지는 않습니다.
	
	<form action="/say" method="post">
	id: <input name="id"><br/>
	pw: <input type="password" name="pw"><br/>
	
	<input type="submit" value="로그인">
	
	</form>
</body>
</html>