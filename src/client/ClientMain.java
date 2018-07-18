package client;

import interfaces.Part;
import interfaces.PartRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ClientMain {
    private Registry REGISTRY;
    
    private Part PART;
    private PartRepository PARTREPOSITORY;
    
    private Map<Part, Integer> MAP_PART_INTEGER;
    
    private boolean BOOLEANBIND = false;

    private boolean DONE = true;

    public ClientMain(){
        this.MAP_PART_INTEGER = new HashMap<Part, Integer>();
        this.PART = null;
        this.DONE = false;
    }

    public static void main (String[] args) {
        ClientMain client = new ClientMain();
        
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            client.setRegistry();
            client.showServers();
            while(!client.isDONE()){
                if(stdIn.ready()) {
                    client.processaInput(stdIn.readLine());
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }
    
    public void bind(String nomeRepositorio) throws RemoteException, NotBoundException {
        this.PARTREPOSITORY = (PartRepository) this.REGISTRY.lookup(nomeRepositorio);
        this.BOOLEANBIND = true;
        System.out.println("Conectado ao repositório " + nomeRepositorio);
    }

    public String listPecas() throws RemoteException {
        return this.PARTREPOSITORY.listPecas();
    }

    public String getPecas(String codigoPeca) throws RemoteException {
        Part part =  this.PARTREPOSITORY.getPecaFromRepository(codigoPeca);
        if(part == null) {
            System.err.println("Codigo nao identificado no repositorio!");
            return "Nenhuma peca encontrada";
        }

        this.PART = part;
        return this.PART.getPeca();
    }

    public String showP() {
        return this.PART.getPeca();
    }

    public void clearlist() {
        this.MAP_PART_INTEGER.clear();
    }

    public void addSubPart(int n) {
        this.MAP_PART_INTEGER.put(this.PART, n);
    }

    public void addP(String nome, String desc) throws RemoteException {
        this.PARTREPOSITORY.inserirNovaPeca(nome, desc, this.MAP_PART_INTEGER);
    }

    public void processaInput(String in) throws RemoteException, NotBoundException {
        String[] stringSplit = in.split(" ");
        String stringCommand = stringSplit[0];
        List<String> listStringArgs = new LinkedList<String>(Arrays.asList(stringSplit));
        listStringArgs.remove(0);
        
        if(!stringCommand.equals("bind") && !stringCommand.equals("quit") && !this.isBound())
            this.showServers();
        else
            switch (stringCommand){
                case "bind":
                    this.bind(listStringArgs.get(0));
                    break;
                case "listp":
                    System.out.println(this.listPecas());
                    break;
                case "getp":
                    System.out.println(this.getPecas(listStringArgs.get(0)));
                    break;
                case "showp":
                    System.out.println(this.showP());
                    break;
                case "clearlist":
                    this.clearlist();
                    System.out.println("Done!");
                    break;
                case "addsubpart":
                    this.addSubPart(Integer.parseInt(listStringArgs.get(0)));
                    System.out.println("Done!");
                    break;
                case "addp":
                    String name = listStringArgs.get(0);
                    listStringArgs.remove(0);
                    
                    StringBuilder desc = new StringBuilder();
                    for(String s : listStringArgs) {
                    	desc.append(s).append(" ");
                    }
                    this.addP(name, desc.toString());
                    System.out.println("Done!");
                    break;
                case "quit":
                    this.setDONE(true);
                    break;
                default:
                    System.err.println("Tente novamente!");
                    break;
            }
    }

    public void showServers() throws RemoteException {
    	System.out.println("Servidores disponíveis:");
        
        for(String s : this.getRegistry().list()) {
            System.out.println(s);
        }
        
        System.out.println("Digite bind [Nome do servidor] para se conectar.");
    }

    public boolean isDONE() {
        return DONE;
    }

    public void setDONE(boolean done) {
        this.DONE = done;
    }

    public void setRegistry() throws Exception {
        this.REGISTRY = LocateRegistry.getRegistry();
    }

    public Registry getRegistry() {
        return this.REGISTRY;
    }

    public Boolean isBound() { 
    	return this.BOOLEANBIND; 
    }
}