import java.util.Scanner;
import java.util.InputMismatchException;

import java.io.BufferedReader;
import java.io.FileReader;

public class VueIT extends Vue {
	protected ControleurIT controleur;
	protected Partie partie;
	private Scanner scanner;

	public VueIT(ControleurIT controleurIT) {
		super();
		this.controleur = controleurIT;
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
	protected void menuJouer(Partie partie) {
		this.partie = partie;
		this.miseAJourPlateau();
		int xmax = this.partie.getPlateauCourant().getXMax();
		int ymax = this.partie.getPlateauCourant().getYMax();

		while (this.partie.getPartieEnCours()) {
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
			}
		}
	}

	protected void menuInitialisation() {//pourrait être transformé en une méthode de l'interface Vue...?
		System.out.println("menuInitialisation");
		System.out.println("vueIT.controleur");
		System.out.println(this.controleur);
		while (true) {
			String question = "Que voulez vous faire ? Écrivez le numéro de l'action choisie :\n1 lire les règles\n2 voir une démonstration\n3 choisir un joueur\n4 quitter le jeu";
			int action = demandeIntCheck(question, 1, 4);
			System.out.println();
			//Que voulez vous faire ? Écrivez le numéro de l'action choisie :

			if (action == 1) {//1 lire les règles
				this.regles();
			}
			else if (action == 2) {//2 voir une démonstration
				this.controleur.demo();
			}
			else if (action == 3) {//3 choisir un joueur
				String[] options = Joueur.listeJoueursSauvegardes();
				System.out.print("Voici les joueurs existants : ");
				for (String option: options) {
					System.out.print(option);
					System.out.print(" ");
				}
				System.out.println();
				String nom = demandeString("Tapez le nom d'un joueur existant ou d'un nouveau joueur : ");
				System.out.println();
				this.controleur.choisitJoueur(nom);
			}
			else if (action == 4) {//4 quitter le jeu
				this.scanner.close();
				this.controleur.quitteJeu();
			}
		}
	}

	protected void regles() {
		String chemin = "./regles.txt";
		String texte = "";
		String ligne;
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(chemin));
		    while((ligne = reader.readLine()) != null)
			texte += ligne+"\n";
		}
		catch(Exception exception) {
		    texte = "Une erreur s'est produite durant la lecture : "+exception.getMessage();
		}
		System.out.println(texte);
		System.out.println();
	}

	protected void miseAJourJoueurs() {}

	protected void miseAJourPlateau() {
		System.out.println(this.partie.getPlateauCourant());
		System.out.println(this.partie);
		System.out.println();
	}

	protected void bravo() {
		System.out.println("Bravo, vous avez gagné !");
		System.out.println();
	}
}
