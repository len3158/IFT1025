/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
 * Auteurs: 										        +
 * Lenny Siemeni									        +
 * Ali Chams Eddine Touil						            +
 * Classe: Surligneur.java						     	   	+
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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


class Surligneur extends JFrame
{
	protected static JTextArea txt;
	int i;
	Surligneur(){}	

	//Constructeur du surligneur qui marque tous les mots inconnus du dictionnaire
	public void highlight(JTextArea txt, ArrayList<String> tabMots) {
		effacerSurlignes(Gui.txt);
		try {
			Highlighter surligne = txt.getHighlighter();
			Document doc = txt.getDocument();
			String texte = doc.getText(0, doc.getLength());
			
			//Un tableau contenant tous les mots du textArea principal
			//Notre token sera un espace pour considerer une chaine comme un mot
			String[] motJTextArea = texte.split(" ");
			List<String> listeJTextArea = new ArrayList<String>(Arrays.asList(motJTextArea));
			int pos = 0;
			
			//On itere chaque mot sur tout le dictionnaire
			for (int k=0; k<listeJTextArea.size(); k++){
				for(int i=0; i<tabMots.size(); i++){
					
					//Si le mot est dans le dictionnaire, on l'efface de notre liste
					//de mots inconnus
					if(listeJTextArea.get(k).equalsIgnoreCase(tabMots.get(i))){
						System.out.println(listeJTextArea.get(k));
						listeJTextArea.remove(k);
						System.out.println(tabMots.get(i));
						break;
					}
				}
			}
			
			//On surligne maintenant tous les mots du textArea qui sont 
			//Dans la liste de mots inconnus
			for(int j=0; j<listeJTextArea.size(); j++){
				while (((pos = texte.indexOf(listeJTextArea.get(j), pos)) >= 0)) {
					surligne.addHighlight(pos, pos+listeJTextArea.get(j).length(), leSurligneur);
					pos+=listeJTextArea.get(j).length();
				}
			}
		} catch (BadLocationException e) {}
	}
	
	//Methode permettant d'effacer les mots surlignes que nous avons cree
	public static void effacerSurlignes(JTextComponent textComp) {
		Highlighter surligne = textComp.getHighlighter();
		Highlighter.Highlight[] surlignes = surligne.getHighlights();
	
		for (int i=0; i<surlignes.length; i++) {
			if (surlignes[i].getPainter() instanceof LeSurligneur) {
				surligne.removeHighlight(surlignes[i]);
			}
		}
	}
	
	// On modifie la couleur de l'instance du Highlight Painter
	Highlighter.HighlightPainter leSurligneur = new LeSurligneur(Color.red);
	
	//Cette sous-classe est necessaire pour modifier la couleur du Highlight painter
	//On obtient tous les attributs du Highlight painter, ainsi que la couleur
	//que l'on peut modifier
	class LeSurligneur extends DefaultHighlighter.DefaultHighlightPainter {
		public LeSurligneur(Color color) {
			super(color);
		}
	}
}
