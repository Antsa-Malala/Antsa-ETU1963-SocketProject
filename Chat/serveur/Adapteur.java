package serveur;

import java.awt.event.*;
import java.io.IOException;

public class Adapteur extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        if (Serveur.liste_utilisateur != null && Serveur.liste_utilisateur.size() != 0) {
            try {
                new Envoye(Serveur.liste_utilisateur, "", "5", "");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        System.exit(0);
    }
}
