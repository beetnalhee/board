package com.ezen.member.dao;



import com.ezen.member.dto.Member;

import java.sql.SQLException;
import java.util.List;

/**
 * 회원 관련 Database 처리 명세(역할)
 */
public interface MemberDao {
    // 회원가입
    public void create(Member member) throws Exception;

    //회원 상세 정보 반환
    public Member findById(String id) throws SQLException;

    // 회원 아이디와 비밀번호를 전달받아 인증 결과 반환
    public boolean findByIdNPasswd(String id, String passwd) throws SQLException;

    // 회원 전체 목록 반환 (sql예외는 무조건 일어나기
    // 마련이라 미리 지정)
    public List<Member> findByAll() throws SQLException;


    }
