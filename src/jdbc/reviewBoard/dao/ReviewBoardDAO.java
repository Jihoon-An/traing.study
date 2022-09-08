package jdbc.reviewBoard.dao;

import jdbc.reviewBoard.dto.PostDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;

public class ReviewBoardDAO {
    private String dbID = "review_board";
    private String dbPW = "review";
    private String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";

    public String getDbID() {
        return dbID;
    }

    public void setDbID(String dbID) {
        this.dbID = dbID;
    }

    public String getDbPW() {
        return dbPW;
    }

    public void setDbPW(String dbPW) {
        this.dbPW = dbPW;
    }

    public String getDbURL() {
        return dbURL;
    }
    private BasicDataSource bds = new BasicDataSource();

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    private static ReviewBoardDAO instance = null;
    public synchronized static ReviewBoardDAO getInstance(){
        if(instance == null){
            instance = new ReviewBoardDAO();
        }
        return instance;
    }

    private ReviewBoardDAO() {
        this.bds.setUrl(dbURL);
        this.bds.setUsername(dbID);
        this.bds.setPassword(dbPW);
        this.bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        this.bds.setInitialSize(30);
    }

    public ReviewBoardDAO(String dbID, String dbPW) {
        this.dbID = dbID;
        this.dbPW = dbPW;
        this.bds.setUrl(dbURL);
        this.bds.setUsername(dbID);
        this.bds.setPassword(dbPW);
        this.bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        this.bds.setInitialSize(30);
    }

    private Connection getConnection() throws Exception{
        return bds.getConnection();
    }

    public int insert(PostDTO post) {
        String sql = "insert into review_board values(review_seq.nextval, ?, ?, ?, sysdate)";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, post.getPoster());
            statement.setString(2, post.getPost_title());
            statement.setString(3, post.getPost_cont());
            int result = statement.executeUpdate();

            con.commit();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<PostDTO> select() {
        String sql = "select POST_NO, POSTER, POST_TITLE, POST_TIME from review_board order by 1";
        ArrayList<PostDTO> out = new ArrayList<>();
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            try (
                    ResultSet result = statement.executeQuery();
            ) {
                while (result.next()) {
                    PostDTO post = new PostDTO();
                    post.setPost_no(result.getInt("POST_NO"));
                    post.setPoster(result.getString("POSTER"));
                    post.setPost_title(result.getString("POST_TITLE"));
                    post.setPost_time(result.getTimestamp("POST_TIME"));
                    post.setPost_time_str();
                    out.add(post);
                }
                return out;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PostDTO select(int findPostNo) {
        String sql = "select * from review_board where post_no = ?";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setInt(1, findPostNo);
            try (
                    ResultSet result = statement.executeQuery();
            ) {
                result.next();
                PostDTO post = new PostDTO();
                post.setPost_no(result.getInt("POST_NO"));
                post.setPoster(result.getString("POSTER"));
                post.setPost_title(result.getString("POST_TITLE"));
                post.setPost_cont(result.getString("POST_CONT"));
                post.setPost_time(result.getTimestamp("POST_TIME"));
                post.setPost_time_str();

                return post;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int delete(int postNo) {
        String sql = "delete from review_board where post_no = ?";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setInt(1, postNo);
            int result = statement.executeUpdate();

            con.commit();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int update(PostDTO rePost) {
        String sql = "Update review_board set POSTER = ?, POST_TITLE = ?, POST_CONT = ? where POST_NO = ?";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, rePost.getPoster());
            statement.setString(2, rePost.getPost_title());
            statement.setString(3, rePost.getPost_cont());
            statement.setInt(4, rePost.getPost_no());
            int result = statement.executeUpdate();

            con.commit();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Integer> search(String searchText) {
        String sql = "select POST_NO from review_board where (POST_TITLE like ?) or (POSTER like ?) order by POSTER";
        ArrayList<Integer> outStr = new ArrayList<>();
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, "%" + searchText + "%");
            statement.setString(2, "%" + searchText + "%");
            try (
                    ResultSet result = statement.executeQuery();
            ) {

                while (result.next()) {
                    int postNo = result.getInt("POST_NO");
                    outStr.add(postNo);
                }
                return outStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
