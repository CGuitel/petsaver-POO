import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Joueur implements Serializable { /*La classe joueur sert à sauvegarder la progression dans le jeu. C'est dans cette classe que l'on garderait la trace du score, si on avait eu le temps d'implémenter cette fonctionnalité.*/
	private static final long serialVersionUID = 1L;
	private String nom;
	private int niveau;
	
	public Joueur() {} /*Constructeur sans arguments, utilisé par deserialise().*/

	public Joueur(String nom, Vue vue) {
		this.nom = nom;
		this.niveau = 1;
		this.serialise();
	}

	public String getNom() {
		return this.nom;
	}

	public int getNiveau() {
		return this.niveau;
	}

	public void incrementeNiveau() {
		this.niveau += 1;
		this.serialise();
	}

	public String toString() {
		String resultat = this.getNom()+", niveau : " + this.getNiveau();
		return resultat;
	}

	private void serialise() {
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
}
