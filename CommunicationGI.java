import javax.swing.*;
import java.awt.*;

public class CommunicationGI {
	EnvironnementGI environnement;

	public CommunicationGI(EnvironnementGI environnement) {
		this.environnement = environnement;
	}

	public void regles(JPanel contenuDroite) {
		JPanel regles = new JPanel("Règles...");
		this.environnement.setContenuDroite(regles);
		//RAF créer une carte regles, les ajouter depuis un ficher, ajouter la carte à EnvGI
	}
}
