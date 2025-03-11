import java.rmi.Naming;
import java.util.Scanner;

import static common.Utility.getTimestamp;

public class ClientRMI {
    public static void main(String[] args) {
        try {
            KeyValueStore keyValueStore = (KeyValueStore) Naming.lookup("rmi://localhost/KeyValueStoreService");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print(getTimestamp() + " Enter command (e.g., PUT key value, GET key, DELETE key, or EXIT): ");
                String input = scanner.nextLine();
                String[] parts = input.split(" ", 3);
                String command = parts[0].toUpperCase();

                if (command.equals("EXIT")) break;

                switch (command) {
                    case "PUT" -> {
                        if (parts.length == 3) System.out.println(keyValueStore.put(parts[1], parts[2]));
                        else System.out.println(getTimestamp() + " Invalid PUT format. Usage: PUT key value");
                    }
                    case "GET" -> {
                        if (parts.length == 2) System.out.println(keyValueStore.get(parts[1]));
                        else System.out.println(getTimestamp() + " Invalid GET format. Usage: GET key");
                    }
                    case "DELETE" -> {
                        if (parts.length == 2) System.out.println(keyValueStore.delete(parts[1]));
                        else System.out.println(getTimestamp() + " Invalid DELETE format. Usage: DELETE key");
                    }
                    default -> System.out.println(getTimestamp() + " Invalid command. Use PUT, GET, DELETE, or EXIT.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
