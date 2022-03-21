package multithreaded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    public static void main(String[] args) throws IOException {

        // listening to port 5056
        ServerSocket ss = new ServerSocket(5056);

        while (true) {
            Socket s = null; // create a new socket
            try {
                s = ss.accept(); // receiving incoming client requests
                System.out.println("A new client is connected: " + s);

                // get I/O stream
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client: ");
                Thread t = new ClientHandler(s, dis, dos);
                t.start();
            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
    DataInputStream dis;
    DataOutputStream dos;
    Socket s;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String input, output;
        while (true) {
            try {
                dos.writeUTF("Show Date or Time\n" + "Type exit to terminate.");
                input = dis.readUTF();

                // exit operations
                if (input.equals("exit")) {
                    System.out.println("Client " + this.s + "sends text...");
                    System.out.println("Closing the connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // generate output according to client requests
                Date date = new Date();
                switch (input) {
                    case "Date":
                        output = dateFormat.format(date);
                        dos.writeUTF(output);
                        break;
                    case "Time":
                        output = timeFormat.format(date);
                        dos.writeUTF(output);
                        break;
                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // close resources
        try {
            this.dis.close();
            this.dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
