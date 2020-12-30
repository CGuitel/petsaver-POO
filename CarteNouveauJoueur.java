import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CarteNouveauJoueur extends Carte {
	
	CarteNouveauJoueur (String chemin) {
		super(chemin);
		JTextField nomJoueur = new JTextField("");
		//pour recuperer : nomJoueur.getText();
		JLabel nomLabel = new JLabel ("Nom du joueur");
		nomJoueur.setFont(new Font("freemono", Font.BOLD, 100));
		nomJoueur.setPreferredSize(new Dimension(150, 30));
		JLabel niveauLabel = new JLabel ("Niveau preferré");
		JFormattedTextField niveau = new JFormattedTextField(NumberFormat.getIntegerInstance());
		niveau.setFont(new Font("freemono", Font.BOLD, 100));
		niveau.setPreferredSize(new Dimension(150, 30));
		JButton b = new JButton ("OK");
		//b.addActionListener(new BoutonListener() --> commencer le jeu);
		this.imageFon.add(nomLabel);
		this.imageFon.add(nomJoueur);
		nomJoueur.setEditable(true);
		this.imageFon.add(niveauLabel);
		this.imageFon.add(niveau);
		this.imageFon.add(b);
	}
}
