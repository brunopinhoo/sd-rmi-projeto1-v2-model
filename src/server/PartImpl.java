package server;

import java.util.Map;

import interfaces.Part;

public class PartImpl implements Part{
	
    private String codigo, descricao, nome;
    private Map<Part, Integer> subComponentes;

    public PartImpl(String nome, String desc, Map<Part, Integer> sub, String codigo){
        this.nome = nome;
        this.descricao = desc;
        this.subComponentes = sub;
        this.codigo = codigo;
    }

    public String getPeca() {
        StringBuilder str = new StringBuilder();
        str.append("\nSubcomponentes: ");

        for(Map.Entry<Part, Integer> entry : this.subComponentes.entrySet())
            str.append(entry.getKey().getNome()).append(": ").append(entry.getValue()).append("\n");

        return "Peca: " + this.codigo + "\nNome: " + this.nome + "\nDescricao: " + this.descricao + str;
    }
    
    public String getCodigo() {
    	return this.codigo;
    }

    public String getNome() {
    	return this.nome;
    }
}