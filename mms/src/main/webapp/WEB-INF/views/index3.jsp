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

<jsp:include page="part_header.jsp"/>


<h1> index.jsp의 본문입니다</h1>

<jsp:forward page="/board/list"/>

<div>
	<button onclick="msg('안녕')">클릭해 보세요.</button>
	
	<button onclick="me1(1, 4)">클릭</button>
	<button onclick="uriGet()">uriGet통신</button>
	<button onclick="zzz()">zzz()함수</button>
	<button onclick="changeView()"> 깜빡 </button>
	
	<button onclick="a1()">a1</button>
	<button onclick="a2('안녕')">a2</button>
	<button onclick="a3()">a3</button>
	<button onclick="a4('안녕')">a4</button>
	<button onclick="a5()">a5</button>
	<button onclick="a6('hello world')">a6</button>
	<button>a7</button>
	<button>a8</button>
	<button>a9</button>
	
	<div id="result">
	
	

</div>

<script type="text/javascript" src="/js/tl.js"></script>

<script type="text/javascript" src="/js/tl2.js"></script>

<script type="text/javascript">
	
	
	
	let arr = [
		{msg: "good", name : "김유신"},
		{msg : "hi", name : "qorqja"},
		{msg : "world" , name : "dkswndrms"}
		
	];
	
	let str = test15(arr);
	$("#result").html(str);
	
	function a1(){
		let uriGet = "/member/checknickname?nickName=m1000";
		let options = {
				method : "GET" ,
				headers : {
					"Content-Type" : "application/json"
				}
		};
				
		fetch(uriGet, options)
		.then( (response) => response.json() )
		.then( (data) => {
			document.getElementById("result").innerText = data["result"];
		});
	}
	function a2(msg) {
		let uriGet = "/member/checknickname?nickName=m1000";
		let options = {
				method : "GET" ,
				headers : {
					"Content-Type" : "application/json"
				}
		};
				
				fetch(uriGet, options)
				.then( (response) => response.json() )
				.then ( (date) => {
					$("#result").text(data["result"])
				} );
	}

	function a3() {
		
		let uriGet = "/member/checknickname?nickName=m1000";
		let options = {
				method : "GET",
				headers : {
					"Content-Type" : "application/json"
				}
		}
		
				
		fetch(uriGet, options)			
		.then( (response) => response.json())
		.then( (data) => {
			document.getElementById("result").innerText = data["result"];		
		});
	}
	function a4(msg) {
		let uriGet = "/member/checknickname?nickName=m1000"
		let options = {
				method : "GET",
				headers : {"Content-Type" : "application/json"}
		};
				
				
		fetch(uriGet, options)
		.then((response) => response.json())
		.then((data) => {
			document.getElementById("result").innerText=data["result"];
		});
	}
	function a5() {
		let nickName = prompt();
		let uriGet = "/member/checknickname?nickName="+nickName;
			let options = {
					method : "GET",
					headers : {"Content-Type" : "application/json"}
			};
					
					
			fetch(uriGet, options)
			.then((response) => response.json())
			.then((data) => {
				document.getElementById("result").innerText=data["result"];
			});
	}
	function a6(msg){
		let uriGet = "/member/checknickname?nickName=m1000"
			let options = {
					method : "GET",
					headers : {"Content-Type" : "application/json"}
			};
					
					
			fetch(uriGet, options)
			.then((response) => response.json())
			.then((data) => {
				document.getElementById("result").innerText=data["result"];
			});
	}
	

	function changeView() {
		
		
		location.href = "/";
	}
	
	
	function zzz() {
	alert(1111);
	alert(2222);
	alert(3333);

	}



	function uriGet() {	
	let uriGet = "/member/checknickname?nickName=m1000";
	let options = {
			method : "GET",
			headers : {
			"Content-Type" : "application/json"}
	}
	fetch(uriGet. options)
	.then( (response) => response.json())
		.then((data) => {
			document.getElementById("result").innerText=data["result"];
		});
	}	
	
</script>

</body>
</html>