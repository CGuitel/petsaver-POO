import java.util.*;

public class ControleurIG extends Controleur {
	private VueIG vueIG;
	private Partie partie;

	public ControleurIG() {
		this.vueIG = new VueIG(this);
	}

/*menuInitialisation*/
	protected void regles(CarteMenuInitialisation carteInitialisation) {
		carteInitialisation.regles();
	}

	protected void choixDuJoueur(CarteMenuInitialisation carteInitialisation) {
		carteInitialisation.choixDuJoueur();
	}

	protected void choisitJoueur(String nom) {
		List<String> liste = new ArrayList<>(Arrays.asList(Joueur.listeJoueursSauvegardes()));
		Joueur joueur;
		if (liste.contains(nom)) {
			joueur = Joueur.deserialise(nom);
		}
		else {
			joueur = Joueur.nouveauJoueur(nom);
		}
		System.out.println("appel à miseajour générale dans le controleur");
		this.vueIG.miseAJour(); /*Pour mettre à jour la liste des joueurs existants dans la carte choisirJoueur dans menuInitialisation. pour vraiment respecter le schéma VMC, il aurait fallu que ce soit la fonction nouveauJoueur de Joueur qui appèle la mise à jour de la vue.*/
		this.partie = new Partie(joueur, this.vueIG);
		this.vueIG.menuJouer(this.partie);
	}

	protected void demo() {}

	protected void quitteJeu() {
		System.exit(0);
	}

/*menuJouer*/
	protected void utiliseFusee(int x) {
	}

	protected void annuleAction() {
	}

	protected void cliqueBloc(int x, int y) {
	}

	protected void quittePartie() {
		this.vueIG.menuInitialisation();
	}

	public static void main(String[] args) {
		new ControleurIG();
	}
}
