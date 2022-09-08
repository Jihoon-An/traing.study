package test;

import server.dao.SignUpDAO;
import server.dto.SignUpDTO;

public class SignInTest {
    public static void main(String[] args) throws Exception {
        SignUpDAO sign = SignUpDAO.getInstance();
        System.out.println(sign.signInDAO(new SignUpDTO("asdff", "asdff")));
    }
}