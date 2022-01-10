<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width", initial-scale="1">
    <link rel="stylesheet" href="/../resources/css/bootstrap.css">
    <title>Title</title>
</head>

<body>
    <nav class="navbar navbar-default">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                aria-expanded="false">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/main">JSP 게시판 웹사이트</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="/main">메인</a></li>
                <li><a href="/board">게시판</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                        data-toggle="dropdown" role="button" aria-haspopup="true"
                        aria-expanded="false">회원메뉴<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                       <li><a href="../../logout">로그아웃</a></li>
                       <li><a href="update" class="active">회원 정보 수정</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="col-lg-4"></div>
        <div class="col-lg-4">
            <div class="jumbotron" style="padding-top: 20px;">
                <form id="profileUpdate" onsubmit="update(${principal.userEntity.id}, event)">
                    <h3 style="text-align: center;">회원 정보 수정</h3>
                    <div class="form-group">
                        아이디
                        <input type="text" class="form-control" placeholder="아이디" name="username" id="username" maxlength="20" value="${principal.userEntity.username}" readonly="readonly">
                    </div>
                    <div class="form-group">
                        수정을 위해 현재 비밀번호 입력
                        <input type="password" class="form-control" placeholder="현재 비밀번호" name="password" maxlength="20" required="required">
                    </div>
                    <div class="form-group">
                        이메일
                        <input type="email" class="form-control" placeholder="이메일" name="userEmail" id="userEmail" maxlength="20" required="required" value="${principal.userEntity.userEmail}">
                        <div class="emailCheck" id="emailCheck"></div>
                    </div>
                    <div class="form-group">
                        이름
                        <input type="text" class="form-control" placeholder="이름" name="name" maxlength="20" required="required" value="${principal.userEntity.name}">
                    </div>

                    <button class="btn btn-primary form-control" id="regSubmit">정보 수정</button>
                </form>
                    <br/>
                    <button class="btn btn-primary form-control" onclick="location.href='/profile/${principal.userEntity.id}/pwUpdate'">비밀번호 변경</button>
            </div>
        </div>


    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/bootstrap.js"></script>
    <script src="/resources/js/update.js"></script>

<script type="text/javascript">
// 이메일 유효성 검사(true = 중복 / false = 중복x)
	$("#userEmail").blur(function() {
		var userEmail = $("#userEmail").val();

        if(userEmail == ""){
            $("#emailCheck").text("이메일을 입력해주세요");
            $("#emailCheck").css("color", "red");
            $("#regSubmit").attr("disabled", true);
        }

		$.ajax({
            type: "get",
            url: "/api/auth/emailCheck/" + userEmail,
            dataType: "json"
		}).done(res => {
		    if (res.data == true){
                $("#emailCheck").text("사용중인 이메일 입니다.");
                $("#emailCheck").css("color", "red");
                $("#regSubmit").attr("disabled", true);
            }else {
                $("#emailCheck").text("사용 가능한 이메일 입니다.");
                $("#emailCheck").css("color", "blue");
                $("#regSubmit").attr("disabled", false);
            }

		}).fail(error => {
            console.log("오류", error);
		});
	})
</script>
</body>
</html>