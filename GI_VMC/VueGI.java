import javax.swing.*;
import java.awt.*;

public class VueGI extends JFrame implements Vue { //la fenêtre en gros
	private JFrame fenetre = new JFrame("Pet Saver");
	private Container contenu = fenetre.getContentPane();
	private ControleurGI controleurGI;

	public VueGI(ControleurGI controleurGI) {
		this.controleurGI = controleurGI;
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setSize(900,900);
		this.fenetre.setVisible(true);
		this.contenu.setLayout(new CardLayout(0,0));
	}
	
	public <T extends Component> void jouer(T cartePartie) { //une carte de base menuAction, on ajoute une carte jouer
		this.contenu.add(cartePartie);
	}

	private void regles();
	private void bravo();
	private void miseAJour();


	public static void main(String[] args) {
		VueGI vue = new EnvironnementGI();
		vue.contenu.add(new CarteMenuEnvironnement()); //l'équivalent de lancer menuEnvironnement()
	}
}
