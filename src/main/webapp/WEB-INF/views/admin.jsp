<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
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
	var cnt = 1;
	var checkCnt = 0;
	$.ajax({
	    type: "post",
	    url: "${pageContext.request.contextPath}/admin/all?${_csrf.parameterName}=${_csrf.token}",
	    data: {},
	    dataType :"json",
	    success : function(result){
	    	$.each(result.data, function(key, value) {
	    		if(value.username != "admin") {
	    			if(value.checked == true) checkCnt++;
					$('#resultBody').append('<tr' + (value.checked ? ' style="background:#E3E3E3;">' : '>') + '<th scope="row">' + cnt++ + '</th><td>' + value.username + '</td><td>' + value.password + '</td><td>' + value.grade.total + '</td><td>' + value.grade.detail + '</td>' + (value.checked ? '<td style="font-weight:700; color:#0DCAF0;">O</td>' : '<td style="font-weight:700; color:lightgray;">X</td>') + '</tr>');
	    		}
	    	})
	    	
	    	var percentage = (result.data.length == 1) ? 0 : checkCnt/(result.data.length-1)*100;
	    	$('#resultMsg').html(checkCnt + '/' + (result.data.length-1) + '명 확인 완료 (' + percentage.toFixed(2) + '%)');
	    },
	    error: function(e){
	    	console.log("resultBody에서 에러 발생");
	    }
	});
</script>

<script>
	var progress;  
	function check() {
		$('#progress').css('width', '0%');

		console.log("check() start");
		
		if($('#uploadMsg').length) {
			console.log("uploadMsg 삭제");
			$('div').remove('#uploadMsg');
		}
		
		$("#excelUploadForm").ajaxForm({
			type: "post",
            url: "${pageContext.request.contextPath}/admin/upload?${_csrf.parameterName}=${_csrf.token}",
            data: {},
            contentType: "text/plain",
            dataType :"json",
			beforeSend : function() {
				console.log("beforeSubmit");
				
				//$("#progress").html("0%");
				progress = setInterval(excelUploadProgress, 50);
			},
			success : function(result) {
				console.log(result.status);
				
				if(result.status == "success") {
					var cnt = 1;
					var checkCnt = 0;
					$('#progress').css('width','100%');
					$('#form-group').append('<div id="uploadMsg" class="error-message mt-2 mb-2" style="color:#0DCAF0;">파일 업로드를 완료했습니다</div>');
					
					if($('#resultBody').length) {
						console.log("resultBody 삭제");
						$('#resultBody').empty();
					}
					
					if($('#resultMsg').length) {
						console.log("resultMsg 삭제");
						//$('div').remove('#resultMsg');
						$('#resultMsg').html('');
					}
					
					$.each(result.data, function(key, value) {
						if(value.username != "admin") {
							if(value.checked == true) checkCnt++;
							$('#resultBody').append('<tr' + (value.checked ? ' style="background:#E3E3E3;">' : '>') + '><th scope="row">' + cnt++ + '</th><td>' + value.username + '</td><td>' + value.password + '</td><td>' + value.grade.total + '</td><td>' + value.grade.detail + '</td>' + (value.checked ? '<td style="font-weight:700; color:#0DCAF0;">O</td>' : '<td style="font-weight:700; color:lightgray;">X</td>') + '</tr>');
	            		}
					})
					
					var percentage = (result.data.length == 0) ? 0 : checkCnt/result.data.length*100;
	            	$('#resultMsg').html(checkCnt + '/' + result.data.length + '명 확인 완료 (' + percentage.toFixed(2) + '%)');
					
					console.log('upload success !');
					
					setTimeout(function() {
						$('#progress').css('width','100%');
						alert("파일 업로드를 완료했습니다");}, 800);
					
						 	
				} else if(result.status == "error") {
					$('#form-group').append('<div id="uploadMsg" class="error-message mt-2 mb-2" style="color:#ff0000;">잘못된 형식의 파일입니다</div>');
					
					//파일초기화
					var agent = navigator.userAgent.toLowerCase();
					if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
					    $("#file").replaceWith($("#file").clone(true));
					}else{
					    $("#file").val("");
					}
					
				} else if(result.status == "empty") {
					$('#form-group').append('<div id="uploadMsg" class="error-message mt-2 mb-2" style="color:#ff0000;">파일을 선택하세요</div>');
				}

				clearInterval(progress);
				excelUploadProgressClear();
			},
			error : function(request,status,error) {
				console.log("check()에서 에러 발생");
				console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				
				$('#form-group').append('<div id="uploadMsg" class="error-message mt-2 mb-2" style="color:#ff0000;">파일을 다시 선택하세요</div>');
				
				//파일초기화
				var agent = navigator.userAgent.toLowerCase();
				if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
				    $("#file").replaceWith($("#file").clone(true));
				}else{
				    $("#file").val("");
				}

				clearInterval(progress);
			}
		});
		//$("#excelUploadForm").submit();
	}
	
	function excelUploadProgress(){
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/admin/uploadProgress?${_csrf.parameterName}=${_csrf.token}",
            data: {},
            dataType :"text",
            success : function(resultData){
                if(resultData == -1){
                    //clearInterval(progress);    
                } else { 
	                console.log(resultData);
	                //$("#progress").html(resultData +"%");
	                $('#progress').css('width', resultData +'%');
	            }
            },
            error: function(e){
            	console.log("excelUploadProgress()에서 에러 발생");
            }
        });               
    }
	
	function excelUploadProgressClear(){
        
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/admin/uploadSuccess?${_csrf.parameterName}=${_csrf.token}",
            data: {},
            dataType :"text",
            success : function(resultData){
            	console.log("excelUploadProgressClear()");
            },
            error: function(e){
            	console.log("excelUploadProgressClear()에서 에러 발생");
            }
        });        
    }
	
	function showResultTable() {
		console.log("showResultTable()");
		
		if($('#result').is(':visible')) {
			$('#result').slideUp();
			$('#resultMenu').html('데이터베이스 펼치기 ▼');
		} else {
			$('#result').slideDown();
			$('#resultMenu').html('데이터베이스 접기 ▲');
		}
	}
	

</script>

</head>

<body class="text-center">

	<main class="form-signin">
	
	<%-- <c:if test="${not empty alertMsg}">
		<script>
			alert("${alertMsg}");
		</script>
	</c:if> --%>
	
	<sf:form id="excelUploadForm" action="${pageContext.request.contextPath}/admin/upload?${_csrf.parameterName}=${_csrf.token}" method="post" modelAttribute="fileBucket" enctype="multipart/form-data">
		<h3 class="h3 mb-4 fw-normal">파일 업로드</h3>
		
		<div class="form-group" id="form-group">
			<div class="small-message mb-2"><small>성명(텍스트), 면허번호(숫자), 평점(텍스트), 평점내역(텍스트) 형식의 엑셀 파일만 업로드 가능합니다.</small></div>
			
			<!-- progress bar -->
            <div class="progress mb-2">
                <div id="progress" class="progress-bar progress-bar-striped bg-info" role="progressbar" style="width:0%;" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
            </div>  
			
			<div class="input-label"><label for="file" class="visually-hidden text-muted mb-2"> 파일 선택</label></div>
			<sf:input path="file" type="file" id="file" name="file" class="form-control" accept=".xls, .xlsx" />
	     
	     	<%-- <c:if test="${not empty errorMsg}">
				<div class="error-message mt-2 mb-2" style="color:#ff0000;">${errorMsg}</div>
			</c:if> --%>
	     </div>
	    
	    <button class="w-100 btn btn-lg btn-secondary" onclick="check()">업로드</button>
  		
	</sf:form>
	
	<div class="mt-5 mb-5">
	<div id="resultMsg" class="result-text mb-3" style="color:#ff0000;"></div>
	<div id="resultMenu" class="text-info more-link" onclick="javascript:showResultTable()">데이터베이스 접기 ▲</div>
	<div id="result" class="mt-1 mb-3">
		<table id="resultTable" class="table table-hover table-sm mt-2">
			<thead id="resultHead">
				<tr class="table-info">
					<th scope="col">#</th>
					<th scope="col">이름</th>
					<th scope="col">면허번호</th>
					<th scope="col">평점</th>
					<th scope="col">평점 내역</th>
					<th scope="col">확인</th>
				</tr>
			</thead>
			<tbody id="resultBody">
				<script>
				      
				</script>
			</tbody>
		</table>
	</div>
	</div>

	<form id="logout" action="<c:url value="/logout" />" method="post">
		<input type="hidden" name="${_csrf.parameterName }"
			value="${_csrf.token }" />
		<button class="w-100 btn btn-lg btn-info" type="submit">로그아웃</button>
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
