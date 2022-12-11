package client;

import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

public class MpihainoB implements ActionListener {
    String ref;
    GClient gclient;

    GClient getgclient() {
        return this.gclient;
    }

    void setgclient(GClient gc) {
        this.gclient = gc;
    }

    void setref(String ref) {
        this.ref = ref;
    }

    String getref() {
        return this.ref;
    }

    public MpihainoB(GClient gc, String ref) {
        this.setgclient(gc);
        this.setref(ref);
    }

    public void actionPerformed(ActionEvent e) {
        if (getref().equals("sortir")) {
            if (GClient.socket == null) {
                JOptionPane.showMessageDialog(GClient.window,
                        "La connexion a ete interrompue!");
            } else if (GClient.socket != null && GClient.socket.isConnected()) {
                try {
                    new EnvoyeC(GClient.socket, getgclient().getnom_utilisateur(), "3", "");
                    GClient.connect.setText("Entrer");
                    GClient.sortir.setText("Bye!");
                    GClient.socket.close();
                    GClient.socket = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (getref().equals("connect")) {
            if (GClient.socket != null && GClient.socket.isConnected()) {
                JOptionPane.showMessageDialog(GClient.window, "Connecte!");
            } else {
                String ipString = "localhost";
                String portClinet = getgclient().port.getText();
                String name = getgclient().nom_utilisateur.getText();

                if ("".equals(name) || "".equals(portClinet)) {
                    JOptionPane.showMessageDialog(GClient.window,
                            "L'utilisateur ou le port ne peut pas etre vide !");
                } else {
                    try {
                        int ports = Integer.parseInt(portClinet);
                        GClient.socket = new Socket(ipString, ports);
                        GClient.connect.setText("Entrer");
                        GClient.sortir.setText("sortir");
                        new EnvoyeC(GClient.socket, getgclient().getnom_utilisateur(), "2", "");
                        new Thread(new ReceveurC(GClient.socket)).start();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(GClient.window,
                                "Echec de la connexion, verifiez l'ip et le port");
                    }
                }
            }
        } else if (getref().equals("prive")) {
            getgclient().envoyerMsgPriv();
        } else if (getref().equals("envoye")) {
            getgclient().envoyerMsg();
        }
    }
}
