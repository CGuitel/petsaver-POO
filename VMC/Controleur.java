import java.util.ArrayList;
import java.util.Arrays;

public class Controleur {
/*Le controleur a besoin d'avoir accès à la vue et à la partie, car c'est lui qui lance menuJouer et menuInitialisation.
Autrement, il y a des problèmes d'ordre d'éxecution des choses dans l'interface graphique. Cela serait peut-être rêglable en utilisant une autre thread et invokeLater ? Vu l'état de nos connaissances sur les threads en ce moment, on a préféré passer outre.*/
	private Vue vue;
	private Partie partie;

	public Controleur() {}

	private void lance(Vue vue) {
/*On a séparé la création du controleur et l'ajout de la vue car sinon, on a new Controleur(new Vue(???)), où ??? est supposé être l'adresse du controleur...*/
		this.vue = vue;
		this.vue.menuInitialisation();
	}	

	public void demo() {} /*RAF... Mais plus probablement qu'on va faire l'impasse dessus par manque de temps.*/

/*Les actions possibles dans menuInitialisation sont réparties entre le controleur et la vue. choisitJoueur et quitteJeu sont dans le contrôleur, regles ainsi que l'affichage des joueurs sauvegardés sont dans la partie Vue. On aurait pû faire une boucle avec le controleur, mais cela devenait un peu artificiel, car la partie modèle ne fait que répondre à des demandes d'information (la liste des joueurs sauvegardés, la lecture du fichier règles.txt). Le problème est là : on ne peut pas faire de boucle V→M→C→V si le modèle n’est pas actif. Est-ce un problème ou non ?*/

	public void choisitJoueur(String nom) {
		ArrayList<String> liste = new ArrayList<>(Arrays.asList(Joueur.listeJoueursSauvegardes()));
		Joueur joueur;
		if (liste.contains(nom)) {
			joueur = Joueur.deserialise(nom);
		}
		else {
			joueur = new Joueur(nom, this.vue);
			this.vue.miseAJourJoueurs();
		}
		this.partie = new Partie(joueur, this.vue);
		this.vue.menuJouer(this.partie);
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

	public static void main(String[] args) {
/*Pour faire fonctionner le programme, il faut:
1 compiler avec "javac *.java,
2 lancer le programme avec "java Controleur IG" ou "java Controleur IT"*/
		Controleur jeu = new Controleur();
		if (args[0].equals("IG")) {
			jeu.lance(new VueIG(jeu));
		} else if (args[0].equals("IT")) {
			jeu.lance(new VueIT(jeu));
		}
	}
}
