package server.test;

import server.dao.SignUpDAO;
import server.dto.SignUpDTO;

public class SignUpTest {
    public static void main(String[] args) throws Exception {
        SignUpDAO sign = SignUpDAO.getInstance();
        System.out.println(sign.signUpDAO(new SignUpDTO("asdff", "asdff", "asdff")));
    }
}
