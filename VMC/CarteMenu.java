import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public abstract class CarteMenu extends JPanel { //Pour les cartes menuAction et jouer. Dans son propre fichier parce que sérieux ça ferait long d'avoir toutes les classes cartes dans le même fichier.
	private JPanel menuGauche;
	private JPanel conteneurDroite;
	protected Controleur controleur;

	public CarteMenu(Controleur controleur) {
		this.controleur = controleur;
	}

	protected void setContenuDroite(JPanel contenu) { /*Nous avons remarqué que si l'on utilise attributContenu = nouveauContenu, l'affichage n'est pas mis à jour. La solution que l'on a trouvé est de faire attributConteneur.add(nouveauContenu). Il existe probablement une meilleur méthode.*/
		this.conteneurDroite.add(contenu);
	}

	protected void setUp() {
		this.setLayout(new BorderLayout());

		this.conteneurDroite = new JPanel();
		this.add(this.conteneurDroite, BorderLayout.CENTER);

		this.menuGauche = new JPanel(new BorderLayout());
		this.add(this.menuGauche, BorderLayout.WEST);

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

		class PanelImage extends JPanel {
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

		PanelImage fond = new PanelImage("./../éléments visuels/menu background.png");
		this.menuGauche.add(fond, BorderLayout.CENTER);

		this.menuGauche.setBackground(new Color(246,236,213,255));
		this.conteneurDroite.setBackground(new Color(246,236,213,255));
	}

	protected abstract void setUpActions();
	protected abstract void setUpContenu();

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
		this.menuGauche.add(panelActions, BorderLayout.NORTH);
	}

	protected void ajouterLabel(JLabel label) {
		label.setBackground(new Color(246,236,213,255));
		label.setFont(new Font("FreeMono", Font.BOLD, 15));
		this.menuGauche.add(label, BorderLayout.NORTH);
	}	
}
