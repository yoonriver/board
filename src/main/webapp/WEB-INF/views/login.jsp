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
    <style type="text/css">
    #loginCheck {
        color : red;
    }
    img {
        display : block;
        margin : auto;
    }
    #find {
        width: 100px;
        margin: auto;
        text-align: center;
    }
    </style>
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
                <li><a class="active" href="/board/list?page=0&keyword=&option=&category=">게시판</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                   <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">접속하기<span class="caret"></span></a>
                   <ul class="dropdown-menu">
                      <li class="active"><a href="/auth/login">로그인</a></li>
                      <li><a href="/auth/join">회원가입</a></li>
                   </ul>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="col-lg-4"></div>
        <div class="col-lg-4">
            <div class="jumbotron" style="padding-top: 20px;">
                <form method="POST" action="/auth/login">
                    <h3 style="text-align: center;">로그인 화면</h3>
                    <div class="loginStatus" id="loginStatus" style="color:red;">${loginStatus}</div>
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="아이디" name="username" maxlength="20" />
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" placeholder="비밀번호" name="password" maxlength="20" />
                    </div>
                    <input type="checkbox" name="remember-me"> 자동 로그인
                    <button class="btn btn-primary form-control">로그인</button>
                </form>
                    <br/>
                    <button class="btn btn-primary form-control" onclick="location.href='/auth/join'">회원가입</button>
                    <br/>
                    <br/>
                    &nbsp;&nbsp;&nbsp;
                    <button id="find" class="btn btn-success btn-sm" onclick="location.href='/auth/find/id'">ID 찾기</button>
                    <button id="find" class="btn btn-success btn-sm" onclick="location.href='/auth/find/pw'">비밀번호 찾기</button>
                    <br/>
                    <br/>
                    <a href="https://kauth.kakao.com/oauth/authorize?client_id=cd71d24ba09df178e17c6c59fa8289c5&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code"><img src="/../resources/image/kakao_login_button.png"></a>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/../resources/js/bootstrap.js"></script>

</body>
</html>