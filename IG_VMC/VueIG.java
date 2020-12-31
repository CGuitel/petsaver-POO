import javax.swing.*;
import java.awt.*;

public class VueIG extends JFrame implements Vue { //la fenêtre en gros
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
		this.contenuCartes.add(this.carteInitialisation, "initialisation"); //l'équivalent de lancer menuInitialisation()

		this.fenetre.getContentPane().add(this.contenuCartes);
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setSize(900,900);
		this.fenetre.setVisible(true);
	}
	
	public void jouer(Partie partie) {
		this.carteJouer = new CarteMenuJouer(this.controleurIG, partie);
		this.contenuCartes.add(this.carteJouer, "jouer");
		this.layoutCartes.show(this.contenuCartes, "jouer");
		System.out.println("!!");
	}
	//et la fin de la partie ?

	public void bravo() {}//fenêtre popup ? //pb d'héritage de l'interface, donc public...

	public void miseAJour() {//??? peut-être que cette méthode ne devrait pas être là... //pb d'héritage de l'interface, donc public...
		if (this.carteJouer !=  null) {
			this.carteJouer.miseAJour();
		}
		else {
			this.carteInitialisation.miseAJour();
		}
	}
}
