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
/*Une CarteMenu (dans le GridLayout de VueIG, qui prend toute la fenêtre) avec en contenuDroite un autre GridLayout avec deux cartes, carteRegles et carteChoisirJoueur. On a choisit d'utiliser des classes internes car ces cartes ne sont sensées être utilisées qu'avec ce menu/cette classe englobante là.*/
	private JPanel cartesContenu;
	private CardLayout layoutCartes;
	private CarteContenuRegles carteRegles;
	private CarteContenuChoisirJoueur carteChoisirJoueur;
	private ControleurIG controleurIG;

	public CarteMenuInitialisation(ControleurIG controleurIG) {
		super(controleurIG);
		super.setUp();
		this.controleurIG = controleurIG;
		this.setUpCartes();
		this.setUpActions();
	}

	private void setUpCartes() {
		this.carteRegles = new CarteContenuRegles();
		this.carteChoisirJoueur = new CarteContenuChoisirJoueur();

		this.layoutCartes = new CardLayout();
		this.cartesContenu = new JPanel(layoutCartes);
		this.cartesContenu.add(carteRegles, "regles");
		this.cartesContenu.add(carteChoisirJoueur, "choisirJoueur");

		this.setContenuDroite(this.cartesContenu);
	}

	private void setUpActions() {
/*Dans le changement de cartes, la partie modèle n'est pas appelée. On a choisi de passer quand même par le contrôleur, au cas où, pour une raison x ou y, on aurait par la suite envie de la faire rentrer en jeu. Comme ça, si jamais on veut complètement changer le comportement de l'affichage, on n'aura pas à changer les listeners. L'inconvénient est que cela veut dire qu'il faut encore une fois trouver un moyen d'accéder aux variables qui ne devraient dépendre que de cette classe. Plutôt que d'en faire des variables attributs dans les différentes classes, on a préféré créer des fonctions qui s'appelent entre les classes et de passer les références nécéssaires en arguments. Ce choix est bien sûr discutable, et n'est pas uniforme dans notre programme : rien que dans cette classe, on utilise un attribut pour garder l'adresse du controleur passée en argument du constructeur, au lieu d'appeler une fonction dans VueIG qui ensuite appèlerait la fonction du controleur.*/
		JButton regles = new JButton("règles");
		JButton demo = new JButton("démo");
		JButton choisirJoueur = new JButton("choisir un joueur");
		JButton quitter = new JButton("quitter le jeu");

		CarteMenuInitialisation refThis = this;

		regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleurIG.regles(refThis);
			}
		});

		demo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleurIG.demo();
 			}
		});

		choisirJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleurIG.choixDuJoueur(refThis);
			}
		});

		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleurIG.quitteJeu();
			}
		});

		JButton[] actions = {regles, demo, choisirJoueur, quitter};
		this.ajouterActions(actions);
	}

	protected void regles() {
		this.layoutCartes.show(this.cartesContenu, "regles");
	}

	protected void choixDuJoueur() {
		this.layoutCartes.show(this.cartesContenu, "choisirJoueur");
	}

	protected void miseAJour() { //RAF ???
		this.carteChoisirJoueur.miseAJour();
	}

	private class CarteContenu extends JPanel {
		public CarteContenu() {
			this.setBackground(new Color(246,236,213,255));
			this.setBorder(new LineBorder(new Color(246,236,213,255), 10, true));
		}
	}

	private class CarteContenuRegles extends CarteContenu {
		CarteContenuRegles() { /*Attention, le JScrollPane ne change pas de taille dynamiquement.*/
			this.setLayout(new BorderLayout());

			JTextArea texte = new JTextArea();
			texte.setText(lireFichier("./regles.txt"));
			texte.setWrapStyleWord(true);
			texte.setLineWrap(true);
			texte.setOpaque(true);
			texte.setEditable(false);
			texte.setFocusable(false);
			texte.setBackground(new Color(246,236,213,255));
			texte.setFont(new Font("FreeMono", Font.PLAIN, 15));
			texte.setMargin(new Insets(5,5,5,5));

			JScrollPane scroll = new JScrollPane(texte);
			//scroll.setViewportView(texte);
			scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setPreferredSize(new Dimension(600,600));
			scroll.setBorder(null);

			this.add(scroll, BorderLayout.CENTER);
			scroll.invalidate();
			scroll.validate();
			scroll.repaint();
		}

		public String lireFichier(String chemin) {
			String text = "";
			String ligne;
			try {
			    BufferedReader reader = new BufferedReader(new FileReader(chemin));
			    while((ligne = reader.readLine()) != null)
				text += ligne+"\n";
			}
			catch(Exception exception) {
			    text = "Une erreur s'est produite durant la lecture : "+exception.getMessage();
			}
			return text;
		}
	}

	private class CarteContenuChoisirJoueur extends CarteContenu {
		private JPanel conteneurJoueursExistants;

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
			this.miseAJour();
			this.add(this.conteneurJoueursExistants);

			JPanel nouveauJoueur = new JPanel();
			nouveauJoueur.setBackground(new Color(246,236,213,255));
			JTextField champsSaisie = new JTextField("joueur");
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
					controleurIG.choisitJoueur(champsSaisie.getText());
				}
			});

			nouveauJoueur.add(champsSaisie);
			nouveauJoueur.add(boutonOK);
			/*this.add(niveauLabel);
			this.add(niveau);*/
			this.add(nouveauJoueur);
		}

		protected void miseAJour() {
			this.conteneurJoueursExistants.removeAll();
			JPanel panel = new JPanel();
			panel.setBackground(new Color(246,236,213,255));
			panel.setBorder(new LineBorder(new Color(246,236,213,255), 10, true));
			
			String[] joueurs = Joueur.listeJoueursSauvegardes();
			JLabel[] labels = new JLabel[joueurs.length];

			BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
			panel.setLayout(layout);

			JLabel titre = new JLabel("Joueurs existants :");
			titre.setBackground(new Color(246,236,213,255));
			titre.setFont(new Font("FreeMono", Font.BOLD, 15));
			panel.add(titre);

			for (int i = 0 ; i < joueurs.length ; i++) {
				labels[i] = new JLabel(joueurs[i]);
				labels[i].setBackground(new Color(246,236,213,255));
				labels[i].setFont(new Font("FreeMono", Font.PLAIN, 15));
				panel.add(labels[i]);
			}
			this.conteneurJoueursExistants.add(panel);
		}
	}
	
	public static void main(String[] args) {}
}
