<%@ page import="com.ezen.board.service.BoardService" %>
<%@ page import="com.ezen.board.service.BoardServiceImpl" %><%--
  게시글 글쓰기 처리
  User: HJ
  Date: 2024-03-29
  Time: 오후 4:18
  To change this template use File | Settings | File Templates.
--%>

<%--//서블릿. db처리해야한다 --%>

<%
    request.setCharacterEncoding("utf-8");
%>

<%--useBean 으로 변경! --%>
<%--요청이 오면 article객체가 만들어진다. --%>
<%--parameter 이름과 같아야 한다--%>
<jsp:useBean id="article" class="com.ezen.board.dto.Article" scope="request"/>
<jsp:setProperty name="article" property="*"/>

<%--보드넘버는 보드서비스에 미포함이라 따로 써준다--%>
<%
//    int boardId = Integer.parseInt(request.getParameter("boardId"));
    int boardId =10;
    String passwd = "1111";
    article.setBoardId(boardId);
    article.setPasswd(passwd);

    BoardService boardService = new BoardServiceImpl();
    boardService.writeArticle(article);

    response.sendRedirect("list.jsp?boardId="+boardId);
%>

