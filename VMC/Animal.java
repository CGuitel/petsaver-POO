public class Animal extends Piece implements Cloneable {
	private int type;

	public Animal(int espece) {
		this.type = espece % 26;
	}

	public int getType() {
		return this.type;
	}

	public String toString() {
		char caractere = 'A';
		int ascii = ((int) caractere) + (this.type % 26);
		caractere = (char) ascii;
		return "" + caractere;
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
		clone.type = this.type;
		return clone;
	}
}
