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
// OU
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

public class CarteMenuJouer extends CarteMenu {
	private ContenuPlateau contenuPlateau;
	private Partie partie;
	private JButton boutonFusee;
	private JLabel decompteFusees;
	private JLabel decompteCoups;
	private JLabel decompteAnimaux;
	

	public CarteMenuJouer(Controleur controleur, Partie partie) {
		super(controleur);
		super.setUp();
		this.partie = partie;

		this.setUpActions();
		this.setUpLabels();
		this.setUpContenu();
	}

	protected void setUpActions() {
		this.boutonFusee = new JButton("utiliser une fusée");
		JButton annuler = new JButton("annuler");
		JButton quitter = new JButton("quitter");

		CarteMenuJouer refThis = this;

		boutonFusee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refThis.contenuPlateau.changeFusee();
			}
		});

		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refThis.controleur.annuleAction();
 			}
		});

		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refThis.controleur.quittePartie();
			}
		});

		JButton[] actions = {boutonFusee, annuler, quitter};
		this.ajouterActions(actions);
	}

	protected void setUpLabels() {
		this.decompteFusees = new JLabel();
		this.decompteCoups = new JLabel();
		this.decompteAnimaux = new JLabel();
		JLabel niveau = new JLabel("Niveau : "+Integer.toString(this.partie.getJoueur().getNiveau()));

		JLabel[] labels = {niveau, this.decompteCoups, this.decompteFusees, this.decompteAnimaux};
		super.ajouterLabels(labels);

		this.miseAJourDecomptes();
	}

	protected void setUpContenu() {
		this.contenuPlateau = new ContenuPlateau(this.partie.getPlateauCourant());
		this.setContenuDroite(this.contenuPlateau);
		/*La création du contenuPlateau est séparée de son remplissage, parce qu'on a besoin qu'il soit mis en place pour pouvoir calculer les dimensions, et donc il faut faire setContenuDroite avant l'affichage. If faut d'ailleurs que la CarteMenuJouer ai été ajoutée au layout de la fenètre.*/
	}

	protected void setUpPlateau() {
		this.contenuPlateau.setUpPlateau(this.partie.getPlateauCourant());
	}

	private void miseAJourDecomptes() {
		String texteCoups = "Coups : "+Integer.toString(this.partie.getCoupCourant());
		texteCoups += "/"+Integer.toString(this.partie.getCoupsTotal());
		this.decompteCoups.setText(texteCoups);

		String texteFusees = "Fusées : "+Integer.toString(this.partie.getPlateauCourant().getFusees());
		this.decompteFusees.setText(texteFusees);

		String texteAnimaux = "Animaux à sauver : "+Integer.toString(this.partie.getPlateauCourant().getAnimaux());
		this.decompteAnimaux.setText(texteAnimaux);
	}

	public void miseAJourPlateau() {
		//this.setContenuDroite(new ContenuPlateau(this.partie.getPlateauCourant()));
		this.contenuPlateau.miseAJour(this.partie.getPlateauCourant());
		this.miseAJourDecomptes();
	}

	private class ContenuPlateau extends JPanel {
		private JPanel panelPlateau;
		private GridBagLayout grille;
		private GridBagConstraints contraintes;
		private int xMax;
		private int yMaxVisible;
		private int tailleBlocs;
		private boolean fusee;

		public ContenuPlateau(Plateau plateau) {
			this.fusee = false;
			this.setLayout(new BorderLayout());
			this.panelPlateau = new JPanel();
			this.panelPlateau.setBackground(new Color(246,236,213,255));
			this.add(this.panelPlateau, BorderLayout.CENTER);
			this.xMax = plateau.getXMax();
			//RAF et pour les fusées ?
			this.panelPlateau.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent event) {
					if (fusee) {
						int x = event.getX()/tailleBlocs;
						controleur.utiliseFusee(x);
						changeFusee();
					}
					else {
						int x = event.getX()/tailleBlocs;
						int y = yMaxVisible - (event.getY()/tailleBlocs) - 1;
						controleur.cliqueBloc(x,y);
					}
				}

				public void mouseReleased(MouseEvent e) {}
			});
		}

		private void changeFusee() {
			if (fusee) {
				fusee = false;
				boutonFusee.setBackground(new JButton().getBackground());
			} else {
				fusee = true;
				boutonFusee.setBackground(new Color(200,200,200,255));
			}
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
						//animal.setPreferredSize(new Dimension(tailleBlocs, tailleBlocs));
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

				this.setPreferredSize(new Dimension(tailleBlocs, tailleBlocs));
				Border contour = new LineBorder(new Color(246,236,213,255), 1, true);
				this.setBorder(contour);
			}

			/*public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				Shape line = new Line2D.Double(0,0,tailleBlocs,tailleBlocs);
				Shape roundRect = new RoundRectangle2D.Double(0,0, tailleBlocs-2, tailleBlocs-2, 1, 1);
				g2d.draw(line);
				g2d.draw(roundRect);
				this.paintComponents(g2d);
			}*/

			
		}

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

