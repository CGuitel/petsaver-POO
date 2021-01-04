import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class BlocPicture extends JPanel {
	int x;
	int y;
	JPanel bloc = new JPanel() {
		public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Shape line = new Line2D.Double(x, y, x+10, y+10);
        Shape roundRect = new RoundRectangle2D.Double(x, y, 8, 8, 1, 1);
        g2d.draw(line);
        g2d.draw(roundRect);
        bloc.paintComponents(g2d);
		}
	};
	BlocPicture (int x, int y) {
		this.x = x;
		this.y = y;
	}
}

