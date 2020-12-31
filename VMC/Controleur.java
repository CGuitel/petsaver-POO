import java.util.*;

public abstract class Controleur {
/*Classes abstraites et pas interfaces, parce que c'est la définition du fonctionem&ent interne de ces classes, pas de la façon dont elles sont utilisées par d'autres objets. On veut limiter l'accès autant que possible.
Dans une première version VMC, après la version système/communication, on avait une classe hybride contrôleur/vue pour l'affichage en terminal. Poser les définitions des fonctions dans les interfaces Controleur et Vue posait alors un problème au niveau des arguments, car cette classe V/C était utilisée pour obtenir ET traiter une information, et les fontions ne prenaient donc pas d'arguments. Dans la version graphique, le controleur ne fait que traiter une information que la vue lui transmet, et il fallait donc passer ces informations d'une façon ou d'une autre. Avec l'élaboration du schéma VMC en IG sont apparues les vraies places des fonctions de cette classe hybride. La séparer en deux est assez artificiel, et entraine beaucoup de va-et-viens de fonctions qui ne font qu'en appeler d'autres d'une classe à l'autre, mais cela nous permet (de 1) d'uniformiser avec nos classes abstraites et (de 2) de reste fidèles au schéma VMC.*/
	protected Vue vue;
	protected Partie partie;

	protected abstract void demo();
	protected void quitteJeu() {
		System.exit(0);
	}
	protected abstract void choisitJoueur(String nom);

	protected abstract void utiliseFusee(int x);
	protected abstract void annuleAction();
	protected abstract void cliqueBloc(int x, int y);
	protected abstract void quittePartie();
}
