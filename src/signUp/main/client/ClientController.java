package signUp.main.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientController {
    public static void service() {
        try (Socket client = new Socket("localhost", 25000);
             InputStream is = client.getInputStream();
             OutputStream os = client.getOutputStream();             // String 을 받아올 수 없음.
             DataInputStream dis = new DataInputStream(is);
             DataOutputStream dos = new DataOutputStream(os);)
        {
            System.out.println("EX");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
