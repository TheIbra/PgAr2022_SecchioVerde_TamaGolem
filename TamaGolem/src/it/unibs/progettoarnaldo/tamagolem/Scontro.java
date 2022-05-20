package it.unibs.progettoarnaldo.tamagolem;

import java.util.ArrayList;
import java.util.Random;

public class Scontro {

	Random rand = new Random();

	/**
	 * Metodo per svolgere la partita
	 * @param elementi
	 */
	public void partita(ArrayList<Elementi> elementi) {
		
		int cntPietre1 = 0;
		int cntPietre2 = 0;
		int posElemento1 = 0; 
		int posElemento2 = 0;
		int nElementi = elementi.size();
		int posizione1;
		int posizione2;
		int quantitaPietre = ((nElementi + 1) / 3) + 1;
		int quantitaGolem = ((nElementi - 1)*(nElementi - 2) / (2*quantitaPietre));
		ArrayList<Pietre> scorta = new ArrayList<Pietre>();
		ArrayList<TamaGolem> giocatore1 = new ArrayList<TamaGolem>(quantitaGolem);
		ArrayList<TamaGolem> giocatore2 = new ArrayList<TamaGolem>(quantitaGolem);
		ArrayList<String> forti = new ArrayList<String>(elementi.size());
		ArrayList<String> deboli = new ArrayList<String>(elementi.size());
		TamaGolem golem = new TamaGolem(0,"",null);
		boolean fine = false;
		String elemento1 = "";
		String elemento2 = "";
		String nome1 = "";
		String nome2 = "";
		
		//riempio la scorta di pietre totale
		riempiScorta(elementi, scorta, quantitaPietre, quantitaGolem);
		//richiesta all'utente di inserire i nomi dei giocatori
		nome1 = InputDati.leggiStringaNonVuota("\nInserisci nome del primo giocatore: ");
		nome2 = InputDati.leggiStringaNonVuota("\nInserisci nome del secondo giocatore: ");
		//creo i golem per entrambi i giocatori
		for(int i = 0; i < quantitaGolem; i++) {
			System.out.println("Creazione del golem numero " + (i + 1) + " di " + nome1);
			giocatore1.add(golem.creaGolem(elementi));
		}
		for(int i = 0; i < quantitaGolem; i++) {
			System.out.println("Creazione del golem numero " + (i + 1) + " di " + nome2);
			giocatore2.add(golem.creaGolem(elementi));
		}
		//evocazione primo golem per i 2 giocatori
		System.out.println("evocazione golem per " + nome1);
		posizione1 = evocazione(elementi, scorta, giocatore1, quantitaPietre);
		System.out.println("evocazione golem per " + nome2);
		posizione2 = evocazione(elementi, scorta, giocatore2, quantitaPietre);
		System.out.println(giocatore1.get(posizione1).toString() + ", pietra corrente: " + giocatore1.get(posizione1).getMettiPietre().get(cntPietre1).getElemento());  //visualizzazione golem
		System.out.println(giocatore2.get(posizione2).toString() + ", pietra corrente: " + giocatore2.get(posizione2).getMettiPietre().get(cntPietre2).getElemento());   //visualizzazione golem
		
		do {
			//cerco gli elementi delle pietre correnti
			elemento1 = giocatore1.get(posizione1).getMettiPietre().get(cntPietre1).getElemento();
			elemento2 = giocatore2.get(posizione2).getMettiPietre().get(cntPietre2).getElemento();
			//trovo la posizione dell'elemento della pietra 1 nell'array di elementi e clono gli array forti e deboli di quell'elemento
			for(posElemento1 = 0; posElemento1 < elementi.size(); posElemento1++) {
				if(elemento1.equals(elementi.get(posElemento1).getNome())) {                              
					forti = (ArrayList<String>)elementi.get(posElemento1).getElementoForte().clone();
					deboli = (ArrayList<String>)elementi.get(posElemento1).getElementoDebole().clone();
					break;
				}
			}
			//controllo che gli elementi siano diversi
			if(!elemento1.equals(elemento2)) {
				//controllo se il secondo elemento è tra gli elementiForti del primo
				for(posElemento2 = 0; posElemento2 < forti.size(); posElemento2++){
					if(elemento2.equals(forti.get(posElemento2))) {
						//il secondo golem subisce danno pari alla potenza nella stessa posizione dell'elemento della sua pietra nell'array forti
						giocatore2.get(posizione2).setVita(giocatore2.get(posizione2).getVita() - elementi.get(posElemento1).getPotenze().get(posElemento2));
						System.out.println(giocatore2.get(posizione2).getNome() + " le ha prese da " + giocatore1.get(posizione1).getNome() 
								+ " scendendo a " + giocatore2.get(posizione2).getVita() + " di vita ");
						break;
					}
				}
				//controllo se il secondo elemento è tra gli elementiDeboli del primo
				for(posElemento2 = 0; posElemento2 < deboli.size(); posElemento2++){
					if(elemento2.equals(deboli.get(posElemento2))) {
						//il primo golem subisce danno pari alla debolezza nella stessa posizione dell'elemento della sua pietra nell'array deboli
						giocatore1.get(posizione1).setVita(giocatore1.get(posizione1).getVita() - elementi.get(posElemento1).getDebolezze().get(posElemento2));
						System.out.println(giocatore1.get(posizione1).getNome() + " le ha prese da " + giocatore2.get(posizione2).getNome() 
								+ " scendendo a " + giocatore1.get(posizione1).getVita() + " di vita ");
						break;
					}
				}
			}
			else {
				//se gli elementi delle 2 pietre sono uguali non succede niente
				System.out.println("Gli elementi delle 2 pietre sono uguali, non succede nulla!");
			}
			//aumento o resetto i contatori dell pietre
			if(cntPietre1 < quantitaPietre-1) {   
				cntPietre1++;
			}
			else {
				cntPietre1 = 0;
			}
			if(cntPietre2 < quantitaPietre-1) {
				cntPietre2++;
			}
			else {
				cntPietre2 = 0;
			}
			//controllo se il golem del giocatore 1 è ko 
			if(giocatore1.get(posizione1).getVita() <= 0) {
				System.out.println(giocatore1.get(posizione1).getNome() + " è stato sfondato ");
				giocatore1.remove(posizione1);
				//controllo se sono finiti i golem del giocatore 1
				if(giocatore1.size() == 0) {
					System.out.println(nome2 + " ha sfondato " + nome1 + ", troppo facile!");
					fine = true;
				}
				//evoco il nuovo golem
				else {
					System.out.println("evocazione nuovo golem per " + nome1);
					posizione1 = evocazione(elementi, scorta, giocatore1, quantitaPietre);
					cntPietre1 = 0;
				}
			}
			//controllo se il golem del giocatore 2 è ko 
			else if(giocatore2.get(posizione2).getVita() <= 0) {
				System.out.println(giocatore2.get(posizione2).getNome() + " è stato sfondato ");
				giocatore2.remove(posizione2);
				//controllo se sono finiti i golem del giocatore 2
				if(giocatore2.size() == 0) {
					System.out.println(nome1 + " ha sfondato " + nome2 + ", troppo facile!");
					fine = true;
				}
				//evoco il nuovo golem
				else {
					System.out.println("evocazione nuovo golem per " + nome2);  
					posizione2 = evocazione(elementi, scorta, giocatore2, quantitaPietre);
					cntPietre2 = 0;
				}
			}
			if(!fine) {
				//stampo le pietre correnti 
				System.out.println(giocatore1.get(posizione1).toString() + ", pietra corrente: " + giocatore1.get(posizione1).getMettiPietre().get(cntPietre1).getElemento());  //visualizzazione golem
				System.out.println(giocatore2.get(posizione2).toString() + ", pietra corrente: " + giocatore2.get(posizione2).getMettiPietre().get(cntPietre2).getElemento());   //visualizzazione golem
				InputDati.leggiStringa("\nPremere un tasto per passare al turno successivo \n");
			}
			
		}while(!fine);
		
	}
	
	/**
	 * Metodo per riempire la scorta di pietre
	 * @param elementi
	 * @param scorta
	 * @param nPietre
	 * @param nGolem
	 */
	public void riempiScorta(ArrayList<Elementi> elementi, ArrayList<Pietre> scorta, int nPietre, int nGolem) {  
		
		int cnt = 0;
		int random;
		int rimasti=elementi.size();
		int nPietreInserite = 0;
		int totPietre = (2*nGolem*nPietre);
		int max = nPietre;
		int temp = 0;
		if(totPietre % elementi.size() != 0) {
			temp = (totPietre/elementi.size() + 1)*elementi.size() - totPietre;
		}
		//inserisco tante pietre quante sono i golem moltiplicate per il numero di pietre che contengono, in modo che abbiano tutte la stessa quantità o al massimo 1 in meno,
		//quelli che ne hanno una in meno vengono scelti casualmente ogni partita con un rand 
		for(int i = 0; i < elementi.size(); i++) {
			cnt = 0;
			max = nPietre;
			if(rimasti*(nPietre-1) == totPietre-nPietreInserite) {                                  
				random = 0;
			}
			else {
				random = rand.nextInt(2);
			}
			if(random == 0) {
				temp--;
				if(temp >= 0) {
					max--;
				}
			}
			do {
				scorta.add(new Pietre(elementi.get(i).getNome()));
				cnt++;
				nPietreInserite++;
			}while(cnt < max);
			rimasti--;
		}
	}
	
	/**
	 * Metodo per evocare i golem
	 * @param elementi
	 * @param scorta
	 * @param golem
	 * @param nPietre
	 * @return
	 */
	public int evocazione(ArrayList<Elementi> elementi, ArrayList<Pietre> scorta, ArrayList<TamaGolem> golem, int nPietre) {
		
		int pos;
		int j;
		boolean controllo = false;
		String elementoPietra = "";
		ArrayList<Pietre> pietreIngurgitate = new ArrayList<Pietre>();
		//stampo i nomi dei golem disponibili del giocatore
		for(int i = 0; i < golem.size(); i++) {
			System.out.println((i) + " - " + golem.get(i).getNome());
		}
		//faccio scegliere il golem
		do {
			pos = InputDati.leggiIntero("scegli golem");
			if(pos < 0 || pos >= golem.size()) {
				System.out.println("Bella mossa! Peccato non sia disponibile :) reinseriscilo pivello");
			}
		}while(pos < 0 || pos >= golem.size());
		//faccio inserire l'elemento delle pietre del golem
		for(int i = 0; i < nPietre; i++) {
			for(int k = 0; k < scorta.size(); k++) {
				System.out.print(scorta.get(k).getElemento() + " - ");
			}
			controllo = false;
			do {
				elementoPietra = InputDati.leggiStringaNonVuota("\nInserisci l'elemento della pietra da inserire: ");
				for(int l = 0; l < elementi.size(); l++) {
					//controllo che l'elemento inserito esista
					if(elementoPietra.equals(elementi.get(l).getNome())) {
						controllo = true;
						break;
					}
				}
				if(!controllo) {
					System.out.println("l'elemento inserito non esiste, inserirne uno esistente");
				}
			}while(!controllo);
			
			//trovo la posizione nella scorta dell'elemento della pietra inserita
			for(j = 0; j < scorta.size(); j++) {
				if(scorta.get(j).getElemento().equals(elementoPietra)) {
					break;
				}
			}
			//rimuovo l'elemento
			scorta.remove(j);
			pietreIngurgitate.add(new Pietre(elementoPietra));
		}	
		golem.get(pos).setMettiPietre(pietreIngurgitate);
		return pos;
	}
	
}
