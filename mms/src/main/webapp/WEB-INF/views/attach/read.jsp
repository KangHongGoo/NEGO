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
<p>${dbsaveFilename}</p>


<div id="result">
	ㅎ.ㅎ
</div>

<script type="text/javascript">
	let dbsaveFilename = "${dbsaveFilename}";
	
	isImageFile(dbsaveFilename);
	
	
	
	function getOrgFilename(filename) {
		let idx = filename.indexOf("_", 12) + 1;
		
		return filename.substring(idx);
	}

	function isImageFile(filename) {
		let idx = filename.lastIndexOf(".") + 1;
		
		let formatName = filename.substring(idx);
		
		
	
		
		if(formatName === "png" ||
				formatName ==="gif" ||
				formatName === "jpg" ||
				formatName === "jpeg") {
		document.getElementById("result").innerHTML =
			"<a href='#'><img alt='안나옴' src='/attach/showImage?filename="+filename+"'/></a>"
			
		}else {
			document.getElementById("result").innerHTML =
				"<a href='/attach/download?filename="+filename+"'>"+getOrgFilename(dbsaveFilename)+"</a>"
		}
		
		return idx;
	}
	
</script>


</body>
</html>