package signUp.main.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbcp2.BasicDataSource;
import signUp.main.server.dto.SignUpDTO;

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
        this.bds.setUrl("jdbc:oracle:thin:@192.168.50.54:1521:xe");
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

            int result = pst.executeUpdate();

            String SignUp_truefalse;

            if(result > 0) {
                SignUp_truefalse="회원가입되셨습니다.";
            }else {
                SignUp_truefalse="회원가입에 실패하였습니다.";
            }
            return SignUp_truefalse;
        }
        catch (Exception e){
            return "회원가입에 실패하였습니다.";
        }
    }



    public String signInDAO(SignUpDTO user) {
        String sql = "select * from user_table where user_id = ?";
        String output = "";
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, user.getUser_id());
            try (
                    ResultSet result = statement.executeQuery();
            ) {
                while(true) {
                    if (result.next()) {
                        if(user.getUser_id().equals(result.getString("user_id")))
                        {
                            if(user.getUser_pw().equals(result.getString("user_pw"))){
                                return "로그인에 성공하였습니다.";
                            }
                            else{
                                return "비밀번호가 일치하지 않습니다.";
                            }
                        }
                    } else{
                        return "존재하는 아이디가 없습니다.";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "오류가 발생했습니다.:"+e.getMessage();
        }
    }





}