/* +++++++++++++++++++++++++++++++++++++++++++++++++
 * Auteurs: 										  +
 * Lenny Siemeni									  +
 * Ali Chams Eddine Touil						  +
 * Classe: Laby.java								  +
 * Ce programme est un jeu de labyrynthe invisible.+
 * +++++++++++++++++++++++++++++++++++++++++++++++++
 */

import java.util.Scanner;

class Laby {
//Fonction affichant le nombre de vies restantes
//Ainsi que la direction a prendre
public static void demanderAction(Personnage p, int nbVies){
	System.out.println("Il vous reste "+p.getNbVie()+" vies sur "+nbVies+".\n");
	System.out.println("Quelle direction souhaitez-vous prendre?");
	System.out.println("droite: d; gauche: g ou s; haut: h ou e; bas: b ou x;");
}

//Fonction permettant de controler les mouvements du personnage
public static int controlePerso(Labyrinthe l, Labyrinthe lInvi,Personnage p, int hauteur, int largeur, int duree, float densite, int nbVies){
	int nbViesReset = p.getNbVie();
	
	//On continue de jouer tant nbVies >0
	//nbVies a recuperer de l'objet personnage p
	while(p.getNbVie()>0){
		demanderAction(p, nbVies);
		
		//'d,s,g,h,e,b,x' pour les commandes de base
		//'q,p,v' pour les commandes en godmode
		Scanner controle = new Scanner(System.in);
	    char c = controle.next().charAt(0);
	    
		//Gere les controles du joueur
		switch (c) {
			case 'd': //dans le cas ou le joueur veut se deplacer a droite
				if(l.aMuretADroite(p.getPositionI(), p.getPositionJ())){
					p.setNbVie(p.getNbVie()-1);
					lInvi.dessineMuretVertical(p.getPositionI(), p.getPositionJ()+1);
					lInvi.effaceEcran();
					System.out.println(lInvi.affiche());
				} else if(lInvi.aSortieADroite(p.getPositionI(), p.getPositionJ())){
					return 0; //Si on a trouve la sortie, le jeu est termine
							//On quitte la boucle while et traitement message fin de jeu
				} else {
					lInvi.effacePersonnage(p);
					p.setPositionJ(p.getPositionJ()+1);
					lInvi.dessinePersonnage(p);
					lInvi.effaceEcran();
					System.out.println(lInvi.affiche());
				}
				break;
			case 'g':
			case 's': //dans le cas ou le joueur veut se deplacer a gauche
				if(l.aMuretAGauche(p.getPositionI(), p.getPositionJ())){
					p.setNbVie(p.getNbVie()-1);
					lInvi.dessineMuretVertical(p.getPositionI(), p.getPositionJ());
					lInvi.effaceEcran();
					System.out.println(lInvi.affiche());
				} else if(lInvi.aEntreeAGauche(p.getPositionI(), p.getPositionJ())){
						lInvi.effaceEcran();
						System.out.println(lInvi.affiche());
						System.out.println("Erreur vous ne pouvez pas sortir par l'entree.\n");
				} else {
					lInvi.effacePersonnage(p);
					p.setPositionJ(p.getPositionJ()-1);
					lInvi.dessinePersonnage(p);
					lInvi.effaceEcran();
					System.out.println(lInvi.affiche());
					
				}
				break;
			case 'h':
			case 'e': //dans le cas ou le joueur veut se deplacer vers le haut
				if(l.aMuretEnHaut(p.getPositionI(), p.getPositionJ())){
				    p.setNbVie(p.getNbVie()-1);
				    lInvi.effaceEcran();
					lInvi.dessineMuretHorizontal(p.getPositionI(), p.getPositionJ());
					System.out.println(lInvi.affiche());
				} else {
					lInvi.effacePersonnage(p);
					p.setPositionI(p.getPositionI()-1);
					lInvi.effaceEcran();
					lInvi.dessinePersonnage(p);
					System.out.println(lInvi.affiche());
				}
				break;
			case 'b':
			case 'x'://dans le cas ou le joueur veut se deplacer vers le bas
				if(l.aMuretEnbas(p.getPositionI(), p.getPositionJ())){
					p.setNbVie(p.getNbVie()-1);
					lInvi.effaceEcran();
					lInvi.dessineMuretHorizontal(p.getPositionI()+1, p.getPositionJ());
					System.out.println(lInvi.affiche());
				} else {
					lInvi.effacePersonnage(p);
					p.setPositionI(p.getPositionI()+1);
					lInvi.effaceEcran();
					lInvi.dessinePersonnage(p);
					System.out.println(lInvi.affiche());
					
				}
				break;
			case 'q'://Si le joueur souhaite interrompre le jeu
				System.out.println("Le programme 'Laby.java' a ete quitte.");
				System.exit(0);
				break;
			case 'p'://Permet de creer un nouveau labyrinthe
				lInvi.effaceEcran();
				System.out.println("Nouvelle partie");
				JeuLabiryntheInvisible(hauteur, largeur, densite, duree, nbVies);
				//Condition sentinelle pour forcer l'arret de la fonction
				//En cas de lancement d'une nouvelle partie
				if(true) {
					return 0;
				}
			case 'v'://Permet de continuer a jouer mais avec le Labyrinthe visible
				l.effaceEcran();
				System.out.println("Godmode activated.");
				lInvi = l;
				System.out.println(l.affiche());
				controlePerso(lInvi, l, p, hauteur, largeur, duree, densite, nbVies);
				break;
			default:
				break;
		}
		
	}//Fin du while(nbVie>0)
	return 0;
}

//Fonction permettant de faire une pause dans le jeu
public static void sleep(int duree){
	try{
		Thread.sleep(duree);
	}
	catch (InterruptedException e) {
		System.out.println("Sleep interrompu");
	}
}

//Fonction appele uniquement en fin de partie
//Si le joueur a epuise toutes ses vies ou si il a trouve la sortie
public static void finPartie(int nbVies, Personnage p, Labyrinthe l){
	//Afficher le message lorsque l'usager termine sa partie
	switch (p.getNbVie()) {
		case 0:
			System.out.println("Vous avez perdu, vous avez épuisé vos "+nbVies+" vies!");
			break;
		default:
			System.out.println("Bravo, vous êtes parvenu jusqu'à la sortie en commettant seulement "+ (nbVies-p.getNbVie()+" erreurs."));
			break;
	}
}

//Fonction demarrant le programme
public static void JeuLabiryntheInvisible(int hauteur, int largeur, float densite, int duree, int nbVies){
	Labyrinthe l = new Labyrinthe(hauteur, largeur);
	l.construitLabyrintheAleatoire(densite);
	Labyrinthe lInvi = new Labyrinthe(l);
	Personnage p = new Personnage(l,l.getPositionEntree(), 0, nbVies);
	l.dessinePersonnage(p);
	System.out.println(l.affiche());
	sleep(duree);
	l.effaceEcran();
	lInvi.dessinePersonnage(p);
	System.out.println(lInvi.affiche());
	controlePerso(l,lInvi,p,hauteur, largeur, duree, densite, p.getNbVie());
	l.effaceEcran();
	finPartie(nbVies,p, l);
	
}

//Fonction main
	public static void main(String[] args) {
		String reponse;
	do {
			//Si la commande dans la console est mauvaise, le programme ne s'execute pas plus loin
			if(args.length != 5){
				System.out.println("Nombre de parametre incorrects.");
				System.out.println("Utilisation:java Laby <hauteur> <largeur> <densite> <duree visible> <nb vies>");
				System.out.println("Ex:java Laby 10 20 0.20 10 5");
				System.exit(0);
			}
			//Parsing des arguments de la console
			int hauteur = Integer.parseInt(args[0]), largeur=Integer.parseInt(args[1]),  duree=Integer.parseInt(args[3]), nbVies=Integer.parseInt(args[4]);
			float densite = Float.parseFloat(args[2]);
			JeuLabiryntheInvisible(hauteur, largeur, densite, duree, nbVies);
			
			System.out.println("Voulez-vous jouer une nouvelle partie?(Tapez: oui/non)");
			Scanner input = new Scanner(System.in);
			reponse = input.nextLine();
		} while (reponse.equalsIgnoreCase("oui"));//Tant que la reponse est oui on continue de jouer
		System.out.println("Le programme 'Laby.java' a été quitte.");
		System.exit(0);
		}
	}
