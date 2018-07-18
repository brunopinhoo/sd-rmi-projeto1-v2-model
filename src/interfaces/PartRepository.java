package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface PartRepository extends Remote{

    public void inserirNovaPeca(String nome, String desc, Map<Part, Integer> subP)
            throws RemoteException;

    public Part getPecaFromRepository(String codigo)
            throws RemoteException;

    public String listPecas()
            throws RemoteException;
}