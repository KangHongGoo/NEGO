<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<sec:authorize access="isAuthenticated()">
  <sec:authentication property="principal" var="principal"/>

</sec:authorize>

<meta id="x_csrf_token" content="${_csrf.token}">
<meta id="header" content="${_csrf.headerName}">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>

<div class="container-fluid">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="/">메인</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="/member/signup">회원 가입 <span class="sr-only">(current)</span></a>
      </li>
      
      <c:choose>
      	<c:when test="${empty principal }">
      		 <li class="nav-item">
      	    <a class="nav-link" href="/member/login"}>로그인</a>
      </li>
      </c:when>
      
      <c:otherwise>
      <sec:authorize access="hasRole('ROLL_ADMIN')">
      <li class="nav-item">
      	 <a class="nav-link" href="/member/list/"}>회원목록</a>
       </li>
       </sec:authorize>
        <li class="nav-item">
      	<a class="nav-link" href="/member/read/${principal.username}">회원 정보 보기</a>
      </li>
      
      <li class="nav-item">
      	<a class="nav-link" href="/member/logout">로그아웃</a>
      </li>
     </c:otherwise>
      
      </c:choose>
      
     
      
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-expanded="false">
          게시판
        </a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="/board/list">목록</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="/board/write">글쓰기</a>
         
          <%-- <a class="dropdown-item" href="#">${principal.username}</a> --%>
        </div>
      </li>
      <li class="nav-item">
        
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="${principal.username}" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">로그인 된 ID</button>
    </form>
  </div>
</nav>


</div>