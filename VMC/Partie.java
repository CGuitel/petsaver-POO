import java.util.LinkedList;

public class Partie {
	private Vue vue;

	private Joueur joueur;
	private LinkedList<Plateau> historique;
	private Plateau plateauCourant;
	private boolean partieEnCours;
	private int coupCourant;
	private int coupsTotal;

	public Partie(Joueur joueur, Vue vue){
		this.joueur = joueur;
		this.vue = vue;
   		this.historique = new LinkedList<Plateau>();
		this.plateauCourant = Partie.plateauSelonNiveau(this.joueur.getNiveau());
		this.partieEnCours = true;
		this.coupsTotal = 50; //Constante pour l'instant, pourrait être changée ?
		this.coupCourant = 0;
	}

	private static Plateau plateauSelonNiveau(int niveau) {
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

	protected Plateau getPlateauCourant() {
		return this.plateauCourant;
	}

	protected Joueur getJoueur() {
		return this.joueur;
	}

	protected boolean getPartieEnCours() {
		return this.partieEnCours;
	}

	protected void quittePartie() {
		//System.out.println("quittePartie dans Partie"); //RAS
		this.partieEnCours = false;
		if (this.plateauCourant.aGagne()) {
			this.joueur.incrementeNiveau(); //c'est là qu'il va falloir ajouter le score, selon comment on fait...?
		}
		this.vue.menuInitialisation();
	}

	protected void cliqueBloc(int x, int y) {
		this.historique.add(plateauCourant.clone());
		this.plateauCourant.cliqueBloc(x, y);
		this.coupCourant += 1;
		this.vue.miseAJourPlateau();
		this.checkSiPartieFinie();
	}

	protected void utiliseFusee(int x) {
		this.historique.add(plateauCourant.clone());
		this.plateauCourant.utiliseFusee(x);
		this.coupCourant += 1;
		this.vue.miseAJourPlateau();
		this.checkSiPartieFinie();
	}

	protected void annuleAction() {
		try {
			this.plateauCourant = this.historique.pop();
			this.coupCourant += 1; //RAF choisir si ça compte comme +/- 1
			this.vue.miseAJourPlateau();
		} catch (java.util.NoSuchElementException exception) {}
	}

	private void checkSiPartieFinie() {
		//System.out.println("checkSiPartieFinie"); //RAS
		if (this.coupCourant >= this.coupsTotal) {
			this.quittePartie();
		}
		if (this.coupCourant < this.coupsTotal && this.plateauCourant.aGagne()) {
			//System.out.println("quitte en gagnant"); //RAS
			this.vue.bravo();
			this.quittePartie();
		}
	}

	public String toString() {
		return "Niveau : "+this.joueur.getNiveau()+"\tcoups : "+this.coupCourant+"/"+this.coupsTotal;
	}
}
