import javax.swing.*;
import java.awt.*;

public class VueIG extends Vue {
	private JFrame fenetre;
	private JPanel contenuCartes;
	private CardLayout layoutCartes;
	private ControleurIG controleurIG;
	private CarteMenuInitialisation carteInitialisation;
	private CarteMenuJouer carteJouer;

	public VueIG(ControleurIG controleurIG) {
		this.fenetre = new JFrame("Pet Saver");
		this.controleurIG = controleurIG;
		this.carteInitialisation = new CarteMenuInitialisation(this.controleurIG);

		this.contenuCartes = new JPanel();
		this.layoutCartes = new CardLayout();
		this.contenuCartes.setLayout(this.layoutCartes);
		this.contenuCartes.add(this.carteInitialisation, "initialisation");
		this.menuInitialisation(); //redondant mais bon

		this.fenetre.getContentPane().add(this.contenuCartes);
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setSize(900,900);
		this.fenetre.setVisible(true);
	}
	
	protected void menuJouer(Partie partie) {
		this.carteJouer = new CarteMenuJouer(this.controleurIG, partie);
		this.contenuCartes.add(this.carteJouer, "jouer");
		this.layoutCartes.show(this.contenuCartes, "jouer");
	}

	protected void menuInitialisation() {
		this.layoutCartes.show(this.contenuCartes, "initialisation");
		if (this.carteJouer != null) {
			this.layoutCartes.removeLayoutComponent(this.carteJouer);
			this.carteJouer = null;
		}
	}

	protected void bravo() {}//fenêtre popup ?

	protected void miseAJour() {
		if (this.carteJouer !=  null) {
			this.carteJouer.miseAJour();
		}
		else {
			this.carteInitialisation.miseAJour();
		}
	}
}
