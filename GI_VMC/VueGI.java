import javax.swing.*;
import java.awt.*;

public class VueGI extends JFrame implements Vue { //la fenêtre en gros
	private JFrame fenetre = new JFrame("Pet Saver");
	private Container contenu = fenetre.getContentPane();
	private ControleurIG controleurIG;

	public VueGI(ControleurIG controleurIG) {
		this.controleurIG = controleurIG;
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setSize(900,900);
		this.fenetre.setVisible(true);
		this.contenu.setLayout(new CardLayout(0,0));
		this.contenu.add(new CarteMenuInitialisation(this.controleurIG)); //l'équivalent de lancer menuInitialisation()
	}
	
	public <T extends Component> void jouer(T cartePartie) { //une carte de base menuAction, on ajoute une carte jouer
		this.contenu.add(cartePartie);
	}


	public void bravo() {}//fenêtre popup ? //pb d'héritage de l'interface, donc public...
	public void miseAJour() {}//??? peut-être que cette méthode ne devrait pas être là... //pb d'héritage de l'interface, donc public...


	public static void main(String[] args) {
		//VueGI vue = new VueGI(controleur);
	}
}
