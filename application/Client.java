package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    final static int port = 1234;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, port);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        
        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String text = sc.nextLine();
                    try {
                        dos.writeUTF(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread read = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String text = dis.readUTF();
                        System.out.println(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        send.start();
        read.start();
    }
}
