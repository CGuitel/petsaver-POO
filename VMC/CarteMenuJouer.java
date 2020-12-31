import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CarteMenuJouer extends CarteMenu {
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
		this.miseAJourPlateau();

		this.setUpActions();
		this.setUpContenu();
		//RAF ajouter les labels pour score fusées et etc
	}

	private void setUpActions() {
		JButton fusee = new JButton("utiliser une fusée");
		JButton annuler = new JButton("annuler");
		JButton quitter = new JButton("quitter");

		CarteMenuJouer refThis = this;

		fusee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//controleurIG.fusee(refThis);
			}
		});

		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//controleurIG.annuler(refThis);
 			}
		});

		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controleurIG.quittePartie();
			}
		});

		JButton[] actions = {fusee, annuler, quitter};
		this.ajouterActions(actions);
	}

	private void setUpContenu() {
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

	public static void main(String[] args) {}
}

