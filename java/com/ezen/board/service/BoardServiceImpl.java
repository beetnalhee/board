package com.ezen.board.service;

import com.ezen.board.dao.ArticleDao;
import com.ezen.board.dao.JdbcArticleDao;
import com.ezen.board.dto.Article;
import com.ezen.board.dto.Board;

import java.sql.SQLException;
import java.util.List;

public class BoardServiceImpl implements BoardService{
        ArticleDao articleDao = new JdbcArticleDao();

    /**
     * 게시판 목록 반환
     * @return 게시판 목록
     */
    public List<Board> boardList(){
        try {
            return articleDao.findByBoardAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeArticle(Article article) {
        try {
            articleDao.createArticle(article);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeComment(Article article) {
        try {
            articleDao.createComment(article);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 게시글 목록 반환
     * @return 게시글 목록
     */
    public List<Article> articleList(int rowCount, int requestPage, String type, String value){
        try {
            return articleDao.findByAll(rowCount, requestPage, type, value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getArticleCount(String type, String value) {
        int count = 0;
        try {
            count = articleDao.findByArticleCount(type,value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public Article printArticle(int articleId) {
        Article article= null;
        try {
             article= articleDao.readContent(articleId);
             articleDao.updateArticleHitCount(articleId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return article;
    }
}

