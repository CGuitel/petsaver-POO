import java.util.*;

public class ControleurIT extends Controleur {

	public ControleurIT() {
		this.vue = new VueIT(this);
		this.vue.menuInitialisation(); /*On ne peut pas laisser le constructeur de VueIT lancer le menu initialisation, sinon la vueIT ne sera attribuée à l'attribut this.vue qu'une fois le jeu terminé, ce qui empèche son fonctionnement. Même chose pour nouveauJoueur et miseAJourJoueurs, et Partie et menuJouer. Il y a là une différence entre interface graphique et interface terminal, qui est captée par des conditions */
	}

	protected void demo() {}

	protected void choisitJoueur(String nom) {
		List<String> liste = new ArrayList<>(Arrays.asList(Joueur.listeJoueursSauvegardes()));
		Joueur joueur;
		System.out.println(liste);
		System.out.println("a");
		if (liste.contains(nom)) {
			joueur = Joueur.deserialise(nom);
		} else {
			joueur = Joueur.nouveauJoueur(nom, this.vue);
		}
		this.partie = new Partie(joueur, this.vue);
		this.vue.menuJouer(this.partie);
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
		new ControleurIT();
	}

}
