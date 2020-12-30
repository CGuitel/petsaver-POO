import javax.swing.*;
import java.awt.*;

public class CarteMenuJouer extends CarteMenu {
	JButton fusee = new JButton("utiliser une fusée");
	JButton annuler = new JButton("annuler");
	JButton quitter = new JButton("quitter");
	JButton[] actions = {fusee, annuler, quitter};
	CarteDroitePlateau contenuDroite;
	Partie partie;
	//JLables pour score, fusées, etc
	

	public CarteMenuJouer(ControleurIG controleurIG, Partie partie) {
		super(controleurIG);
		super.setUp();
		super.ajouterActions(actions);
		this.partie = partie;
		//ajouter les labels pour score fusées et etc
		//reste à générer l'affichage...
		this.contenuDroite = new CarteDroitePlateau();
		this.miseAJour();
	}

	public void miseAJour() {
		this.contenuDroite.miseAJour(this.partie.getPlateauCourant());
	}

	private class CarteDroitePlateau extends JPanel {
		public CarteDroitePlateau() {}
		public void miseAJour(Plateau plateau) {}
	}

	public static void main(String[] args) {}
}

