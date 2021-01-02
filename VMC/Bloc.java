public class Bloc extends Piece implements Cloneable {
	private int type;

	public Bloc(int couleur) {
		this.type = couleur;
	}

	public int getType() {
		return this.type;
	}

	public String toString() {
		char caractere = '0';
		int ascii = ((int) caractere) + (this.type % 10);
		caractere = (char) ascii;
		return "" + caractere;
	}

	public Bloc clone() {
		Bloc clone = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la méthode super.clone()
			clone = (Bloc) super.clone();
		} catch(CloneNotSupportedException exception) {
			// Ne devrait jamais arriver car nous implémentons l'interface Cloneable.
			exception.printStackTrace(System.err);
		}
		clone.type = this.type;
		return clone;
	}
}
