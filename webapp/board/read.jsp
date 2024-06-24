<%@ page import="com.ezen.board.service.BoardService" %>
<%@ page import="com.ezen.board.service.BoardServiceImpl" %>
<%@ page import="com.ezen.board.dto.Article" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
//  String boardId = request.getParameter("boardId");
  String articleId = request.getParameter("articleId");
  BoardService boardService = new BoardServiceImpl();
  Article article = boardService.printArticle(Integer.parseInt(articleId));
  request.setAttribute("article", article);
%>


<c:url var="comment" value="comment.jsp">
  <c:param name="boardId" value="10"/>
</c:url>
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
  <div class="d-flex" id="wrapper">
    
    <!-- Sidebar -->
    <jsp:include page="/module/sidebar.jsp"/>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

      <!-- Nav Start -->
      <jsp:include page="/module/nav.jsp"/>
      <!-- Nav End -->

      <div class="container-fluid">
        <h3 class="mt-4">게시글 상세</h3>
        <hr>
        <div class="form-group">
          <label>제목</label>
          <input type="text" class="form-control" name="title" value="${article.title}" readonly>
        </div>
        <div class="form-group">
          <label>내용</label>
          <textarea class="form-control" name="content" rows="5" readonly >${article.content}</textarea>
        </div>
        <div class="form-group">
          <label>작성자</label>
          <input type="text" class="form-control" name="writer" value="${article.writer}" readonly>
        </div>
        <div class="form-group">
          <label>등록일자</label>
          <input type="text" class="form-control" name="regdate" value="${article.regdate}" readonly>
        </div>

        <a href="comment.jsp?boardId=10&articleId=${article.articleId}&groupNo=${article.groupNo}&levelNo=${article.levelNo}&orderNo=${article.orderNo}" class="btn btn-primary">댓글쓰기</a>
        <a href="list.jsp" class="btn btn-info">목록</a>
        <a href="#" class="btn btn-warning">수정</a>
      </div>
    </div>
    <!-- /#page-content-wrapper -->
  </div>
  <!-- /#wrapper -->

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
