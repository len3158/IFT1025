/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
 * Auteurs: 										        +
 * Lenny Siemeni									        +
 * Ali Chams Eddine Touil						            +
 * Classe: Gui.java						     	   		    +
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

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.text.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Gui extends JFrame implements MouseListener,ActionListener,ItemListener {
	private static JMenuBar menuBar;
	private static JMenu menu, fichier;
	private static JMenuItem dictionnaire, verifier, ouvrir, enregistrer;
	private static JLabel l, l2;
	private static JPanel p;
	protected static JTextArea txt, txt2;
	protected static JScrollPane scroll;
	protected static JScrollPane scroll2;
	
	public Gui() {
		//On cree tous les éléments de la fenêtre
		JFrame f=new JFrame("Correcteur");
		p=new JPanel();
		txt=new JTextArea(10,40);
		txt2=new JTextArea(6,40);
		scroll = new JScrollPane(txt,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll2=new JScrollPane(txt2,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		menuBar=new JMenuBar();
		l=new JLabel("Texte a corriger:");
		l2=new JLabel("Mots proposes:");
		menu= new JMenu("Menu");
		fichier=new JMenu("Fichier");
		ouvrir=new JMenuItem("Ouvrir");
		enregistrer=new JMenuItem("Enregistrer");
		dictionnaire=new JMenuItem("Dictionnaire");
		verifier=new JMenuItem("Verifier");
		
		//On ajoute ces deux proprietes a nos textArea
		txt.setLineWrap( true );
		txt2.setEditable(false);
		
		//Contruit le menu
		menuBar.add(menu);
		fichier.add(ouvrir);
		fichier.add(enregistrer);
		menu.add(fichier);
		menu.addSeparator();
		menu.add(dictionnaire);
		menu.addSeparator();
		menu.add(verifier);
		
		//On ajoute des listeners aux elements qui en ont besoin
		dictionnaire.addActionListener(this);
		verifier.addActionListener(this);
		ouvrir.addActionListener(this);
		enregistrer.addActionListener(this);
		txt.addMouseListener(this);
		txt2.addMouseListener(this);
		
		//On ajoute nos label et barre de defilement au panel
		p.add(l,BorderLayout.LINE_START);
		p.add(scroll,BorderLayout.PAGE_END);
		p.add(l2,BorderLayout.CENTER);
		p.add(scroll2,BorderLayout.LINE_END);
		
		//Enfin on contruit le JFrame
		f.setJMenuBar(menuBar);
		f.add(p);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500,400);
	}
	
	public static JTextArea getTextArea(){
		return txt;
	}
	
	//Notre controleur qui cree un FileChooser selon le menu choisit ou un surligneur
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==dictionnaire) {
			FileChooser.creerFileChooser('d');
		}else if(e.getSource()==verifier) {
			if(!(Dictionnaire.getTabMots().isEmpty())){
				Surligneur s = new Surligneur();
				s.highlight(Gui.txt, Dictionnaire.tabMots);
			} else {
				MyJDialog.run();
			}	
		}else if (e.getSource()==ouvrir) {
			FileChooser.creerFileChooser('o');
		}else if(e.getSource()==enregistrer){
			FileChooser.creerFileChooser('s');
		}
	}
	
	//Controleur mouseClicked
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==txt){
			txt2.setText("");
				try{	
					int distance = -1;
					int offset = txt.viewToModel( e.getPoint());
					int debut = Utilities.getWordStart(txt, offset);
					int fin = Utilities.getWordEnd(txt, offset);
					boolean estDansDico = false;
					String leMot = txt.getDocument().getText(debut, fin-debut);
					List<String> listeCorrection = new ArrayList();
					int nbCorrection = 0;
					for(int i=0; i<Dictionnaire.tabMots.size(); i++){
						distance = Levenshtein.distance(leMot, Dictionnaire.tabMots.get(i));
						if(distance==0){
							estDansDico = true;
							break;
						} else if((distance > 0)&&(distance<=5)){
							listeCorrection.add(Dictionnaire.tabMots.get(i)+"\n");
							nbCorrection++;
						} else if(nbCorrection == 5){
							break;
						}
					}
					if(!estDansDico){
						for(int k=0; k<listeCorrection.size();k++){
							txt2.append(listeCorrection.get(k));
						}
					}
					int start = txt.getText().indexOf(leMot);
					int offset2 = txt2.viewToModel( e.getPoint());
					int debut2 = Utilities.getWordStart(txt2, offset2);
					int fin2 = Utilities.getWordEnd(txt2, offset2);
					if(start >= 0){
						txt.replaceRange(txt2.getDocument().getText(debut2, fin2-debut2), start, start-txt.getDocument().getText(debut, fin-debut).length());
					}

					}
					catch (Exception e2) {}
		} 
	}
	public void itemStateChanged(ItemEvent i) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	public static void main (String args[]) {
		new Gui();
	}
}