import javax.swing.*;
import java.awt.*;

public class VueIG extends Vue {
	private CarteMenuInitialisation carteInitialisation;
	private CarteMenuJouer carteJouer;
	private JPanel contenuCartes;
	private CardLayout layoutCartes;

	public VueIG(ControleurIG controleurIG) {
		JFrame fenetre = new JFrame("Pet Saver");
		this.controleur = controleurIG;
		this.carteInitialisation = new CarteMenuInitialisation(this.controleur);

		this.contenuCartes = new JPanel();
		this.layoutCartes = new CardLayout();
		this.contenuCartes.setLayout(this.layoutCartes);
		this.contenuCartes.add(this.carteInitialisation, "initialisation");
		this.menuInitialisation(); //redondant mais bon

		fenetre.getContentPane().add(this.contenuCartes);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(900,900);
		fenetre.setVisible(true);
	}

	protected void menuJouer(Partie partie) {
		this.partie = partie;
		this.carteJouer = new CarteMenuJouer(this.controleur, this.partie);
		this.contenuCartes.add(carteJouer, "jouer");
		this.layoutCartes.show(this.contenuCartes, "jouer");
	}

	protected void menuInitialisation() {
		this.layoutCartes.show(this.contenuCartes, "initialisation");
		if (this.carteJouer != null) {
			this.layoutCartes.removeLayoutComponent(this.carteJouer);
			this.carteJouer = null;
		}
	}

	protected void regles() {
		this.carteInitialisation.regles();
	}

	protected void bravo() {}//fenêtre popup ?

	protected void miseAJourJoueurs() {
		this.carteInitialisation.miseAJourJoueurs();
	}

	protected void miseAJourPlateau() {
		//if (this.partie.getPartieEnCours()) {
			this.carteJouer.miseAJourPlateau();
		//}
	}
}