import java.util.*;

public class ControleurIG extends Controleur {
	public ControleurIG() {
		this.vue = new VueIG(this);
	}

/*menuInitialisation*/
	protected void choisitJoueur(String nom) {
		List<String> liste = new ArrayList<>(Arrays.asList(Joueur.listeJoueursSauvegardes()));
		Joueur joueur;
		if (liste.contains(nom)) {
			joueur = Joueur.deserialise(nom);
		}
		else {
			joueur = Joueur.nouveauJoueur(nom, this.vue); //nouveauJoueur() lance la mise à jour de l'affichage si en IG
		}
		this.partie = new Partie(joueur, this.vue); //Partie() lance la mise à jour de l'affichage si en IG
	}

	protected void demo() {}

/*menuJouer*/
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
		this.vue.menuInitialisation();
	}

	public static void main(String[] args) {
		new ControleurIG();
	}
}