public class Animal extends Piece {
	private int espece;

	public Animal(int espece) {
		this.espece = espece % 6; /*Techniquement, on pourrait aller jusqu'à 26, mais on est limité par le nombre d'éléments graphiques préparés.*/
	}

	public int getType() {
		return this.espece;
	}

	public String toString() {
		char caractere = 'A';
		int ascii = ((int) caractere) + (this.espece % 6);
		caractere = (char) ascii;
		return "" + caractere;
	}

	protected Animal clone() {
		return new Animal(this.espece);
	}
}
