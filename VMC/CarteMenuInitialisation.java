import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import java.io.BufferedReader;
import java.io.FileReader;


/*Une CarteMenuInitialisation est une Carte Menu qui a en contenuDroite un autre GridLayout avec deux cartes, carteRegles et carteChoisirJoueur. On a ici aussi choisit d'utiliser des classes internes.*/
class CarteMenuInitialisation extends CarteMenu {
	private JPanel cartesContenu;
	private CardLayout layoutCartes;
	private CarteContenuRegles carteRegles;
	private CarteContenuChoisirJoueur carteChoisirJoueur;

	public CarteMenuInitialisation(Controleur controleur) {
		super(controleur);
		this.setUpContenu();
		this.setUpActions();
	}

	private void setUpContenu() {
		this.carteRegles = new CarteContenuRegles();
		this.carteChoisirJoueur = new CarteContenuChoisirJoueur();

		this.layoutCartes = new CardLayout();
		this.cartesContenu = new JPanel(layoutCartes);
		this.cartesContenu.add(carteRegles, "regles");
		this.cartesContenu.add(carteChoisirJoueur, "choisirJoueur");

		this.setContenuDroite(this.cartesContenu);
	}

	private void setUpActions() {
		JButton regles = new JButton("règles");
		JButton demo = new JButton("démo");
		JButton choisirJoueur = new JButton("choisir un joueur");
		JButton quitter = new JButton("quitter le jeu");

		regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regles();
			}
		});

		demo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleur.demo();
 			}
		});

		choisirJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choixDuJoueur();
			}
		});

		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleur.quitteJeu();
			}
		});

		JButton[] actions = {regles, demo, choisirJoueur, quitter};
		this.ajouterActions(actions);
	}
/*L'affichage des cartes à droite ne passe pas par le système et le controleur mais se fait directement dans la vue. C'est surtout dû au fait que la partie modèle n'est pas aussi active que dans le menu jouer : elle ne fait que renvoyer de l'information, que ce soit la liste des joueurs sauvegardés ou le texte du fichier règles. Faire une boucle aurait été simple pour l'interface graphique, mais nécessiterait plus de changements pour l'interface terminale. Cela reste à faire.*/
	protected void regles() {
		this.layoutCartes.show(this.cartesContenu, "regles");
	}

	protected void choixDuJoueur() {
		this.layoutCartes.show(this.cartesContenu, "choisirJoueur");
	}

	protected void miseAJourJoueurs() { //RAF ???
		this.carteChoisirJoueur.miseAJourJoueurs();
	}

	private abstract class CarteContenu extends JPanel {
/*Cette petite classe abstraite n'est pas forcément très intéressante, si ce n'est qu'elle permet de déveloper notre hiérarchie de classes et de, pour une fois, ne pas avoir à copier coller plusieurs fois le même code de formatage. C'est dans ces moments là qu'on se dit qu'un UImanager ça aurait quand même été bien... Ou même quelques fonctions auxiliaires static public ?*/
		public CarteContenu() {
			this.setBackground(new Color(246,236,213,255));
			this.setBorder(new LineBorder(new Color(246,236,213,255), 10, true));
		}
	}

	private class CarteContenuRegles extends CarteContenu {
		CarteContenuRegles() {
			this.setLayout(new BorderLayout());

			JTextArea texte = new JTextArea();
			texte.setText(Joueur.lireRegles());
			texte.setWrapStyleWord(true);
			texte.setLineWrap(true);
			texte.setOpaque(true);
			texte.setEditable(false);
			texte.setFocusable(false);
			texte.setBackground(new Color(246,236,213,255));
			texte.setFont(new Font("FreeMono", Font.PLAIN, 15));
			texte.setMargin(new Insets(5,5,5,5));

			JScrollPane scroll = new JScrollPane(texte);
			scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setBorder(null);

			this.add(scroll, BorderLayout.CENTER);
			scroll.invalidate();
			scroll.validate();
			scroll.repaint();
		}
	}

	private class CarteContenuChoisirJoueur extends CarteContenu {
		private JPanel conteneurJoueursExistants; /*Encore une fois, on n'a pas trouvé d'autre façon de faire en sorte que changer le JPanel en attribut provoque sa mise à jour visuelle. On a donc un JPanel conteneur qui ne change pas, et on y enlève/ajoute un autre JPanel contenu.*/

		public CarteContenuChoisirJoueur() {
			BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
			this.setBackground(new Color(246,236,213,255));
			this.setLayout(layout);

			JLabel instructions = new JLabel("Choisissez ou créez un joueur :");
			instructions.setFont(new Font("FreeMono", Font.BOLD, 15));
			instructions.setBackground(new Color(246,236,213,255));
			this.add(instructions);

			this.conteneurJoueursExistants = new JPanel();
			this.conteneurJoueursExistants.setBackground(new Color(246,236,213,255));
			this.miseAJourJoueurs();
			this.add(this.conteneurJoueursExistants);

			JPanel nouveauJoueur = new JPanel();
			nouveauJoueur.setBackground(new Color(246,236,213,255));
			JTextField champsSaisie = new JTextField("joueur");
			champsSaisie.setFont(new Font("FreeMono", Font.BOLD, 15));
			champsSaisie.setMargin(new Insets(2,2,2,2));
			champsSaisie.setEditable(true);

			JButton boutonOK = new JButton ("OK");
			boutonOK.setMargin(new Insets(2,2,2,2));
			boutonOK.setFont(new Font("FreeMono", Font.BOLD, 15));
			boutonOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controleur.choisitJoueur(champsSaisie.getText());
				}
			});

			nouveauJoueur.add(champsSaisie);
			nouveauJoueur.add(boutonOK);
			this.add(nouveauJoueur);
		}

		protected void miseAJourJoueurs() {
			this.conteneurJoueursExistants.removeAll();

			JPanel panelJoueursExistants = new JPanel();
			panelJoueursExistants.setBackground(new Color(246,236,213,255));
			panelJoueursExistants.setBorder(new LineBorder(new Color(246,236,213,255), 10, true));
			
			String[] joueurs = Joueur.listeJoueursSauvegardes();
			JLabel[] labels = new JLabel[joueurs.length];

			BoxLayout layout = new BoxLayout(panelJoueursExistants, BoxLayout.PAGE_AXIS);
			panelJoueursExistants.setLayout(layout);

			JLabel titre = new JLabel("Joueurs existants :");
			titre.setBackground(new Color(246,236,213,255));
			titre.setFont(new Font("FreeMono", Font.BOLD, 15));
			panelJoueursExistants.add(titre);

			for (int i = 0 ; i < joueurs.length ; i++) {
				labels[i] = new JLabel(joueurs[i]);
				labels[i].setBackground(new Color(246,236,213,255));
				labels[i].setFont(new Font("FreeMono", Font.PLAIN, 15));
				panelJoueursExistants.add(labels[i]);
			}
			this.conteneurJoueursExistants.add(panelJoueursExistants);
		}
	}
}
