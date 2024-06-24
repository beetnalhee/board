package com.ezen.board.dao;

import com.ezen.board.dto.Article;
import com.ezen.board.dto.Board;

import java.sql.SQLException;
import java.util.List;

/**
 * 신규글쓰기 dao
 * 각 데이터에 접근
 */
public interface ArticleDao {
    // BoardDao에 만들어야 되는 메소드 ...
    public List<Board> findByBoardAll() throws SQLException;
    public void createArticle(Article article) throws SQLException;

    public void createComment(Article article) throws SQLException;


    public List<Article> findByAll(int rowCount, int requestPage, String type, String value) throws SQLException;

    public int findByArticleCount(String type, String value) throws SQLException;

//--------------------------------0402--------------------------------------

    // 게시글 읽기
    public Article readContent(int articleId) throws SQLException;
    // int boardId 필요시 추가하기

    //조회수
    public void updateArticleHitCount(int articleId) throws SQLException;


}
