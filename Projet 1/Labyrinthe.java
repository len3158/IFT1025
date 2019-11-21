/* +++++++++++++++++++++++++++++++++++++++++++++++++
 * Auteurs: 										  +
 * Lenny Siemeni									  +
 * Ali Chams Eddine Touil						  +
 * Classe: Labyrinthe.java								  +
 * Ce programme est un jeu de labyrynthe invisible.+
 * +++++++++++++++++++++++++++++++++++++++++++++++++
 */

public class Labyrinthe {
	private static final int LMURET = 8;
	private static final int HMURET = 4;
	private int hauteur;
	private int largeur;
	private char tabJeu [][]; //correspond a notre tableau de caractere 2d du jeu
	private int positionEntree;
	private int positionSortie;
	
//initialise le tableau des avec les parametres passes des le debut
	public Labyrinthe(int h, int w){
		this.hauteur=h;
		this.largeur=w;
		creeTableau(h,w);
		dessineMurdEnceinte();
	}
	/*methode constructeur qui prend en paramËtre un autre Labyrinthe et le
	recopie dans celui qui est crÈÈ.*/ 
	public Labyrinthe(Labyrinthe l){
	 creeTableau(l.hauteur,l.largeur);
	dessineMurdEnceinte();
	dessineOuvertureGauche(l.positionEntree);
	dessineOuvertureDroite(l.positionSortie);
	}

	//retourne la hauteur
	public int getHauteur(){
		return this.hauteur;
	}
	//retourne largeur 
	public int getLargeur(){
		return this.largeur;
	}
	
	public int getPositionEntree() {
		return this.positionEntree;
	}
	
	public int getPositionSortie() {
		return this.positionSortie;
	}

	//Cree tableau 2d de caracetre  de hauteur:hauteur et largeur:largeur et le remplit de caractere ' '. 
	public char [][] creeTableau(int hauteur, int largeur){
		//tabJeu est initialisÈ avec ses dimensions et un caractere vide a chaque indice.
		tabJeu=new char [(hauteur*HMURET+1)][ (largeur*LMURET+1)];
		 for(int i=0;i<tabJeu.length;i++){
			for (int j=0; j<tabJeu[i].length-1;j++){
				tabJeu[i][j]=' ';
			}
		}
		return tabJeu;
	}
	
	// initialise chacun des indices de tabJeu avec ' '.
	public void effaceTableau(){
		for(int i=0;i<tabJeu.length;i++){
			for(int j=0;j<tabJeu[i].length;j++){
				tabJeu[i][j]=' ';
			}
		}
		
	}
	
	//methode dessiant les murets de l'enceinte ferme
	public String dessineMurdEnceinte(){
		
		String value="";	//valeur qui contiendra le contenue du tableau
		for(int i=0;i<tabJeu.length;i++){  //intialisiation des murets '-' et '|'
			for(int j=0;j<tabJeu[i].length;j++){
				tabJeu[0][j]='-';
				tabJeu[tabJeu.length-1][j]='-';
				tabJeu[i][0]='|';
				tabJeu[i][tabJeu[i].length-1]='|';
			} 
		}
		for(int i=0;i<tabJeu.length;i++){
			for(int j=0;j<tabJeu[i].length;j++){
				// Ètablissement des quatres coins '+'
				if(i==0 && j==0 || i==tabJeu.length-1 && j==0 || i==0 && j==tabJeu[i].length-1 || i== tabJeu.length-1 && j==tabJeu[i].length-1){
				tabJeu[i][j]='+';
				}
				value+=tabJeu[i][j]; //ajout du contenue dans le tableau 2d tabJeu
			}
			value+="\n"; //Saut de ligne a chaque apres chaque row ajoutÈ
		}
		return value; //value contient le tableau 2d tabJeu maintenant
	}
	
	//methode qui cree une ouverture dans le muret ferme de l'enceinte correspondant a la sortie
	public String dessineOuvertureDroite(int j){
		/*itere sur le tableau 2d tabJeu a partir de la postion j et s'assure d'enlever HMURET
		  depuis cette position */
		String value=""; //value contenant le nouveau tabJeu avec une Ouverture crÈÈe
		int h=j*HMURET; //h sera la case ligne ou apparaitra une ouverture 
		//iteration et initialistaion de l'ouverture sur la derniere colonne de tabJeu
		for(int x=0;x<tabJeu.length;x++){
			for(int y=0;y<tabJeu[x].length;y++){
				if (x>=h+1 && x<h+HMURET && y== tabJeu[x].length-1)
					tabJeu[x][y]=' ';
				value+=tabJeu[x][y]; //chaque indice rentre dans value
			}
			value+="\n";	//saut de ligne
		}
		return value; //tabJeu avec ouverture droite
	}
	//methode qui cree une ouverture dans le muret ferme de l'enceinte correspondant a l'entree.
	public String dessineOuvertureGauche(int j){
		/*itere sur le tableau 2d tabJeu a partir de la postion j et s'assure d'enlever HMURET
		  depuis cette position, meme chose que methode dessineOuevrtureDroite sauf que l'ouverture sera premiere colonne du tabJeu */
		String value="";
		int h=j*HMURET;
		for(int x=0;x<tabJeu.length;x++){
			for(int y=0;y<tabJeu[x].length;y++){
				if (x>=h+1 && x<h+HMURET && y==0 )
					tabJeu[x][y]=' ';
				value+=tabJeu[x][y];
			}
			value+="\n";	
		}
		return value;
	}
	
	/*methode qui dessine des murets verticaux de taille HMURET 
	 a partir d'une postion i,j donnÈe en parametre*/
	public String dessineMuretVertical(int i, int j){
		int l=j*LMURET; //case colone ou muret verticale en haut sera cree 
		int h=i*HMURET; //case ligne ou muret verticale en haut sera cree
		String value=""; // tabJeu et ses nouveaux murets verticaux sera a l'interieur de value
		/*itere sur le tableau 2d tabJeu a partir de la postion i,j et s'assure de
		  crÈer HMURET depuis cette position */
		for(int x=0;x<tabJeu.length;x++){
			for(int y=0;y<tabJeu[x].length;y++){
				if(x>=h+1 && x<h+HMURET &&y==l  )
					tabJeu[x][y]='|'; //initialisation du muret
				value+=tabJeu[x][y]; // nouveau muret ajoutÈ a tabJeu
			}
			value+="\n"; //saut de ligne
		}

		return value; //tabJeu avec ses murets fraichement crÈÈ est retournÈ
	}
	
	/*methode qui dessine des murets horizentaux de taille LMURET
	 a partir d'une postion i,j donnÈe en parametre*/
	public String dessineMuretHorizontal(int i, int j){
		/*meme chose que dessineMuretVerticale sauf l'initilisation du muret */
		int l=j*LMURET;
		int h=i*HMURET;
		String value=""; 
		/*itere sur le tableau 2d tabJeu a partir de la postion i,j et s'assure de
		  crÈer HMURET depuis cette position */
		for(int x=0;x<tabJeu.length;x++){
			for(int y=0;y<tabJeu[x].length;y++){
				if(y>=l+1 && y<l+LMURET &&x==h  )
					tabJeu[x][y]='-';
				value+=tabJeu[x][y];
			}
			value+="\n";
		}
		return value;
	}
	
	/*methode qui verifie si a la postion i,j il y a un muret ou mur enceinte,
	a gauche du joueur*/
	
	protected boolean aMuretAGauche(int i,int j){
		int k=(int)(Math.ceil(HMURET/2));
		int positionA=i*HMURET+k;
		int positionB=j*LMURET;
		return tabJeu[positionA][positionB]=='|';
	}
	/*methode qui verifie si a la postion i,j il y a un muret ou mur enceinte,
	a droite du joueur*/
	protected boolean aMuretADroite(int i, int j){
		int k=(int)(Math.ceil(HMURET/2));
		int positionA=i*HMURET+k;
		int positionB=j*LMURET+LMURET;
		return tabJeu[positionA][positionB]=='|';
	}
	/*methode qui verifie si a la postion i,j il y a un muret ou mur enceinte,
	en Haut du joueur*/
	protected boolean aMuretEnHaut(int i,int j){
		int positionA=i*HMURET;
		int positionB=j*LMURET+HMURET;
		//tabJeu[positionA][positionB]='#';
		return tabJeu[positionA][positionB]=='-';
	}
	/*methode qui verifie si a la postion i,j il y a un muret ou mur enceinte,
	en bas du joueur*/
	protected boolean aMuretEnbas(int i,int j){
		int positionA=i*HMURET+HMURET;
		int positionB=j*LMURET+HMURET;
		return tabJeu[positionA][positionB]=='-';
	}
	
	/*methode qui verifie si a la postion i,j il y a une entree,
	a gauche du joueur*/
	protected boolean aEntreeAGauche(int i, int j){
		int k=(int)(Math.ceil(HMURET/2));
		int positionA=i*HMURET+k;
		//tabJeu[positionA][0+(2*HMURET)] = '#';
		return tabJeu[positionA][0]==' ' && tabJeu[positionA][0+(HMURET)] == '@';
	}
	
	/*methode qui verifie si ‡ la postion i,j il y a une sortie,
	a droite du joueur*/
	protected boolean aSortieADroite(int i, int j){
		int k=(int)(Math.ceil(HMURET/2));
		int positionA=i*HMURET+k;
		return tabJeu[positionA][tabJeu[positionA].length-1]==' ' && tabJeu[positionA][tabJeu[positionA].length-(HMURET+1)] == '@';
	}
	
	/*methode qui prend en parametre un objet de type personnage
	 et le dessine au centre de la case corresondant a sa postion*/
	public String dessinePersonnage(Personnage p){
		String value =""; //value ou tabJeu sera contenue avec le personnage
		int k=(int)(Math.ceil(HMURET/2));
		int l=(int)(Math.ceil(LMURET/2));
		int i= p.getPositionI()*HMURET; //permet d'avoir la case ligne ou personnage est
		int j= p.getPositionJ()*LMURET;	//permet d'avoir la case colonne ou personnage est
		//centralisation du personnage dans la case i,j
		int positionA=i+k;
		int positionB=j+l;
		//iteration pour avoir le tabJeu avec le personnage place au bon endroit 
		for(int x=0;x<tabJeu.length;x++){
			for(int y=0;y<tabJeu[x].length;y++){
				if(x==positionA && y==positionB) {
					tabJeu[x][y]='@';
				}
				value+=tabJeu[x][y]; //chaque indice de tabJeu sera dans value
			}
			value+="\n"; 
		}
		return value;// tabJeu avec personnage
	}
	/*methode qui prend en parametre un objet de type personnage
	 et l'efface de la case corresondant a sa postion*/
	public String effacePersonnage(Personnage p){
		String value =""; //value ou tabJeu sera contenue avec le personnage
		int k=(int)(Math.ceil(HMURET/2));
		int l=(int)(Math.ceil(LMURET/2));
		int i= p.getPositionI()*HMURET; //permet d'avo,ir la case ligne ou personnage est
		int j= p.getPositionJ()*LMURET;	//permet d'avoir la case colonne ou personnage est
		//centralisation du personnage dans la case i,j
		int positionA=i+k;
		int positionB=j+l;
		//iteration pour avoir le tabJeu avec le personnage placÈ au bon endroit 
		for(int x=0;x<tabJeu.length;x++){
			for(int y=0;y<tabJeu[x].length;y++){
				if(x==positionA && y==positionB) {
					System.out.println(x+";"+y);
					tabJeu[x][y]=' ';
				}
				value+=tabJeu[x][y]; //chaque indice de tabJeu sera dans value
			}
			value+="\n"; 
		}
		return value;// tabJeu avec personnage
	}
	
	//methode qui insere 200 lignes vides pour donner l'illusion d'efface l'ecran
	public void effaceEcran(){
		for(int i=0;i<=200;i++){
		 System.out.println();
		}
	}
	
	//Affiche la grille de Jeu 
	public String affiche(){
		String result="";
		for(int i=0;i<tabJeu.length;i++){
			for(int j=0;j<tabJeu[i].length;j++){
				result+=tabJeu[i][j];
			}
			result+="\n";
		}
		return result;
		
	}
	
	/*methode qui prend en parametre la densite et construit des murets 
	  aleatoirement et pratique une ouverture au hasard sur le mur d'enceinte
	   de droite.*/
	public void construitLabyrintheAleatoire(double densite){
		int random1=(int)(Math.ceil(Math.random()*hauteur-1));//random entree
		int random2=(int)(Math.ceil(Math.random()*hauteur-1));//random sortie
		//iteration sur tabJeu si densite plus grande que Math.random a chaque coup, on cree un muret vertical et horizental
		for(int i=0;i<tabJeu.length;i++){
			for(int j=0;j<tabJeu[i].length;j++){
				if(Math.random()<densite){
					dessineMuretVertical(i,j);
					dessineMuretHorizontal(i,j);
				}
					
			}
		}
		positionEntree=random1; //variable d'usage pour transmettre la position de l'entree dans le lab invisible
		positionSortie=random2; //variable d'usage pour transmettre la position de la sortie dans le lab invisible
		dessineOuvertureGauche(random1); //cree une ouverture entree d'apres la position generee
		dessineOuvertureDroite(random2); //cree une ouverture a droite aleatoirement
	}
}

