import java.util.LinkedList;

public class Partie {
	private Joueur joueur;
	private LinkedList<Plateau> historique;
	private Plateau plateauCourant;
	private boolean partieEnCours;
	private int coupCourant;
	private int coupsTotal;

	public Partie(Joueur joueur){
		this.joueur = joueur;
   		this.historique = new LinkedList<Plateau>();
		this.plateauCourant = Partie.plateauSelonNiveau(this.joueur.getNiveau());
		this.partieEnCours = true;
		this.coupsTotal = 50; //RAF
		this.coupCourant = 0;
	}

	private static Plateau plateauSelonNiveau(int niveau) {
		return new Plateau(5,5,3,1,1);
	}

	public Plateau getPlateauCourant() {
		return this.plateauCourant;
	}

	public void joue(){
		Environnement.communication.affiche(this);

		while (partieEnCours) {
			int action = Environnement.communication.demandeActionJouer();
			//Que voulez vous faire ? Écrivez le numéro de l'action choisie :

			if (action == 1) {//1 cliquer sur une case
				int xmax = this.plateauCourant.getXMax();
				int ymax = this.plateauCourant.getYMax();
				int x = Environnement.communication.demandeCoordonneeX(xmax);
				int y = Environnement.communication.demandeCoordonneeY(ymax);
				this.historique.add(plateauCourant.clone());
				this.plateauCourant.cliqueBloc(x, y);
				this.coupCourant += 1;
			}
			else if (action == 2) {//2 utiliser une action spéciale
				int xmax = this.plateauCourant.getXMax();
				int x = Environnement.communication.demandeCoordonneeX(xmax);
				this.historique.add(plateauCourant.clone());
				this.plateauCourant.utiliseFusee(x);
				this.coupCourant += 1;
			}
			else if (action == 3) {//3 annuler la dernière action (attention, c'est irréversible !)
				this.plateauCourant = this.historique.pop();
				this.coupCourant += 1; //RAF choisir si ça compte comme +/- 1
			}
			else if (action == 4) {//4 quitter la partie (elle ne sera pas sauvegardée)";
				this.partieEnCours = false;
			}
			Environnement.communication.affiche(this);
			checkSiPartieFinie();
		}
	}

	private void checkSiPartieFinie() {
		if (this.coupCourant >= this.coupsTotal) {
			this.partieEnCours = false;
		}
		else if (this.plateauCourant.aGagne()) {
			this.partieEnCours = false;
			Environnement.communication.bravo();
			this.joueur.incrementeNiveau();
			this.joueur.serialise(); //on sauvegarde automatiquement le passage au niveau supérieur
		}
	}

	public String toString() {
		return "Niveau : "+this.joueur.getNiveau()+"\tcoups : "+this.coupCourant+"/"+this.coupsTotal;
	}
}
