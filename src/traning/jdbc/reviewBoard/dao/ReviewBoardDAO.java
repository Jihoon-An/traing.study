package traning.jdbc.reviewBoard.dao;

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

    public int insert(String... contents) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        String sql = "insert into review_board values(review_seq.nextval, ?, ?, ?, sysdate)";
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            int i = 1;
            for (String content : contents) {
                statement.setString(i++, content);
            }
            int result = statement.executeUpdate();

            con.commit();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public ArrayList<String> select() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        String sql = "select POST_NO, POSTER, POST_TITLE, POST_TIME from review_board order by 1";
        ArrayList<String> outStr = new ArrayList<>();
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int postNo = result.getInt("POST_NO");
                String poster = result.getString("POSTER");
                String postTitle = result.getString("POST_TITLE");
                Timestamp postTimeDate  = result.getTimestamp("POST_TIME");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
                String postTime = simpleDateFormat.format(postTimeDate.getTime());

                outStr.add(postNo + " : " + poster + " : " + postTitle + " : " + postTime);
            }
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> select(int findPostNo) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        ArrayList<String> outStr = new ArrayList<>();
        String sql = "select * from review_board where post_no = ?";
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, findPostNo);
            ResultSet result = statement.executeQuery();
            result.next();
            int postNo = result.getInt("POST_NO");
            String poster = result.getString("POSTER");
            String postTitle = result.getString("POST_TITLE");
            Timestamp postTimeDate  = result.getTimestamp("POST_TIME");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
            String postTime = simpleDateFormat.format(postTimeDate);

            outStr.add(postNo + " : " + poster + " : " + postTitle + " : " + postTime);
            outStr.add(result.getString("POST_CONT"));

            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int delete(int postNo) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
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


    public int update(int rePostNo, String rePortCol, String rePostCon) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        String sql = "Update review_board set ? = ? where pid = ?";
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, rePortCol);
            statement.setString(2, rePostCon);
            statement.setInt(3, rePostNo);
            int result = statement.executeUpdate();

            con.commit();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Integer> search(String searchText){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        String sql = "select POST_NO from review_board where (POST_TITLE = ?) or (POSTER = ?) order by 1";
        ArrayList<Integer> outStr = new ArrayList<>();
        try (
                Connection con = DriverManager.getConnection(dbURL, dbID, dbPW);
        ) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, searchText);
            statement.setString(2, searchText);
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
