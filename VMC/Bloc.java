public class Bloc extends Piece implements Cloneable {
	private int couleur;

	public Bloc(int couleur) {
		this.couleur = couleur;
	}

	public int getType() {
		return this.couleur;
	}

	public String toString() { //RAF utiliser ascii pour automatiser ça ?
		if (couleur == 0) {return "0";}
		else if (couleur == 1) {return "1";}
		else if (couleur == 2) {return "2";}
		else if (couleur == 3) {return "3";}
		else {return "?";}
	}
	public Bloc clone() {
		Bloc clone = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la méthode super.clone()
			clone = (Bloc) super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons l'interface Cloneable.
			cnse.printStackTrace(System.err);
		}
		clone.couleur = this.couleur;
		return clone;
	}
}
