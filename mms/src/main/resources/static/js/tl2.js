/**
 * 
 */

function getRepliesInTl4(arr, username){
	
	let str = ``;
	
	arr.map((obj) =>{
		str += getReply(obj, username);
	});
	
	return str;
}



function getRepliesInTl3(arr, username){
	
	let str = ``;
	
	arr.map(function(obj){
		str += getReply(obj, username);
	});
	
	return str;
}

function getRepliesInTl2(arr, username){
	
	let str = ``;
	
	for(let i=0;i<arr.length;i++) {
		
		str += getReply(arr[i], username);
	
	}
	
	return str;
}

function getReply(reply, username) {
	
	if(reply.nickName === username){
	
	let str = `

	<div class="container-fluid" >
		<div class="card" style="width: 30rem;">
			 <div class="card-body shadow p-3 bg-white rounded"> 
				작성자 : ${reply.nickName} <br>
				 수정일 : ${reply.updateDate}<br>
				 작성일 : ${reply.createDate}<br>
				 내용 : <p>${reply.content}</p>
					
					<a href="/reply/update?id=${reply.id}" class="btn btn-primary btn-sm reply_update" data-reply-id="${reply.id}" >수정</a>
					<a href="/reply/delete?id=${reply.id}" class="btn btn-danger btn-sm reply_delete" data-reply-id="${reply.id}" >삭제</a>
			</div>
		</div>
</div>


	`;
	
	return str;
	}else{
		let str = `
	
	<div class="container-fluid" >
		<div class="card" style="width: 30rem;">
			 <div class="card-body shadow p-3 bg-white rounded"> 
				작성자 : ${reply.nickName} <br>
				 수정일 : ${reply.updateDate}<br>
				 작성일 : ${reply.createDate}<br>
				 내용 : <p>${reply.content}</p>
					
			</div>
		</div>
</div>

	`;
	
	return str;
	}
}

function test15(arr) {
	let str = ``;
	
	arr.map((el)=>{
		str += test13(el);
	});
	/*
	for(let i=0;i<arr.length;i++){
		str += test13(arr[i]);
		//= str = str + test13(arr[i])
	}*/
	
	return str;
}

function test14(arr){
	
	arr.map((el)=>{
		console.log(el);
	})
	
	/*arr.map(function(el){
		console.log(el);
	})*/
	
	
/*	for(let i=0;i<arr.length;i++) {
		console.log(el);
	}*/
}

function test13(obj){
	let str = `
	<div>
		<p>${obj.msg}, ${obj.name}</p>
		<button>수정</button>
	</div>
	
	
	`;
	
	return str;
}


function test12(msg, name) {
	let str = `
	
	<div>
		<p>${msg}, ${name}</p>
		<button>수정</button>
	</div>
	
	
	`;
	
	return str;
}

function test11(name) {
	let str = `
		Hello, ${name}
	
	`;
	
	return str;
}


function test10() {
	let str = `
		hello, js!
	
	`;
	
	return str;
}


 function haha2() {
	 alert(222);
 }