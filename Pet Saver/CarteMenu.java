import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/*Défini les cartes CarteMenuJouer et CarteMenuInitialisation.
Chaque carte menu est séparée en deux zones : à gauche, des boutons et des labels, à droite, un panel laissé vide pour le contenu. CarteMenu met à disposition les fonctions nécessaires pour gérer les éléments qui ne sont pas communs aux deux cartes.
Bien que l'attribut contrôleur ne soit pas redéclaré dans les classes filles, il y est quand même présent.*/
public abstract class CarteMenu extends JPanel {
	private JPanel menuGauche;
	private JPanel menuGaucheHaut;
	private JPanel conteneurDroite;
	protected Controleur controleur;

	public CarteMenu(Controleur controleur) {
		this.controleur = controleur;
		this.setUp();
	}

/*La partie commune à toutes les cartes, y compris les éléments auxquels on va ajouter d'autres éléments :
menuGaucheHaut pour les boutons et les labels, conteneurDroite pour le plateau ou autres.
Mise en place automatiquement par l'appel à super dans les classes filles.*/
	protected void setUp() {
		this.setLayout(new BorderLayout());

		this.conteneurDroite = new JPanel(new BorderLayout());
		this.add(this.conteneurDroite, BorderLayout.CENTER);

		this.menuGauche = new JPanel(new BorderLayout());
		this.add(this.menuGauche, BorderLayout.WEST);

		this.menuGaucheHaut = new JPanel(new BorderLayout());
		this.menuGauche.add(this.menuGaucheHaut, BorderLayout.NORTH);

		JLabel titre1 = new JLabel("PET");
		titre1.setFont(new Font("freemono", Font.BOLD, 65));
		JLabel titre2 = new JLabel("SAVER");
		titre2.setFont(new Font("freemono", Font.BOLD, 65));
		JLabel auteure1 = new JLabel("Valentina Fedchenko");
		auteure1.setFont(new Font("FreeMono", Font.BOLD, 20));
		JLabel auteure2 = new JLabel("Cécile Guitel");
		auteure2.setFont(new Font("FreeMono", Font.BOLD, 20));
		JLabel credits1 = new JLabel("Élements graphiques adaptés de OpenMoji");
		credits1.setFont(new Font("FreeMono", Font.BOLD, 10));
		JLabel credits2 = new JLabel("License : CC BY-SA 4.0");
		credits2.setFont(new Font("FreeMono", Font.BOLD, 10));
		JPanel panelTexte = new JPanel();
		panelTexte.setLayout(new BoxLayout(panelTexte, BoxLayout.PAGE_AXIS));
		panelTexte.add(titre1);
		panelTexte.add(titre2);
		panelTexte.add(auteure1);
		panelTexte.add(auteure2);
		panelTexte.add(credits1);
		panelTexte.add(credits2);
		panelTexte.setBackground(new Color(0,0,0,0));
		panelTexte.setBorder(new LineBorder(new Color(246,236,213,255), 10, true));
		this.menuGauche.add(panelTexte, BorderLayout.SOUTH);

		PanelImage fond = new PanelImage("./../éléments visuels/menu background.png"); //RAF là va falloir changer le chemin si on bouge les fichiers...
		this.menuGauche.add(fond, BorderLayout.CENTER);

		this.menuGauche.setBackground(new Color(246,236,213,255));
		this.conteneurDroite.setBackground(new Color(246,236,213,255));
	}

/*Nous avons remarqué que si l'on utilise attributContenu = nouveauContenu, l'affichage n'est pas mis à jour, même avec validate/revalidate/repaint.
La solution que l'on a trouvé est d'utiliser add, qui renouvèle bien l'affichage. Il existe probablement une meilleure méthode.*/
	protected void setContenuDroite(JPanel contenu) { 
		this.conteneurDroite.add(contenu, BorderLayout.CENTER);
	}

/*Fonctions auxiliaires pour rajouter les boutons et les labels en haut à gauche.*/
	protected void ajouterActions(JButton[] actions) {
		GridLayout layout = new GridLayout(actions.length, 1);
		layout.setVgap(5);
		layout.setHgap(5);
		JPanel panelActions = new JPanel(layout);
		panelActions.setBackground(new Color(246,236,213,255));
		panelActions.setBorder(new LineBorder(new Color(246,236,213,255), 10, true));
		for (JButton action : actions) {
			action.setMargin(new Insets(2,2,2,2));
			action.setFont(new Font("FreeMono", Font.BOLD, 15));
			panelActions.add(action);
		}
		this.menuGaucheHaut.add(panelActions, BorderLayout.NORTH);
	}

	protected void ajouterLabels(JLabel[] labels) {
		GridLayout layout = new GridLayout(labels.length, 1);
		layout.setVgap(5);
		layout.setHgap(5);
		JPanel panelLabels = new JPanel(layout);
		panelLabels.setBackground(new Color(246,236,213,255));
		panelLabels.setBorder(new LineBorder(new Color(246,236,213,255), 10, true));
		for (JLabel label : labels) {
			label.setFont(new Font("FreeMono", Font.BOLD, 15));
			panelLabels.add(label);
		}
		this.menuGaucheHaut.add(panelLabels, BorderLayout.SOUTH);
	}

/*Classe interne utilisée par setUp pour ajouter le fond à gauche.*/
	private class PanelImage extends JPanel {
		private BufferedImage image;

		public PanelImage(String chemin){
			try {
				this.image = ImageIO.read(new File(chemin));
			} catch (IOException exception) {
				System.err.println("IO error: "+ exception.getMessage());
				exception.printStackTrace();
			}
			this.setBackground(new Color(246,236,213,255));
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(this.image,0,0,this);
		}
	}	
}
