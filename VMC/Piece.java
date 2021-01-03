public abstract class Piece implements Cloneable{
	/*On avait pensé à déclarer clone() ici, mais on ne peut alors plus accéder à super.clone() dans Animal et Bloc. Il existe probablement une façon de faire ça proprement, probablement en récupérant le type (animal ou bloc) dans une variable type et en mettant la fonction clone() dans Piece ?*/
	protected int type;

	public abstract int getType(); /*On avait aussi pensé à mettre la définition de getType ici, mais cela retournait toujours 0.*/

	public abstract String toString();

	protected abstract Piece clone();
}
