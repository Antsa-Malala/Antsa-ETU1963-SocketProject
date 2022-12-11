package serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Envoye {
    Envoye(Socket s, Object message, String info) throws IOException {
        String messages = info + "." + message;
        PrintWriter po = new PrintWriter(s.getOutputStream(), true);
        po.println(messages);
    }

    Envoye(ArrayList<Socket> liste_utilisateur, Object message, String info, String nom) throws IOException {
        String messages = info + "." + message + "." + nom;
        PrintWriter po = null;
        for (Socket s : liste_utilisateur) {
            po = new PrintWriter(s.getOutputStream(), true);
            po.println(messages);
        }
    }

}
