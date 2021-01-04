import java.awt.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/*Une CarteMenuJouer a pour contenu à droite un panel pour afficher le plateau.
Celui-ci est géré grâce à une classe interne (ContenuPlateau) qui affiche les éléments grâce à un GridBagLayout. Le panel tout entier a un Listener, ce ne sont pas les petits panels pour les blocs et les animaux (et les cases vides) qui ont des Listeners.*/
class CarteMenuJouer extends CarteMenu {
	private ContenuPlateau contenuPlateau;
	private Partie partie;
	private JButton boutonFusee;
	private JLabel decompteFusees;
	private JLabel decompteCoups;
	private JLabel decompteAnimaux;
	

	public CarteMenuJouer(Controleur controleur, Partie partie) {
		super(controleur);
		this.partie = partie;
		this.setUpActions();
		this.setUpLabels();
		this.setUpContenu();
	}

	private void setUpActions() {
		this.boutonFusee = new JButton("utiliser une fusée");
		JButton boutonAnnuler = new JButton("annuler");
		JButton boutonQuitter = new JButton("quitter");

		CarteMenuJouer refThis = this;

		this.boutonFusee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refThis.contenuPlateau.changeFusee();
			}
		});

		boutonAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refThis.controleur.annuleAction();
 			}
		});

		boutonQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refThis.controleur.quittePartie();
			}
		});

		JButton[] actions = {this.boutonFusee, boutonAnnuler, boutonQuitter};
		this.ajouterActions(actions);
	}

	private void setUpLabels() {
		this.decompteFusees = new JLabel();
		this.decompteCoups = new JLabel();
		this.decompteAnimaux = new JLabel();
		JLabel niveau = new JLabel("Niveau : "+Integer.toString(this.partie.getJoueur().getNiveau()));

		JLabel[] labels = {niveau, this.decompteCoups, this.decompteFusees, this.decompteAnimaux};
		super.ajouterLabels(labels);

		this.miseAJourDecomptes();
	}


/*La création du contenuPlateau est séparée de son remplissage, parce qu'on a besoin qu'il soit mis en place dans la carte menu et dans la fenètre pour pouvoir calculer ses dimensions avec getWidth et getHeight.
Il faut donc appeler setContenuDroite avant cela, et lancer setUpPlateau depuis la classe qui ajoute la carte, ici, VueIG.*/
	private void setUpContenu() {
		this.contenuPlateau = new ContenuPlateau(this.partie.getPlateauCourant());
		this.setContenuDroite(this.contenuPlateau);
	}

/*On a créé une classe interne pour l'affichage du plateau.*/
	protected void setUpPlateau() {
		this.contenuPlateau.setUpPlateau(this.partie.getPlateauCourant());
	}

/*Les fonctions de mise à jour de l'affichage : */
	private void miseAJourDecomptes() {
		String texteCoups = "Coups : "+Integer.toString(this.partie.getCoupCourant());
		texteCoups += "/"+Integer.toString(this.partie.getCoupsTotal());
		this.decompteCoups.setText(texteCoups);

		String texteFusees = "Fusées : "+Integer.toString(this.partie.getPlateauCourant().getFusees());
		this.decompteFusees.setText(texteFusees);

		String texteAnimaux = "Animaux à sauver : "+Integer.toString(this.partie.getPlateauCourant().getAnimaux());
		this.decompteAnimaux.setText(texteAnimaux);
	}

	protected void miseAJourPlateau() {
		this.contenuPlateau.miseAJour(this.partie.getPlateauCourant());
		this.miseAJourDecomptes();
	}

/*Le panel plateau :
Le plateau tout entier a un Listener, et non pas chaque bloc.
Les deux méthodes sont possibles, mais comme on enlève et recré tous ces petits panels à chaque mise à jour du plateau, cela nous a paru plus économe de ne pas aussi avoir à refaire des listeners.
Cependant, si l’on voulait pouvoir afficher sur l’interface quelle case est cliquée (ou quelle colonne si on utilise une fusée), l'autre version serait probablement préférable, à moins que l’on puisse récupérer l’élément grâce à ses coordonnées dans le GridBagLayout.*/
	private class ContenuPlateau extends JPanel {
		private JPanel panelPlateau;
		private GridBagLayout grille;
		private GridBagConstraints contraintes;
		private int xMax;
		private int yMaxVisible;
		private int tailleBlocs;
		private boolean fusee;

/*L’idée de tailleBlocs, yMaxVisible et xMax est de garder la fonctionalité d’un plateau à largeur variable. Il faut donc savoir la largeur des blocs pour savoir leur hauteur et donc pouvoir calculer le nombre de blocs visibles dans une colonne.
On priorise ainsi la forme carrée des blocs (si l'on ne redimensionne pas la fenètre) plutôt que le nombre constant de blocs visibles sur une colonne.*/

		public ContenuPlateau(Plateau plateau) {
			this.fusee = false;
			this.setLayout(new BorderLayout());
			this.panelPlateau = new JPanel();
			this.panelPlateau.setBackground(new Color(246,236,213,255));
			this.add(this.panelPlateau, BorderLayout.CENTER);
			this.xMax = plateau.getXMax();

			ContenuPlateau refThis = this;

/*On réévalue à chaque fois la hauteur et la largeur des blocs, au cas où la fenêtre aurait été redimensionnée.*/
			this.panelPlateau.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent event) {
					if (fusee) {
						int x = event.getX()/(refThis.getWidth() / refThis.xMax);
						controleur.utiliseFusee(x); //Il s'agit du contrôleur de la classe englobante.
						refThis.changeFusee();
					}
					else {
						int x = event.getX()/(refThis.getWidth() / refThis.xMax);
						int y = yMaxVisible - (event.getY()/(refThis.getHeight() / refThis.yMaxVisible)) - 1;
						controleur.cliqueBloc(x,y);
					}
				}

				public void mouseReleased(MouseEvent e) {}
			});
		}

		private void setUpPlateau(Plateau plateau) {
			this.tailleBlocs = this.panelPlateau.getWidth() / this.xMax;
			this.yMaxVisible = this.panelPlateau.getHeight() / this.tailleBlocs;

			this.grille = new GridBagLayout();
			this.contraintes = new GridBagConstraints();
			contraintes.fill = GridBagConstraints.BOTH;
			contraintes.weightx = 1.0;
			contraintes.anchor = GridBagConstraints.PAGE_END;
			this.panelPlateau.setLayout(this.grille);
			this.setBackground(new Color(246,236,213,255));
		}

/*L'équivalent d'un bouton toggle, implémenté grâce à un attribut booléen. On change l'affichage pour communiquer au joueur que l'option fusée a été sélectionnée.*/
		private void changeFusee() {
			if (fusee) {
				fusee = false;
				boutonFusee.setBackground(new JButton().getBackground()); //boutonFusee fait référence à l'attribut de la classe englobante.
			} else {
				fusee = true;
				boutonFusee.setBackground(new Color(200,200,200,255));
			}
		}

		private void miseAJour(Plateau plateau) {
			this.panelPlateau.removeAll();

			for (int y = 0 ; y < this.yMaxVisible ; y++) {
				for (int x = 0 ; x < this.xMax ; x++) {
					Piece piece = plateau.getPiece(x, y);
					this.contraintes.gridx = x;
					this.contraintes.gridy = this.yMaxVisible - y;
					if (piece instanceof Animal) {
						String chemin = "../éléments visuels/animal"+Integer.toString(piece.getType())+".png";
						PanelAnimal animal = new PanelAnimal(chemin);
						this.grille.setConstraints(animal, this.contraintes);
						this.panelPlateau.add(animal);
					} else {
						PanelBloc bloc = new PanelBloc(piece, this.tailleBlocs);
						this.grille.setConstraints(bloc, this.contraintes);
						this.panelPlateau.add(bloc);
					}
				}
			}
			this.revalidate();
			this.repaint();
		}

/*Pour que les colonnes et lignes vides restent vides, on introduit des blocs invisibles.*/
		private class PanelBloc extends JPanel {
			private int tailleBlocs;

			public PanelBloc (Piece piece, int tailleBlocs) {
				this.tailleBlocs = tailleBlocs;

				if (piece == null) {
					this.setBackground(new Color(150,150,0,0));
				} else {
					int type = piece.getType();
					if (type == 1) {
						this.setBackground(new Color(255,255,153,255));
					} else if (type == 2) {
						this.setBackground(new Color(255,153,255,255));
					} else if (type == 3) {
						this.setBackground(new Color(153,255,255,255));
					} else if (type == 4) {
						this.setBackground(new Color(153,153,255,255));
					} else if (type == 5) {
						this.setBackground(new Color(255,153,153,255));
					} else {
						this.setBackground(new Color(153,255,153,255));
					}
				}

/*Les deux prochaines lignes permettent la redimension de la fenetre : */
				this.setPreferredSize(new Dimension(tailleBlocs, tailleBlocs));
				this.setMinimumSize(new Dimension(tailleBlocs, tailleBlocs));
				Border contour = new LineBorder(new Color(246,236,213,255), 1, true);
				this.setBorder(contour);
			}
		}

/*Les PanelAnimal, par contre, ne peuvent pas être redimensionnés. Ils ne s'adaptent pas à la taille de la case. Il semblerait qu'il soit compliqué de rendre scalable les ImageIcon, auquel cas une autre implémentation pourrait être envisagée.*/
		private class PanelAnimal extends JPanel{
			BufferedImage image;
			
			public PanelAnimal(String chemin) {
				this.setLayout(new BorderLayout());
				try {
					this.image = ImageIO.read(new File (chemin));
					JLabel contenu = new JLabel();
					contenu.setIcon(new ImageIcon(this.image));
					contenu.setHorizontalAlignment(JLabel.CENTER);
					contenu.setBackground(new Color(246,236,213,255));
					this.setBackground(new Color(246,236,213,255));
					this.add(contenu, BorderLayout.CENTER);
			}
				catch (IOException ex) {
					System.err.println("IO error");
					ex.printStackTrace();}
			}
		}
	}
}

