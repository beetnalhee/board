package com.ezen.member.service;

import com.ezen.member.dao.JdbcMemberDao;
import com.ezen.member.dao.MemberDao;
import com.ezen.member.dto.Member;

import java.sql.SQLException;

/**
 * 회원관련한 비즈니스 메소드 정의
 *
 * MemberService 굳이 만드는 이유?
 */
public class MemberService {
    private MemberDao memberDao;

    public MemberService () {
        memberDao = new JdbcMemberDao();
    }


    //회원가입처리
    public Member registerMember(Member member) throws Exception {
        memberDao.create(member);
        return memberDao.findById(member.getId());
    }

    //로그인(회원인증)
    public Member login(String id, String passwd) throws SQLException {
        Member loginMember = null;
        boolean isMember =  memberDao.findByIdNPasswd(id,passwd);
        if(isMember){
            loginMember = memberDao.findById(id);
        }
        return loginMember;
    }

    //기타 등등

    public static void main(String[] args) throws SQLException {
        MemberService memberService = new MemberService();
        Member member = memberService.login("jelly4","1111");
        System.out.println(member);
    }
}
