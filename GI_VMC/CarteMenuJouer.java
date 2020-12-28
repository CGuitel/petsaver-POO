import javax.swing.*;
import java.awt.*;

public class CarteMenuJouer extends Carte {
	JButton fusee = new JButton("utiliser une fusée");
	JButton annuler = new JButton("annuler");
	JButton quitter = new JButton("quitter");
	JButton[] actions = {fusee, annuler, quitter};
	Partie partie;
	//JLables pour score, fusées, etc
	

	public CarteMenuJouer(ControleurIG controleurIG, Partie partie) {
		super.setUp(controleurIG);
		super.ajouterActions(actions);
		this.partie = partie;
		//ajouter les labels pour score fusées et etc
		//reste à générer l'affichage...
	}

	private class CarteDroitePlateau extends JPanel {
		//gérer l'affichage en prenant le plateau 
		public void miseAJour() {}
	}

	public static void main(String[] args) {}
}

