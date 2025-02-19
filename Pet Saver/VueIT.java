import java.util.Scanner;
import java.util.InputMismatchException;

import java.io.BufferedReader;
import java.io.FileReader;

public class VueIT extends Vue { /*VueIT utilise un Scanner ainsi que des fonctions auxiliaires (demandeIntCheck et autres) afin de demander des informations à l’utilisateur.*/
	private Scanner scanner;

	public VueIT(Controleur controleur) {
		super();
		this.controleur = controleur;
		this.scanner = new Scanner(System.in);
	}

/*Fonctions auxiliaires pour utiliser le scanner pour poser une question au joueur et recevoir sa réponse :*/
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


/*Fonctions héritées de Vue :*/
/*menuInitialisation*/
	public void menuInitialisation() {
		while (true) {
			String question = "Que voulez vous faire ? Écrivez le numéro de l'action choisie :\n1 lire les règles\n2 voir une démonstration\n3 choisir un joueur\n4 quitter le jeu";
			int action = demandeIntCheck(question, 1, 4);
			System.out.println();
			//Que voulez vous faire ? Écrivez le numéro de l'action choisie :

			if (action == 1) {//1 lire les règles
				this.controleur.regles();
			}
			else if (action == 2) {//2 voir une démonstration
				this.controleur.demo();
			}
			else if (action == 3) {//3 choisir un joueur
				this.controleur.choisirJoueur();
			}
			else if (action == 4) {//4 quitter le jeu
				this.scanner.close();
				this.controleur.quitteJeu();
			}
		}
	}


/*menuJouer*/
	public void menuJouer(Partie partie) {
		this.miseAJourPartie(partie); //On affiche le plateau à chaque fois.
		int xmax = partie.getPlateauCourant().getXMax();
		int ymax = partie.getPlateauCourant().getYMax();

		boolean continuer = true;
		while (continuer) {
			String question = "Que voulez vous faire ? Écrivez le numéro de l'action choisie :\n1 cliquer sur une case\n2 utiliser une fusée\n3 annuler la dernière action (attention, c'est irréversible !)\n4 quitter la partie (elle ne sera pas sauvegardée)";
			int action = demandeIntCheck(question, 1, 4);
			System.out.println();

			if (action == 1) {//1 cliquer sur une case
				int x = this.demandeCoordonneeX(xmax);
				int y = this.demandeCoordonneeY(ymax);
				this.controleur.cliqueBloc(x, y);
			}

			else if (action == 2) {//2 utiliser une fusée
				int x = this.demandeCoordonneeX(xmax);
				this.controleur.utiliseFusee(x);
			}
			else if (action == 3) {//3 annuler la dernière action (attention, c'est irréversible !)
				this.controleur.annuleAction();
			}
			else if (action == 4) {//4 quitter la partie (elle ne sera pas sauvegardée)";
				this.controleur.quittePartie();
				//continuer = false;
			}
		}
	}

/*Au contraire de la vue IG, la vue IT n'a qu'un seul composant, et tout est donc traité dans les fonctions de ce fichier au lieu d'être relayé vers d'autres éléments.*/
	public void miseAJourJoueurs(String[] joueurs) {
		System.out.print("Voici les joueurs existants : ");
		for (String option: joueurs) {
			System.out.print(option);
			System.out.print(" ");
		}
		System.out.println();
		String nom = demandeString("Choisissez ou créez un joueur : ");
		System.out.println();
		this.controleur.aChoisitJoueur(nom);
	}

	public void miseAJourPartie(Partie partie) {
		System.out.println(partie);
		System.out.println();
	}

	public void miseAJourRegles(String texte) {
		System.out.println(texte);
		System.out.println();
	}

	public void bravo() {
		System.out.println("Bravo, vous avez gagné ! Votre nouveau niveau a été sauvegardé. Si vous voulez continuer sur la même sauvegarde, resélectionnez le même joueur.");
		System.out.println();
	}
}
