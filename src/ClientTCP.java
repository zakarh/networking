import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static common.Utility.getTimestamp;

public class ClientTCP extends ClientBase{
    public static void main(String[] args) {
        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            Socket clientSocket = new Socket(host, port);
            clientSocket.setSoTimeout(5000);
            System.out.println(getTimestamp() + " Connected to server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print(getTimestamp() + " Enter command (e.g., PUT key value, GET key, DELETE key, or EXIT): ");
                String userInput = scanner.nextLine();

                if (validateCommand(userInput)) {
                    System.out.println(getTimestamp() + " Invalid command format. Suggestions: PUT key value | GET key | DELETE key | EXIT");
                    continue;
                }

                out.println(userInput);

                if (userInput.equalsIgnoreCase("EXIT")) {
                    System.out.println(getTimestamp() + " Disconnecting...");
                    break;
                }

                try {
                    String serverResponse = in.readLine();
                    if (serverResponse == null) {
                        System.out.println(getTimestamp() + " Server closed the connection.");
                        break;
                    }
                    System.out.println(getTimestamp() + " Server response: " + serverResponse);
                } catch (Exception e) {
                    System.out.println(getTimestamp() + " Error while reading server response: " + e.getMessage());
                    break;
                }
            }
            clientSocket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
