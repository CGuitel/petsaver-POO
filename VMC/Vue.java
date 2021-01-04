import javax.swing.JFrame;

public abstract class Vue { /*Les classes Vue ont deux fonctions principales, menuInitialisation et menuJouer.*/
	protected Controleur controleur;
	protected Partie partie;

	abstract protected void bravo();
	abstract protected void miseAJourJoueurs();
	abstract protected void miseAJourPlateau();
	abstract protected void regles();

	abstract protected void menuJouer(Partie partie);
	abstract protected void menuInitialisation();
}
