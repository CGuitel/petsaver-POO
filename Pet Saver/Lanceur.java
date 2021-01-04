import java.io.File;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;

public class Lanceur {
	private Vue vue;

	public Lanceur() {}

	private void lance(Vue vue) {
		this.vue = vue;
		this.vue.menuInitialisation();
	}

/*La partie modèle passe de Lanceur à Partie. On a choisit de */
	public Partie choisitJoueur(String nom) {
		ArrayList<String> liste = new ArrayList<>(Arrays.asList(this.listeJoueursSauvegardes()));
		Joueur joueur;
		if (liste.contains(nom)) {
			joueur = Joueur.deserialise(nom);
		}
		else {
			joueur = new Joueur(nom, this.vue);
			this.vue.miseAJourJoueurs(this.listeJoueursSauvegardes()); // On recalcule la liste puisqu'on a rajouté un joueur.
		}
		Partie partie = new Partie(joueur, this.vue);
		return partie;
	}

	public void miseAJourJoueurs() {
		this.vue.miseAJourJoueurs(this.listeJoueursSauvegardes());
	}

	public void miseAJourRegles() {
		this.vue.miseAJourRegles(this.lireRegles());
	}

	private static String lireRegles() {
		String chemin = "./regles.txt";
		String texte = "";
		String ligne;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(chemin));
			while((ligne = reader.readLine()) != null) {
				texte += ligne+"\n";
			}
		} catch(Exception exception) {
			texte = "Une erreur s'est produite durant la lecture : "+exception.getMessage();
		}
		return texte;
	}

	private static String[] listeJoueursSauvegardes() { /*Donne la liste des sauvegardes.*/
		LinkedList<String> resultats = new LinkedList<String>();

		File directoryPath = new File("./");
		String[] contenu = directoryPath.list();

		for (String fichier: contenu) {
			if (fichier.endsWith("Joueur.ser")) {
				resultats.add(fichier.replace("Joueur.ser", ""));
			}
		}
		String[] resultatsTableau = new String[resultats.size()];
		for (int i = 0 ; i < resultats.size() ; i++) {
			resultatsTableau[i] = resultats.get(i);
		}
		return resultatsTableau;
	}

	public static void main(String[] args) {
/*Pour faire fonctionner le programme, il faut:
1 compiler avec "javac *.java,
2 lancer le programme avec "java Lanceur IG" ou "java Lanceur IT"*/
		Lanceur lanceur = new Lanceur();
		Controleur controleur = new Controleur(lanceur);
		if (args[0].equals("IG")) {
			lanceur.lance(new VueIG(controleur));
		} else if (args[0].equals("IT")) {
			lanceur.lance(new VueIT(controleur));
		}
	}
}
