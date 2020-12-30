import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.border.LineBorder;

/*import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;*/


public class CarteMenuInitialisation extends CarteMenu {
	private JPanel cartesContenu;
	private CardLayout layoutCartes;

	public CarteMenuInitialisation(ControleurIG controleurIG) {
		super(controleurIG);
		super.setUp();
		this.setUpCartes();
		this.setUpActions();
	}

	private void setUpCartes() {
		CarteContenuCreerJoueur carteCreerJoueur = new CarteContenuCreerJoueur();
		CarteContenuRegles carteRegles = new CarteContenuRegles();
		CarteContenuChoisirJoueur carteChoisirJoueur = new CarteContenuChoisirJoueur();

		this.layoutCartes = new CardLayout();
		this.cartesContenu = new JPanel(layoutCartes); //RAF faire les différentes cartes
		this.cartesContenu.add(carteCreerJoueur, "creerJoueur");
		this.cartesContenu.add(carteRegles, "regles");
		this.layoutCartes.addLayoutComponent(carteChoisirJoueur, "choisirJoueur");

		this.setContenuDroite(this.cartesContenu);
	}

	private void setUpActions() {
		JButton regles = new JButton("règles");
		JButton demo = new JButton("démo");
		JButton choisirJoueur = new JButton("choisir un joueur");
		JButton creerJoueur = new JButton("créer un joueur"); // ajouter à choisir joueur ?
		JButton quitter = new JButton("quitter le jeu");

		regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layoutCartes.show(cartesContenu, "regles");
			}
		});

		demo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getControleurIG().demo(); //RAF demo
 			}
		});

		choisirJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layoutCartes.show(cartesContenu, "choisirJoueur");
			}
		});

		creerJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layoutCartes.show(cartesContenu, "creerJoueur");
			}
		});

		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getControleurIG().quitteJeu();
			}
		});

		JButton[] actions = {regles, demo, choisirJoueur, creerJoueur, quitter};
		this.ajouterActions(actions);
	}

	private class CarteContenu extends JPanel {
		public CarteContenu() {
			this.setBackground(new Color(246,236,213,255));
			this.setBorder(new LineBorder(new Color(246,236,213,255), 10, true));
		}
	}

	private class CarteContenuRegles extends CarteContenu {
		CarteContenuRegles() { //RAF n'affiche pas de texte pour l'instant
			JTextArea zoneText = new JTextArea(lireFichier("./regles.txt"));
			JScrollPane texteAsc = new JScrollPane(zoneText);
			texteAsc.setVerticalScrollBarPolicy(texteAsc.VERTICAL_SCROLLBAR_ALWAYS);
			this.add(texteAsc);
			zoneText.setEditable(true);
			zoneText.setVisible(true);
		}

		public String lireFichier(String file) {
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

	private class CarteContenuChoisirJoueur extends CarteContenu {}

	private class CarteContenuCreerJoueur extends CarteContenu {
		private String[] nomsJoueursExistants;

		public CarteContenuCreerJoueur() {
			this.nomsJoueursExistants = Joueur.listeJoueursSauvegardes();
			GridLayout layout = new GridLayout(3, 1);
			layout.setVgap(5);
			layout.setHgap(5);
			JPanel contenu = new JPanel(layout);
			contenu.setBackground(new Color(246,236,213,255));
			this.add(contenu);

			JLabel instruction = new JLabel ("Choisissez le joueur :");
			instruction.setFont(new Font("FreeMono", Font.BOLD, 15));
			instruction.setBackground(new Color(246,236,213,255));

			JPanel nouveauJoueur = new JPanel();
			nouveauJoueur.setBackground(new Color(246,236,213,255));
			JTextField champsSaisie = new JTextField("Nouveau joueur");
			champsSaisie.setFont(new Font("FreeMono", Font.BOLD, 15));
			champsSaisie.setMargin(new Insets(2,2,2,2));
			champsSaisie.setEditable(true);

			/*JLabel niveauLabel = new JLabel ("Niveau preferré");
			JFormattedTextField niveau = new JFormattedTextField(NumberFormat.getIntegerInstance());
			niveau.setFont(new Font("freemono", Font.BOLD, 100));
			niveau.setPreferredSize(new Dimension(150, 30));*/

			JButton boutonOK = new JButton ("OK");
			boutonOK.setMargin(new Insets(2,2,2,2));
			boutonOK.setFont(new Font("FreeMono", Font.BOLD, 15));
			//boutonOK.setPreferredSize(new Dimension(20, 20));
			boutonOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getControleurIG().creeNouveauJoueur(champsSaisie.getText());
				}
			});

			contenu.add(instruction);
			nouveauJoueur.add(champsSaisie);
			nouveauJoueur.add(boutonOK);
			/*this.add(niveauLabel);
			this.add(niveau);*/
			contenu.add(this.joueursExistants());
			contenu.add(nouveauJoueur);
		}

		private JPanel joueursExistants() {
			JButton[] boutons = new JButton[this.nomsJoueursExistants.length];
			GridLayout layout = new GridLayout(this.nomsJoueursExistants.length, 1);
			layout.setVgap(5);
			layout.setHgap(5);
			JPanel panel = new JPanel(layout);
			panel.setBackground(new Color(246,236,213,255));
			panel.setBorder(new LineBorder(new Color(246,236,213,255), 10, true));
			for (int i = 0 ; i < this.nomsJoueursExistants.length ; i++) {
				boutons[i] = new JButton(this.nomsJoueursExistants[i]);
				/*boutons[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						getControleurIG().choisitJoueur(this.nomsJoueursExistants[i]); //RAF demo
		 			}
				});*/
				boutons[i].setMargin(new Insets(2,2,2,2));
				boutons[i].setFont(new Font("FreeMono", Font.BOLD, 15));
				panel.add(boutons[i]);
			}
			return panel;
		}
	}
	
	public static void main(String[] args) {}
}
