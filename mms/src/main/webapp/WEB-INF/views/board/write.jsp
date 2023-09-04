<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>

</head>
  

<body>
<%-- <jsp:include page="../part_header.jsp"/> --%>
<%@ include file="../part_header.jsp" %>

<form action="/board/write" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
  <div class="form-group" style="width: 50rem;">
  작성자 : <input value="${principal.username}" readonly><br>
    <label for="exampleInputEmail1">제 목</label>
    <input  class="form-control" name="subject">
    
  </div>
  <div class="form-group" style="width: 50rem;">
    <label for="">내 용</label>
    <textarea  rows="10" style="resize:none;" class="form-control" name="content"></textarea>
  </div>
  <div class="form-group form-check">
    
    
  </div>
  <button type="submit" class="btn btn-success">확인</button>
</form>


</body>
</html>