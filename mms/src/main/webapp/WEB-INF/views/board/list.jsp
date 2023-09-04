<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>

</head>
<body>

 



<jsp:include page="../part_header.jsp"/>
<h4>게시판 <span class="badge badge-danger">New</span></h4>
<table class="table">
  <thead>
    <tr>
      <th scope="col">글번호</th>
      <th scope="col">제목</th>
      <th scope="col">작성자</th>
      <th scope="col">작성일</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${page.content}" var="dto">
   	 <tr>
     	 <th scope="row">${dto.id}</th>
     	 
    	 <td> <a href="/board/read/${dto.id}">${dto.subject}</a></td>

			<td>${dto.nickName}</td>
	
    	  <td>${dto.updateDate}</td>
   	 </tr>
    </c:forEach>
    
  </tbody>
  
 
  
</table>

<div style="padding:0px 0px 0px 700px;" >
<a href="/board/write" class="badge badge-dark">글쓰기 화면으로</a>

	<!-- 여기가 페이징 처리하는 부분 여기는 include 기능을 이용할 것임 -->

</div>
<%@ include file="part_paging.jsp" %>

</body>
</html>