/**
 * 
 */
function test3(msg, name) {
	let str = `
	<div>
		<p>${msg}</p>
		<button>${name}</button>	
	</div>
	`;
	
	return str;
}

function test2(name) {
	let str = `
	<div>
		<p>hello</p>
		<button>${name}</button>	
	</div>
	`;
	
	return str;
}

function test1(name) {
	let str = `Hello, ${name}`;
	
	return str;
}

function test0(name) {
	let str = `Hello, name`;
	
	return str;
}

 function haha(){
	 alert(111);
 }