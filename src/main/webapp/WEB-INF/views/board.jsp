<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
                <li><a href="main.jsp">메인</a></li>
                <li><a class="active" href="/board">게시판</a></li>
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
                          <li class="active"><a href="auth/login">로그인</a></li>
                          <li><a href="auth/join">회원가입</a></li>
                       </sec:authorize>
                       <sec:authorize access="isAuthenticated()">
                          <li><a href="/logout">로그아웃</a></li>
                          <li><a href="profile/update">회원 정보 수정</a></li>
                       </sec:authorize>
                   </ul>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <table class="table table-striped" style="text-align: center; border: 1px solid #ddddd">
                <thead>
                    <tr>
                        <th style="background-color: #eeeee; text-align: center;">번호</th>
                        <th style="background-color: #eeeee; text-align: center;">제목</th>
                        <th style="background-color: #eeeee; text-align: center;">작성자</th>
                        <th style="background-color: #eeeee; text-align: center;">조회수</th>
                        <th style="background-color: #eeeee; text-align: center;">작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${writeList}" var="list">

                        <tr>
                            <td>${list.id}</td>
                            <td><a href="board/${list.id}">${list.title}</a></td>
                            <td>${list.userEntity.name}</td>
                            <td>${list.count}</td>
                            <td><fmt:parseDate value="${list.createDate}"
                                    pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="board/write" class="btn btn-primary pull-right">글쓰기</a>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/../resources/js/bootstrap.js"></script>

</body>
</html>