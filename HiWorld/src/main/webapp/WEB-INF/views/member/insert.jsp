<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 페이지</title>
</head>
<body>
	<form action="/member/insert" method="post">
	ID : <input name="id">
	<br/>
	PW : <input type="password" name="pw">
	<br/>
	이름 : <input name="name">
	<br/>
	<input type="submit" value="가입">
	
	</form>
	

</body>
</html>