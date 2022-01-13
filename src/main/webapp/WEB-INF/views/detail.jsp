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
                          <li><a href="profile/${principal.userEntity.id}/update">회원 정보 수정</a></li>
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
                        <th colspan="2" style="background-color: #eeeeee; text-align: center;">게시글</th>
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
                            ${writes.userEntity.name}
                            <label>조회수</label>
                            ${writes.count}
                        </td>
                    </tr>
                    <tr>
                        <td><b>내용</b> ${writes.content}</td>
                    </tr>
                </tbody>
            </table>
            <button class="btn btn-primary btn-sm" onclick = "">추천</button>
            <button class="btn btn-primary btn-sm " onclick = "location.href = '/board'">글 목록</button>
            <c:choose>
                <c:when test="${principal.userEntity.id == writes.userEntity.id}">
                    <button class="btn btn-primary btn-sm" onclick = "location.href = '/board/modify/${writes.id}'">글 수정</button>
                    <button class="btn btn-primary btn-sm" onclick = "writesDelete(${writes.id}, event)">글 삭제</button>
                </c:when>
            </c:choose>
        </div>
    </div>
    <br>
    <br>
    <div class="container">
        <div class="row">
            <table class="table table-striped" style="text-align: center; border: 1px solid #ddddd">
                <thead>
                    <tr>
                        <th colspan="2" style="background-color: #eeeeee; text-align: center;">댓글</th>
                    </tr>
                </thead>
                <tbody style="text-align: left">
                    <c:forEach items="${commentList}" var="comment">
                        <tr>
                            <td>
                                <label>이름</label> ${comment.userEntity.name}
                                &nbsp;&nbsp;<label>추천수&nbsp;</label>0&nbsp;&nbsp; <button class="btn-primary btn-sm" onclick = "">추천</button>
                                &nbsp<button class="btn-primary btn-sm" onclick="location.href='/comment/reply/${comment.id}'">대댓글</button>&nbsp;
                                <c:choose>
                                    <c:when test="${principal.userEntity.id == comment.userEntity.id}">
                                        <button class="btn-primary btn-sm" onclick="location.href='/comment/modify/${comment.id}'">댓글 수정</button>&nbsp;
                                        <button class="btn-primary btn-sm" onclick="commentDelete(${comment.id}, ${writes.id}, event)">댓글 삭제</button>
                                    </c:when>
                                </c:choose>
                                <br>
                                <label>내용</label> ${comment.content}
                                <br>
                                <br>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <br>
    <br>
    <div class="container">
            <div class="row">
                <form id="comment" onsubmit="comment(${writes.id}, event)">
                    <table class="table table-striped" style="text-align: center; border: 1px solid #ddddd">
                        <thead>
                            <tr>
                                <th colspan="2" style="background-color: #eeeeee; text-align: center;">댓글 작성</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><textarea class="form-control" placeholder="댓글" name="content" maxlength="2048" style="height: 100px"></textarea></td>
                            </tr>
                        </tbody>
                    </table>
                    <button class="btn btn-primary form-control">댓글 쓰기</button>
                </form>
            </div>
        </div>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/bootstrap.js"></script>
    <script src="/resources/js/comment.js"></script>
    <script src="/resources/js/modify.js"></script>

</body>
</html>