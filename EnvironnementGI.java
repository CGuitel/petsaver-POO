import javax.swing.*;
import java.awt.*;

public class EnvironnementGI extends JFrame { //la fenêtre en gros
	private JFrame fenetre = new JFrame("Pet Saver");
	private Container contenu = fenetre.getContentPane();
	protected CommunicationGI communication; //peut pas être final, parce qu'on est obligé d'avoir des instances de EnvironnementGI RAF à voir

	public EnvironnementGI() {
		this.communication = new CommunicationGI(this);
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setSize(900,900);
		this.fenetre.setVisible(true);
		this.contenu.setLayout(new CardLayout(0,0));
	}
	
	public <T extends Component> void jouer(T cartePartie) { //une carte de base menuAction, on ajoute une carte jouer
		this.contenu.add(cartePartie);
	}


	public static void main(String[] args) {
		EnvironnementGI jeu = new EnvironnementGI();
		jeu.contenu.add(new CarteMenuEnvironnement()); //l'équivalent de lancer menuEnvironnement()
	}
}
