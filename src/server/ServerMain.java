package server;

import interfaces.PartRepository;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {
	
    public static void main(String[] args) {
    	
        try {
        	
            PartRepositoryImpl partRepositoryImpl = new PartRepositoryImpl(args[0]);
            PartRepository partRepository = (PartRepository) UnicastRemoteObject.exportObject(partRepositoryImpl, 0);

            Registry reg = LocateRegistry.getRegistry();
            reg.bind(args[0], partRepository);
            System.out.println("Servidor iniciado!");
            
        }catch (Exception e){
        	
            System.err.println(e.toString());
 
        }
    }
}