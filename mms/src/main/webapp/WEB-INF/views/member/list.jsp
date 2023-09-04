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

<h1>회원 목록</h1>

<table>
	<thead>
		<tr>
			<th>id</th>
			<th>name</th>
		</tr>
	
	</thead>
	
	<tbody>
	
	<c:forEach items="${list}" var="dto">
		<tr>
			<td>${dto.nickName}</td>
			<td><a href="/member/read/${dto.nickName}">    ${dto.name}   </a></td>
		</tr>
		
	</c:forEach>
	
	
	
		
		
	</tbody>
</table>

	<div>
		<button>1</button>  
		<button>2</button>
		<p id="result1"></p>
		<p class="result2"></p>
	</div>
<script type="text/javascript">

	$("button:eq(0)").click(function(){
		$("#result1").text("hi");
	});
	$("button:eq(1)").click(function(){
		$(".result2").text("world");
	});




</script>

</body>
</html>