<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삭제</title>

</head>
<body>
<jsp:include page="../part_header.jsp"></jsp:include>

<form action="/board/delete/${dto.id}" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
  삭제 하시겠습니까 ? 
 <input type="hidden" name="id" value="${dto.id}">
 <button>삭제 완료</button>
</form>

</body>
</html>