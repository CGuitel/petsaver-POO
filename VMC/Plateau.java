import java.lang.Math;
import java.util.Random;


public class Plateau implements Cloneable{
	private Piece[][] plateau;
	private int xmax;
	private int ymax;
	private int fusees; //RAF là c'est la version statique, les fusées sont un paramètre qui dépend des niveaux, pas du joueur et de son score. Ça serait sympa à implémenter, on verra plus tard.
	private int animaux;

	public Plateau(int xmax, int ymax, int couleurs, int fusees, int animaux) {
		if (xmax <= 10) {
			this.xmax = xmax;
		}
		else {
			this.xmax = 10; //RAF fixé car affichage sur terminal...
		}
		this.ymax = ymax;
		this.plateau = new Piece[this.xmax][this.ymax];

//Pour gérer les bords du tableau, on a d'abord ajouté une bordure de cases null. Ensuite, on a opté pour des blocs try/catch qui permettent de gérer les exceptions IndexOutOfBound. Cela facilite l'écriture des boucles de parcours du tableau et alège les conditions.

		this.fusees = fusees;

		this.animaux = 0;
		this.ajouteAnimaux(animaux);

		this.ajouteBlocs(couleurs);
	}

//On a voulu garder la nomenclature de x et y ainsi que l'abcisse en bas à gauche, afin de faciliter le repérage. C'est un choix personnel, on aurait aussi bien pu parler de hauteur et de largeur, et mettre les 0 dans un autre coin. Mettre le haut du tableau au bord opposé de 0 facilite l'écriture des fonctions comme gravite(), avec des boucles d'incrémentation positives, mais on doit alors renverser l'ordre de parcours pour certaines fonctions comme ajouteAnimaux() et toString().

// Pour introduire un élément de hasard, nous utilisons un objet Random. Cela suffit amplement pour ajouteAnimaux. Cependant, il aurait été intéressant de pouvoir contrôler la probabilité de deux blocs voisins de même couleur. Nous avions pensé à utiliser une distribution de probabilité pour cela, mais n'avons pas trouvé d'outils pour le faire. Nous avons donc décidé de gérer ce facteur de difficulté grâce au nombre de couleurs.

	private void ajouteAnimaux(int nAnimauxTotal) {
		Random random = new Random();
		for (int y = this.ymax - 1 ; y >= 0 ; y--) {
			for (int x = 0; x < this.xmax; x++) {
				boolean animalSurCase = true;
				if (nAnimauxTotal == 1) { //Problème avec 1*2/3 = 0...
					animalSurCase = (random.nextInt(this.xmax) < nAnimauxTotal);
				}
				else {
					animalSurCase = (random.nextInt(this.xmax) < nAnimauxTotal * 2/3);
				}
				if (animalSurCase && this.animaux < nAnimauxTotal) {
					//System.out.println("x,y : " + Integer.toString(x) + "," + Integer.toString(y));
					this.plateau[x][y] = new Animal(random.nextInt(3) + 1); // 3 = nb types animaux
					this.animaux += 1;
				}
			}
		}
	}

	private void ajouteBlocs(int nbCouleurs) {
		Random random = new Random();
		for (int y = 0; y < this.ymax; y++) {
			for (int x = 0; x < this.xmax; x++) {
				if (this.plateau[x][y] == null) { //Si il n'y a pas d'animal sur la case.
					this.plateau[x][y] = new Bloc(random.nextInt(nbCouleurs) + 1);
				}
			}
		}
	}

	public int getXMax() {
		return this.xmax;
	}

	public int getYMax() {
		return this.ymax;
	}

	public boolean aGagne() {
		return (this.animaux == 0);
	}


	public void cliqueBloc(int x, int y) {
		if (this.blocDestructible(x, y)) {
			this.detruitBlocsVoisins(x,y);
			this.gravite();
		}
		while (this.peutSauverAnimal()) {
			this.sauveAnimaux();
			this.gravite();
		}
	}

	private void sauveAnimaux() {// sauve les animaux tout en bas, applique gravité
		for (int x = 0 ; x < this.xmax ; x++) {
			if (this.plateau[x][0] instanceof Animal) {
				this.plateau[x][0] = null;
				this.animaux -= 1;
			}
		}
	}

	private boolean peutSauverAnimal() { // si il y a un animal tout en bas du plateau
		for (int x = 0 ; x < this.xmax ; x++) {
			if (this.plateau[x][0] instanceof Animal) {
				return true;
			}
		}
		return false;
	}

//On a choisi que la taille minimale d'un groupe de blocs voisins de même couleur pour pouvoir le détruire soit 2. Nous avons aussi écrit une fonction pour si le minimum était de 3, sauvegardée dans un autre fichier.

	boolean blocDestructible(int x, int y) {
		int couleur = this.plateau[x][y].getType();

		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int yOffset = -1; yOffset <= 1; yOffset++) {
				if (Math.abs(xOffset) + Math.abs(yOffset) == 1) {
					try {
						if (this.plateau[x+xOffset][y+yOffset].getType() == couleur) {
							return true;
						}
					} catch (NullPointerException e) {continue;}
					catch (IndexOutOfBoundsException e) {continue;}
				}
			}
		 }
		return false;
	}

	private void detruitBlocsVoisins(int x, int y) {
		//System.out.println("lance pour : " + Integer.toString(x) + " " + Integer.toString(y));

		int couleur = this.plateau[x][y].getType();
		//System.out.println("couleur " + Integer.toString(couleur));

		this.plateau[x][y] = null;
		//System.out.println("mis à null");

		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int yOffset = -1; yOffset <= 1; yOffset++) {
				//System.out.println("\tcheck pour : " + Integer.toString(x+xOffset) + " " + Integer.toString(y+yOffset));
				if (Math.abs(xOffset) + Math.abs(yOffset) == 1) {
					try {
						if (this.plateau[x+xOffset][y+yOffset] instanceof Bloc && this.plateau[x+xOffset][y+yOffset].getType() == couleur) {
							this.detruitBlocsVoisins(x+xOffset, y+yOffset);
						}
					} catch (NullPointerException e) {continue;}
					catch (IndexOutOfBoundsException e) {continue;}
					//On aurait très bien pu utiliser des conditions pour éviter ces exceptions. C'est d'ailleurs la solution utilisée dans la version minimum == 3 de detruitBlocsVoisins.
				}
			}
		}
		//System.out.println("done checking for : " + Integer.toString(x) + " " + Integer.toString(y));
		return;
	}

	private void gravite() {
		//System.out.println("gravité...");
		for (int x = 0 ; x < this.xmax ; x++) { // colonne par colonne
			//System.out.println("permute en x " + Integer.toString(x));
			while (mauvaiseGraviteSurColonne(x)) {
				for (int y = 0 ; y < this.ymax -1 ; y++) {
					//System.out.println("permute en y " + Integer.toString(y));
					if (this.plateau[x][y] == null) {
						this.plateau[x][y] = this.plateau[x][y+1];
						this.plateau[x][y+1] = null;
					}
				}
			}
		}
	}

	private boolean mauvaiseGraviteSurColonne(int x) {
		//System.out.println("check gravité : " + Integer.toString(x));
		for (int y = 0 ; y < this.ymax -1 ; y++) {
			//System.out.println("check y : " + Integer.toString(y));
			if (this.plateau[x][y] == null && this.plateau[x][y + 1] != null) {
				return true;
			}
		}
		return false;
	}

	public void utiliseFusee(int x) {//throw plusDeFusees
		if (this.fusees > 0) {
			this.fusees -= 1;
			for (int y = 0 ; y < this.ymax ; y++) {
				if (this.plateau[x][y] instanceof Animal) {
					this.animaux -= 1;
				}
				this.plateau[x][y] = null;
			}
		}
	}

	//public void affiche() {System.out.println(this);}

	public String toString() {
		String resultat = "\n\tPET RESCUE";
		resultat = resultat + "\n\nfusées : " + Integer.toString(this.fusees);
		resultat = resultat + "\tanimaux : " + Integer.toString(this.animaux);
		resultat = resultat + "\n\n";

		int ymaxVisible = 10; //RAF à déterminer
		if (this.ymax < ymaxVisible) {
			ymaxVisible = this.ymax;
		}

		for (int y = ymaxVisible-1 ; y >=0 ; y--) {
			resultat = resultat + Integer.toString(y) + "\t";
			for (int x = 0 ; x < this.xmax ; x++) {
				if (this.plateau[x][y] == null) {
					resultat = resultat + "  ";
				}
				else {
					resultat = resultat + this.plateau[x][y].toString() + " ";
				}
			}
			resultat = resultat + "\n";
		}
		resultat = resultat + "\ny/x\t";
		for (int x = 0 ; x < this.xmax ; x++) {
			resultat = resultat + Integer.toString(x) + " ";
		}
		resultat = resultat + "\n";
		return resultat;
	}

	public Plateau clone() {
		Plateau clone = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la méthode super.clone()
			clone = (Plateau) super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons l'interface Cloneable.
			cnse.printStackTrace(System.err);
		}
		clone.xmax = this.xmax;
		clone.ymax = this.ymax;
		clone.fusees = this.fusees;
		clone.animaux = this.animaux;

		clone.plateau = new Piece[xmax][ymax];
		for (int x = 0 ; x < this.xmax ; x++) {
			for (int y = 0 ; y < this.ymax ; y++) {
				if (this.plateau[x][y] instanceof Animal) {
					clone.plateau[x][y] = ((Animal)(this.plateau[x][y])).clone();
				}
				else if (this.plateau[x][y] instanceof Bloc) {
					clone.plateau[x][y] = ((Bloc)(this.plateau[x][y])).clone();
				}
			}
		}
		return clone;
	}

	public static void main (String[] args) {
		Plateau testPlateau = new Plateau(5,5,3,1,4);
		//testPlateau.affiche();
		testPlateau.cliqueBloc(1, 1);
		//testPlateau.affiche();
	}
}
