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

<%-- <jsp:include page="../part_header.jsp"/> --%>
<%@ include file="../part_header.jsp" %>

<form action="/board/update" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
 	<input type="hidden" name="id" value="${dto.id}">
 	
 	<div class="form-group">
 	작성자 : <input name="nickName" value="${dto.nickName}"><br>
    <label for="exampleInputEmail1">제 목</label>
    <input  class="form-control" name="subject" value="${dto.subject }">
    
  </div>
  <div class="form-group">
    <label for="">내 용</label>
    <textarea class="form-control" name="content" >${dto.content} </textarea>
  </div>
  <div class="form-group form-check">
    
    
  </div>
  <button type="submit" class="btn btn-primary">수정완료</button>

</form>

</body>
</html>