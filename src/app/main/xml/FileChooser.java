package app.main.xml;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class FileChooser {

    public static File selectFile() throws Exception {

        File file = null;
        try {
            file = new File("fichiersXML2022").getCanonicalFile();
            // System.out.println("Répertoire courant : " + repertoireCourant);
        } catch (IOException e) {
            System.out.println("error selectMapFile : " + e.getMessage());
        }

        // création de la boîte de dialogue dans ce répertoire courant
        // (ou dans "home" s'il y a eu une erreur d'entrée/sortie, auquel
        // cas repertoireCourant vaut null)
        JFileChooser dialogue = new JFileChooser(file);

        // affichage
        int returnValue = dialogue.showOpenDialog(null);
        if (returnValue == JFileChooser.CANCEL_OPTION) {
            return null;
        }
        return dialogue.getSelectedFile();

    }
}
