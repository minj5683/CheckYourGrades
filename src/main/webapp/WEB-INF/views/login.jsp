<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author"
	content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Hugo 0.80.0">
<title>평점 확인</title>

<!-- Web Font --> 
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/gh/moonspam/NanumSquare@1.0/nanumsquare.css">

<!-- Favicons -->
<link rel="icon" href="<c:url value="/resources/favicon.ico"/>">

<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet">


<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>

<!-- Custom styles for this template -->
<link href="<c:url value="/resources/css/signin.css"/>" rel="stylesheet">

<!-- JQuery -->
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.3.0/jquery.form.min.js" integrity="sha384-qlmct0AOBiA2VPZkMY3+2WqkHtIQ9lSdAsAn5RUJD/3vA5MKDgSGcdmIv4ycVxyn" crossorigin="anonymous"></script>
<!--<script src="https://malsup.github.com/jquery.form.js"></script>-->

<script>
	/*function check() {
		
		var username = $('#password').val();
		var password = $('#username').val();
		
		var specialPattern = /[~!@\#$%<>^&*\()\-=+_\’]/;
		var blankPattern = /[\s]/g;
		
		if(specialPattern.test(username) || specialPattern.test(password)) {
			$('#input-errorMsg').html('특수문자는 입력하실 수 없습니다.');
		}
		if(blankPattern.test(username) || blankPattern.test(password)) {
			$('#input-errorMsg').html('공백은 입력하실 수 없습니다.');
		}
	} */
</script>

</head>

<body class="text-center" >

	<main class="form-signin">

	<form action="<c:url value="/login"/>" method="post">
	
		<!-- <img class="mb-4" src="/docs/5.0/assets/brand/bootstrap-logo.svg"
			alt="" width="72" height="57"> -->

		<h3 class="h3 mb-5 fw-normal"><span class="info-color bold-message">평점 확인</span></h3>
		
		<div class="input-label"><label for="password" class="visually-hidden text-muted mb-2"> 성명</label></div>
		<input type="text" pattern="[a-zA-Zㄱ-ㅎ가-힣ㅏ-ㅣ]+" oninvalid="setCustomValidity('공백없이 입력하세요.')" oninput="this.setCustomValidity('')" id="password" name="password" class="form-control" placeholder="성명" required autofocus> 
		
		<div class="input-label"><label for="username" class="visually-hidden text-muted mb-2 mt-3"> 면허번호</label></div>
		<input type="number" pattern="[0-9]+" oninvalid="setCustomValidity('숫자를 입력하세요.')" oninput="this.setCustomValidity('')" inputmode="numeric" id="username" name="username" class="form-control" placeholder="면허번호" required>
		
		<c:if test="${not empty errorMsg}">
			<div class="error-message mt-2" style="color:#ff0000;">${errorMsg}</div>
		</c:if>
		
		<div id="input-errorMsg" class="error-message mt-2" style="color:#ff0000;"></div>
		
		<%-- <c:if test="${not empty logoutMsg }">
			<div style="color:#0000ff;">${logoutMsg}</div>
		</c:if> --%>
		
		<!-- <div class="checkbox mb-3">
			<label> <input type="checkbox" value="remember-me">
				Remember me
			</label>
		</div> -->

		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			
		<button class="w-100 mt-5 btn btn-lg btn-info" type="submit">로그인</button> 
		
		<!-- <p class="mt-5 mb-3 text-muted">문의 ☎ 02-2038-7988</p> -->

	</form>

	<footer class="mt-5 foot-message text-muted">
		<p>
		<div class="foot-bold-message mb-2">평점 문의</div>
		Minjung Byun T. 02-1234-1234<br>
		</p>
		<div class="mt-4 foot-small-message">
			Copyright &copy; 2021. Minjung Byun. All rights reserved.
		</div>
	</footer>

	</main>

	
</body>
</html>
