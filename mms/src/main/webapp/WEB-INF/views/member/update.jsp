<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정 화면</title>
</head>
<body>
 	<h1>회원 정보 수정 화면</h1>
 	
	
	<form action="/member/update" method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	
		id : <input name = "nickName" readonly value="${dto.nickName}"><br>
		pw : <input type="password" name = "password"><br>
		
		이름 : <input name = "name" value="${dto.name}"> <br>
		<button> 수정 완료 </button>
		
	
	
	</form>




</body>
</html>