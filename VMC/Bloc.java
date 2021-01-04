public class Bloc extends Piece {
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

	protected Bloc clone() {
		return new Bloc(this.type);
	}
}
