/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
 * Auteurs: 										        +
 * Lenny Siemeni									        +
 * Ali Chams Eddine Touil						            +
 * Classe: FileChooser.java						     	    +
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

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JComponent;

class FileChooser extends JPanel implements ActionListener {
		JButton openButton, saveButton;
		JFileChooser fc;
		
	//FileChooser par defaut permet d'ouvrir un fichier
	public FileChooser() {
		//Create a file chooser
		fc = new JFileChooser(".");
		FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Text Document", "txt");
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Java Document", "java");
		fc.addChoosableFileFilter(filter1);
		fc.addChoosableFileFilter(filter2);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(FileChooser.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Dictionnaire.lire(fc.getSelectedFile());
		} else if (returnVal == JFileChooser.CANCEL_OPTION){
			fc.cancelSelection();
			fc.setVisible(false);
		}
	}
	
	//FileChooser permettant de sauvegarder un fichier	
	public FileChooser(String type){
		//Create a file chooser
		fc = new JFileChooser(".");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showSaveDialog(FileChooser.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Dictionnaire.enregistrer(fc.getSelectedFile());
		} else if (returnVal == JFileChooser.CANCEL_OPTION){
			fc.cancelSelection();
			fc.setVisible(false);
		}
	}
	
	//FileChooser permettant de creer le dictionnaire a partir d'un fichier
	public FileChooser(String type, int i) {
				//Create a file chooser
				fc = new JFileChooser(".");
				FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
						"Text Document", "txt");
					fc.addChoosableFileFilter(filter1);
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fc.showOpenDialog(FileChooser.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					Dictionnaire.creerDictionnaire(file);
				} else if (returnVal == JFileChooser.CANCEL_OPTION){
					fc.cancelSelection();
					fc.setVisible(false);
				}
			}
	//Switch utilitaire permettant d'appeler le bon FileChooser dependemment de
	//l'appel depuis Gui.java
	public static void creerFileChooser(char type) {
		switch (type) {
			case 'o':
				new FileChooser();
				break;
			case 's':
				new FileChooser("");
				break;
			case 'd':
				new FileChooser("", 0);
				break;
			default:
				break;
			}
	}
	
	public void actionPerformed(ActionEvent e){}
	public static void main(String[] args) {}
}