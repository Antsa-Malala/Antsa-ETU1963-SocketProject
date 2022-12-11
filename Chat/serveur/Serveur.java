package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;

import javax.swing.JOptionPane;

public class Serveur implements Runnable {
    private int port;
    public static ArrayList<Socket> liste_utilisateur = null;
    public static Vector<String> nom_utilisateur = null;
    public static Map<String, Socket> map = null;
    public static ServerSocket ss = null;
    public static boolean flag = true;

    public void run() {
        Socket s = null;
        liste_utilisateur = new ArrayList<Socket>();
        nom_utilisateur = new Vector<String>();
        map = new HashMap<String, Socket>();

        System.out.println("Serveur initie!");

        try {
            ss = new ServerSocket(port);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while (flag) {
            try {
                s = ss.accept();
                liste_utilisateur.add(s);
                new Thread(new Receveur(s, liste_utilisateur, nom_utilisateur, map)).start();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(GServeur.window, "Serveur arrete");
            }
        }
    }

    public Serveur(int port) throws IOException {
        this.port = port;
    }
}
