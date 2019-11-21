/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
 * Auteurs: 										        +
 * Lenny Siemeni									        +
 * Ali Chams Eddine Touil						            +
 * Classe: Dictionnaire.java						     	+
 * Ce programme un correcteur d'orthographe simple. Il      +
 * permet : 												+
 * _ A un utilisateur de taper un texte a l'aide d'une      +
 * interface graphique										+
 * _ De lire/enregistrer/modifier un texte de le corriger   +
 * faire verifier par un correcteur d'orthographe.          +
 * _ D'enregistrer ces modifications dans un fichier        +
 * _ De creer un dictionnaire a partir d'un fichier et      +
 * _ et d'utiliser celui-ci comme reference pour verifier   +
 * l'orthographe.                                           +
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
 */

//package tp2

import java.util.Scanner;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.*;

//La classe dictionnaire
class Dictionnaire extends Gui {
	protected static ArrayList<String> tabMots = new ArrayList<String>();
	protected String unMot;
	//protected File nomFichier;
	
	//Constructeur du dictionnaire
	public Dictionnaire(){
		this.unMot = unMot;
	}
	
	public static ArrayList<String> getTabMots(){
		return tabMots;
	}
	
	public static ArrayList<String> creerDictionnaire(File nomFichier){
		int	n = 0;
		FileReader fr = null;
		try {
			fr = new FileReader (nomFichier) ;
		}catch (FileNotFoundException e) {
			System.err.printf("Erreur lors de la lecture du fichier " +nomFichier);
			System.exit(1);
		}
		BufferedReader inputData = new BufferedReader(fr);
		boolean EOF = false;
		try{
			while(!EOF){
				String ligneLue = inputData.readLine();
				if(ligneLue == null)
					EOF = true;
				else{
					String unMot = inputData.readLine();
					tabMots.add(unMot);
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la lecture du fichier. Le fichier '" +nomFichier+"' peut etre corrompu.  "+e.toString());
			System.exit(1);
		}
		try {
			inputData.close();
		} catch (IOException e) {
			System.err.println("Erreur lors de la fermeture du fichier: " +nomFichier+" "+e.toString());
			System.exit(1);
		}
			System.out.println("Dictionnaire importe avec succes.");
			System.out.println(tabMots.size()+" mots dans le dictionnaire.");
		return tabMots;
	}

	//methode permettant de lire un fichier .txt
	//et d'en importer son contenu dans le textArea de l'interface utilisateur
	public static void lire(File nomFichier){
		FileReader fr = null;
		try {
			fr = new FileReader (nomFichier) ;
		} catch ( FileNotFoundException e) {
		    System.err.println("Erreur lors de l'ouverture du fichier: " +nomFichier+"."+e.toString());
			System.exit(1);
		}			
		BufferedReader inputData = new BufferedReader(fr);
		boolean EOF = false;
		try {
			while(!EOF){
				String ligneLue = inputData.readLine();
				if (ligneLue == null){
					EOF = true ;
				} else {
					Gui.txt.append(ligneLue+"\n");
				}
			}
		} catch(IOException e) {
			System.err.println("Erreur lors de la lecture du fichier. Le fichier '" +nomFichier+"' peut etre corrompu.  "+e.toString());
			System.exit(1);
		}
		try {
			inputData.close();
		} catch (IOException e) {
			System.err.println("Erreur lors de la fermeture du fichier: " +nomFichier+" "+e.toString());
			System.exit(1);
		}
		
		System.out.println("Le fichier " + nomFichier + " a ete importe avec succes dans le programme!\n");
	}

	
	//Creer un fichier .txt et y ecrit tout le contenu du textArea principal
	public static void enregistrer(File nomFichier){
		try{
			OutputStreamWriter aCreer = new OutputStreamWriter(new FileOutputStream(nomFichier));
			aCreer.write(Gui.txt.getText().toString());
	    	aCreer.close();
			System.out.println("Le fichier '" + nomFichier + "' a ete cree avec succes!");
		}catch(IOException e){
			System.err.println("Erreur lors de la creation du fichier");
			System.exit(1);
		}
	}
}