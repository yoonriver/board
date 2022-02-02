<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width", initial-scale="1">
    <link rel="stylesheet" href="/../resources/css/bootstrap.css">
    <title>내가 쓴 댓글</title>
    <style>
        a.title:visited {color: silver; text-decoration: none;}
        a.title:link {color: blue; text-decoration: none;}
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
                       <sec:authorize access="isAnonymous()">
                           aria-expanded="false">접속하기<span class="caret"></span></a>
                       </sec:authorize>
                       <sec:authorize access="isAuthenticated()">
                           aria-expanded="false">회원메뉴<span class="caret"></span></a>
                       </sec:authorize>
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
    <thead>
        <table class="table table-striped" style="text-align: center; border: 1px solid #ddddd">
            <tr>
                <c:url value="/board/list" var="news">
                    <c:param name="page" value="0" />
                    <c:param name="category" value="소식" />
                </c:url>
                <c:url value="/board/list" var="review">
                    <c:param name="page" value="0" />
                    <c:param name="category" value="리뷰" />
                </c:url>
                <c:url value="/board/list" var="chat">
                    <c:param name="page" value="0" />
                    <c:param name="category" value="잡담" />
                </c:url>
                <th style="text-align:center;">내가 쓴 댓글</th>
            </tr>
        </table>
    </thead>
    <div class="container">
        <div class="row">
            <table class="table table-striped" style="text-align: center; border: 1px solid #ddddd">
                <thead>
                    <tr>
                        <th style="background-color: #eeeee; text-align: center;">글제목</th>
                        <th style="background-color: #eeeee; text-align: center;">내가 쓴 댓글</th>
                        <th style="background-color: #eeeee; text-align: center;">작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- 글 목록 -->
                    <c:forEach items="${commentList.content}" var="list">
                        <tr>
                            <td><a href="/board/${list.writeEntity.id}?page=${pageNum}&keyword=${keyword}&option=${option}&category=${category}" class="title">${list.writeEntity.title}</a></td>
                            <td>${list.content}</td>
                            <td><fmt:parseDate value="${list.createDate}"
                                    pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                                <fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${parsedDateTime}" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <c:set var="page" value="${pageNum+1}" />
            <c:set var="startNum" value="${page-(page-1)%5 }"/> <!-- pager 작성 공식 -->
            <c:set var="lastNum" value="${Math.ceil(page / 5) * 5}"/>
            <c:set var="totalPage" value="${writeList.totalPages}"/>

                <ul class="center">
                    <c:if test="${startNum > 1}">
                        <a href="/profile/own-comment?page=${(startNum-5)-1}&keyword=${keyword}&option=${option}&category=${category}" class="btn btn-prev" >이전</a>
                    </c:if>


                    <c:forEach var="i" begin="${startNum}" end="${lastNum}">
                        <c:if test="${i <= totalPage}">
                            <a href="/profile/own-comment?page=${i-1}&keyword=${keyword}&option=${option}&category=${category}" <c:if test="${page == i}">style="color:orange;"</c:if>>${i}</a>
                        </c:if>
                    </c:forEach>

                    <c:if test="${lastNum < totalPage}">
                        <a href="/profile/own-comment?page=${(startNum+5)-1}&keyword=${keyword}&option=${option}&category=${category}" class="btn btn-prev" >다음</a>
                    </c:if>

                    <!-- 검색 form -->
                    <form action="/profile/own-comment" method="GET">
                        <div>
                            <select id="option" name="option">
                                <option value="content" <c:if test="${option == 'content'}">selected</c:if>>내용</option>
                            </select>
                            <input name="keyword" type="text" placeholder="검색어를 입력해주세요" value="${keyword}">
                            <input name="page" type="hidden" value=0>
                            <input name="category" type="hidden" value="${category}">
                            <button class="btn btn-primary">검색하기</button>
                            <a href="/profile/own-comment?page=0&keyword=&option=&category=" class="btn btn-primary">전체 글 보기</a>
                        </div>
                    </form>
                </ul>


            <a href="/board/write?page=0" class="btn btn-primary pull-right">글쓰기</a>
        </div>
    </div>


    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/../resources/js/bootstrap.js"></script>

</body>
</html>