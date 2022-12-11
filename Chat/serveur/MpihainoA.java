package serveur;

import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class MpihainoA implements ActionListener {
    String ref;
    GServeur gserveur;

    void setref(String ref) {
        this.ref = ref;
    }

    void setgserveur(GServeur serv) {
        this.gserveur = serv;
    }

    String getref() {
        return this.ref;
    }

    GServeur getgserveur() {
        return this.gserveur;
    }

    public MpihainoA(GServeur s, String ref) {
        this.setgserveur(s);
        this.setref(ref);
    }

    public void actionPerformed(ActionEvent e) {
        if (getref().equals("sortir")) {
            if (Serveur.ss == null || Serveur.ss.isClosed()) {
                JOptionPane.showMessageDialog(GServeur.window, "Le serveur a ete ferme !");
            } else {
                if (Serveur.liste_utilisateur != null && Serveur.liste_utilisateur.size() != 0) {
                    try {
                        new Envoye(Serveur.liste_utilisateur, "", "5", "");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    getgserveur().init.setText("Initier");
                    getgserveur().sortir.setText("Arreter");
                    Serveur.ss.close();
                    Serveur.ss = null;
                    Serveur.liste_utilisateur = null;
                    Serveur.flag = false;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (getref().equals("init")) {
            if (Serveur.ss != null && !Serveur.ss.isClosed()) {
                JOptionPane.showMessageDialog(GServeur.window, "Le serveur a demarre !");
            } else {
                GServeur.ports = getgserveur().getport();
                if (GServeur.ports != 0) {
                    try {
                        Serveur.flag = true;
                        new Thread(new Serveur(GServeur.ports)).start();
                        getgserveur().init.setText("Initier");
                        getgserveur().sortir.setText("Arreter");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(GServeur.window, "Erreur initialisation serveur");
                    }
                }
            }
        }
    }
}
