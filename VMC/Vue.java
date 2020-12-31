import javax.swing.JFrame;

public abstract class Vue {
	protected Controleur controleur;
	protected Partie partie;

	public Vue() {
		//System.out.println("Vue()");
		//System.out.println(this);
	}

	abstract protected void bravo();
	abstract protected void miseAJourJoueurs();
	abstract protected void miseAJourPlateau();
	abstract protected void regles();

	abstract protected void menuJouer(Partie partie); /*{
		System.out.println("attention ! menuJouer de l'abstract class !");
	}*/
	abstract protected void menuInitialisation();
}
