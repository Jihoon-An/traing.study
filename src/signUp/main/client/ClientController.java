package signUp.main.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientController {
    public static void service() {
        try (Socket client = new Socket("localhost", 25000);
             InputStream is = client.getInputStream();
             OutputStream os = client.getOutputStream();
             DataInputStream dis = new DataInputStream(is);
             DataOutputStream dos = new DataOutputStream(os);
             ObjectInputStream ois = new ObjectInputStream(is);
             ObjectOutputStream oos = new ObjectOutputStream(os);
        ) {

            while (true) {
                int menuChoice = View.mainMenu();
                dos.writeInt(menuChoice);
                dos.flush();
                Menu:
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
                        dos.close();
                        dis.close();
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
