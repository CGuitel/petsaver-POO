import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnimalPicture extends JPanel{
	JPanel animal = new JPanel();
	BufferedImage inputImage;
	
	AnimalPicture (String chemin) {
		try {
			inputImage = ImageIO.read(new File (chemin));
			JLabel animalImage = new JLabel();
			animalImage.setIcon(new ImageIcon(inputImage));
			animal.add(animalImage);
        }
		catch (IOException ex) {
			System.err.println("IO error");
			ex.printStackTrace();}
	}
}
