package it.unibs.progettoarnaldo.tamagolem;

import java.util.ArrayList;

public class TamaGolem {
	
	private int vita;
	private String nome;
	private ArrayList<Pietre> mettiPietre;
	
	public TamaGolem(int vita, String nome, ArrayList<Pietre> mettiPietre) {
		this.vita = vita;
		this.nome = nome;
		this.mettiPietre = mettiPietre;
	}

	public int getVita() {
		return vita;
	}
	public void setVita(int vita) {
		this.vita = vita;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ArrayList<Pietre> getMettiPietre() {
		return mettiPietre;
	}
	public void setMettiPietre(ArrayList<Pietre> mettiPietre) {
		this.mettiPietre = mettiPietre;
	}
	
	/**
	 * Creazione dei golem
	 * @param elementi
	 * @return
	 */
	public TamaGolem creaGolem(ArrayList<Elementi> elementi) {
		
		int max = 0;
		ArrayList<Pietre> pietre = new ArrayList<Pietre>();
		ArrayList<Integer> potenze = new ArrayList<Integer>();
		//setto la vita massima dei golem al danno massimo degli elementi
		for(int i = 0; i < elementi.size(); i++) {
			potenze = (ArrayList<Integer>)elementi.get(i).getPotenze().clone();
			for(int j = 0; j < potenze.size(); j++) {
				if(potenze.get(j)>max) {
					max = potenze.get(j);
				}
			}
		}
		String nome = InputDati.leggiStringaNonVuota("\nInserisci il nome del golem: ");
        TamaGolem golem = new TamaGolem(max, nome, pietre);
        return golem;
	}

	
	public String toString() {
		
		String frase = "nome=" + nome + ", vita=" + vita;
		for(int i = 0; i < mettiPietre.size(); i++) {
			frase += " " + mettiPietre.get(i).getElemento() + " ";
		}
		return frase;
	}
	
}
