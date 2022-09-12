package signUp.main.client;

import signUp.main.model.SignUpDTO;

import java.io.*;
import java.net.Socket;

public class ClientController {
    public static void service() {
        System.out.println("service start");
        try (
                Socket client = new Socket("localhost", 25000);
                OutputStream os = client.getOutputStream();
                InputStream is = client.getInputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                ObjectInputStream ois = new ObjectInputStream(is);
                DataOutputStream dos = new DataOutputStream(os);
                DataInputStream dis = new DataInputStream(is);
        ) {
            System.out.println("object 연결");
            SignUpDTO dto;
            String resultMsg;
            while (true) {
                int menuChoice = View.mainMenu();
                oos.writeInt(menuChoice);
                oos.flush();
                switch (menuChoice) {
                    case 1: // 로그인
                        dto = View.signInMenu();
                        oos.writeObject(dto);
                        oos.flush();
                        resultMsg = dis.readUTF();
                        View.signInResult(resultMsg);
                        break;
                    case 2: // 회원가입
                        dto = View.signUpMenu();
                        oos.writeObject(dto);
                        oos.flush();
                        resultMsg = dis.readUTF();
                        View.signUpResult(resultMsg);
                        break;
                    case 0: // 프로그램 종료
                        dis.close();
                        dos.close();
                        ois.close();
                        oos.close();
                        is.close();
                        os.close();
                        client.close();
                        View.exit();
                        System.exit(0);
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuChoice);
                }
            }
        } catch (Exception e) {
            View.error();
//            e.printStackTrace();
            System.exit(0);
        }
    }
}
