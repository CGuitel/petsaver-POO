import java.util.LinkedList;

public class Partie {
	private Vue vue; /*La partie n'a pas accès au controleur, mais la partie menuJouer/CarteMenuJouer et le controleur on tous les deux accès à la Partie. Par exemple, la CarteMenuJouer a besoin d'accéder au noombre de coups restants pour pouvoir l'afficher.
Il serait probablement possible d'éviter ça, en passant toutes les valeurs nécéssaires en argument de miseAJour. Cependant, cela voudrait dire déplacer les toString vers la partie Vue, ce qui est à la fois une bonne idée en terme de principe VMC et un peu énervant.*/
	private Joueur joueur;
	private LinkedList<Plateau> historique;
	private Plateau plateauCourant;
	private boolean partieEnCours;
	private int coupCourant;
	private int coupsTotal;

/*Constructeur et ses fonctions auxiliaires : */
	public Partie(Joueur joueur, Vue vue){
		this.joueur = joueur;
		this.vue = vue;
   		this.historique = new LinkedList<Plateau>();
		this.plateauCourant = Partie.plateauSelonNiveau(this.joueur.getNiveau());
		this.partieEnCours = true;
		this.coupsTotal = 50; //Constante pour l'instant, pourrait être changée ?
		this.coupCourant = 0;
	}

	private static Plateau plateauSelonNiveau(int niveau) {/*On a séparé la création du plateau (le constructeur de Plateau) et le calcul de la difficulté.*/
		int xmax = 10;
		int ymax = niveau * 5;
		if (ymax > 30) {
			ymax = 30;
		}
		int couleurs = niveau;
		if (couleurs > 6) {
			couleurs = 6;
		}
		int fusees = 2;
		int animaux = niveau;
		if (animaux > 6) {
			animaux = 6;
		}
		return new Plateau(xmax, ymax, couleurs, fusees, animaux);
	}

/*Getters : */

	public Plateau getPlateauCourant() {
		return this.plateauCourant;
	}


	public int getCoupCourant() {
		return this.coupCourant;
	}

	public int getCoupsTotal() {
		return this.coupsTotal;
	}

	public Joueur getJoueur() {
		return this.joueur;
	}

	public boolean getPartieEnCours() {
		return this.partieEnCours;
	}

/*Fonctions du déroulement de la partie.
La fonction quittePartie sert à la fois quand l'utilisateur décide de quitter la partie avant d'avoir gagné, et quand il est à court de coups ou qu'il a gagné et que la partie se termine d'elle même.
Les fonctions cliqueBloc et utiliseFusee appèlent les fonctions homonymes de la classe Plateau.*/

	public void quittePartie() {
		this.partieEnCours = false;
		if (this.plateauCourant.aGagne()) {
			this.joueur.incrementeNiveau(); //c'est là qu'il faudrait ajouter le score, selon comment on fait...?
		}
		this.vue.menuInitialisation();
	}

	public void cliqueBloc(int x, int y) {
		this.historique.add(plateauCourant.clone());
		this.plateauCourant.cliqueBloc(x, y);
		this.coupCourant += 1;
		this.vue.miseAJourPlateau();
		this.checkSiPartieFinie();
	}

	public void utiliseFusee(int x) {
		this.historique.add(plateauCourant.clone());
		this.plateauCourant.utiliseFusee(x);
		this.coupCourant += 1;
		this.vue.miseAJourPlateau();
		this.checkSiPartieFinie();
	}

	public void annuleAction() {
		try {
			this.plateauCourant = this.historique.removeLast();
			this.coupCourant -= 1; //RAF choisir si ça compte comme +/- 1
			this.vue.miseAJourPlateau();
		} catch (java.util.NoSuchElementException exception) {}
	}

/*Fonction auxiliaire, utilisée à chaque coup (clique ou fusée).
Enclenche la fin de la partie si elle est à son court.*/

	private void checkSiPartieFinie() {
		if (this.coupCourant >= this.coupsTotal) {
			this.quittePartie();
		}
		if (this.coupCourant < this.coupsTotal && this.plateauCourant.aGagne()) {
			this.vue.bravo();
			this.quittePartie();
		}
	}

/*toString pour l'affichage terminal, n'a peut-être pas sa place dans une classe du modèle.*/

	public String toString() {
		return "Niveau : "+this.joueur.getNiveau()+"\tcoups : "+this.coupCourant+"/"+this.coupsTotal;
	}
}
