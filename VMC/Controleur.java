import java.util.*;

public class Controleur {
	protected Vue vue;
	protected Partie partie;

	public Controleur() {}

	private void lance(Vue vue) {
		this.vue = vue;
		this.vue.menuInitialisation();
	}	

	protected void demo() {} //RAF

	protected void choisitJoueur(String nom) {
		List<String> liste = new ArrayList<>(Arrays.asList(Joueur.listeJoueursSauvegardes()));
		Joueur joueur;
		if (liste.contains(nom)) {
			joueur = Joueur.deserialise(nom);
		}
		else {
			joueur = Joueur.nouveauJoueur(nom, this.vue);
			this.vue.miseAJourJoueurs();
		}
		this.partie = new Partie(joueur, this.vue);
		this.vue.menuJouer(this.partie);
	}

	protected void quitteJeu() {
		System.exit(0);
	}

	protected void utiliseFusee(int x) {
		this.partie.utiliseFusee(x);
	}
	protected void annuleAction() {
		this.partie.annuleAction();
	}
	protected void cliqueBloc(int x, int y) {
		this.partie.cliqueBloc(x, y);
	}

	protected void quittePartie() {
		this.partie.quittePartie();
	}

	public static void main(String[] args) {
		Controleur jeu = new Controleur();
		if (args[0].equals("IG")) {
			System.out.println("1");
			jeu.lance(new VueIG(jeu));
		} else if (args[0].equals("IT")) {
			jeu.lance(new VueIT(jeu));
		}
	}
}
