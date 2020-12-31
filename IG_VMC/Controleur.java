public interface Controleur {
/*Poser les définitions des fonctions dans les interfaces Controleur et Vue posait un problème au niveau des arguments. Dans la version terminale, la classe V/C est utilisée pour obtenir ET traiter une information, et les fontions n'ont donc pas d'arguments. Dans la version graphique, le controleur ne fait que traiter une information que la vue lui transmet, et il faut donc passer ces informations d'une façon ou d'une autre. On aurait pû créer des fonctions avec arguments, et passer null/ne pas les utiliser dans la version terminale, mais cela nous a semblé contraire à l'idée d'une définition de méthode, qui est sensée communiquer une information sur ce que la fonction fait. On aurait pû imaginer plus séparer la vue et le controleur dans la version terminale, mais cette distinction nous a semblée trop complexe et artificielle.*/
	//abstract void regles(); //RAF à mettre dans Vue
	//abstract void demo();
	//abstract void quitteJeu();
	//abstract void creeNouveauJoueur(String nom);
	//abstract void choisitJoueur(String nom);
}
