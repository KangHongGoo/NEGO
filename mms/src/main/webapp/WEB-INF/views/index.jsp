<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<sec:authorize access="isAuthenticated()">
  <sec:authentication property="principal" var="principal"/>

</sec:authorize>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


</head>
<body>

<button onclick="getItemAll()">imsapi의 모든 item 가져오기</button>
<button onclick="insertAStaff()">smsapi에 staff 데이터 1개 추가하기</button>
<button onclick="updateAStaff()">수정</button>


<script type="text/javascript">

	function updateAStaff() {
		let url = "http://localhost:9002/staff";
		let options = {
				method : "PUT",
				headers : {
					"Content-Type" : "application/json",
					"X-CSRF-TOKEN" : "${csrf.token}"
				}
		}
		if(options.method !== "GET") {
		options.body =JSON.stringify({
			username : "m001",
			password : "1", 
			password2 : "1",
			orgPassword :"2"
			
		});
		}
		
		fetch(url, options)
		.then(res => {
			if(!res.ok) {
			alert("실패");
			throw new Error("에러");
			
			
			}
			return res.json();
		})
		.then(data => {
			alert("성공");
			console.log(data);
		})
		.catch(error => {
			console.log(error.message);
		})		
		
	}

	function insertAStaff() {
		let url = "http://localhost:9002/staff";
		let options = {
				method : "POST",
				headers : {
					"Content-Type" : "application/json",
					"X-CSRF-TOKEN" : "${csrf.token}"
				}
		}
		if(options.method !== "GET") {
		options.body =JSON.stringify({
			username : "m011",
			password : "0", 
			password2 : "0"
			
		});
		}
		
		fetch(url, options)
		.then(res => {
			if(!res.ok) {
			alert("실패");
			throw new Error("에러");
			
			
			}
			return res.json();
		})
		.then(data => {
			alert("성공");
			console.log(data);
		})
		.catch(error => {
			console.log(error.message);
		})		
		
	}

	function getItemAll() {
		let url = "http://localhost:9001/item/all";
		let options = {
				method : "GET" ,
				headers : {
					"Content-Type" : "application/json"
				}
		
		};
		
		fetch(url, options)
		.then(res => {
			if(!res.ok) {
				alert("실패");
				throw new Error("에러");
			}
			return res.json();
		})
		.then(data => {
			console.log(data.result);
			
		})
		.catch(error=>{
			console.log(error);
		})
		
	}

























</script>

</body>
</html>