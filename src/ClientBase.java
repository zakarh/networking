public class ClientBase {
    public static boolean validateCommand(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 1) return false;
        String command = parts[0].toUpperCase();
        if (command.equals("PUT")) {
            if (parts.length == 3) return true;
        }
        if (command.equals("GET")) {
            if (parts.length == 2) return true;
        }
        if (command.equals("DELETE")) {
            if (parts.length == 2) return true;
        }
        if (command.equals("EXIT")) {
            if (parts.length == 1) return true;
        }
        return false;
    }
}
