import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CarteAvecRegles extends Carte {

	CarteAvecRegles(String chemin) {
		super(chemin);
		JTextArea zoneText = new JTextArea("Les règles du jeu ne sont pas très compliquées");
		JScrollPane texteAsc = new JScrollPane(zoneText);
		texteAsc.setPreferredSize(new Dimension (350, 400));
		super.imageFon.add(texteAsc);
		zoneText.setEditable(false);		
	}
}