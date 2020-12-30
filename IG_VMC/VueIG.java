import javax.swing.*;
import java.awt.*;

public class VueIG extends JFrame implements Vue { //la fenêtre en gros
	private JFrame fenetre;
	private Container contenu;
	private ControleurIG controleurIG;
	private CarteMenuInitialisation carteInitialisation;
	private CarteMenuJouer carteJouer;

	public VueIG(ControleurIG controleurIG) {
		this.fenetre = new JFrame("Pet Saver");
		this.contenu = fenetre.getContentPane();
		this.controleurIG = controleurIG;
		this.carteInitialisation = new CarteMenuInitialisation(this.controleurIG);

		this.contenu.setLayout(new CardLayout(0,0));
		this.contenu.add(this.carteInitialisation); //l'équivalent de lancer menuInitialisation()

		//this.getContentPane().setBackground(new Color(150,150,0,150)); //ne marche pas...
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setSize(900,900);
		this.fenetre.setVisible(true);
	}
	
	public void jouer(CarteMenuJouer carteJouer) {
		this.carteJouer = carteJouer;
		this.contenu.add(this.carteJouer);
	}
	//et la fin de la partie ?

	public void bravo() {}//fenêtre popup ? //pb d'héritage de l'interface, donc public...

	public void miseAJour() {//??? peut-être que cette méthode ne devrait pas être là... //pb d'héritage de l'interface, donc public...
		if (this.carteJouer !=  null) {
			this.carteJouer.miseAJour();
		}
	}
}
