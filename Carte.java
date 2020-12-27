import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Carte extends JPanel {
	BufferedImage monImage;
	JLabel imageFon;
	
	Carte (String chemin) {
		try {
			monImage = ImageIO.read(new File(chemin));
			imageFon = new JLabel(new ImageIcon(monImage));
			imageFon.setLayout(new FlowLayout());
			this.add(imageFon);
			}
		catch (IOException e) {
			System.err.println("IO error: "+ e.getMessage());
			e.printStackTrace();
		}
	}
}
