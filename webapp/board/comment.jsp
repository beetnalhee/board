<%@ page import="com.ezen.member.dto.Member" %>
<%@ page import="com.ezen.board.service.BoardServiceImpl" %>
<%@ page import="com.ezen.board.service.BoardService" %>
<%@ page import="com.ezen.board.dto.Article" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<%--%>
<%--//    //로그인 사용자만이 보여주는 페이지--%>
<%--    Member loginMember = (Member) session.getAttribute("loginMember");--%>
<%--    if (loginMember == null){--%>
<%--        request.setAttribute("message","게시판 글쓰기는 로그인 후 사용가능합니다 ♥");--%>
<%--//        response.sendRedirect("/member/login.jsp");--%>
<%--//        위나. 포워드중에 하나 선택. 그러나 아래 jsp처럼 쓰도록 하자--%>
<%--        application.getRequestDispatcher("/member/login.jsp").forward(request,response);--%>
<%--        }--%>
<%--%>--%>


<%
    int boardId = 10;
    if(request.getParameter("boardId") !=null){
    boardId = Integer.parseInt(request.getParameter("boardId"));
    }
%>

<%
    //  String boardId = request.getParameter("boardId");
    String articleId = request.getParameter("articleId");
    BoardService boardService = new BoardServiceImpl();
    Article article = boardService.printArticle(Integer.parseInt(articleId));
    request.setAttribute("article", article);
%>

<c:if test="${empty loginMember}">
    <c:set var="message" value="♥댓글 글쓰기는 로그인 후 사용가능합니다♥" scope="request"/>
    <c:set var="referer" value="/board/comment.jsp?boardId=10&articleId=${article.articleId}&title=${article.title}" scope="request"/>
    <jsp:forward page="/member/login.jsp"/>
</c:if>



<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>JSP 실습</title>
  <!-- Bootstrap core CSS -->
  <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom styles for this template -->
  <link href="/css/simple-sidebar.css" rel="stylesheet">
</head>

<body>
  <!-- Wrapper Start -->
  <div class="d-flex" id="wrapper">
    
    <!-- Sidebar Start-->
    <jsp:include page="/module/sidebar.jsp"/>
    <!-- /#sidebar-wrapper End-->

    <!-- Page Content Start-->
    <div id="page-content-wrapper">

      <!-- Nav Start -->
      <jsp:include page="/module/nav.jsp"/>
       <!-- Nav End -->

        <!-- Contents Start -->
      <div class="container-fluid">

        <h3 class="mt-4">댓글 쓰기</h3>
        <hr>
        <form name="registerForm" action="comment-action.jsp?articleId=${article.articleId}" method="post">
<%--          보드넘버는 눈에 안보이게 계속 넘겨줘야하므로 ..--%>
          <input type="hidden" name="boardId" value=10>
          <input type="hidden" name="articleId" value="${article.articleId}">
          <input type="hidden" name="groupNo" value="${article.groupNo}">
          <input type="hidden" name="orderNo" value="${article.orderNo}">
          <input type="hidden" name="levelNo" value="${article.levelNo}">

<%--    <input type="hidden" name="articleId" value="${param.articleId}">--%>
<%--    <input type="hidden" name="groupNo" value="${param.groupNo}">--%>
<%--    <input type="hidden" name="orderNo" value="${param.orderNo}">--%>
<%--    <input type="hidden" name="levelNo" value="${param.levelNo}">--%>
          <div class="form-group">
            <label>제목</label>
            <input type="text" class="form-control" name="title" placeholder="[re : ${article.title}]">
          </div>
          <div class="form-group">
            <label>내용</label>
            <textarea class="form-control" rows="5" name="content"></textarea>
          </div>
          <div class="form-group">
            <label>작성자</label>
            <input type="text" class="form-control" name="writer" value="${loginMember.id}">
          </div>

          <button type="submit" class="btn btn-success">등 록</button>
        </form>
      </div>
      <!-- Contents End -->
    </div>
    <!-- /#page-content-wrapper End-->
      </div>
  <!-- /#wrapper End-->
  

  <!-- Bootstrap core JavaScript -->
  <script src="/vendor/jquery/jquery.min.js"></script>
  <script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Menu Toggle Script -->
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>
