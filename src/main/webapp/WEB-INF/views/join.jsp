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
            <a class="navbar-brand" href="/main">스프링 + JPA 게시판</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="/main">메인</a></li>
                <li><a class="active" href="/board/list?page=0&keyword=&option=&category=">게시판</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                   <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown" role="button" aria-haspopup="true"
                       <sec:authorize access="isAnonymous()">
                           aria-expanded="false">접속하기<span class="caret"></span></a>
                       </sec:authorize>
                       <sec:authorize access="isAuthenticated()">
                           aria-expanded="false">회원메뉴<span class="caret"></span></a>
                       </sec:authorize>
                   <ul class="dropdown-menu">
                       <sec:authorize access="isAnonymous()">
                          <li><a href="/auth/login">로그인</a></li>
                          <li class="active"><a href="/auth/join">회원가입</a></li>
                       </sec:authorize>
                       <sec:authorize access="isAuthenticated()">
                          <li><a href="/logout">로그아웃</a></li>
                          <li><a href="/profile/${principal.userEntity.id}/update">회원 정보 수정</a></li>
                       </sec:authorize>
                   </ul>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <div class="col-lg-4"></div>
        <div class="col-lg-4">
            <div class="jumbotron" style="padding-top: 20px;">
                <form method="POST" action="/auth/join">
                    <h3 style="text-align: center;">회원가입 화면</h3>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="아이디" name="username" id="username" maxlength="20" >
                        <div class="idCheck" id="idCheck"></div>
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" placeholder="비밀번호" name="password" maxlength="20" required="required">
                    </div>
                    <div class="form-group">
                                            <input type="email" class="form-control" placeholder="이메일" name="userEmail" id="userEmail" maxlength="20" required="required">
                                            <div class="emailCheck" id="emailCheck"></div>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="이름" name="name" maxlength="20" required="required">
                    </div>
                    <button class="btn btn-primary form-control" id="regSubmit">회원가입</button>
                </form>
            </div>
        </div>

    </div>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/../resources/js/bootstrap.js"></script>


<script type="text/javascript">
// 아이디 유효성 검사(true = 중복 / false = 중복x)
	$("#username").blur(function() {
		var username = $("#username").val();
		if(username == "") {
            $("#idCheck").text("아이디를 입력해주세요");
            $("#idCheck").css("color", "red");
            $("#regSubmit").attr("disabled", true);
        }

		$.ajax({
            type: "get",
            url: "/api/auth/idCheck/" + username,
            dataType: "json"

		}).done(res => {
		    if (res.data == true){
                $("#idCheck").text("사용중인 아이디 입니다.");
                $("#idCheck").css("color", "red");
                $("#regSubmit").attr("disabled", true);
            }else {
                $("#idCheck").text("사용 가능한 아이디 입니다.");
                $("#idCheck").css("color", "blue");
                $("#regSubmit").attr("disabled", false);

                if(username.length > 20 || username.length < 2) {
                    $("#idCheck").text("아이디는 2자 이상 20자 이내로 해주세요.");
                    $("#idCheck").css("color", "red");
                    $("#regSubmit").attr("disabled", true);
                }

            }

		}).fail(error => {
            console.log("오류", error);
		});
	});

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
	});
</script>


</body>
</html>