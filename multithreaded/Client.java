package multithreaded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        try {
            Scanner sc = new Scanner(System.in);

            // get localhost ip
            InetAddress ip = InetAddress.getByName("localhost"); 

            // establish connection with server port 5056
            Socket s = new Socket(ip, 5056);    

            // creata I/O streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            
            // exchange information between client and client handler
            while (true) {
                System.out.println(dis.readUTF());
                String text = sc.nextLine();
                dos.writeUTF(text);

                // exit operation
                if (text.equals("exit")) {
                    System.out.println("Closing the connection: " + s);
                    s.close();
                    System.out.println("Conection closed");
                    break;
                }

                // result information requested by cilent
                String result = dis.readUTF();
                System.out.println(result);
            }

            // close connection
            sc.close();
            dis.close();
            dos.close();
        } catch (Exception i) {
            i.printStackTrace();
        }
    }
}
