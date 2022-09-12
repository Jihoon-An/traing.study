package signUp.main.client;

import java.io.*;
import java.net.Socket;

public class ClientController {
    public static void service() {
        System.out.println("service start");
        try {
            Socket client = new Socket("localhost", 25000);
            System.out.println("Socket연결");
            InputStream is = client.getInputStream();
            OutputStream os = client.getOutputStream();
            System.out.println("is, os 연결");
            ObjectInputStream ois = new ObjectInputStream(is);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            System.out.println("object 연결");
            while (true) {
                int menuChoice = View.mainMenu();
                oos.writeInt(menuChoice);
                oos.flush();
                switch (menuChoice) {
                    case 1: // 로그인
                        oos.writeObject(View.signInMenu());
                        oos.flush();
                        View.signInResult(ois.readUTF());
                        break;
                    case 2: // 회원가입
                        oos.writeObject(View.signUpMenu());
                        oos.flush();
                        View.signUpResult(ois.readUTF());
                        break;
                    case 0: // 프로그램 종료
                        oos.close();
                        ois.close();
                        is.close();
                        os.close();
                        client.close();
                        View.exit();
                        System.exit(0);
                }
            }
        } catch (Exception e) {
            View.error();
            e.printStackTrace();
            System.exit(0);
        }
    }
}
