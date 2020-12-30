import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CarteAvecRegles extends Carte {

	CarteAvecRegles(String chemin) {
		super(chemin);
		JTextArea zoneText = new JTextArea(20, 30);
		JScrollPane texteAsc = new JScrollPane(zoneText);
		texteAsc.setVerticalScrollBarPolicy(texteAsc.VERTICAL_SCROLLBAR_ALWAYS);
		super.imageFon.add(texteAsc);
		zoneText.setEditable(true);
		zoneText.setVisible(true);
	}
}