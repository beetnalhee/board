<%@ page import="com.ezen.member.dto.Member" %>
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

<%--자바코드가 최대한 없도록 노력해봅시다 ~ 위여러줄을 아래처럼 바꾸기 연습
jsp는 데이터 관련기능 졔외하고, 거의 다댐--%>

<%
    int boardId = 10;
    if(request.getParameter("boardId") !=null){
    boardId = Integer.parseInt(request.getParameter("boardId"));
    }
%>

<c:if test="${empty loginMember}">
    <c:set var="message" value="♥게시판 글쓰기는 로그인 후 사용가능합니다♥" scope="request"/>
    <c:set var="referer" value="/board/register.jsp" scope="request"/>
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

        <h3 class="mt-4">게시글 쓰기</h3>
        <hr>
        <form name="registerForm" action="register-action.jsp" method="post">
<%--          보드넘버는 눈에 안보이게 계속 넘겨줘야하므로 ..--%>
          <input type="hidden" name="boardId" value="${param.boardId}">
          <div class="form-group">
            <label>제목</label>
            <input type="text" class="form-control" name="title" >
          </div>
          <div class="form-group">
            <label>내용</label>
            <textarea class="form-control" rows="5" name="content"></textarea>
          </div>
          <div class="form-group">
            <label>작성자</label>
            <input type="text" class="form-control" name="writer" value="${loginMember.id}" readonly>
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
