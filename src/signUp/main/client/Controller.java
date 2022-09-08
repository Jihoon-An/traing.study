package signUp.main.client;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

public class Controller {
    public static void main(String[] args) {
        try (Socket client = new Socket("192.168.50.54", 25000);
             InputStream is = client.getInputStream();             // String 을 받아올 수 없음.
             DataInputStream dis = new DataInputStream(is);)
        {

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
