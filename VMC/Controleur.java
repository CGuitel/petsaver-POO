

public class Controleur {
/*On a séparé la partie modèle en deux : le lanceur (menu initialisation) et la partie (menu jouer). On aurait pu mettre partie en attribut du lanceur et passer toujours par le lanceur, mais ça aurait voulu dire rajouter une étape dans l'excécution de toutes les fonctions du menu jouer : controleur->lanceur->partie->(plateau->)vue. En règle gérérale, on aurait plus tendance à séparer l'initialisation et la partie en deux contrôleurs. Voir le schéma des interactions VMC.*/
	private Lanceur lanceur;
	private Partie partie;

	public Controleur(Lanceur lanceur) {
		this.lanceur = lanceur;
	}

/*Les fonctions d'initialisation : */
	public void demo() {} /*RAF... Mais plus probablement qu'on va faire l'impasse dessus par manque de temps.*/

/*Les fonctions choisirJoueur et aChoisitJoueur sont nécéssaires au circuit fermé à sens unique du VMC. Il faut d'abord afficher les joueurs sauvegardés et demander de choisir un joueur, puis on peut transmettre le choix au modèle.*/
	public void choisirJoueur() {	
		this.lanceur.miseAJourJoueurs();
	}

	public void aChoisitJoueur(String nom) {
		this.partie = this.lanceur.choisitJoueur(nom);
		this.partie.lance();
	}

	public void regles() {
		this.lanceur.miseAJourRegles();
	}

	public void quitteJeu() {
		System.exit(0);
	}

/*Les fonctions de jeu : */
	public void utiliseFusee(int x) {
		this.partie.utiliseFusee(x);
	}
	public void annuleAction() {
		this.partie.annuleAction();
	}
	public void cliqueBloc(int x, int y) {
		this.partie.cliqueBloc(x, y);
	}

	public void quittePartie() {
		this.partie.quittePartie();
		this.partie = null;
	}
}
