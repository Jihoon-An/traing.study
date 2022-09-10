package signUp.main.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbcp2.BasicDataSource;
import signUp.main.model.SignUpDTO;

public class SignUpDAO {

    private static SignUpDAO instance =null;

    public synchronized static SignUpDAO getInstance() {

        if(instance == null) {
            instance = new SignUpDAO();
        }
        return instance;
    }

    private BasicDataSource bds= new BasicDataSource();

    private SignUpDAO() {
        this.bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        this.bds.setUsername("signup");
        this.bds.setPassword("signup");
        this.bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        this.bds.setInitialSize(30);
    }

    private Connection getConnection() throws Exception{
        return this.bds.getConnection();
    }


    public String signUpDAO(SignUpDTO dto) {

        String sql="insert into user_table values(?,?,?)";

        try(Connection con = getConnection();
            PreparedStatement pst= con.prepareStatement(sql);){

            pst.setString(1,dto.getUser_id());
            pst.setString(2,dto.getUser_pw());
            pst.setString(3,dto.getUser_name());

            int sqlResult = pst.executeUpdate();

            if(sqlResult > 0) {
                return "success";
            }else {
                return "fail";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }



    public String signInDAO(SignUpDTO trySignIn) {
        String sql = "select * from user_table where user_id = ?";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, trySignIn.getUser_id());
            try (
                    ResultSet sqlResult = statement.executeQuery();
            ) {
                while(true) {
                    if (sqlResult.next()) {
                        if(trySignIn.getUser_id().equals(sqlResult.getString("user_id")))
                        {
                            if(trySignIn.getUser_pw().equals(sqlResult.getString("user_pw"))){
                                return "success";
                            }
                            else{
                                return "fail";
                            }
                        }
                    } else{
                        return "notFoundId";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}