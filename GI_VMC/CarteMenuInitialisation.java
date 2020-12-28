import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CarteMenuInitialisation extends CarteMenu {
	private JButton regles = new JButton("règles");
	private JButton demo = new JButton("démo");
	private JButton choisirJoueur = new JButton("choisir un joueur");
	private JButton creerJoueur = new JButton("créer un joueur"); // ajouter à choisir joueur ?
	private JButton quitter = new JButton("quitter le jeu");
	private JButton[] actions = {regles, demo, choisirJoueur, creerJoueur, quitter};
	private ControleurIG controleurIG;

	public CarteMenuEnvironnement(ControleurIG controleurIG) {
		this.controleurIG = controleurIG;
		regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleurIG.regles(); //RAF règles
			}
		});

		demo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleurIG.demo(); //RAF demo
 			}
		});

		choisirJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleurIG.choisitJoueur();//RAF carte choisirJoueur
			}
		});

		creerJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleurIG.creeNouveauJoueur();//RAF créer joueur
			}
		});

		super.setUp(environnement);
		super.ajouterActions(actions);
		this.setContenuDroite(new JPanel(new CardLayout())); //RAF faire les différentes cartes

		//créer les cartesDroite et les ajouter au CardLayout
	}

	private class CarteDroiteRegles() extends JPanel {}
	private class CarteDroiteDemo() extends JPanel {}
	private class CarteDroiteChoisirJoueur() extends JPanel {}
	private class CarteDroiteCreerJoueur() extends JPanel {}
	
	public static void main(String[] args) {}
}
