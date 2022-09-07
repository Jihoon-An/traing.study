package jdbc.reviewBoard.dao;

import jdbc.reviewBoard.dto.Post;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReviewBoardDAO {
    private String dbID;
    private String dbPW;
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

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public ReviewBoardDAO() {
    }

    public ReviewBoardDAO(String dbID, String dbPW) {
        this.dbID = dbID;
        this.dbPW = dbPW;
    }

    private Connection getConnection() throws Exception{
        Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        return con;
    }

    public int insert(Post post) {
        String sql = "insert into review_board values(review_seq.nextval, ?, ?, ?, sysdate)";
        try (
                Connection con = getConnection();
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, post.getPoster());
            statement.setString(2, post.getPostTitle());
            statement.setString(3, post.getPostCont());
            int result = statement.executeUpdate();

            con.commit();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public ArrayList<Post> select() {
        String sql = "select POST_NO, POSTER, POST_TITLE, POST_TIME from review_board order by 1";
        ArrayList<Post> out = new ArrayList<>();
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Post post = new Post();
                post.setPostNo(result.getInt("POST_NO"));
                post.setPoster(result.getString("POSTER"));
                post.setPostTitle(result.getString("POST_TITLE"));
                post.setPostTimestamp(result.getTimestamp("POST_TIME"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
                long difTime = (System.currentTimeMillis() - post.getPostTimestamp().getTime())/(3600000);
                //하루가 안 지났을 때
                if(difTime < 24) {
                    post.setPostTime(difTime + "시간 전");
                }
                else {
                    // 하루가 지났을 때
                    post.setPostTime(simpleDateFormat.format(post.getPostTimestamp().getTime()));
                }
                out.add(post);
            }
            return out;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Post select(int findPostNo) {
        String sql = "select * from review_board where post_no = ?";
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, findPostNo);
            ResultSet result = statement.executeQuery();
            result.next();
            Post post = new Post();
            post.setPostNo(result.getInt("POST_NO"));
            post.setPoster(result.getString("POSTER"));
            post.setPostTitle(result.getString("POST_TITLE"));
            post.setPostCont(result.getString("POST_CONT"));
            post.setPostTimestamp(result.getTimestamp("POST_TIME"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
            long difTime = (System.currentTimeMillis() - post.getPostTimestamp().getTime())/(3600000);
            //하루가 안 지났을 때
            if(difTime < 24) {
                post.setPostTime(difTime + "시간 전");
            }
            else {
                // 하루가 지났을 때
                post.setPostTime(simpleDateFormat.format(post.getPostTimestamp().getTime()));
            }

            return post;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public int delete(int postNo) {
        String sql = "delete from review_board where post_no = ?";
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, postNo);
            int result = statement.executeUpdate();

            con.commit();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int update(Post rePost) {
        String sql = "Update review_board set POSTER = ?, POST_TITLE = ?, POST_CONT = ? where POST_NO = ?";
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, rePost.getPoster());
            statement.setString(2, rePost.getPostTitle());
            statement.setString(3, rePost.getPostCont());
            statement.setInt(4, rePost.getPostNo());
            int result = statement.executeUpdate();

            con.commit();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Integer> search(String searchText){
        String sql = "select POST_NO from review_board where (POST_TITLE like ?) or (POSTER like ?) order by POSTER";
        ArrayList<Integer> outStr = new ArrayList<>();
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, "%"+searchText+"%");
            statement.setString(2, "%"+searchText+"%");
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int postNo = result.getInt("POST_NO");
                outStr.add(postNo);
            }
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
