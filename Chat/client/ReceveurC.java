package client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceveurC implements Runnable {
    private Socket s;

    public ReceveurC(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while (true) {
                String s = br.readLine();
                String[] str = s.split("\\.");
                String info = str[0];
                String nom = "", cmd = "";
                if (str.length == 2)
                    cmd = str[1];
                else if (str.length == 3) {
                    cmd = str[1];
                    nom = str[2];
                }

                if (info.equals("1")) { // 1 pour message
                    GClient.textMessage.append(cmd + "\r\n");
                    GClient.textMessage.setCaretPosition(GClient.textMessage.getText().length());
                } else if (info.equals("2") || info.equals("3")) { // 2 pour entrer et 3 pour sortir
                    if (info.equals("2")) {
                        GClient.textMessage.append(nom + " est entre(e)!" + "\r\n");
                        GClient.textMessage.setCaretPosition(GClient.textMessage.getText().length());
                    } else {
                        GClient.textMessage.append(nom + " est sorti(e)!" + "\r\n");
                        GClient.textMessage.setCaretPosition(GClient.textMessage.getText().length());
                    }
                    String list = cmd.substring(1, cmd.length() - 1);
                    String[] data = list.split(",");
                    GClient.utilisateur.clearSelection();
                    GClient.utilisateur.setListData(data);
                } else if (info.equals("4")) { // 4 pour notifications
                    GClient.connect.setText("entrer");
                    GClient.sortir.setText("sortir");
                    GClient.socket.close();
                    GClient.socket = null;
                    JOptionPane.showMessageDialog(GClient.window, "Quelqu'un utilise deja ce nom d'utilisateur");
                    break;
                } else if (info.equals("5")) { // 5 pour fermer le serveur
                    GClient.connect.setText("entrer");
                    GClient.sortir.setText("sortir");
                    GClient.socket.close();
                    GClient.socket = null;
                    break;
                } else if (info.equals("6")) { // 6 pour message prive
                    GClient.textMessage.append("(Message prive) " + cmd + "\r\n");
                    GClient.textMessage.setCaretPosition(GClient.textMessage.getText().length());
                } else if (info.equals("7")) {
                    JOptionPane.showMessageDialog(GClient.window, "Cet utilisateur n'est pas en ligne");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(GClient.window, "Quitter avec succes");
        }
    }
}
