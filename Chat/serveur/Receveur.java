package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class Receveur implements Runnable {
    private Socket socket;
    private ArrayList<Socket> liste_utilisateur;
    private Vector<String> nom_utilisateur;
    private Map<String, Socket> map;

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String s = br.readLine();
                String[] str = s.split(",,");
                String info = str[0];
                String cmd = str[1];
                String name = "";
                if (str.length == 3)
                    name = str[2];

                if (info.equals("1")) { // 1 pour nouveau message
                    GServeur.console.append("Nouveau message de " + cmd + "\r\n");
                    GServeur.console.setCaretPosition(GServeur.console.getText().length());
                    new Envoye(liste_utilisateur, cmd, "1", "");
                } else if (info.equals("2")) { // 2 pour connexion
                    if (!nom_utilisateur.contains(cmd)) {
                        GServeur.console.append("Nouvelle connexion : " + cmd + "\r\n");
                        GServeur.console.setCaretPosition(GServeur.console.getText().length());
                        nom_utilisateur.add(cmd);
                        map.put(cmd, socket);
                        GServeur.utilisateur.setListData(nom_utilisateur);
                        new Envoye(liste_utilisateur, nom_utilisateur, "2", cmd);
                    } else {
                        GServeur.console.append("Connexion double : " + cmd + "\r\n");
                        GServeur.console.setCaretPosition(GServeur.console.getText().length());
                        liste_utilisateur.remove(socket);
                        new Envoye(socket, "", "4");
                    }
                } else if (info.equals("3")) { // 3 pour sortir
                    GServeur.console.append("Un client est sorti : " + cmd + "\r\n");
                    GServeur.console.setCaretPosition(GServeur.console.getText().length());
                    nom_utilisateur.remove(cmd);
                    liste_utilisateur.remove(socket);
                    map.remove(cmd);
                    GServeur.utilisateur.setListData(nom_utilisateur);
                    new Envoye(liste_utilisateur, nom_utilisateur, "3", cmd);
                    socket.close();
                    break;
                } else if (info.equals("4")) { // 4 pour message prive
                    GServeur.console.append("Nouveau message prive venant de " + cmd + "\r\n");
                    GServeur.console.setCaretPosition(GServeur.console.getText().length());
                    if (map.containsKey(name))
                        new Envoye(map.get(name), cmd, "6");
                    else
                        new Envoye(socket, "", "7");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Receveur(Socket s, ArrayList<Socket> liste_utilisateur, Vector<String> nom_utilisateur,
            Map<String, Socket> map) {
        this.socket = s;
        this.liste_utilisateur = liste_utilisateur;
        this.nom_utilisateur = nom_utilisateur;
        this.map = map;
    }

}
