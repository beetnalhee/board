<%@ page import="com.ezen.board.service.BoardService" %>
<%@ page import="com.ezen.board.service.BoardServiceImpl" %>
<%@ page import="com.ezen.board.dto.Article" %><%--
  댓글 글쓰기 처리
  User: HJ
  Date: 2024-4-03
  Time: 오후 4:18
  To change this template use File | Settings | File Templates.
--%>

<%--//서블릿. db처리해야한다 --%>

<%
    request.setCharacterEncoding("utf-8");
%>

<jsp:useBean id="comment" class="com.ezen.board.dto.Article" scope="request"/>
<jsp:setProperty name="comment" property="*"/>

<%--보드넘버는 보드서비스에 미포함이라 따로 써준다--%>
<%

    int boardId = Integer.parseInt(request.getParameter("boardId"));
    boardId = 10;

    BoardService boardService = new BoardServiceImpl();
    int articleId = Integer.parseInt(request.getParameter("articleId"));
    Article article = boardService.printArticle(articleId);
    request.setAttribute("article", article);


    String passwd = "1111";
    int groupNo = Integer.parseInt(request.getParameter("groupNo"));
    int orderNo = Integer.parseInt(request.getParameter("orderNo"));

    comment.setBoardId(boardId);
    comment.setArticleId(articleId);
    comment.setPasswd(passwd);
    comment.setGroupNo(article.getGroupNo());
    comment.setOrderNo(article.getOrderNo());

    boardService.writeComment(comment);
    response.sendRedirect("list.jsp?boardId=" + boardId);


%>

