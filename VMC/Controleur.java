

public class Controleur {
/*Le controleur a besoin d'avoir accès à la vue et à la partie, car c'est lui qui lance menuJouer et menuInitialisation.
Autrement, il y a des problèmes d'ordre d'éxecution des choses dans l'interface graphique. Cela serait peut-être rêglable en utilisant une autre thread et invokeLater ? Vu l'état de nos connaissances sur les threads en ce moment, on a préféré passer outre.*/
	private Jeu jeu;
	private Partie partie;

	public Controleur(Jeu jeu) {
		this.jeu = jeu;
	}

	public void demo() {} /*RAF... Mais plus probablement qu'on va faire l'impasse dessus par manque de temps.*/

/*Les actions possibles dans menuInitialisation sont réparties entre le controleur et la vue. choisitJoueur et quitteJeu sont dans le contrôleur, regles ainsi que l'affichage des joueurs sauvegardés sont dans la partie Vue. On aurait pû faire une boucle avec le controleur, mais cela devenait un peu artificiel, car la partie modèle ne fait que répondre à des demandes d'information (la liste des joueurs sauvegardés, la lecture du fichier règles.txt). Le problème est là : on ne peut pas faire de boucle V→M→C→V si le modèle n’est pas actif. Est-ce un problème ou non ?*/
	public void choisitJoueur() {	
		this.jeu.miseAJourJoueurs();
	}

	public void choisitJoueur(String nom) {
		this.partie = this.jeu.choisitJoueur(nom);
		this.partie.lance();
	}

	public void regles() {
		this.jeu.miseAJourRegles();
	}

	public void quitteJeu() {
		System.exit(0);
	}

/*Les fonctions appelées par menuJouer/CarteMenuJouer dans Vue, qui elles font bien une boucle VMC.
La vue appèle le controleur, qui passe l'information au système, qui met à jour la vue.*/
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
	}
}
