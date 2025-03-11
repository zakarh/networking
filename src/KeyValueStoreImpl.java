import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import static common.Utility.getTimestamp;

public class KeyValueStoreImpl extends UnicastRemoteObject implements KeyValueStore {
    private final HashMap<String, String> store;

    protected KeyValueStoreImpl() throws RemoteException {
        store = new HashMap<>();
    }

    @Override
    public synchronized String put(String key, String value) {
        store.put(key, value);
        return getTimestamp() + " PUT successful: " + key + " -> " + value;
    }

    @Override
    public synchronized String get(String key) {
        return getTimestamp() + " " + store.getOrDefault(key, "Key not found.");
    }

    @Override
    public synchronized String delete(String key) {
        return getTimestamp() + " " + (store.remove(key) != null ? "DELETE successful." : "Key not found.");
    }
}