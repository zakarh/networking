import java.util.HashMap;

public class ServerBase {
    private static final HashMap<String, String> store = new HashMap<>();

    public static String processCommand(String input) {
        String[] parts = input.split(" ", 3);
        if (parts.length < 1) return "Error: Command not recognized.";

        String command = parts[0].toUpperCase();
        return switch (command) {
            case "PUT" -> handlePut(parts);
            case "GET" -> handleGet(parts);
            case "DELETE" -> handleDelete(parts);
            case "EXIT" -> "Adios!";
            default -> "Invalid command. Use PUT, GET, DELETE, or EXIT.";
        };
    }

    private static String handlePut(String[] parts) {
        if (parts.length != 3) return "Error: PUT requires a key and a value.";
        synchronized (store) {
            store.put(parts[1], parts[2]);
        }
        return "PUT successful: " + parts[1] + " -> " + parts[2];
    }

    private static String handleGet(String[] parts) {
        if (parts.length != 2) return "Error: GET requires a key.";
        synchronized (store) {
            return store.getOrDefault(parts[1], "Key not found.");
        }
    }

    private static String handleDelete(String[] parts) {
        if (parts.length != 2) return "Error: DELETE requires a key.";
        synchronized (store) {
            return store.remove(parts[1]) != null ? "DELETE successful." : "Key not found.";
        }
    }
}
