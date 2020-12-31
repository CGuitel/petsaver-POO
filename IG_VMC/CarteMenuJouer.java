import javax.swing.*;
import java.awt.*;

public class CarteMenuJouer extends CarteMenu {
	private JButton fusee = new JButton("utiliser une fusée");
	private JButton annuler = new JButton("annuler");
	private JButton quitter = new JButton("quitter");
	private JButton[] actions = {fusee, annuler, quitter};
	private ContenuDroitePlateau contenuPlateau;
	private ControleurIG controleurIG;
	private Partie partie;
	//JLabels pour score, fusées, etc
	

	public CarteMenuJouer(ControleurIG controleurIG, Partie partie) {
		super(controleurIG);
		super.setUp();
		this.partie = partie;
		this.controleurIG = controleurIG;

		this.contenuPlateau = new ContenuDroitePlateau();
		this.setContenuDroite(this.contenuPlateau);
		this.miseAJour();

		super.ajouterActions(actions);
		//RAF ajouter les labels pour score fusées et etc

		System.out.println("constructeur de CarteMenuJouer");
	}

	public void miseAJour() {
		this.contenuPlateau.miseAJour(this.partie.getPlateauCourant());
	}

	private class ContenuDroitePlateau extends JPanel {
		public ContenuDroitePlateau() {}
		public void miseAJour(Plateau plateau) {
			this.setBackground(new Color(150,150,150,255));
		}
	}

	public static void main(String[] args) {}
}

