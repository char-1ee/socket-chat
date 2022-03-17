package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {

    static Vector<ClientHandler> handlers = new Vector<>();
    static int cnt = 0; // counter for clients

    public static void main(String[] args) throws IOException {

        // listening to port 1234
        ServerSocket ss = new ServerSocket(1234);
        Socket s;

        // loop for receiving requests
        while (true) {
            s = ss.accept(); // initialize socket
            System.out.println("New client request received :" + s);

            // get I/O stream
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating new handler for this client...");

            // create new client handler for this request
            ClientHandler mHandler = new ClientHandler(s, "client " + cnt, dis, dos);
            Thread t = new Thread(mHandler);
            System.out.println("Adding this client to active client list");

            handlers.add(mHandler);
            t.start();
            cnt++;
        }
    }
}

class ClientHandler implements Runnable {
    Scanner sc = new Scanner(System.in);
    Socket s;
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    boolean isLogin;

    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isLogin = true;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {
                received = dis.readUTF();
                System.out.println(received);
                if (received.equals("logout")) {
                    this.isLogin = false;
                    this.s.close();
                    break;
                }

                // break out messages (where can produce exception when string in unexpected format)
                StringTokenizer st = new StringTokenizer(received, "#");
                String text = st.nextToken();
                String user = st.nextToken(); 

                for (ClientHandler handler : Server.handlers) {
                    if (handler.name.equals(user) && handler.isLogin == true) {
                        handler.dos.writeUTF(this.name + " : " + text);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // close connection
        try {
            this.dis.close();
            this.dos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
