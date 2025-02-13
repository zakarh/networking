import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import static common.Utility.getTimestamp;

public class ClientUDP extends ClientBase{
    public static void main(String[] args) {
        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            InetAddress serverAddress = InetAddress.getByName(host);
            DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print(getTimestamp() + " Enter command (e.g., PUT key value, GET key, DELETE key, or EXIT): ");
                String userInput = scanner.nextLine();

                if (validateCommand(userInput)) {
                    System.out.println(getTimestamp() + " Invalid command format. Suggestions: PUT key value | GET key | DELETE key | EXIT");
                    continue;
                }

                byte[] sendData = userInput.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
                socket.send(sendPacket);

                if (userInput.equalsIgnoreCase("EXIT")) {
                    System.out.println(getTimestamp() + " Disconnecting...");
                    break;
                }

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(getTimestamp() + " Server response: " + serverResponse);
            }
            socket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
