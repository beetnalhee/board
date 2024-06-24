<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.ezen.member.dto.Member" %>
<%@ page import="com.ezen.member.service.MemberService" %>

<%request.setCharacterEncoding("utf-8");%>
<jsp:useBean id="member" class="com.ezen.member.dto.Member" scope="session"/>
<jsp:setProperty name="member" property="*"/>
<jsp:setProperty name="member" property="picture" value="default.jpg"/>

<%
MemberService memberService = new MemberService();
Member registerMember = memberService.registerMember(member);
//// 현재 요청한 브라우저에 해당하는 세션(장부) (forward 하면 이중등록되니까 하지말고)
session.setAttribute("member", registerMember);
%>
<c:redirect url="/member/result.jsp"/>

