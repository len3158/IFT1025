/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
 * Auteurs: 										        +
 * Lenny Siemeni									        +
 * Ali Chams Eddine Touil						            +
 * Classe: Levemshtein.java						     	    +
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

//La classe Levenshtein telle que ecrites sur gitHub
//Retourne la distance entre deux chaines de caractere
class Levenshtein {
	public static int distance(String s1, String s2){
		  int edits[][]=new int[s1.length()+1][s2.length()+1];
		  for(int i=0;i<=s1.length();i++)
				edits[i][0]=i;
		  for(int j=1;j<=s2.length();j++)
				edits[0][j]=j;
		  for(int i=1;i<=s1.length();i++){
				for(int j=1;j<=s2.length();j++){
					 int u=(s1.charAt(i-1)==s2.charAt(j-1)?0:1);
					 edits[i][j]=Math.min(
										  edits[i-1][j]+1,
										  Math.min(
											  edits[i][j-1]+1,
											  edits[i-1][j-1]+u
										  )
									 );
				}
		  }
		  return edits[s1.length()][s2.length()];
	}
}