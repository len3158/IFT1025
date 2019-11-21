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

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

//JDialog pour afficher un message utilitaire si on lance
//la verification orthographique alors que le dictionnaire est vide
public class MyJDialog extends JDialog implements ActionListener{
	private JButton button;
	public MyJDialog(JFrame parent, String titre, String message) {
		super(parent, titre);
		
		//La position a laquelle la fenetre apparait
		Point p = new Point(400, 400);
		setLocation(p.x, p.y);

		// Creer le panel qui contiendra le message
		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel(message));
		getContentPane().add(messagePane);

		JPanel buttonPane = new JPanel();
		JButton button = new JButton("Fermer");
		buttonPane.add(button);
		
		//ajouter un actionListener au boutton permettant juste de fermer la fenetre
		button.addActionListener(this);
		getContentPane().add(buttonPane, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	//Methode permettant de fermer la fentre lorsque le button 'fermer'
	//est presse
	public void actionPerformed(ActionEvent e){
			System.out.println("test");
			setVisible(false);
			dispose();
	}
	
	public static void run(){
		MyJDialog dialog = new MyJDialog(new JFrame(), "Dictionnaire vide", "Vous devez d'abord importer un dictionnaire via le menu 'Dictionnaire'.");
		dialog.setSize(500, 150);
	}
}