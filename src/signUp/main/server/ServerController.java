package signUp.main.server;

import signUp.main.model.SignUpDTO;
import signUp.main.server.dao.SignUpDAO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    public static void service() {
        while (true) {
            try (
                    ServerSocket server = new ServerSocket(25000);
                    Socket socket = server.accept();
                    OutputStream os = socket.getOutputStream();
                    InputStream is = socket.getInputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    ObjectInputStream ois = new ObjectInputStream(is);
                    DataOutputStream dos = new DataOutputStream(os);
                    DataInputStream dis = new DataInputStream(is);
            ) {
                System.out.println(socket.getInetAddress() + " 에서 접속하였습니다.");
                SignUpDAO signUpDAO = SignUpDAO.getInstance();

                while (true) {
                    int menuChoice = ois.readInt();
                    SignUpDTO readDto;
                    switch (menuChoice) {
                        case 1: // 로그인
                            System.out.println(socket.getInetAddress() + " 에서 로그인을 시도합니다.");
                            readDto = (SignUpDTO)ois.readObject();
                            String inResult = signUpDAO.signInDAO(readDto);
                            System.out.println(inResult);
                            dos.writeUTF(inResult);
                            break;
                        case 2: // 회원가입
                            System.out.println(socket.getInetAddress() + " 에서 회원가입을 시도합니다.");
                            readDto = (SignUpDTO)ois.readObject();
                            String upResult = signUpDAO.signUpDAO(readDto);
                            System.out.println(upResult);
                            dos.writeUTF(upResult);
                            break;
                        case 0: // 프로그램 종료
                            dis.close();
                            dos.close();
                            ois.close();
                            oos.close();
                            is.close();
                            os.close();
                            server.close();
                            System.out.println(socket.getInetAddress() + "에서 프로그램을 종료합니다.");
                        default:
                            throw new IllegalStateException("Unexpected value: " + menuChoice);
                    }
                }
            } catch (Exception e) {
                System.out.println("접속이 끊어졌습니다.");
            }
        }
    }
}
