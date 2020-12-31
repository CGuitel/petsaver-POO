import java.util.Scanner;
import java.util.InputMismatchException;

public class VueControleurTerminal implements Controleur, Vue {
	private Scanner scanner;
	private Partie partie;

	public VueControleurTerminal() {
		this.scanner = new Scanner(System.in);
	}

//Les fonctions auxiliaires génériques pour utiliser le scanner pour poser une question au joueur et recevoir sa réponse :
	private String demandeString(String question) {
		System.out.println(question);
		String reponse = "";
		try {
			reponse = this.scanner.nextLine();
		} catch (InputMismatchException exception) {
			System.out.println("Attention, il nous faut un mot !");
			reponse = demandeString(question);
		}
		return reponse;
	}

	private int demandeInt(String question) {
		System.out.println(question);
		int reponse = 0;
		try {
			reponse = Integer.parseInt(this.scanner.nextLine());
		} catch (NumberFormatException exception) {
			System.out.println("Attention, il nous faut un chiffre !");
			reponse = demandeInt(question);
		}
		return reponse;
	}

	private String demandeStringCheck(String question, String[] reponsesAcceptees) {
		String reponse = demandeString(question);
		for (String reponseAcceptee: reponsesAcceptees) {
			if (reponse.equals(reponseAcceptee)) {
				return reponse;
			}
		}
		System.out.print("Attention, les réponses acceptées sont : ");
		for (String option: reponsesAcceptees) {
			System.out.print(option);
			System.out.print(" ");
		}
		System.out.println();
		return demandeStringCheck(question, reponsesAcceptees);
	}

	private boolean demandeBoolean(String question) {
		String reponse = demandeString(question);
		if (reponse == "oui") {
			return true;
		}
		else if (reponse == "non") {
			return false;
		}
		System.out.print("Attention, il faut répondre par 'oui' ou 'non'.");
		return demandeBoolean(question);
	}

	private int demandeIntCheck(String question, int min, int max) {
		int reponse = demandeInt(question);
		if (reponse <= max && reponse >= min) {
			return reponse;
		}
		System.out.println("Attention, il faut un chiffre entre "+Integer.toString(min)+" et "+Integer.toString(max)+" !");
		return demandeIntCheck(question, min, max);
	}

//Les fonctions du menu d'initialisation :
	public void creeNouveauJoueur(String nom) { //public ou quoi
		nom = demandeString("Quel est votre nom?");
		Joueur joueur = Joueur.nouveauJoueur(reponse);
		this.partie = new Partie(joueur, this);
		this.menuPartie();
		System.out.println();
	}

	public void choisitJoueur(String nom) { //public ou quoi
		String[] options = Joueur.listeJoueursSauvegardes();
		System.out.print("Voici les joueurs existants : ");
		for (String option: options) {
			System.out.print(option);
			System.out.print(" ");
		}
		System.out.println();
		nom = demandeString("Tapez le nom d'un joueur existant ou d'un nouveau joueur : ");
		System.out.println();
		Joueur joueur = Joueur.deserialise(nom);
		this.partie = new Partie(joueur, this);
		this.menuPartie();
	}

	public void regles() { //public ou quoi
		//RAF lire les règles depuis un fichier
		System.out.println("Règles.");
		System.out.println();
	}

	public void demo() {} //RAF  //public ou quoi

	public void quitteJeu() { //public ou quoi
		this.scanner.close();
		System.exit(0);
	}

	private void menuInitialisation() { //pourrait être transformé en une méthode de l'interface Vue...?
		while (true) {
			String question = "Que voulez vous faire ? Écrivez le numéro de l'action choisie :\n1 lire les règles\n2 voir une démonstration\n3 choisir un joueur\n4 quitter le jeu";
			int action = demandeIntCheck(question, 1, 4);
			System.out.println();
			//Que voulez vous faire ? Écrivez le numéro de l'action choisie :

			if (action == 1) {//1 lire les règles
				this.regles();
			}
			else if (action == 2) {//2 voir une démonstration
				this.demo();
			}
			else if (action == 3) {//3 choisir un joueur
				this.choisitJoueur();
			}
			else if (action == 4) {//4 quitter le jeu
				this.quitteJeu();
			}
		}
	}

//Les fonctions du menu de la partie :
//Les fonctions dans le menu déroulant sont dans la classe Partie. Cette classe a donc un rôle un peu à cheval entre modèle et controleur, puisqu'elle répercute les effets du choix de l'utilisateur entre le controleur et le reste du modèle (le plateau). Cela permet de ne pas avoir à passer par getPlateauCourant pour chaque changement. //RAF je sais pas si c'est la meilleure idée ? à voir

	private void menuPartie() { //pourrait être transformé en une méthode de l'interface Vue...?
		this.miseAJour();
		int xmax = this.partie.getPlateauCourant().getXMax();
		int ymax = this.partie.getPlateauCourant().getYMax();

		while (this.partie.getPartieEnCours()) {
			String question = "Que voulez vous faire ? Écrivez le numéro de l'action choisie :\n1 cliquer sur une case\n2 utiliser une fusée\n3 annuler la dernière action (attention, c'est irréversible !)\n4 quitter la partie (elle ne sera pas sauvegardée)";
			int action = demandeIntCheck(question, 1, 4);

			if (action == 1) {//1 cliquer sur une case
				int x = this.demandeCoordonneeX(xmax);
				int y = this.demandeCoordonneeY(ymax);
				this.partie.cliqueBloc(x, y);
			}

			else if (action == 2) {//2 utiliser une fusée
				int x = this.demandeCoordonneeX(xmax);
				this.partie.utiliseFusee(x);
			}
			else if (action == 3) {//3 annuler la dernière action (attention, c'est irréversible !)
				this.partie.annuleAction();
			}
			else if (action == 4) {//4 quitter la partie (elle ne sera pas sauvegardée)";
				this.partie.setPartieEnCours(false);
			}
			this.partie.checkSiPartieFinie();
		}
		if (this.partie.checkSiPartieGagnee()) {
			this.bravo();
			this.partie.getJoueur().incrementeNiveau(); //incrementeNiveau sauvegarde automatiquement
		}
	}

	private int demandeCoordonneeX(int xmax) {
		int reponse = demandeIntCheck("Coordonnée x ?", 0, xmax);
		System.out.println();
		return reponse;
	}

	private int demandeCoordonneeY(int ymax) {
		int reponse = demandeIntCheck("Coordonnée y ?", 0, ymax);
		System.out.println();
		return reponse;
	}

	public void miseAJour() { //public ou quoi
		System.out.println(this.partie.getPlateauCourant());
		System.out.println(this.partie);
		System.out.println();
	}

	public void bravo() {  //public ou quoi
		System.out.println("Bravo, vous avez gagné !");
		System.out.println();
	}

	public static void main(String[] args) {
		VueControleurTerminal VCT = new VueControleurTerminal();
		VCT.menuInitialisation();
	}
}
