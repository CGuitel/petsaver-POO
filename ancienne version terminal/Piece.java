public abstract class Piece {
	public abstract String toString();
	public abstract int getType();
	// On avait pensé à déclarer clone() ici, mais on ne peut alors plus accéder à super.clone() dans Animal et Bloc
}
