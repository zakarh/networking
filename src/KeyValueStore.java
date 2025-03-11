import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KeyValueStore extends Remote {
    String put(String key, String value) throws RemoteException;
    String get(String key) throws RemoteException;
    String delete(String key) throws RemoteException;
}