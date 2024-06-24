package com.ezen.board.dto;

// dto 만들때는 테이블켜두고 속성 똑같이 해주기
public class Board {
    private int boardId;
    private int category;
    private String title;
    private String description;

    public Board() {}

    public Board(int boardId, int category, String title, String description) {
        this.boardId = boardId;
        this.category = category;
        this.title = title;
        this.description = description;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Board{" +
                "boardId=" + boardId +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
