import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PanelImage extends JPanel {
	private BufferedImage image;

	public PanelImage(String chemin){
		try {
			this.image = ImageIO.read(new File(chemin));
		} catch (IOException exception) {
			System.err.println("IO error: "+ exception.getMessage());
			exception.printStackTrace();
		}
		this.setBackground(new Color(246,236,213,255));
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.image,0,0,this);
	}
}
