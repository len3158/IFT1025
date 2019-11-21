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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ArrayList;

public class Gui extends JFrame implements MouseListener,ActionListener,ItemListener {
	private static JMenuBar menuBar;
	private static JMenu menu, fichier;
	private static JMenuItem dictionnaire, verifier, ouvrir, enregistrer,mot1,mot2,mot3,mot4,mot5;
	private static JLabel l;
	private static JPanel p;
	protected static JTextArea txt;
	protected static JScrollPane scroll;
	
	
	public Gui() {
		//On cree tous les elements de la fenetre
		JFrame f=new JFrame("Correcteur");
		p=new JPanel();
		txt=new JTextArea(50,40);
		scroll = new JScrollPane(txt,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		menuBar=new JMenuBar();
		l=new JLabel("Texte a corriger:");
		menu= new JMenu("Menu");
		fichier=new JMenu("Fichier");
		ouvrir=new JMenuItem("Ouvrir");
		enregistrer=new JMenuItem("Enregistrer");
		dictionnaire=new JMenuItem("Dictionnaire");
		verifier=new JMenuItem("Verifier");
		
		//On ajoute ces deux proprietes a nos textArea
		txt.setLineWrap( true );
		
		
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

		
		//On ajoute nos label et barre de defilement au panel
		p.add(l,BorderLayout.LINE_START);
		p.add(scroll,BorderLayout.PAGE_END);

		
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

	//Tableau contenant les mots les plus proches de celui a corriger
	public String[] motProche( MouseEvent e) {
		List<String> listeSimilaire = new ArrayList<String>();
		ArrayList<Integer> min=new ArrayList<Integer>();
		try{
			int distance = 0;
			int offset = txt.viewToModel( e.getPoint());
			int debut = Utilities.getWordStart(txt, offset);
			int fin = Utilities.getWordEnd(txt, offset);
			String leMot = txt.getDocument().getText(debut, fin-debut);
			for(int i=0; i<15; i++){
				distance = Levenshtein.distance(leMot, Dictionnaire.tabMots.get(i));
				min.add(distance);
				
			}
			while(min.size()>=5) {
				min.remove(Collections.max(min));
			}
			
			for(int i=0;i<min.size();i++) {
				for(int j=0;j<Dictionnaire.tabMots.size();j++) {
					if(min.get(i)==Levenshtein.distance(leMot, Dictionnaire.tabMots.get(j))) {
						listeSimilaire.add(Dictionnaire.tabMots.get(j));
					}
				}
			}
		}catch (Exception ev) {}
			String tab []= new String [5];
			for(int i=0;i<tab.length;i++) {
				tab[i]=listeSimilaire.get(i);
			}
		return tab;
	}
	
	//Controleur mouseClicked qui lorsque active, creer un sous-menu
	//contenant la liste des mots proches du mot a corriger
	public void mouseClicked(MouseEvent e) {
		if ( SwingUtilities.isRightMouseButton(e)) {
			try {
				String []tab=motProche(e);
				JPopupMenu correction= new JPopupMenu();
				mot1=new JMenuItem(""+tab[0]);
				mot2=new JMenuItem(""+tab[1]);
				mot3=new JMenuItem(""+tab[2]);
				mot4=new JMenuItem(""+tab[3]);
				mot5=new JMenuItem(""+tab[4]);
			
				correction.add(mot1);
				correction.add(mot2);
				correction.add(mot3);
				correction.add(mot4);
				correction.add(mot5);
				correction.show(e.getComponent(),e.getX(),e.getY());
				int debut=txt.getSelectionEnd()-txt.getSelectionStart();	
				mot1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {txt.replaceRange(""+tab[0],(txt.getSelectionStart()-debut) ,(txt.getSelectionEnd()-debut));}});
				mot2.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {txt.setText(""+tab[1]);}});
				mot3.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {txt.setText(""+tab[2]);}});
				mot4.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {txt.setText(""+tab[3]);}});
				mot5.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {txt.setText(""+tab[4]);}});
			}catch(Exception e2){}	
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