public class ControleurIG implements Controleur {
	private VueIG vueIG;

	public ControleurIG() {
		this.vueIG = new VueIG(this);
	}

	public void regles() {}
	public void demo() {}
	public void quitteJeu() {}
	public void creeNouveauJoueur() {}
	public void choisitJoueur() {}

	public static void main(String[] args) {
		new ControleurIG();
	}
}
