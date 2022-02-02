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
        <div class="row">
            <form method="POST" action="/board/write?page=${pageNum}" enctype="multipart/form-data">
                <table class="table table-striped" style="text-align: center; border: 1px solid #ddddd">
                    <thead>
                        <tr>
                            <th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글쓰기 양식</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td width=100%>
                                <label>카테고리</label>
                                <select id="category" name="category">
                                    <option value="소식">소식</option>
                                    <option value="리뷰">리뷰</option>
                                    <option value="잡담">잡담</option>
                                    <c:if test="${principal.userEntity.role == 'ADMIN'}">
                                        <option value="공지">공지</option>
                                    </c:if>
                                </select>
                                &nbsp;&nbsp;

                                <input type="text" class="form-control" placeholder="글 제목" name="title" maxlength="50"/>
                            </td>
                        </tr>
                        <tr>
                            <td><textarea class="form-control" placeholder="글 내용" name="content" maxlength="2048" style="height: 350px"></textarea></td>
                        </tr>
                    </tbody>
                </table>

                <!--사진업로드-->
                <input multiple = "multiple" type="file" name="fileList" accept="image/gif, image/jpeg, image/png">(PNG/JPG 파일, 최대 10개까지 업로드 가능, 한 파일당 10MB 초과 불가)
                <br>
                <br>

                <button class="btn btn-primary form-control" id="write">글쓰기</button>
            </form>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/bootstrap.js"></script>

</body>
</html>