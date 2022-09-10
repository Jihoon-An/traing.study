package signUp.main.server;

import signUp.main.model.SignUpDTO;
import signUp.main.server.dao.SignUpDAO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    public static void service() {
        while (true) {
            try (ServerSocket server = new ServerSocket(25000);
                 Socket socket = server.accept();
                 InputStream is = socket.getInputStream();
                 OutputStream os = socket.getOutputStream();
                 DataInputStream dis = new DataInputStream(is);
                 DataOutputStream dos = new DataOutputStream(os);
                 ObjectInputStream ois = new ObjectInputStream(is);
                 ObjectOutputStream oos = new ObjectOutputStream(os);)
            {
                System.out.println(socket.getInetAddress()+" 에서 접속하였습니다.");
                SignUpDAO signUpDAO = SignUpDAO.getInstance();
                while (true) {
                    int menuChoice = dis.readInt();
                    switch (menuChoice) {
                        case 1: // 로그인
                            System.out.println(socket.getInetAddress()+" 에서 로그인을 시도합니다.");
                            String inResult = signUpDAO.signInDAO((SignUpDTO) ois.readObject());
                            System.out.println(inResult);
                            dos.writeUTF(inResult);
                            break;
                        case 2: // 회원가입
                            System.out.println(socket.getInetAddress()+" 에서 회원가입을 시도합니다.");
                            String upResult = signUpDAO.signUpDAO((SignUpDTO) ois.readObject());
                            System.out.println(upResult);
                            dos.writeUTF(upResult);
                            break;
                        case 0: // 프로그램 종료
                            oos.close();
                            ois.close();
                            dos.close();
                            dis.close();
                            is.close();
                            os.close();
                            server.close();
                            System.out.println("프로그램을 종료합니다.");
                            System.exit(0);
                    }
                }
            } catch (Exception e) {
                System.out.println("접속이 끊어졌습니다.");
                e.printStackTrace();
            }
        }
    }
}
