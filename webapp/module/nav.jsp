<%@ page import="com.ezen.board.service.BoardService" %>
<%@ page import="com.ezen.board.service.BoardServiceImpl" %>
<%@ page import="com.ezen.board.dto.Board" %>
<%@ page import="java.util.List" %><%--
  네비게이션 메뉴
  User: HJ
  Date: 2024-03-29
  Time: 오후 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    BoardService boardService = new BoardServiceImpl();
    List<Board> boardList = boardService.boardList();
    pageContext.setAttribute("boardList",boardList);
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
    <button class="btn btn-primary" id="menu-toggle">Toggle</button>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>



    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                <a class="nav-link" href="/index.jsp">홈 <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">메뉴1</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">메뉴2</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    게시판
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">

                    <c:forEach var="board" items="${boardList}">
                        <a class="dropdown-item" href="/board/list.jsp?boardId=${board.boardId}">${board.title}</a>

                    </c:forEach>
<%--                    아래처럼 하드코딩한거 위에서 동적으로 돌리는것으로 변경--%>
<%--                    <a class="dropdown-item" href="/board/list.jsp?boardId=10">자유게시판</a>--%>
<%--                    <a class="dropdown-item" href="#">만땅 자료실</a>--%>
<%--                    <a class="dropdown-item" href="#">공지사항</a>--%>
<%--                    <a class="dropdown-item" href="#">묻고 답하기</a>--%>
<%--                    <div class="dropdown-divider">묻고 답하기</div>--%>

<%--                    <a class="dropdown-item" href="#">토론방</a>--%>
<%--                    --%>
                </div>
            </li>

            <c:choose>
                <c:when test="${empty loginMember}">
                    <li class="nav-item">
                        <a class="btn btn-outline-primary" href="/member/register.jsp">회원가입</a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-primary" href="/member/login.jsp">로그인</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="btn btn-outline-primary"> ${loginMember.name}님 로그인 중 </a>
                        <a class="btn btn-outline-danger" href="/member/logout-action.jsp">로그아웃</a>
                    </li>
                </c:otherwise>
            </c:choose>

        </ul>
    </div>
</nav>
