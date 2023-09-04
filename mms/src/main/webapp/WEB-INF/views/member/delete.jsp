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
<jsp:include page="../part_header.jsp"/>

 <form action="/member/delete" method="post">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
 <input type="hidden" name="nickName" value="${dto.nickName}">
 <input type="password" name = "password"><br>
 <button>삭제 완료</button>
 
 </form>
 

</body>
</html>