<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<meta charset="UTF-8">

<c:if test="${not empty principal }">

	<a href="/board/reply" id="test" class="btn btn-info">댓글</a>
	
</c:if>
<div style = "margin: 0 auto" align="center">



<c:if test="${empty principal }">
	<a href="/member/login" class="btn btn-info">로그인</a>
</c:if>

<div class="collapse" id="myTest">

	<form >

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
 <%-- <c:forEach items="${reply}" var="reply">

	
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

</c:forEach> --%>


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

</div>

<script type="text/javascript" src="/js/tl2.js"></script>

<script type="text/javascript">

	const X_CSRF_TOKEN = document.getElementById("x_csrf_token").content;
	const HEADER = document.getElementById("x_csrf_token").content;

	let username = "${principal.username}"
	
	getReplies(username);
	
	
	 $("#reply_button").click(function(event){
		 event.preventDefault();
		let bid = $("#reply_bid").val();
		let content = $("#content_id").val();
		let nickName = $("#reply_nickName").val();
		let uri = "/reply";
		
		let options = {
				method : 'POST',
				headers : {
					"Content-Type" : "application/json",
					"header" : "X-CSRF-TOKEN",
					"X-CSRF-TOKEN" : "${_csrf.token}"
				},
				body : JSON.stringify({
					bid : bid,
					nickName : nickName,
					content : content
				})
				
		}
		
		fetch(uri, options)
		.then(res => {
			if(!res.ok) {
				alert("수정 실패")
				throw new Error("에러가 발생했습니다.")
				}
			return res.json();
		})
		.then(data => {
			alert("성공")
			getReplies(username);
		})
		.catch(error =>{
			console.log(error)
		})
		.finally(() =>{
		$("#content_id").val('');
		})
		
		
	}); 


//////////////////////수정, 수정성공 실패표시 ////////////////////


	$("#reply_update_modal_submit").click(function(){
		let id = $("#reply_update_modal_id").text();
		let content = $("#reply_update_modal_content").val();
		let url = "/reply/update";
		
		let options = {
				method : 'PUT',
				headers : {
					"Content-Type" : "application/json",
					"header" : "X-CSRF-TOKEN",
					"X-CSRF-TOKEN" : "${_csrf.token}"
					
				},
				body : JSON.stringify({
					id : id,
					content : content
					
				})
		}
		
		fetch(url, options)
		.then(res => {
			if(!res.ok){
				alert("수정 실패")
				throw new Error("에러가 발생했습니다.")
			}
			
			
			return res.json()
			
		})
		.then(data => {
			alert("성공")
			getReplies(username);
		})
		.catch(error =>{
			console.log(error)
		})
		.finally(()=>{
			$("#mymodal").modal("hide")
			
		})
		/* $("#form_reply").attr("action", url);
		$("#form_reply_id").val(id);
		$("#form_reply_content").val(content);
		
		$("#form_reply").submit(); 
		*/
	});
///////////////////////// 수정 클릭 이벤트 // //////////////////////////
	$("#replies").on("click", ".reply_update", function(event){
		event.preventDefault();
		let data_reply_id = $(this).attr("data-reply-id");
		$("#reply_update_modal_id").text(data_reply_id);
		
		let content = $(this).prev().text();
		
		$("#reply_update_modal_content").val(content);
		
		$("#mymodal").modal("show");
		
	});
	
	/* $(".reply_update").click(function(event){
		event.preventDefault();
		let data_reply_id = $(this).attr("data-reply-id");
		$("#reply_update_modal_id").text(data_reply_id);
		
		let content = $(this).prev().text();
		
		$("#reply_update_modal_content").val(content);
		
		$("#mymodal").modal("show");
		
	});
 */
/////////////////////////////////////////////////////////////////
//////////////////////// 삭제 클릭 이벤트 //////////////////////////
	$("#replies").on("click", ".reply_delete", function(event){
		event.preventDefault();
		
		let isTrue = confirm("정말로 삭제하겠습니까?");
		
		if(!isTrue){
			return;
		}
		
		let id = $(this).attr("data-reply-id");
		
		let uri="/reply";
		
		let options = {
				method : 'DELETE',
				headers : {
					"Content-Type" : "application/json",
						"header" : "X-CSRF-TOKEN",
						"X-CSRF-TOKEN" : "${_csrf.token}"
						
				},
				body : JSON.stringify({
					id : id

				
				})
		
		}
		fetch(uri, options)
		.then(response => {
			if(!response.ok){
				alert("삭제 실패")
				throw new Error("에러가발생했습니다.")
			}
		
			
		return response.json()
		
		})
		.then(data => {
			alert("성공")
			getReplies(username);
		})
		.catch(error =>{
			console.log(error)
		})
		.finally(()=>{
			$("#mymodal").modal("hide")
		})
	
		/* $("#form_reply_id").val(data_reply_id);
		$("#form_reply").submit(); */
	});

/* 	$(".reply_delete").click(function(event){
		event.preventDefault();
		
		let data_reply_id = $(this).attr("data-reply-id");
		
	
		$("#form_reply_id").val(data_reply_id);
		$("#form_reply").submit();
	}); */
////////////////////////////////////////////////////////////////
	$("#test").click(function(event) {
		event.preventDefault();
		$("#myTest").collapse("toggle");
	});
	
	function getReplies(username){
		let bid = ${dto.id};
		let uri = "/reply/all/"+${dto.id}
		
		let options = {
				method : "GET",
				headers : {
					"Content-Type" : "application/json"
				}
		}
		
		fetch(uri, options)
		.then(response => response.json())
		.then(data => {
			let str = getRepliesInTl2(data.arr, username)
			$("#replies").html(str);
		});
	}
	
</script>
