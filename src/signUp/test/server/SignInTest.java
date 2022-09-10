package signUp.test.server;

import signUp.main.server.dao.SignUpDAO;
import signUp.main.model.SignUpDTO;

public class SignInTest {
    public static void main(String[] args) throws Exception {
        SignUpDAO sign = SignUpDAO.getInstance();
        System.out.println(sign.signInDAO(new SignUpDTO("asdff", "asdff")));
    }
}