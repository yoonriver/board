<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
                          <li><a href="join">회원 정보 보기</a></li>
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
                        <th colspan="2" style="background-color: #eeeeee; text-align: center;">글</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td width=100%>
                            <label>카테고리</label> ${writes.category} &nbsp;&nbsp;
                            <label>날짜&nbsp;</label>
                            <fmt:parseDate value="${writes.createDate}"
                               pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" /><br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>제목</label>
                            ${writes.title} &nbsp;&nbsp;
                            <label>추천수&nbsp;</label>0<br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>이름</label>
                            ${writes.userEntity.name}<br>
                            <label>조회수</label>
                            ${writes.count}
                        </td>
                    </tr>
                    <tr>
                        <td><b>내용</b> ${writes.content}</td>
                    </tr>
                </tbody>
            </table>
            <button class="btn btn-primary btn-sm form-control" onclick = "location.href = '/board'">글 목록</button>
            <button class="btn btn-primary btn-sm form-control" onclick = "">추천</button>
            <c:choose>
                <c:when test="${principal.userEntity.id == writes.userEntity.id}">
                    <button class="btn btn-primary btn-sm form-control" onclick = "location.href = '/board/modify/${writes.id}'">글 수정</button>
                    <button class="btn btn-primary btn-sm form-control" onclick = "writesDelete(${writes.id}, event)">글 삭제</button>
                </c:when>
            </c:choose>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/bootstrap.js"></script>
    <script src="/resources/js/modify.js"></script>


</body>
</html>