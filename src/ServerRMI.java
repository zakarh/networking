import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerRMI {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            KeyValueStoreImpl keyValueStore = new KeyValueStoreImpl();
            Naming.rebind("KeyValueStoreService", keyValueStore);
            System.out.println("RMI Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}