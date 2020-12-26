import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CarteMenuEnvironnement extends Carte {
	private JButton regles = new JButton("règles");
	private JButton demo = new JButton("démo");
	private JButton choisirJoueur = new JButton("choisir un joueur");
	private JButton creerJoueur = new JButton("créer un joueur");
	private JButton[] actions = {regles, demo, choisirJoueur, creerJoueur};

	public CarteMenuEnvironnement(EnvironnementGI environnement) {
		regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//RAF règles
			}
		});

		demo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//RAF demo
 			}
		});

		choisirJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//RAF carte choisirJoueur
			}
		});

		creerJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//RAF créer joueur
			}
		});

		super.setUp(environnement);
		super.ajouterActions(actions);
		this.setContenuDroite(new JPanel(new CardLayout())); //RAF faire les différentes cartes
	}

	public static void main(String[] args) {}
}
