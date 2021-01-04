public abstract class Piece implements Cloneable{
	public abstract int getType(); /*On avait pensé à mettre la définition de getType ici, mais cela retournait toujours 0.*/

	public abstract String toString();

	protected abstract Piece clone(); /*Cette fonction est utilisée par la fonction clone du plateau.*/
}
