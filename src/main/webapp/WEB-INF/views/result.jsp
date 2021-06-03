<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>

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
</head>

<body class="text-center">

	<main class="form-signin">

	<div class="h3"><span class="bold-message">${user.username}(${user.password}) 님</span></div>
	<hr class="info-color">
	<div class="h3 mt-5 mb-3"><span class="bold-message info-color">${user.grade.total}</span></div>
	<div class="h3">${user.grade.detail}</div>
	
	<!-- <a class="nav-link mt-5 mb-3" href="javascript:document.getElementById('logout').submit()">로그아웃</a>
 -->
 
	<form id="logout" action="<c:url value="/logout" />" method="post">
		<input type="hidden" name="${_csrf.parameterName }"
			value="${_csrf.token }" />
		<button class="mt-5 mb-3 w-100 btn btn-lg btn-info" type="submit">로그아웃</button>
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
