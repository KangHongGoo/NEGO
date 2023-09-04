<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">



<title>글 자세히 보기</title>

</head>
<body>
<div style = "margin: 0 auto" align="center">
<%@ include file="../part_header.jsp" %> 

<div class="card" style="width: 50rem;">
  <div class="card-body">
   
 

<blockquote class="blockquote text-center">

<h1>글 자세히 보기</h1>


<p></p>

  
 <p class="mb-0">
글 번호 : ${dto.id}<br>
작성자 : ${dto.nickName } <br>
작성일 : ${dto.createDate}<br>
수정일 : ${dto.updateDate}<br>
내용 : ${dto.content } <br>

</p>

</blockquote>

	
  </div>
</div>
</div>
<a href="/board/list" class="btn btn-info">목록</a>


<%-- <c:if test="${dto.nickName == principal.username}">

<a href="/board/update/${dto.id}">수정</a>
<a href="/board/delete/${dto.id}" id="board_read_a_delete">삭제</a>
</c:if>
 --%>






<%@ include file="reply.jsp" %>



<%-- <div class="collapse" id="myTest">

  <form action="/reply/write" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<input type="hidden" name="bid" value="${dto.id }">
  <div class="form-group">
 작성자 : <input value="${principal.username}" readonly><br>
    
  </div>
  <div class="form-group">
    <label for="">내 용</label>
    <textarea  rows="3" style="resize:none;" class="form-control" name="content"></textarea>
  </div>
  <div class="form-group form-check">
    
    
  </div>
  <button type="submit" class="btn btn-primary">작성</button>
  
</form>
</div>

<form action="/board/delete" method="post" class="deleteForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	<input type="hidden" name="id" value="${dto.id}">
</form> --%>



<script type="text/javascript">


	$("#test").click(function(event){
		event.preventDefault();
		$("#myTest").collapse("toggle");
	});

	$("#board_read_a_delete").click((event) => {
		event.preventDefault();
		
	});	



	$("#board_read_a_delete").click(function(event){
		event.preventDefault();
		$(".deleteForm").submit();
	});	



</script>
</body>
</html>