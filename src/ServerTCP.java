import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static common.Utility.getTimestamp;

public class ServerTCP extends ServerBase {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                new Thread(() -> {
                    try (
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true)
                    ) {
                        String clientMessage;
                        while ((clientMessage = in.readLine()) != null) {
                            String clientInfo = clientSocket.getInetAddress() + ":" + clientSocket.getPort();
                            System.out.println(getTimestamp() + " Received query from " + clientInfo + ": \"" + clientMessage + "\"");

                            String response = processCommand(clientMessage);

                            System.out.println(getTimestamp() + " Responding to " + clientInfo + ": \"" + response + "\"");

                            out.println(response);
                        }
                    } catch (Exception e) {
                        System.out.println(getTimestamp() + " Client disconnected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start(); // Start the client thread
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
