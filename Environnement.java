import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Environnement {
//On a choisi de mettre les attributs d'Environnement en static, parcequ'il est nécéssaire de pouvoir accéder au seul et unique scanner de communication dans toutes les classes. Comme ça, il n'est pas nécéssaire de passer un même objet Environnement à tous les constructeurs, on peut simplement faire appel à Environnement.communication.

	public static final Communication communication = new Communication();
	private static boolean continuerAJouer;

	//public Environnement() {
	//}

	public static void actionMenu() {
		int action = Environnement.communication.demandeActionMenu();
		//Que voulez vous faire ? Écrivez le numéro de l'action choisie :

		if (action == 1) {//1 lire les règles
			Environnement.communication.regles();
		}
		else if (action == 2) {//2 voir une démonstration
			//RAF démo
		}
		else if (action == 3) {//3 créer un nouveau joueur
			Joueur nouveauJoueur = Joueur.nouveauJoueur();
			nouveauJoueur.serialise();
		}
		else if (action == 4) {//4 sélectionner un joueur existant et jouer
			String[] options = Environnement.listeJoueursSauvegardes();
			String nomJoueur = Environnement.communication.demandeJoueur(options);
			Joueur joueur = Joueur.deserialise(nomJoueur);
			Partie partie = new Partie(joueur);
			partie.joue();
		}
		else if (action == 5) {//5 quitter le jeu
			Environnement.continuerAJouer = false;
		}
	}

	private static void quitteJeu() {
		Environnement.communication.fermeScanner();
		System.exit(0);
	}

	private static String[] listeJoueursSauvegardes() {
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
		Environnement.continuerAJouer = true;
		while (Environnement.continuerAJouer == true) {
			Environnement.actionMenu();
		}
		quitteJeu();
	}
}
