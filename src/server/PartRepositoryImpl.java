package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import interfaces.Part;
import interfaces.PartRepository;

public class PartRepositoryImpl implements PartRepository {
	
    private List<Part> partList;
    private String name;

    public PartRepositoryImpl(String name) {
    
    	this.name = name;
        this.partList = new ArrayList();
    
    }

    public void inserirNovaPeca(String nome, String desc, Map<Part, Integer> subP) throws RemoteException {
    
    	String codigo = this.genCode(nome);
        Part p = new PartImpl(nome, desc, subP, codigo);
        this.partList.add(p);
    
    }

    public Part getPecaFromRepository(String codigo) throws RemoteException {
    
    	for(Part p : this.partList)
            if(p.getCodigo().equals(codigo))
                return p;
    	
        return null;
        
    }

    public String listPecas() throws RemoteException {
    	
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Lista de Pecas: ");
        
        for(Part p: this.partList) {
            stringBuilder.append(p.getPeca()).append(" ");
        }

        return stringBuilder.toString();
    }

    public String genCode(String nome) {
        String code = Integer.toString(Math.abs((nome + System.currentTimeMillis()).hashCode()));
        return code.substring(0, Math.min(code.length(), 6));
    }
}