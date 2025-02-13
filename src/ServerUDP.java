import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static common.Utility.getTimestamp;

public class ServerUDP extends ServerBase {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Server is running on port " + port + "...");

            byte[] receiveData = new byte[1024];
            byte[] sendData;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                String clientAddress = receivePacket.getAddress().toString();
                int clientPort = receivePacket.getPort();

                System.out.println(getTimestamp() + " Received query from " + clientAddress + ":" + clientPort + ": \"" + clientMessage + "\"");

                String response = processCommand(clientMessage);

                System.out.println(getTimestamp() + " Responding to " + clientAddress + ":" + clientPort + ": \"" + response + "\"");

                sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
