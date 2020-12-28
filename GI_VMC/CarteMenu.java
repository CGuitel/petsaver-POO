import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public abstract class CarteMenu extends JPanel { //pour les cartes menuAction et jouer
	private JPanel menuGauche;
	private JPanel contenuDroite;
	private ControleurIG controleurIG;
	//private static BufferedImage fondMenu;

	public CarteMenu(ControleurIG controleurIG) {
		this.controleurIG = controleurIG;
	}

	protected void setMenuGauche(JPanel menu) {
		this.menuGauche = menu;
	}

	protected void setContenuDroite(JPanel contenu) {
		this.contenuDroite = contenu;
	}

	protected void setUp() {
		this.setLayout(new BorderLayout());

		this.contenuDroite = new JPanel();
		this.add(this.contenuDroite, BorderLayout.CENTER);

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
		this.contenuDroite.setBackground(new Color(246,236,213,255));
	}

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
