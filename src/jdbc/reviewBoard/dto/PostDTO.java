package jdbc.reviewBoard.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PostDTO {
    private int post_no;
    private String poster;
    private String post_title;
    private String post_cont;
    private Timestamp post_time;
    private String post_time_str;

    public String getPost_time_str() {
        return post_time_str;
    }

    public void setPost_time_str(String post_time_str) {
        this.poster = post_time_str;
    }

    public void setPost_time_str() {
        this.post_time_str = post_time_str;
        long difTime = (System.currentTimeMillis() - this.post_time.getTime())/(3600000);
        //하루가 안 지났을 때
        if(difTime < 24) {
            this.post_time_str = difTime + "시간 전";
        }
        else {
            // 하루가 지났을 때
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
            this.post_time_str = simpleDateFormat.format(this.post_time.getTime());
        }
    }


    public int getPost_no() {
        return post_no;
    }

    public void setPost_no(int post_no) {
        this.post_no = post_no;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_cont() {
        return post_cont;
    }

    public void setPost_cont(String post_cont) {
        this.post_cont = post_cont;
    }

    public Timestamp getPost_time() {
        return post_time;
    }

    public void setPost_time(Timestamp post_time) {
        this.post_time = post_time;
    }

    public PostDTO() {
    }

    public PostDTO(String poster, String post_title, String post_cont) {
        this.poster = poster;
        this.post_title = post_title;
        this.post_cont = post_cont;
    }
}

