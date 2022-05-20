package it.unibs.progettoarnaldo.tamagolem;

import java.util.ArrayList;
import java.util.Random;


public class Equilibrio {

	private static final int VALORE_MAX = 5;
	Random rand = new Random();;
	
	/**
	 * Metodo per creare il nuovo equilibrio
	 * @param elementi
	 * @param forte
	 * @param debole
	 * @param potenze
	 * @param debolezze
	 */
	public void creaEquilibrio(ArrayList<Elementi> elementi, ArrayList<String> forte, ArrayList<String> debole, ArrayList<Integer> potenze, ArrayList<Integer> debolezze) {
		
		int somma;
		int sommaTemp;
		int temp;
		int random = 0;
		int rimasti = 0;
		int cntTemp;
		boolean controllo = false;
		//Dichiarazione della matrice che rappresenta le interazioni tra gli elementi, potrà contenere valori positivi e negativi, i positivi indicano che l'elemento in quella riga è forte contro l'elemento della colonna, i negativi il viceversa.
		int[][] matricePazza = new int[elementi.size()][elementi.size()];
		
		//inserimento di valori casuali all'interno della matrice, rappresenteranno le potenze tra i vari elementi
		for(int i = 0; i < elementi.size(); i++ ) {
			rimasti = elementi.size();
			somma = 0;
			for(int j = 0; j < elementi.size(); j++ ) {
				sommaTemp = 0;
				cntTemp = 0;
				controllo = false;
				if(elementi.size() - j == 2) {
					sommaTemp = somma;
				}
				//controllo se il valore nella posizione i,j è già presente o se deve rimanere nullo
				if(i == j || matricePazza[i][j] != 0) {
					somma += matricePazza[i][j];
				}
				//aggiungo il nuovo valore
				else {
					//controllo per aggiungere l'ultimo alemento in modo da rispettare la somma
					if((elementi.size()-j) == 1) {
						random = 0 - somma;
					}
					else {
						//controllo se il valore da aggiungere può assumere qualsiasi valore per assicurare che la somma faccia 0, in questo caso controllo che non possano essere inseriti valori troppo alti
						if(somma>0) {
							for(int k = VALORE_MAX; k >= VALORE_MAX*(-1); k--) {
								if(somma + k > (VALORE_MAX*(rimasti-1))) {
									controllo = true;
									cntTemp++;
								}
								else {
									break;
								}
							}
							//aggiunta di un valore casuale tra tutti quelli possibili
							if(!controllo) {
								do {
									random = rand.nextInt(2*VALORE_MAX + 1) - VALORE_MAX;
									temp = sommaTemp + random;								
									}while(random == 0 || temp == 0 || sistemaMatrice(matricePazza, i, j, elementi.size(), random));
							}
							//aggiunta di un valore casuale ridotta al valore massimo che non renda impossibile arrivare alla somma di 0
							else {
								do {
									random = rand.nextInt(2*VALORE_MAX + 1 - (cntTemp + 1)) - VALORE_MAX;
									temp = sommaTemp + random;
								}while(random == 0 || temp == 0 || sistemaMatrice(matricePazza, i, j, elementi.size(), random));
							}
						}
						//controllo se il valore da aggiungere può assumere qualsiasi valore per assicurare che la somma faccia 0, in questo caso controllo che non possano essere inseriti valori troppo bassi
						else if(somma<0) {
							for(int k = VALORE_MAX*(-1); k <= VALORE_MAX; k++) {
								if(somma + k < (VALORE_MAX*(rimasti-1))*(-1)) {
									controllo = true;
									cntTemp++;
								}
								else {
									break;
								}
							}
							//aggiunta di un valore casuale tra tutti quelli possibili
							if(!controllo) {
								do {
									random = rand.nextInt(2*VALORE_MAX + 1) - VALORE_MAX;   
									temp = sommaTemp + random;
								}while(random == 0 || temp == 0 || sistemaMatrice(matricePazza, i, j, elementi.size(), random));
							}
							//aggiunta di un valore casuale ridotta al valore minimo che non renda impossibile arrivare alla somma di 0
							else {
								do {
									random = rand.nextInt(2*VALORE_MAX + 1 - (cntTemp + 1)) - VALORE_MAX + (cntTemp +1);    // il più 1 probabilmente si può togliere
									temp = sommaTemp + random;
								}while(random == 0 || temp == 0 || sistemaMatrice(matricePazza, i, j, elementi.size(), random));
							}
						}
						//se la somma dei valori già inseriti nella riga fa 0 può essere inserito qualsiasi valore
						else {
							do {
								random = rand.nextInt(2*VALORE_MAX + 1) - VALORE_MAX;
								temp = sommaTemp + random;
							}while(random == 0 || temp == 0 || sistemaMatrice(matricePazza, i, j, elementi.size(), random));
						}
					}
					somma += random;
					sommaTemp += somma;
					//inserisco il valore nella matrice nella posizione i,j
					matricePazza[i][j] = random;
					//inserisco il corrispettivo di segno opposto nella posizione j,i
					matricePazza[j][i] = random*(-1);
				}	
				rimasti--;
			}
		}
		//stampa matrice
		/*
		for(int i = 0; i < elementi.size(); i++ ) {
			for(int j = 0; j < elementi.size(); j++ ) {
				if(j == elementi.size() - 1) {
					System.out.print(matricePazza[i][j]);

				}
				else {
					System.out.print(matricePazza[i][j] + " | ");
				}
			}
			System.out.print("\n");
		}
		*/
		
		//inserisco i valori della matrice nei vettori degli elementi, se il valore in posizione i,j è positivo andrà nelle potenze e l'elemento corrispondente alla colonna negli elementiForti, se è negativo in debolezze ed elementiDeboli
		for(int i = 0; i < elementi.size(); i++ ) {
			creaInterazioni(elementi, forte, debole, potenze, debolezze, matricePazza[i], i);
		}
	}
	
	/**
	 * Metodo per riempire gli array di ciascun elemento con le interazioni tra di essi
	 * @param elementi
	 * @param forte
	 * @param debole
	 * @param potenze
	 * @param debolezze
	 * @param arrayFolle
	 * @param pos
	 */
	public void creaInterazioni(ArrayList<Elementi> elementi, ArrayList<String> forte, ArrayList<String> debole, ArrayList<Integer> potenze, ArrayList<Integer> debolezze, int[] arrayFolle, int pos) {
		
		String temp = "";
		potenze = (ArrayList<Integer>)elementi.get(pos).getPotenze().clone();
		debolezze = (ArrayList<Integer>)elementi.get(pos).getDebolezze().clone();
		forte = (ArrayList<String>)elementi.get(pos).getElementoDebole().clone();
		debole = (ArrayList<String>)elementi.get(pos).getElementoForte().clone();
		for(int i = 0; i < elementi.size(); i++ ) {
			if(arrayFolle[i] > 0) {
				potenze.add(arrayFolle[i]);
				elementi.get(pos).setPotenze(potenze);
				temp = elementi.get(i).getNome();
				forte.add(temp);
				elementi.get(pos).setElementoForte(forte);
			}
			else if(arrayFolle[i] < 0) {
				debolezze.add(arrayFolle[i]*(-1));
				elementi.get(pos).setDebolezze(debolezze);
				temp = elementi.get(i).getNome();
				debole.add(temp);
				elementi.get(pos).setElementoDebole(debole);
			}
		}	
	}	
	
	/**
	 * Metodo per assicurarmi che nella penultima riga ci siano valori che sommati non facciano 0, un numero maggiore del massimo o uno minore del minimo
	 * @param matrice
	 * @param riga
	 * @param colonna
	 * @param dim
	 * @param valore
	 * @return
	 */
	public boolean sistemaMatrice(int[][] matrice, int riga, int colonna, int dim, int valore) {
		
		boolean controllo = false;
		int somma = 0;
		
		//controllo che il corrispettivo del penultimo valore della terzultima colonna sia accettabile
		if(riga == dim-3 && colonna == dim-2) {
			for(int i = 0; i < colonna; i++) {
				somma += matrice[riga + 1][i];
			}
			if(somma - valore == 0 || somma - valore > VALORE_MAX || somma - valore < VALORE_MAX*(-1)) {
				controllo = true;
			}
		}
		return controllo;
	}
	
}