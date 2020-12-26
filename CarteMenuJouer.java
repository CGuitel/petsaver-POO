import javax.swing.*;
import java.awt.*;

public class CarteMenuJouer extends Carte {
	JButton fusee = new JButton("utiliser une fusée");
	JButton annuler = new JButton("annuler");
	JButton quitter = new JButton("quitter");
	JButton[] actions = {fusee, annuler, quitter};
	Partie partie;

	public CarteMenuJouer(EnvironnementGI environnement, Partie partie) {
		super.setUp(environnement);
		super.ajouterActions(actions);
		this.partie = partie; //reste à générer l'affichage...
	}

	public static void main(String[] args) {}
}

