package signUp.test.server;

import signUp.main.server.dao.SignUpDAO;
import signUp.main.server.dto.SignUpDTO;

public class SignUpTest {
    public static void main(String[] args) throws Exception {
        SignUpDAO sign = SignUpDAO.getInstance();
        System.out.println(sign.signUpDAO(new SignUpDTO("asdff", "asdff", "asdff")));
    }
}
