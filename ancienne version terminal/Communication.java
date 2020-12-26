import java.util.Scanner;
import java.util.InputMismatchException;

public class Communication {
	private Scanner scanner;

	public Communication() {
		this.scanner = new Scanner(System.in);
	}

	public void fermeScanner() {
		this.scanner.close();
	}

//Les fonctions auxiliaires pour poser une question au joueur et recevoir sa réponse :
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
		System.out.print("Attention, il faut répondre avec 'oui' ou 'non'.");
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

//Les fonctions pour poser des questions spécifiques :
	public boolean veutJouer() {
		boolean reponse = demandeBoolean("Voulez vous jouer ?");
		System.out.println();
		return reponse;
	}

	public String demandeNom() {
		String reponse = demandeString("Quel est votre nom?");
		System.out.println();
		return reponse;
	}

	//public int demandeNiveau() {
	//	return demandeInt("Sur quel niveau vous souhaiterez jouer?");
	//}

	public String demandeJoueur(String[] options) {
		System.out.print("Voici les joueurs existants : ");
		for (String nom: options) {
			System.out.print(nom);
			System.out.print(" ");
		}
		System.out.println();
		String reponse = demandeStringCheck("Quel joueur voulez vous choisir ?", options);
		System.out.println();
		return reponse;
	}

	public int demandeActionJouer() {
		String question = "Que voulez vous faire ? Écrivez le numéro de l'action choisie :\n1 cliquer sur une case\n2 utiliser une action spéciale\n3 annuler la dernière action (attention, c'est irréversible !)\n4 quitter la partie (elle ne sera pas sauvegardée)";
		int reponse = demandeIntCheck(question, 1, 4);
		System.out.println();
		return reponse;
	}

	public int demandeCoordonneeX(int xmax) {
		int reponse = demandeIntCheck("Coordonnée x ?", 0, xmax);
		System.out.println();
		return reponse;
	}

	public int demandeCoordonneeY(int ymax) {
		int reponse = demandeIntCheck("Coordonnée y ?", 0, ymax);
		System.out.println();
		return reponse;
	}

	public void affiche(Plateau plateau) {
		System.out.println(plateau);
		System.out.println();
	}

	public void bravo() {
		System.out.println("Bravo, vous avez gagné !");
		System.out.println();
	}

	public void affiche(Partie partie) {
		System.out.println(partie.getPlateauCourant());
		System.out.println(partie);
		System.out.println();
	}

	public int demandeActionMenu() {
		String question = "Que voulez vous faire ? Écrivez le numéro de l'action choisie :\n1 lire les règles\n2 voir une démonstration\n3 créer un nouveau joueur\n4 sélectionner un joueur existant et jouer\n5 quitter le jeu";
		int reponse = demandeIntCheck(question, 1, 5);
		System.out.println();
		return reponse;
	}

	public void regles() {
		//RAF lire les règles depuis un fichier
		System.out.println("Règles.");
		System.out.println();
	}	
}
