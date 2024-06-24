package com.ezen.board.service;

import com.ezen.board.dto.Article;
import com.ezen.board.dto.Board;

import java.util.List;

/**
 * 게시판 관련 비즈니스 로직 선언
 */
public interface BoardService {

    /**
     * 신규 게시글 쓰기
     * @param article 게시글
     */

    public List<Board> boardList();

    public void writeArticle (Article article);

    public void writeComment (Article article);

    /**
     * 게시글 목록 반환
     * @return 게시글 목록
     */
    public List<Article> articleList(int rowCount, int requestPage, String type, String value);

    /**
     * 게시글 갯수 반환
     * @return 게시글 갯수
     */
    public int getArticleCount(String type, String value);

    public Article printArticle(int articleId);

}


