<%@ page import="com.ezen.board.service.BoardService" %>
<%@ page import="com.ezen.board.service.BoardServiceImpl" %>
<%@ page import="com.ezen.board.dto.Article" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ezen.common.web.PageParams" %>
<%@ page import="com.ezen.common.web.Pagination" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%--%>
<%--//  int boardId = Integer.parseInt(request.getParameter("boardId"));--%>

<%
// 사용자 요청 게시판-자유게시판 번호
  int boardId = 10;
  if(request.getParameter("boardId")!=null){
    boardId = Integer.parseInt(request.getParameter("boardId"));
  }
// 사용자 요청 페이지 번호
  int requestPage = 1;
  if(request.getParameter("page")!=null){
    requestPage = Integer.parseInt(request.getParameter("page"));
  }
  // 테이블당 보여지는 행의 갯수
  int rowCount = 15;
  if(request.getParameter("count")!=null){
    rowCount = Integer.parseInt(request.getParameter("count"));
  }

  // 페이지에 보여주는 페이지 번호수
  int pageSize = 10;

  // 사용자 검색 유형
  String searchType = request.getParameter("type"); //null, "", "t","c" ...

  //사용자 검색 값
  String searchValue = request.getParameter("value");

//  EL사용하려면 request객체에 저장
  BoardService boardService = new BoardServiceImpl();
    List<Article> list = boardService.articleList(rowCount, requestPage, searchType, searchValue);
  request.setAttribute("list",list);

  // 페이징 처리를 위한 테이블 행의 갯수
  int tableRowCount = boardService.getArticleCount(searchType,searchValue);

  PageParams params = new PageParams(rowCount, pageSize, requestPage, tableRowCount);
  Pagination pagination = new Pagination(params);
  request.setAttribute("pagination", pagination);

%>


<%--jsp 로해도 되는데, c로 할 수도 있다~ (연습)--%>
<c:url var="register" value="register.jsp">
  <c:param name="boardId" value="${param.boardId}" />
</c:url>

<%--화면에 찍어보기 디버깅용--%>
<%--<%=list%>--%>
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

        <h3 class="mt-4">자유게시판 목록
          <span>
<%--            <a href="register.jsp?boardId=${param.boardId}" class="btn btn-sm btn-success">게시글 쓰기</a>--%>
            <a href="${register}" class="btn btn-sm btn-success">게시글 쓰기</a>
          </span>
        </h3>

        <hr>
        <%--액션을 아예 안쓰면 현재페이지 요청이다--%>
<%--        <form action="#" id="searchForm">--%>
        <form  id="searchForm">
          <div class="input-group">
            <input type="hidden" name="page" value="1">
            <div class="input-group-prepend">
              <select class="custom-select" name="type">
                <option selected value="">----- 검색유형 -----</option>
                <option value="t">제목</option>
                <option value="c">내용</option>
                <option value="w">작성자</option>
                <option value="tc">제목 + 내용</option>
                <option value="tcw">제목 + 내용 + 작성자</option>
              </select>
            </div>
            <input type="search" class="form-control" name="value">
            <div class="input-group-append" id="button-addon4">
              <button class="btn btn-success btn-search" type="submit">검색</button>
            </div>
          </div>
        </form>

        <table class="table table-sm table-striped">
          <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>등록일자</th>
          </tr>
          </thead>

          <tbody>
          <c:forEach var="article" items="${list}" varStatus="loop" >
            <tr>
              <td>${pagination.params.rowCount -((pagination.params.requestPage-1) * pagination.params.elementSize) -loop.index}</td>
              <td>
                <c:forEach begin="0" end="${article.levelNo}" step="1" >
            <img src="/img/level.gif" style="width: 10px; height: 10px;">
                </c:forEach>
                <c:if test="${article.levelNo !=0}">
            <img src="/img/re.gif">
                </c:if>
                <a href="read.jsp?boardId=10&articleId=${article.articleId}">${article.title}</a>
              </td>
              <td>${article.writer}</td>
              <td>${article.hitCount}</td>
              <td>${article.regdate}</td>
            </tr>
            </c:forEach>

            <%--          <tr>--%>
<%--            <td>253</td>--%>
<%--            <td>--%>
<%--              <img src="/img/level.gif" style="width: 10px; height: 10px;">--%>
<%--              <img src="/img/re.gif">--%>
<%--              <a href="read.jsp">댓글 제목입니다.</a>--%>
<%--            </td>--%>
<%--            <td>홍길동</td>--%>
<%--            <td>1</td>--%>
<%--            <td>2023-05-05</td>--%>
<%--          </tr>--%>
<%--          <tr>--%>
<%--            <td>252</td>--%>
<%--            <td>--%>
<%--              <img src="/img/level.gif" style="width: 10px; height: 10px;">--%>
<%--              <img src="/img/level.gif" style="width: 10px; height: 10px;">--%>
<%--              <img src="/img/re.gif">--%>
<%--              <a href="read.jsp">대댓글 제목입니다.</a>--%>
<%--            </td>--%>

          </tbody>

        </table>


        <ul class="pagination h-100 justify-content-center align-items-center">

               <c:if test="${pagination.showFirst}">
          <li class="page-item"><a class="page-link" href="?page=&type=${param.type}&value=${param.value}">처음으로</a></li>
          </c:if>

          <c:if test="${pagination.showPrevious}">
          <li class="page-item"><a class="page-link" href="?page=${pagination.previousStartPage}&type=${param.type}&value=${param.value}">이전목록</a></li>
          </c:if>

          <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
            <c:url var="list" value="list.jsp" scope="request">
              <c:param name="page" value="${i}"/>
              <c:param name="type" value="${param.type}"/>
              <c:param name="value" value="${param.value}"/>
            </c:url>
           <li class="page-item
            <c:if test="${i == pagination.params.requestPage}">active</c:if>">
            <a class="page-link" href="${list}">${i}</a>
            </li>
          </c:forEach>


       <c:if test="${pagination.showNext}">
          <li class="page-item"><a class="page-link" href="?page=${pagination.nextStartPage}&type=${param.type}&value=${param.value}">다음목록</a></li>
       </c:if>

         <c:if test="${pagination.showLast}">
          <li class="page-item"><a class="page-link" href="?page=${pagination.totalPages}&type=${param.type}&value=${param.value}">끝으로</a></li>
         </c:if>
        </ul>
      </div>
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
      e.preventDefault();55
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>
