import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket socket;
    private BufferedInputStream input;
    private BufferedOutputStream output;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            input = new BufferedInputStream(System.in);
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (UnknownHostException i) {
            i.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = "";
        while (!line.equals("exit")) {
            try {
                output.write(input.read()); // TODO: read int rather than string
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 5000);
    }
}