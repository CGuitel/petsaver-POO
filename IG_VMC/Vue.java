import javax.swing.JFrame;

public abstract class Vue extends JFrame {
	abstract protected void bravo();
	abstract protected void miseAJour();

	abstract protected void menuJouer(Partie partie);
	abstract protected void menuInitialisation();
}
