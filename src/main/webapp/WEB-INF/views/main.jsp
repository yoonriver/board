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
                <li><a class="active" href="/board/list?page=0&keyword=&option=&category=">게시판</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                   <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown" role="button" aria-haspopup="true"
                       <sec:authorize access="isAnonymous()">
                           aria-expanded="false">접속하기<span class="caret"></span>
                       </sec:authorize>
                       <sec:authorize access="isAuthenticated()">
                           aria-expanded="false">회원메뉴<span class="caret"></span>
                       </sec:authorize>
                   </a>
                   <ul class="dropdown-menu">
                       <sec:authorize access="isAnonymous()">
                          <li><a href="/auth/login">로그인</a></li>
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
            </div>
        </div>

    </div>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="resources/js/bootstrap.js"></script>

</body>
</html>