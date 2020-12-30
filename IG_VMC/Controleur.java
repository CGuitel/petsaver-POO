public interface Controleur {
	abstract void regles(); //RAF à mettre dans Vue
	abstract void demo();
	abstract void quitteJeu();
	abstract void creeNouveauJoueur(String nom);
	abstract void choisitJoueur(String nom);
}
