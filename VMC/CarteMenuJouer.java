import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CarteMenuJouer extends CarteMenu {
	private ContenuDroitePlateau contenuPlateau;
	private Partie partie;
	//JLabels pour score, fusées, etc
	

	public CarteMenuJouer(Controleur controleur, Partie partie) {
		super(controleur);
		super.setUp();
		this.partie = partie;

		this.contenuPlateau = new ContenuDroitePlateau();
		this.setContenuDroite(this.contenuPlateau);
		this.miseAJourPlateau();

		this.setUpActions();
		this.setUpContenu();
		//RAF ajouter les labels pour score fusées et etc
	}

	protected void setUpActions() {
		JButton fusee = new JButton("utiliser une fusée");
		JButton annuler = new JButton("annuler");
		JButton quitter = new JButton("quitter");

		CarteMenuJouer refThis = this;

		fusee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//controleur.fusee(refThis);
			}
		});

		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//controleur.annuler(refThis);
 			}
		});

		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleur.quittePartie();
			}
		});

		JButton[] actions = {fusee, annuler, quitter};
		this.ajouterActions(actions);
	}

	protected void setUpContenu() {
		this.miseAJourPlateau();
	}

	public void miseAJourPlateau() {
		this.contenuPlateau.miseAJour(this.partie.getPlateauCourant());
	}

	private class ContenuDroitePlateau extends JPanel {
		public ContenuDroitePlateau() {
			//RAF
		}

		public void miseAJour(Plateau plateau) {
			this.setBackground(new Color(150,150,150,255));
		}
	}
}

