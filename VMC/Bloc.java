public class Bloc extends Piece {
	private int couleur;

	public Bloc(int couleur) {
		this.couleur = couleur;
	}

	public int getType() {
		return this.couleur;
	}

	public String toString() {
		char caractere = '0';
		int ascii = ((int) caractere) + (this.couleur % 10);
		caractere = (char) ascii;
		return "" + caractere;
	}

	protected Bloc clone() {
		return new Bloc(this.couleur);
	}
}
