import javax.swing.*;
import java.awt.*;

public abstract class Carte extends JPanel { //pour les cartes menuAction et jouer
	private JPanel menuGauche;
	private JPanel contenuDroite;
	private EnvironnementGI environnement;

	protected void setMenuGauche(JPanel menu) {
		this.menuGauche = menu;
	}

	protected void setContenuDroite(JPanel contenu) {
		this.contenuDroite = contenu;
	}

	protected void setUp(EnvironnementGI environnement) {
		this.environnement = environnement;
		this.setLayout(new BorderLayout());

		this.menuGauche = new JPanel(new BorderLayout());
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

	protected void ajouterActions(JButton[] actions) {
		JPanel panelActions = new JPanel(new GridLayout(actions.length, 1));
		for (JButton action : actions) {
			panelActions.add(action);
		}
		this.menuGauche.add(panelActions, BorderLayout.NORTH);
	}
}
