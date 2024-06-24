package com.ezen.member.dao;

import com.ezen.common.database.ConnectionFactory;
import com.ezen.member.dto.Member;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 회원 관련 관계형Database(RDB) 전담처리관련 클래스
 */
//DAO 패턴 적용한 모습, 인터페이스(MemberDao) 설정했음
public class JdbcMemberDao implements MemberDao {

    private ConnectionFactory connectionFactory;
 //
    public JdbcMemberDao(){
        connectionFactory = ConnectionFactory.getInstance();
    }


    // #공통.DB 연결 관련 설정 정보은 connectionfactory에서

    // 회원 테이블에 회원정보 입력(등록)
    public void create(Member member) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO member(member_id, password, name, email, picture)")
           .append(" VALUES (?, ?, ?, ?, ? )");
//        Connection con = ConnectionFactory.getInstance().getConnection(); 위에 선언함으로써 코드 줄이기
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPasswd());
            pstmt.setString(3, member.getName());
            pstmt.setString(4, member.getEmail());
            pstmt.setString(5, member.getPicture());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close(); // 종료는 역순으로
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 회원 아이디를 전달받아 회원 상세번호를 반환
    public Member findById(String id) throws SQLException {
        Member member = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT member_id, name, email, TO_CHAR(regdate, 'yyyy-MM-DD HH24:MI:SS') regdate, picture")
           .append(" FROM member")
           .append("  WHERE member_id = ?");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null; //ResultSet은 SELECT의 결과를 저장하는 객체
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                member = new Member();
                member.setId(rs.getString("member_id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setRegdate(rs.getString("regdate"));
                member.setPicture(rs.getString("picture"));
            }
        } finally {
            try {
                /* ResultSet, Statement, Connection이 null이 아니라면,
                즉 어떠한 데이터를 받아오는 등 프로그램에 의하여 사용되었다면,
                리소스 관리를 위해 반드시 종료 */

                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }return member;
    }

    // 회원 아이디와 비밀번호를 전달받아 인증 결과 반환
    public boolean findByIdNPasswd(String id, String passwd) throws SQLException {
        boolean isMember = false;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT member_id, name, email")
           .append(" FROM member")
           .append("  WHERE member_id = ? AND password = ?");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null; //ResultSet은 SELECT의 결과를 저장하는 객체
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, id);
            pstmt.setString(2, passwd);
            rs = pstmt.executeQuery();
            isMember = rs.next(); // 행이 있으면 true 없으면 false
        } finally {
            try {
                /* ResultSet, Statement, Connection이 null이 아니라면,
                즉 어떠한 데이터를 받아오는 등 프로그램에 의하여 사용되었다면,
                리소스 관리를 위해 반드시 종료 */

                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }return isMember;
    }

    // 회원 전체 목록 반환
    @Override
    public List<Member> findByAll() throws SQLException {
        List<Member> list = new ArrayList<>(); // 목록이니까 리스트로 받는다
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT member_id, name, email, TO_CHAR(regdate, 'yyyy-MM-DD HH24:MI:SS') regdate")
           .append(" FROM member");
//           .append("  WHERE member_id = ?"); 전체목록이니까 조건 필요없음
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null; //ResultSet은 SELECT의 결과를 저장하는 객체
        try {
            pstmt = con.prepareStatement(sql.toString());
//            pstmt.setString(1, id); 바인딩 될게 없으니 삭제
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getString("member_id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setRegdate(rs.getString("regdate"));
                list.add(member);
            }
        } finally {
            try {
                /* ResultSet, Statement, Connection이 null이 아니라면,
                즉 어떠한 데이터를 받아오는 등 프로그램에 의하여 사용되었다면,
                리소스 관리를 위해 반드시 종료 */

                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }return list; // 담은 목록 반환
    }

    // 여기 메인은 임시테스트용임
    public static void main(String[] args) throws Exception {
        MemberDao memberDao = new JdbcMemberDao();
//        Member member = new Member("moNday","1111","월요일","monday@gmail.com");
//        memberDao.create(member);
//        System.out.println("회원가입 완료...");

//          Member member =  memberDao.findById("mango");
//        System.out.println(member);

        boolean isMember = memberDao.findByIdNPasswd("mango","1111");
        System.out.println(isMember);
//        DataSource dataSource = createDataSource();
//        Connection connection = dataSource.getConnection();
        List<Member>list = memberDao.findByAll();
        for (Member member : list) {
            System.out.println(member);
        }
        }





}
