import java.util.LinkedList;

public class Partie {
	private Vue vue;
	private Controleur controleur;

	private Joueur joueur;
	private LinkedList<Plateau> historique;
	private Plateau plateauCourant;
	private boolean partieEnCours;
	private int coupCourant;
	private int coupsTotal;

	public Partie(Joueur joueur, Vue vue, Controleur controleur){ //RAF ajouter Vue ??
		this.joueur = joueur;
		this.vue = vue;
		this.controleur = controleur;
   		this.historique = new LinkedList<Plateau>();
		this.plateauCourant = Partie.plateauSelonNiveau(this.joueur.getNiveau());
		this.partieEnCours = true;
		this.coupsTotal = 50; //RAF
		this.coupCourant = 0;
		if (vue instanceof VueIG) { //RAF ???
			vue.menuJouer(this);;
		}
	}

	private static Plateau plateauSelonNiveau(int niveau) {
		return new Plateau(5,5,3,1,1);
	}

	public Plateau getPlateauCourant() {
		return this.plateauCourant;
	}

	public Joueur getJoueur() {
		return this.joueur;
	}

	public boolean getPartieEnCours() {
		return this.partieEnCours;
	}

	public void quittePartie() {
		this.partieEnCours = false;
		if (this.plateauCourant.aGagne()) {
			this.joueur.incrementeNiveau(); //c'est là qu'il va falloir ajouter le score, selon comment on fait...?
		}
	}

	public void cliqueBloc(int x, int y) {
		this.historique.add(plateauCourant.clone());
		this.plateauCourant.cliqueBloc(x, y);
		this.coupCourant += 1;
		this.checkSiPartieFinie();
		this.vue.miseAJourPlateau();
	}

	public void utiliseFusee(int x) {
		this.historique.add(plateauCourant.clone());
		this.plateauCourant.utiliseFusee(x);
		this.coupCourant += 1;
		this.checkSiPartieFinie();
		this.vue.miseAJourPlateau();
	}

	public void annuleAction() {
		this.plateauCourant = this.historique.pop();
		this.coupCourant += 1; //RAF choisir si ça compte comme +/- 1
		this.vue.miseAJourPlateau();
	}

	private void checkSiPartieFinie() {
		if (this.coupCourant >= this.coupsTotal || this.plateauCourant.aGagne()) {
			this.partieEnCours = false;
			this.vue.bravo();
		}
	}

	public String toString() {
		return "Niveau : "+this.joueur.getNiveau()+"\tcoups : "+this.coupCourant+"/"+this.coupsTotal;
	}
}
