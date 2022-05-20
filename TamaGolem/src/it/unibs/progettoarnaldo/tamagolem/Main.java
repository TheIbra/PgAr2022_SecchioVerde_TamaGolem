package it.unibs.progettoarnaldo.tamagolem;

import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		
		int scelta;
		int nuovaPartita = 1;
		ArrayList<Elementi> elementi = new ArrayList<Elementi>();
		ArrayList<String> forte = new ArrayList<String>();
		ArrayList<String> debole = new ArrayList<String>();
		ArrayList<Integer> potenze = new ArrayList<Integer>();
		ArrayList<Integer> debolezze = new ArrayList<Integer>();
		//creazione degli elementi
		elementi.add(new Elementi("Sabbia", potenze, debolezze, forte, debole));
		elementi.add(new Elementi("Nebbia", potenze, debolezze, forte, debole));
		elementi.add(new Elementi("Nuvola", potenze, debolezze, forte, debole));
		elementi.add(new Elementi("Magma", potenze, debolezze, forte, debole));
		elementi.add(new Elementi("Foglia", potenze, debolezze, forte, debole));
		Equilibrio equilibrio = new Equilibrio();
		Scontro scontro = new Scontro();
		
        //Menu per interagire con l'utente
        do {
        	System.out.println("Benvenuto nel menu' di combattimento!\n");
            scelta = InputDati.leggiInteroNonNegativo("1)Nuova partita\n"
                    + "2)Esci dal programma\n");
        } while(scelta != 1 && scelta != 2);

        switch(scelta) {
        	case 1: 
        		do {
        			//creazione equilibrio
        			equilibrio.creaEquilibrio(elementi, forte, debole, potenze, debolezze);
        			//scontro
            		scontro.partita(elementi);
            		//visualizzazione dell'equilibrio
            		System.out.println("\nVISUALIZZAZIONE EQUILIBRIO\n");
            		for(int i = 0; i < elementi.size(); i++) {
            			System.out.println(elementi.get(i).toString());
            		}
            		//scelta per iniziare una nuova partita a fine scontro
            		do {
            			nuovaPartita = InputDati.leggiInteroNonNegativo("\n1)Nuova partita\n"
                                + "2)Esci dal programma\n");
            		}while(nuovaPartita != 1 && nuovaPartita != 2); 
        		}while(nuovaPartita != 2);
        		break;
        	case 2:
        		System.out.println("\nCiao!");
        		System.exit(0);
        		break;
        }
	}

}














