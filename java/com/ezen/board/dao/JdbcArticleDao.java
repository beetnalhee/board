package com.ezen.board.dao;

import com.ezen.board.dto.Article;
import com.ezen.board.dto.Board;
import com.ezen.common.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcArticleDao implements ArticleDao {

    private ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

    // JdbcMemberDao 에서 복붙해오고 수정하기
    @Override
    public List<Board> findByBoardAll() throws SQLException {
        List<Board> list = new ArrayList<>(); // 목록이니까 리스트로 받는다

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT board_id, category, title, description")
                .append("  FROM board")
                .append("   ORDER BY board_id");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Board board = new Board();
                board.setBoardId(rs.getInt("board_id"));
                board.setCategory(rs.getInt("category"));
                board.setTitle(rs.getString("title"));
                board.setDescription(rs.getString("description"));
                list.add(board); // 행의 수만큼 리스트에 담기게 된다.
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list; // 담은 목록 반환
    }

    // 동적으로 바뀌는 부분만 바인딩 변수(?)로 바꾸기
    @Override
    public void createArticle(Article article) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO article (article_id, board_id, writer, title, content, passwd, group_no, level_no, order_no) ")
                .append("  VALUES (article_id_seq.NEXTVAL,?,?,?,?,?, article_id_seq.CURRVAL,0,0)");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, article.getBoardId());
            pstmt.setString(2, article.getWriter());
            pstmt.setString(3, article.getTitle());
            pstmt.setString(4, article.getContent());
            pstmt.setString(5, article.getPasswd());
            pstmt.executeUpdate();
        } finally {
            try {
                if (pstmt != null) pstmt.close(); // 종료는 역순으로
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void createComment(Article article) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("  INSERT INTO article (article_id, board_id, writer, title, content, passwd, group_no, level_no, order_no) ")
           .append("  VALUES   ( article_id_seq.NEXTVAL, 10, ?, ?, ?, ?, ?, ? + 1,")
           .append(" (SELECT MAX(order_no) + 1")
           .append(" FROM   article")
           .append(" WHERE  board_id = 10 AND group_no = ?))");

        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, article.getWriter());
            pstmt.setString(2, article.getTitle());
            pstmt.setString(3, article.getContent());
            pstmt.setString(4, article.getPasswd());
            pstmt.setInt(5,article.getGroupNo());
            pstmt.setInt(6,article.getLevelNo());
            pstmt.setInt(7,article.getGroupNo());
            pstmt.executeUpdate();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //----------------주석업뎃필요-------------------

    /**
     * 게시글 전체 목록 및 검색유형에 따른 게시글 목록 반환
     *
     * @param rowCount    테이블당 보여지는 행의 갯수
     * @param requestPage 사용자 요청 페이지
     * @param type        검색 유형
     * @param value       검색 값
     * @return 검색 목록
     * @throws SQLException
     */
    @Override
    public List<Article> findByAll(int rowCount, int requestPage, String type, String value) throws SQLException {
        List<Article> list = new ArrayList<>(); // 목록이니까 리스트로 받는다
        if (type != null && type.equals("")) {
            type = null;
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  article_id,  title,  writer,  regdate,  hitcount, group_no, level_no, order_no  ")
                .append(" FROM  (SELECT article_id,  CEIL(rownum / ?) request_page, title, writer,  TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI') regdate, hitcount, group_no, level_no, order_no ")
                .append(" FROM  (SELECT article_id, title, writer, regdate,  hitcount, group_no, level_no, order_no ")
                .append(" FROM   article  ")
                .append(" WHERE  board_id = 10 "); // 자유게시판

//          검색타입 및 값에 따른 동적 sql처리
        if (type != null) {
            switch (type) {
                case "t":  // 제목검색
                    value = "%" + value + "%";
                    sql.append(" AND title LIKE ?");
                    break;

                case "c": // 내용검색
                    value = "%" + value + "%";
                    sql.append(" AND content LIKE ?");
                    break;

                case "w": // 작성자 검색
                    value = "%" + value + "%";
                    sql.append(" AND writer LIKE ?");
                    break;

                case "tc": // 제목 + 내용 검색
                    value = "%" + value + "%";
                    sql.append(" AND title LIKE ? OR content LIKE ?");
                    break;

                case "tcw": // 제목 + 내용 + 작성자 검색
                    value = "%" + value + "%";
                    sql.append(" AND title LIKE ? OR content LIKE ? OR writer LIKE ?");
                    break;
            }

        }
        sql.append(" ORDER  BY group_no DESC, order_no ASC)) ")
                .append(" WHERE  request_page = ?");// 1페이지내에서 찾아줘라
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, rowCount); // 이기능도 구현해 보슈
            // 전체검색인경우
            if (type == null) {
                pstmt.setInt(2, requestPage);
            } else {
//                pstmt.setString(2, value);
//                pstmt.setInt(3, requestPage);

                //조건검색인경우
                switch (type) {
                    case "t": // 제목 검색
                    case "c": // 내용 검색
                    case "w": // 작성자 검색
                        pstmt.setString(2, value);
                        pstmt.setInt(3, requestPage);
                        break; // t,c,w는 조건1개씩이라 한방에
                    case "tc": // 제목 + 내용 검색
                        pstmt.setString(2, value);
                        pstmt.setString(3, value);
                        pstmt.setInt(4, requestPage);
                        break;

                    case "tcw": // 제목 내용 작성자 검색
                        pstmt.setString(2, value);
                        pstmt.setString(3, value);
                        pstmt.setString(4, value);
                        pstmt.setInt(5, requestPage);
                        break;
                }

            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Article article = new Article();
//                article.setBoardId(rs.getInt("board_id"));
                article.setArticleId(rs.getInt("article_id"));
                article.setTitle(rs.getString("title"));
                article.setWriter(rs.getString("writer"));
                article.setRegdate(rs.getString("regdate"));
                article.setHitCount(rs.getInt("hitcount"));
                article.setGroupNo(rs.getInt("group_no"));
                article.setLevelNo(rs.getInt("level_no"));
                article.setOrderNo(rs.getInt("order_no"));
                list.add(article); // 행의 수만큼 리스트에 담기게 된다.
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }


    // 검색조건에 따라 검색되는 페이징 갯수가 달라지는 기능구현
    @Override
    public int findByArticleCount(String type, String value) throws SQLException {
        int count = 0;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT count(*) count")
                .append("  FROM article");

        // 검색타입 및 값에 따른 동적 sql처리
        if (type != null) {
            switch (type) {
                case "t":  // 제목검색
                    value = "%" + value + "%";
                    sql.append(" WHERE title LIKE ?");
                    break;

                case "c": // 내용검색
                    value = "%" + value + "%";
                    sql.append(" WHERE content LIKE ?");
                    break;

                case "w": // 작성자 검색
                    value = "%" + value + "%";
                    sql.append(" WHERE writer LIKE ?");
                    break;

                case "tc": // 제목 + 내용 검색
                    value = "%" + value + "%";
                    sql.append(" WHERE title LIKE ? OR content LIKE ?");
                    break;

                case "tcw": // 제목 + 내용 + 작성자 검색
                    value = "%" + value + "%";
                    sql.append(" WHERE title LIKE ? OR content LIKE ? OR writer LIKE ?");
                    break;
            }


        }

        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            // 조건 검색인 경우
            if (type != null) {
                switch (type) {
                    case "t": // 제목 검색
                    case "c": // 내용 검색
                    case "w": // 작성자 검색
                        pstmt.setString(1, value);
                        break;
                    case "tc": // 제목 + 내용 검색
                        pstmt.setString(1, value);
                        pstmt.setString(2, value);
                        break;

                    case "tcw": // 제목 내용 작성자 검색
                        pstmt.setString(1, value);
                        pstmt.setString(2, value);
                        pstmt.setString(3, value);
                        break;
                }
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    //--------------------------------0402-----------------------------------------------------------------------------------
    @Override                         // articleId ? article_id ?
    public Article readContent(int articleId) throws SQLException {
        Article article = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT article_id, board_id, title, content, writer, regdate, order_no, level_no, group_no ")
                .append("  FROM article")
                .append("  WHERE  article_id = ? AND board_id = 10");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, articleId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                article = new Article();
                article.setArticleId(rs.getInt("article_id"));
                article.setBoardId(rs.getInt("board_id"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                article.setWriter(rs.getString("writer"));
                article.setRegdate(rs.getString("regdate"));
                article.setOrderNo(rs.getInt("order_no"));
                article.setLevelNo(rs.getInt("level_no"));
                article.setGroupNo(rs.getInt("group_no"));

            }
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (rs != null) rs.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return article; // 담은 목록 반환


    }


    @Override
    public void updateArticleHitCount(int articleId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE article ")
           .append("  SET hitcount = hitcount + 1")
           .append("  WHERE board_id = 10 AND article_id = ?");
        Connection con = connectionFactory.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, articleId);
            pstmt.executeQuery();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

// // 시퀀스 수정 (사용법 모름 )
//    public void test() throws SQLException {
//        StringBuilder sql = new StringBuilder();
//        sql.append(" SELECT article_id_seq.nextval from dual");
//        Connection con = connectionFactory.getConnection();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        try {
//            pstmt = con.prepareStatement(sql.toString());
//            pstmt.executeQuery();
//            System.out.println("실행");
//        } finally {
//            try {
//                if (pstmt != null) pstmt.close();
//                if (rs != null) rs.close();
//                if (con != null) con.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public static void main(String[] args) throws SQLException {
        ArticleDao articleDao = new JdbcArticleDao();

//        // 신규등록 간이 테스트
        Article article = new Article();
//        article.setBoardId(10);
//        article.setWriter("banana");
//        article.setTitle("살려주세요.");
//        article.setContent("월요병에걸렸습니다.");
//        article.setPasswd("1111");
//
//        articleDao.createArticle(article);
//        System.out.println("신규등록완료..");

//        System.out.println(articleDao.findByBoardAll());

//        페이지별 목록 조회 간이 테스트
//        int rowCount = 20;
//        int requestPage = 2;
//        String type = "t";
//        String value = "살려";
//        List<Article> list = articleDao.findByAll(rowCount, requestPage, type, value);
//        System.out.println("게시글 목록 갯수 :" + list.size());
//        for (Article article : list) {
//            System.out.println(article);

        // 전체검색
//        System.out.println(articleDao.findByArticleCount("w","salmon"));

        // 글상세보기
//        System.out.println(articleDao.readContent(3082));

//         // 시퀀스 수정
//        JdbcArticleDao dao = new JdbcArticleDao();
//        for (int i = 0; i <3600 ; i++) {
//            dao.test();
//        }
//        }


        articleDao.createComment(article);
        System.out.println("완료");


    }
}

