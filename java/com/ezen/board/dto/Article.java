package com.ezen.board.dto;

/**
 * 게시글 추상화 (캠슐화)
 * JavaBean 규약에 따라 작성
 */

public class Article {
    private int articleId;
    private int boardId;
    private String writer;
    private String title;
    private String content;
    private String regdate;
    private int hitCount;
    private String passwd;
    private int groupNo;
    private int levelNo;
    private int orderNo;

    //디폴트는 useBean에서 쓰니까 꼭생성
    public Article() {}

    public Article(int articleId, int boardId, String writer, String title, String content, String regdate, int hitCount, String passwd, int groupNo, int levelNo, int orderNo) {
        this.articleId = articleId;
        this.boardId = boardId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.hitCount = hitCount;
        this.passwd = passwd;
        this.groupNo = groupNo;
        this.levelNo = levelNo;
        this.orderNo = orderNo;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }

    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }


    // 디버깅을 위한 toString
    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", boardId=" + boardId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regdate='" + regdate + '\'' +
                ", hitCount=" + hitCount +
                ", passwd='" + passwd + '\'' +
                ", groupNo=" + groupNo +
                ", levelNo=" + levelNo +
                ", orderNo=" + orderNo +
                '}';
    }
}


