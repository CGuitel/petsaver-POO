import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.io.BufferedReader;
import java.io.FileReader;

/*import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;*/


public class CarteMenuInitialisation extends CarteMenu {
	private JPanel contenuCartes;
	private CardLayout cartes;

	public CarteMenuInitialisation(ControleurIG controleurIG) {
		super(controleurIG);
		super.setUp();
		this.setUpCartes();
		this.setUpActions();
	}

	private void setUpCartes() {
		CarteDroiteCreerJoueur carteCreerJoueur = new CarteDroiteCreerJoueur();
		CarteDroiteRegles carteRegles = new CarteDroiteRegles();
		CarteDroiteChoisirJoueur carteChoisirJoueur = new CarteDroiteChoisirJoueur();

		this.cartes = new CardLayout();
		this.contenuCartes = new JPanel(cartes); //RAF faire les différentes cartes
		this.contenuCartes.add(carteCreerJoueur, "creerJoueur");
		this.contenuCartes.add(carteRegles, "regles");
		//this.cartes.addLayoutComponent(carteChoisirJoueur, "choisirJoueur");

		this.setContenuDroite(this.contenuCartes);
	}

	private void setUpActions() {
		JButton regles = new JButton("règles");
		JButton demo = new JButton("démo");
		JButton choisirJoueur = new JButton("choisir un joueur");
		JButton creerJoueur = new JButton("créer un joueur"); // ajouter à choisir joueur ?
		JButton quitter = new JButton("quitter le jeu");
		JButton[] actions = {regles, demo, choisirJoueur, creerJoueur, quitter};

		regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("test regles");
				cartes.show(contenuCartes, "regles");
			}
		});

		demo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getControleurIG().demo(); //RAF demo
 			}
		});

		choisirJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//cartes.show(contenuCartes, "choisirJoueur");
			}
		});

		creerJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cartes.show(contenuCartes, "creerJoueur");
			}
		});

		this.ajouterActions(actions);
	}

	private class CarteDroiteRegles extends JPanel {
		CarteDroiteRegles() {
			JTextArea zoneText = new JTextArea(readFile("./regles.txt"));
			JScrollPane texteAsc = new JScrollPane(zoneText);
			texteAsc.setVerticalScrollBarPolicy(texteAsc.VERTICAL_SCROLLBAR_ALWAYS);
			this.add(texteAsc);
			zoneText.setEditable(true);
			zoneText.setVisible(true);
		}

		public String readFile(String file) {
			// lit le fichier
			String lines = "";
			String line;
			try {
			    BufferedReader reader = new BufferedReader( new FileReader(file) );
			    while( (line = reader.readLine()) != null )
				lines += line+"\n";
			}
			catch( Exception e ) {
			    lines = "Une erreur s'est produite durant la lecture : "+e.getMessage();
			}
			return lines;
		}
	}

	private class CarteDroiteDemo extends JPanel {}

	private class CarteDroiteChoisirJoueur extends JPanel {}

	private class CarteDroiteCreerJoueur extends JPanel {
		public CarteDroiteCreerJoueur() {
			JTextField champsSaisie = new JTextField("");
			champsSaisie.setFont(new Font("freemono", Font.BOLD, 100));
			champsSaisie.setPreferredSize(new Dimension(150, 30));
			champsSaisie.setEditable(true);

			JLabel instruction = new JLabel ("Nom du joueur");
			instruction.setFont(new Font("freemono", Font.BOLD, 100));
			instruction.setPreferredSize(new Dimension(150, 30));

			/*JLabel niveauLabel = new JLabel ("Niveau preferré");
			JFormattedTextField niveau = new JFormattedTextField(NumberFormat.getIntegerInstance());
			niveau.setFont(new Font("freemono", Font.BOLD, 100));
			niveau.setPreferredSize(new Dimension(150, 30));*/

			JButton boutonOK = new JButton ("OK");
			boutonOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getControleurIG().creeNouveauJoueur(champsSaisie.getText());
				}
			});

			this.add(instruction);
			this.add(champsSaisie);
			/*this.imageFon.add(niveauLabel);
			this.imageFon.add(niveau);*/
			this.add(boutonOK);
		}
	}
	
	public static void main(String[] args) {}
}
