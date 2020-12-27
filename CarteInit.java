import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class CarteInit extends Carte {
	JButton Regles = new JButton("Règles");
	JButton Demo = new JButton("Démo");
	JButton NouveauJoueur = new JButton("Nouveau joueur");
	JButton ChoisirJoueur = new JButton("Choisir joueur");
	JButton Quitter = new JButton("Quitter le jeu?");
	JLabel imageFon = super.imageFon;
	CardLayout cl = new CardLayout ();
	
	CarteInit(String chemin) {
		super(chemin);
		Regles.setBackground(Color.RED);
		Regles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CarteAvecRegles carteRegles = new CarteAvecRegles("debut.png");
				cl.changeCarteDroite(carteRegles);
				
		}
	});
	Demo.setBackground(Color.GREEN);
	//Demo.addActionListener(this);
	NouveauJoueur.setBackground(Color.YELLOW);
	NouveauJoueur.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Carte carteNouveauJoueur = new Carte("debut.png");
			JTextArea zoneText = new JTextArea("Nouveau joueur");
			carteNouveauJoueur.imageFon.add(zoneText);
			zoneText.setEditable(true);
			CardLayout carteNJ = new CardLayout(c, carteNouveauJoueur);
	}
});
	ChoisirJoueur.setBackground(Color.CYAN);
	//ChoisirJoueur.addActionListener(this);
	Quitter.setBackground(Color.WHITE);
	//Quitter.addActionListener(this);
	
	Font font = new Font("Freemono", Font.CENTER_BASELINE, 15);
	Regles.setFont(font);
	Demo.setFont(font);
	NouveauJoueur.setFont(font);
	ChoisirJoueur.setFont(font);
	Quitter.setFont(font);
	imageFon.add(NouveauJoueur, BorderLayout.EAST);
	imageFon.add(ChoisirJoueur, BorderLayout.LINE_END);			  
	imageFon.add(Regles, BorderLayout.LINE_END);
	imageFon.add(Demo, BorderLayout.LINE_END);
	imageFon.add(Quitter, BorderLayout.LINE_END);
	this.add(imageFon);
}
	void miseAjour(CardLayout cl) {
		this.cl = cl;
	}
}
