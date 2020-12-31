public class Animal extends Piece implements Cloneable {
	private int espece;

	public Animal(int espece) {
		this.espece = espece % 3;
	}

	public int getType() {
		return this.espece;
	}

	public String toString() { //RAF ascii...
		if (espece == 1) {return "A";}
		else if (espece == 2) {return "B";}
		else if (espece == 3) {return "C";}
		else {return "D";}
	}
	public Animal clone() {
		Animal clone = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la méthode super.clone()
			clone = (Animal) super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons l'interface Cloneable.
			cnse.printStackTrace(System.err);
		}
		clone.espece = this.espece;
		return clone;
	}
}
