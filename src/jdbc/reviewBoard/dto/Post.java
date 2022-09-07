package jdbc.reviewBoard.dto;

import java.sql.Timestamp;

public class Post {
    private int postNo;
    private String poster;
    private String postTitle;
    private String postCont;
    private Timestamp postTimestamp;
    private String postTime;

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }


    public int getPostNo() {
        return postNo;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostCont() {
        return postCont;
    }

    public void setPostCont(String postCont) {
        this.postCont = postCont;
    }

    public Timestamp getPostTimestamp() {
        return postTimestamp;
    }

    public void setPostTimestamp(Timestamp postTimestamp) {
        this.postTimestamp = postTimestamp;
    }

    public Post() {
    }

    public Post(String poster, String postTitle, String postCont) {
        this.poster = poster;
        this.postTitle = postTitle;
        this.postCont = postCont;
    }
}

