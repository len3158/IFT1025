/* +++++++++++++++++++++++++++++++++++++++++++++++++
 * Auteurs: 										  +
 * Lenny Siemeni									  +
 * Ali Chams Eddine Touil						  +
 * Classe: Personnage.java								  +
 * Ce programme est un jeu de labyrynthe invisible.+
 * +++++++++++++++++++++++++++++++++++++++++++++++++
 */

public class Personnage extends Labyrinthe {	
	private int positionI;
	private int positionJ;
	private int nbVie;
	
	//Constructeur du Personnage
	public Personnage(Labyrinthe l,int i, int j, int vie){
		super(l);
		this.positionI=i;
		this.positionJ=j;
		this.nbVie=vie;
		
	}
	
	//methode qui retourne la position en i du personnage
	public int getPositionI(){
		return this.positionI;
	}
	
	//methode qui retourne la position en j du personnage
	public int getPositionJ(){
		return this.positionJ;
	}
	
	//methode d'acces au nombre de vies du personnage
	public int getNbVie(){
		return this.nbVie;
	}
	
	//methode qui modifie la position en i du personnage
	public int setPositionI(int i){
		return this.positionI=i;
	}
	
	//methode qui modifie la position en j du personnage
	public int setPositionJ(int j){
		return this.positionJ=j;
	}
	
	//methode qui modifie le nombre de vie du personnage
	public int setNbVie(int vieRestante){
		return this.nbVie=vieRestante;
	}
}
