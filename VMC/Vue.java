import javax.swing.JFrame;

public abstract class Vue { /*Les classes Vue ont deux fonctions principales, menuInitialisation et menuJouer, qui affichent le menu en question.*/
	protected Controleur controleur;

/*Les méthodes pour l'initialisation : */
	abstract public void menuInitialisation();
	abstract public void miseAJourJoueurs(String[] joueurs);
	abstract public void miseAJourRegles(String texte);

/*Les méthodes pour la partie : */
	abstract public void menuJouer(Partie partie);
	abstract public void miseAJourPartie(Partie partie);
	abstract public void bravo();
}
