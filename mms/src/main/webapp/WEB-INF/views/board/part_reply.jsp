<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">



<title>글 자세히 보기</title>

</head>
<body style="margin: 20px 150px 20px 150px;">

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

<a href="/board/list" class="btn btn-info">목록</a>

<c:if test="${dto.nickName == principal.username}">

<a href="/board/update/${dto.id}">수정</a>
<a href="/board/delete/${dto.id}" id="board_read_a_delete">삭제</a>
</c:if>







<%@ include file="reply.jsp" %>



<div class="collapse" id="myTest">

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

<meta charset="UTF-8">

<c:if test="${not empty principal }">

	<a href="/board/reply" id="test" class="btn btn-info">댓글</a>
	
</c:if>




<c:if test="${empty principal }">
	<a href="/member/login" class="btn btn-info">로그인</a>
</c:if>

<div class="collapse" id="myTest">

	<form  method="post" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> 
		<input type="hidden" name="bid" value="${dto.id }" id="reply_bid">
		<div class="form-group">
			작성자 : <input value="${principal.username}" name="nickName" readonly id="reply_nickName"><br>

		</div>
		<div class="form-group" style="width: 30rem;">
		
			<label for="" >내 용</label>
			<textarea rows="3" style="resize: none;" class="form-control shadow-lg p-3 mb-5 bg-white rounded"name="content" id="content_id" ></textarea>
			
		</div>
		<div class="form-group form-check"></div>
		<button type="submit" class="btn btn-primary" id="reply_button">작성</button>

	</form>
</div>
<div id="replies"></div>
 <c:forEach items="${reply}" var="reply">

	
	<div class="container-fluid" >
		<div class="card" style="width: 30rem;">
			 <div class="card-body shadow p-3 bg-white rounded"> 
				작성자 : ${reply.nickName} <br>
				 수정일 : ${reply.updateDate}<br>
				 작성일 : ${reply.createDate}<br>
				 내용 : <p>${reply.content}</p>
				<c:if test="${reply.nickName == principal.username}">
					<a href="/reply/update?id=${reply.id}" class="btn btn-primary btn-sm reply_update" data-reply-id="${reply.id}" >수정</a>
					<a href="/reply/delete?id=${reply.id}" class="btn btn-danger btn-sm reply_delete" data-reply-id="${reply.id}" >삭제</a>
				</c:if>
			</div>
		</div>
</div>

</c:forEach>


		<form action="/reply/delete" method="post" id="form_reply">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			 <input type="hidden" name="id" id="form_reply_id">
			 <input type="hidden" name="content" id="form_reply_content">
			 	</form>

<div>
	<!-- Modal -->
	<div class="modal fade" id="mymodal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
				
					<h5 class="modal-title" id="reply_update_modal_id">num</h5>
					
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				
				 <input id="reply_update_modal_content" class="form-control">
				 
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary btn-sm" id="reply_update_modal_submit">확인</button>
					<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">취소</button>
					
				</div>
			</div>
		</div>
	</div>

</div>

<form action="/board/delete" method="post" class="deleteForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	<input type="hidden" name="id" value="${dto.id}">
</form>
 --%>