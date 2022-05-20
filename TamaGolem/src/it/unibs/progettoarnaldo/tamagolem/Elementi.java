package it.unibs.progettoarnaldo.tamagolem;

import java.util.ArrayList;

public class Elementi {
	
	private String nome;
	private ArrayList<Integer> potenze;
	private ArrayList<Integer> debolezze;
	private ArrayList<String> elementoForte;
	private ArrayList<String> elementoDebole;
	
	public Elementi(String nome, ArrayList<Integer> potenze, ArrayList<Integer> debolezze, ArrayList<String> elementoForte, ArrayList<String> elementoDebole) {
		this.nome = nome;
		this.potenze = potenze;
		this.debolezze = debolezze;
		this.elementoForte = elementoForte;
		this.elementoDebole = elementoDebole;
	}

	public ArrayList<Integer> getPotenze() {
		return potenze;
	}
	
	public void setPotenze(ArrayList<Integer> potenze) {
		this.potenze = potenze;
	}

	public ArrayList<Integer> getDebolezze() {
		return debolezze;
	}

	public void setDebolezze(ArrayList<Integer> debolezze) {
		this.debolezze = debolezze;
	}

	public String getNome() {
		return nome;
	}
	 
	public ArrayList<String> getElementoForte() {
		return elementoForte;
	}

	public void setElementoForte(ArrayList<String> elementoForte) {
		this.elementoForte = elementoForte;
	}
	
	public ArrayList<String> getElementoDebole() {
		return elementoDebole;
	}
	
	public void setElementoDebole(ArrayList<String> elementoDebole) {
		this.elementoDebole = elementoDebole;
	}
	
	@Override
	public String toString() {
		String frase = "1: nome=" + nome + ", Elementi forti: ";
		for(int i = 0; i < elementoForte.size(); i++) {
			frase += elementoForte.get(i) + " con potenza: " + potenze.get(i) + ", ";
		}
		frase += "Elementi deboli: ";
		for(int j = 0; j < elementoDebole.size(); j++) {
			frase += elementoDebole.get(j) + " con debolezza: " + debolezze.get(j) + ", ";
		}
		return frase;
	}
}
