import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CardLayout implements ActionListener {
	JPanel cartes = new JPanel();
	Container pane = new Container();
	CarteInit carteGauche = new CarteInit("imageGauche.png");
	Carte carteDroite = new Carte ("debut.png");
	
	public CardLayout() {
		cartes.setLayout(new FlowLayout());
		cartes.add(carteGauche);
		cartes.add(carteDroite);
		pane.add(cartes, BorderLayout.CENTER);
	}
	
	public CardLayout(Carte carteGauche) {
		cartes.setLayout(new FlowLayout());
		cartes.add(carteGauche);
		pane.add(cartes, BorderLayout.CENTER);
	}
	public CardLayout(Carte carteGauche, Carte carteDroite) {
		cartes.setLayout(new FlowLayout());
		cartes.add(carteGauche);
		cartes.add(carteDroite);
		pane.add(cartes, BorderLayout.CENTER);
	}
	public void ajouterComponentAPane (Container pane) {
        pane.add(cartes, BorderLayout.CENTER);
	}
	public void changeCarteDroite (Carte carteDroite) {
		cartes.setLayout(new FlowLayout());
		cartes.add(carteGauche);
		cartes.add(carteDroite);
		pane.add(cartes, BorderLayout.CENTER);
	}
	public void changeCarteGauche (Carte carteGauche) {
		cartes.setLayout(new FlowLayout());
		cartes.add(carteGauche);
		cartes.add(carteDroite);
		pane.add(cartes, BorderLayout.CENTER);
	}
	void miseAJour () {
		//pour changer la carte 2 avec les mise à jour du plateau
		
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//if(source == this.carte1.Regles.getActionListener()){
		//??
		}
}
