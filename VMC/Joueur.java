import java.io.Serializable;
import java.util.LinkedList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;


public class Joueur implements Serializable { /*La classe joueur sert à sauvegarder la progression dans le jeu. C'est dans cette classe que l'on garderait la trace du score, si on avait eu le temps d'implémenter cette fonctionnalité.*/
	private static final long serialVersionUID = 1L;
	private String nom;
	private int niveau;
	
	public Joueur() {} /*Constructeur sans arguments, utilisé par deserialise(). On a séparé la création d'un nouveau joueur par l'utilisateur et ce constructeur ci.*/

	protected static Joueur nouveauJoueur(String nom, Vue vue) {
		Joueur joueur = new Joueur();
		joueur.nom = nom;
		joueur.niveau = 1;
		joueur.serialise();
		return joueur;
	}

	protected String getNom() {
		return this.nom;
	}

	protected int getNiveau() {
		return this.niveau;
	}

	protected void incrementeNiveau() {
		this.niveau += 1;
		this.serialise();
	}
	public String toString() {
		String resultat = Long.toString(serialVersionUID);
		resultat = resultat + "\nNom : " + this.getNom();
		resultat = resultat + "\nNiveau : " + this.getNiveau();
		return resultat;
	}

	public void serialise() {
		ObjectOutputStream objectOutputStream = null;

		try {
			final FileOutputStream fichierOut = new FileOutputStream(this.getNom() + "Joueur.ser");
			objectOutputStream = new ObjectOutputStream(fichierOut);
			objectOutputStream.writeObject(this);
			objectOutputStream.flush();
		} catch (final java.io.IOException exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (objectOutputStream != null) {
					objectOutputStream.close();
				}
			} catch (final IOException exception) {
				exception.printStackTrace();
			}
			//System.out.println("Serialisé !");
		}
	}

	public static Joueur deserialise(String nom) {
		ObjectInputStream ois = null;
		Joueur joueur = null;
		
		try {
			final FileInputStream fichierIn = new FileInputStream(nom + "Joueur.ser");
			ois = new ObjectInputStream(fichierIn);
			joueur = (Joueur) ois.readObject();
			/*System.out.println("Joueur : ");
			System.out.println("nom : " + joueur.getNom());
			System.out.println("niveau : " + joueur.getNiveau());
			System.out.println("Désérialisation réussie");*/
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		return joueur;
	}

	public static String[] listeJoueursSauvegardes() { /*Donne la liste des sauvegardes.*/
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
}
