import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.io.BufferedReader;
import java.io.FileReader;

public class CarteAvecRegles extends Carte {

	CarteAvecRegles(String chemin) {
		super(chemin);
		JTextArea zoneText = new JTextArea(readFile("regles.txt"));
		JScrollPane texteAsc = new JScrollPane(zoneText);
		texteAsc.setVerticalScrollBarPolicy(texteAsc.VERTICAL_SCROLLBAR_ALWAYS);
		super.imageFon.add(texteAsc);
		zoneText.setEditable(true);
		zoneText.setVisible(true);
	}
    public String readFile( String file )
    {
        // lit le fichier
        String lines = "";
        String line;
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader(file) );
            while( (line = reader.readLine()) != null )
                lines += line+"\n";
        }
        catch( Exception e )
        {
            lines = "Une erreur s'est produite durant la lecture : "+e.getMessage();
        }  
         
        return lines;
    }
}