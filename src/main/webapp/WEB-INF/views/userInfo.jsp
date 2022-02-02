<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width", initial-scale="1">
    <link rel="stylesheet" href="/../resources/css/bootstrap.css">
    <title>회원 정보 조회</title>
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
                       <sec:authorize access="isAnonymous()">
                           aria-expanded="false">접속하기<span class="caret"></span></a>
                       </sec:authorize>
                       <sec:authorize access="isAuthenticated()">
                           aria-expanded="false">회원메뉴<span class="caret"></span></a>
                       </sec:authorize>
                   <ul class="dropdown-menu">
                       <sec:authorize access="isAnonymous()">
                          <li class="active"><a href="/auth/login">로그인</a></li>
                          <li><a href="/auth/join">회원가입</a></li>
                       </sec:authorize>
                       <sec:authorize access="isAuthenticated()">
                          <li><a href="/logout">로그아웃</a></li>
                          <li><a href="/profile/${principal.userEntity.id}/update">회원 정보 수정</a></li>
                          <li><a href="/profile/own-board?page=0">내가 쓴 글</a></li>
                          <li><a href="/profile/own-comment?page=0">내가 쓴 댓글</a></li>
                          <c:if test="${principal.userEntity.role == 'ADMIN'}">
                             <li><a href="/user/list?page=0&keyword=&option=">회원 관리</a></li>
                          </c:if>
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
                <h3 style="text-align: center;">회원 정보 조회</h3>
                <div class="form-group">
                    아이디
                    <input  type="text" class="form-control" placeholder="아이디" name="username" id="username" maxlength="20" value="${user.username}" readonly="readonly">
                </div>
                <div class="form-group">
                    이메일
                    <input type="email" class="form-control" placeholder="이메일" name="userEmail" id="userEmail" maxlength="20" required="required" value="${user.userEmail}" readonly="readonly"></input>
                    <div class="emailCheck" id="emailCheck"></div>
                </div>
                <div class="form-group">
                    이름
                    <input type="text" class="form-control" placeholder="이름" name="name" maxlength="20" required="required" value="${user.name}" readonly="readonly">
                </div>

                <button class="btn btn-primary form-control" id="userDelete" onclick="userDelete(${user.id}, ${pageNum}, event)">회원 삭제</button>
                <br>
                <br>
                <a href="/user/list?page=0&keyword=${keyword}&option=${option}" class="btn btn-primary form-control">뒤로 가기</a>
            </div>
        </div>


    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/bootstrap.js"></script>
    <script src="/resources/js/userInfo.js"></script>

</body>
</html>