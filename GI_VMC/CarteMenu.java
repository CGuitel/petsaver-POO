import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

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

		this.menuGauche = new JPanel(new BorderLayout());
		
/*import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
	BufferedImage monImage;
	JLabel imageFon;
	
	Carte (String chemin) {
		try {
			monImage = ImageIO.read(new File(chemin));
			imageFon = new JLabel(new ImageIcon(monImage));
			imageFon.setLayout(new FlowLayout());
			this.add(imageFon);
		}
		catch (IOException e) {
			System.err.println("IO error: "+ e.getMessage());
			e.printStackTrace();
		}
	}*/

		this.add(this.menuGauche, BorderLayout.WEST);

		JLabel titre = new JLabel("PET SAVER");
		JLabel auteure1 = new JLabel("Valentina Fedchenko");
		JLabel auteure2 = new JLabel("Cécile Guitel");
		JLabel credits1 = new JLabel("Élements graphiques adaptés de OpenMoji");
		JLabel credits2 = new JLabel("License: CC BY-SA 4.0");
		JPanel panelTexte = new JPanel(new GridLayout(5,1));
		panelTexte.add(titre);
		panelTexte.add(auteure1);
		panelTexte.add(auteure2);
		panelTexte.add(credits1);
		panelTexte.add(credits2);
		this.menuGauche.add(panelTexte, BorderLayout.SOUTH);

		this.contenuDroite = new JPanel();
		this.add(this.contenuDroite, BorderLayout.CENTER);
	}

	/*private class MenuGauche extends JPanel { //pour pouvoir rajouter le fond
		public MenuGauche() {
			super(new BorderLayout());
		}

		public void paintComponent(Graphics g) {
			g.drawImage(CarteMenu.fondMenu, 0, 0, null);
		}
	}*/

	protected void ajouterActions(JButton[] actions) {
		JPanel panelActions = new JPanel(new GridLayout(actions.length, 1));
		for (JButton action : actions) {
			panelActions.add(action);
		}
		this.menuGauche.add(panelActions, BorderLayout.NORTH);
	}

	protected void ajouterLabel(JLabel label) {
		this.menuGauche.add(label, BorderLayout.NORTH);
	}
//	
}
