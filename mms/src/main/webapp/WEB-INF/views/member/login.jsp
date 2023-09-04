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

${err}
<form action="/member/login" method="post">
<div class="form-group">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
  
   <div class="form-group" style="width: 20rem;">
    <label for="exampleInputEmail1"> ID</label>
    <input name="username" class="form-control" >
  </div>
  
  <div class="form-group" style="width: 20rem;">
    <label for="exampleInputPassword1"> Password</label>
    <input type="password" class="form-control" name="password" id="exampleInputPassword1">
  </div>
  
  <button  type="submit" class="btn btn-primary">로그인</button>

</form>

</body>
</html>