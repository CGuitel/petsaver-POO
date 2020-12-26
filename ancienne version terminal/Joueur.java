import java.io.Serializable;
import java.util.LinkedList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Joueur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;
	private int niveau;
	//private LinkedList<Action> inventaire;
	
	public Joueur() { //Constructeur sans arguments, utilisé par deserialise(). On a donc séparé la création d'un nouveau joueur par l'utilisateur et ce constructeur-ci.
	}

	public static Joueur nouveauJoueur() {
		Joueur joueur = new Joueur();
		joueur.setNom(Environnement.communication.demandeNom());
		return joueur;
	}

	public String getNom() {
		return this.nom;
	}

	public int getNiveau() {
		return this.niveau;
	}

	private void setNom(String nom) {
		this.nom = nom;
	}

	private void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public void incrementeNiveau() {
		this.niveau += 1;
		//sauvegarde le joueur ?
	}
	public String toString() {
		String resultat = Long.toString(serialVersionUID);
		resultat = resultat + "\nNom : " + this.getNom();
		resultat = resultat + "\nNiveau : " + this.getNiveau();
		return resultat;
	}

	public void serialise() { //RAF tester si le joueur existe déjà, et où il est sauvegardé
		ObjectOutputStream oos = null;

		try {
			final FileOutputStream fichierOut = new FileOutputStream(this.getNom() + "Joueur.ser");
			oos = new ObjectOutputStream(fichierOut);
			oos.writeObject(this);
			oos.flush();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		//} catch (final ClassNotFoundException e) {
		//	e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
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
			//System.out.println("Joueur : ");
			//System.out.println("nom : " + joueur.getNom());
			//System.out.println("niveau : " + joueur.getNiveau());
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
			//System.out.println("Déserialisé !");
		}
		return joueur;
	}
}
