<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<div style = "margin: 0 auto" align="center">
<nav aria-label="...">

  <ul class="pagination">
  

    <li class="page-item ${page.isFirst()? 'disabled':'' } ">
      <a class="page-link" href="/board/list?pagenum=${page.number+1 -1}">Previous</a>
    </li>
    

    
    <c:forEach begin="${pt.begin}" end="${pt.end}" var="idx"  >
 
     <li class="page-item ${page.number==idx? 'active' : ''}" aria-current="page">
      <a class="page-link" href="/board/list?pagenum=${idx+1}">${idx+1}</a>
    </li>
     
    </c:forEach>
    
     
   
    
  
  
    
    <li class="page-item ${page.isLast()? 'disabled':'' }">
      <a class="page-link" href="/board/list?pagenum=${page.number+1+1}">Next</a>
    </li>
  </ul>
  
</nav>
</div>

