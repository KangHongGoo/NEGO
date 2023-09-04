<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원 가입 화면</title>
	</head>
	<body>
	<jsp:include page="../part_header.jsp"/>
 		<h1>회원 가입 화면</h1>
 		
 		<div data-kanghonggoo = "강홍구"></div>
 	
	
		<form action="/member/signup" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		
			id : <input name = "nickName">  <button type="button" onclick="test()" id="member_signup_btn_checkid">중복 검사</button><br>
			pw1 : <input type="password" name = "password1"><br>
			pw2 : <input type="password" name = "password2"><br>
			이름 : <input name = "name"> <br>
			<button> 회원 가입 완료 </button>
			
		
		
		</form>


<script type="text/javascript">		

	//jquery로 구현
	// 아이디 중복 검사 버튼을 클릭하면, 이 코드가 적용됨.
	$("#member_signup_btn_checkid222").click((event) => {
		// form 태그 안에 있는 버튼은 모두 submit 버튼 기능을 가지고 있다.
		// 우리는 form 태그의 내용을 서버쪽으로 전달하려는 것이 아니라 아이디 중복 검사만 할 것 인니 까
		// 이 버튼의 submit 기능을 막아야 하는데 
		// 그 기본 기능을 막는 코드
		event.preventDefault();
		
		// nickName 입력태그엥서 입력된 값 가져오는 코드
		let nickName = $("#nickName").val();
		
		
		//이하 코드는 어제와 오늘 아침에 배웠던 fetch를 이용한 비동기 통신
		let uriGet = "/member/checknickname?nickName="+nickName;
		
		let options = {
				method : "GET",
				headers : {
					"Content-Type" : "appilcation/json"
				}
		};
		
		fetch(uriGet, options)
		.then(response => response.json())
		.then(data => {
			alert(data["result"]);
		})
	});

	function test(){
		
		
		let nickName = document.getElementById("nickName").value;
		
		let uri = "/member/checknickname?nickName="+nickName
		
		let options = {
				method : "GET",
				headers : {
					"Content-Type" : "application/json"
				}
				
		}
		
		fetch(uri, options)
		.then(response => response.json())
		.then(data => {
			alert(data["result"])
		})
	}
</script>
</body>
</html>