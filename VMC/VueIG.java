import javax.swing.*;
import java.awt.*;

/*VueIG utilise un système de CardLayout qui remplit toute la fenêtre, avec une carte pour l’initialisation et une carte pour le jeu (CarteMenuInitialisation et CarteMenuJouer).*/
public class VueIG extends Vue {
	private CarteMenuInitialisation carteInitialisation;
	private CarteMenuJouer carteJouer;
	private JPanel contenuCartes;
	private CardLayout layoutCartes;
/*On ne l'a pas redéfinit ici, mais VueIG possède un attribut Controleur controleur hérité de Vue.*/

	public VueIG(Controleur controleur) {
		JFrame fenetre = new JFrame("Pet Saver");
		this.controleur = controleur;
		this.carteInitialisation = new CarteMenuInitialisation(this.controleur);

		this.contenuCartes = new JPanel();
		this.layoutCartes = new CardLayout();
		this.contenuCartes.setLayout(this.layoutCartes);
		this.contenuCartes.add(this.carteInitialisation, "initialisation");

		fenetre.getContentPane().add(this.contenuCartes);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(900,900);
		fenetre.setVisible(true);
	}

	public void menuJouer(Partie partie) {
		this.carteJouer = new CarteMenuJouer(this.controleur);
		this.contenuCartes.add(carteJouer, "jouer");
		this.layoutCartes.show(this.contenuCartes, "jouer");
		this.carteJouer.setUpPlateau(partie.getPlateauCourant());
	}

	public void menuInitialisation() {
		this.layoutCartes.show(this.contenuCartes, "initialisation");
		if (this.carteJouer != null) {
			this.layoutCartes.removeLayoutComponent(this.carteJouer);
			this.carteJouer = null;
		}
	}

	public void bravo() {}//Fenêtre popup ? RAF

/*Ces trois fonctions font descendre les instructions entre la partie visible de la vue (Vue IG) et les éléments non-accessibles (carteInitialisation et carteJouer).*/
	public void miseAJourJoueurs(String[] joueurs) {
		this.carteInitialisation.miseAJourJoueurs(joueurs);
	}

	public void miseAJourPartie(Partie partie) {
		this.carteJouer.miseAJourPartie(partie);
	}

	public void miseAJourRegles(String texte) {
		this.carteInitialisation.miseAJourRegles(texte);
	}
}
