<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
                        aria-expanded="false">접속하기<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li class="active"><a href="auth/login">로그인</a></li>
                        <li><a href="join">회원가입</a></li>
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
                        <input type="text" class="form-control" placeholder="아이디" name="userId" id="userId" maxlength="20" >
                        <div class="idCheck" id="idCheck"></div>
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" placeholder="비밀번호" name="userPassword" maxlength="20" required="required">
                    </div>
                    <div class="form-group">
                                            <input type="email" class="form-control" placeholder="이메일" name="userEmail" id="userEmail" maxlength="20" required="required">
                                            <div class="emailCheck" id="emailCheck"></div>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="이름" name="userName" maxlength="20" required="required">
                    </div>
                    <div class="form-group" style="text-align: center;">
                        <div class="btn-group" data-toggle="buttons">
                            <label class="btn btn-primary active">
                                <input type="radio" name="userGender" autocomplete="off" value="남자" checked>남자
                            </label>
                            <label class="btn btn-primary">
                                <input type="radio" name="userGender" autocomplete="off" value="여자">여자
                            </label>
                        </div>
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
	$("#userId").blur(function() {
		var userId = $("#userId").val();

		if(userId == ""){
            $("#idCheck").text("아이디를 입력해주세요");
            $("#idCheck").css("color", "red");
            $("#regSubmit").attr("disabled", true);
        }

		$.ajax({
            type: "get",
            url: "/api/auth/idCheck/" + userId,
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



                if(userId.length > 20 || userId.length < 2) {
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
	})
</script>


</body>
</html>