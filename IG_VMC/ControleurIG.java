public class ControleurIG implements Controleur {
	private VueIG vueIG;

	public ControleurIG() {
		this.vueIG = new VueIG(this);
	}

	public void regles() {}

	public void demo() {}

	public void quitteJeu() {
		System.exit(0);
	}

	public void creeNouveauJoueur(String nom) {
		Joueur.nouveauJoueur(nom);
		//lancer la partie...
	}

	public void choisitJoueur(String nom) {}

	public static void main(String[] args) {
		new ControleurIG();
	}
}
