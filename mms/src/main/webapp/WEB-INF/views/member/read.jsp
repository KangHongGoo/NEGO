<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 자세히 보기</title>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
<style type="text/css">

	.hello{
	color: #ff00ff;
	background-color: rgb(255,255,0);
	}
	
	 #wrold{
	font-size : 30px;
	}

</style>
</head>
<body>
<h1 class="hello">회원 정보 자세히 보기</h1>


	<div class="container" >
	<div class="card">
  	<h5 class="card-header">${dto.nickName}</h5>
 	 <div class="card-body">
   	 <h5 class="card-title">${dto.name}</h5>
  	  <p class="card-text">${dto.password}</p>
  	  <a href="/member/list" class="btn btn-info">목록</a>
   	<a href="/member/update/${dto.nickName}" class="btn btn-primary">수정</a>
   	<a href="/member/delete/${dto.nickName}" class="btn btn-danger">삭제</a>
  </div>
</div>
	
	
	
	
	</div>



</body>
</html>