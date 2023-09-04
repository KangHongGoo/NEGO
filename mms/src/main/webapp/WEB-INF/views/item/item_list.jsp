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
<div id="test">
코드 <input id="item_code"><br>
이름 <input id="item_name"><br>
정보 <input id="item_info"><br>
가격 <input id="item_price"><br>

<button type="button" id="test_button">ㅇddd</button>






</div>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript">



$("#")


$("#test_button").click(function(){
	let codeOfItem = $("#item_code").val();
	let nameOfItem = $("#item_name").val();
	let infoOfItem = $("#item_info").val();
	let priceOfItem = $("#item_price").val();
	let uri = "/item/item_list";
	
	let options = {
			method : 'POST',
			headers : {
				"Content-Type" : "application/json",
				"header" : "X-CSRF-TOKEN",
				"X-CSRF-TOKEN" : "${_csrf.token}"
			},
			body : JSON.stringify({
				codeOfItem : codeOfItem,
				nameOfItem : nameOfItem,
				infoOfItem : infoOfItem,
				priceOfItem : priceOfItem
			})
	}
	
	fetch(uri, options)
	.then(res => {
		if(!res.ok) {
			alert("실패")
			throw new Error("에러")
		}
		return res.json();
	})
	.then(data => {
		alert("성공")
	})
	.catch(error =>{
		console.log(error)
	})
});






































</script>
</body>
</html>