package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GClient {
    public static JFrame window;
    public static JButton connect, sortir;
    public static JTextArea textMessage;
    public static Socket socket = null;
    public static JList<String> utilisateur;

    JTextField nom_utilisateur;
    JTextField port;
    JTextField message;
    JTextField msgPriv;
    JButton msgprive;
    JButton envoyer;

    public void init() {
        window = new JFrame("Chat ");
        window.setLayout(null);
        window.setBounds(800, 200, 538, 440);

        JLabel label_nom_utilisateur = new JLabel("Utilisateur :");
        label_nom_utilisateur.setBounds(10, 28, 70, 30);
        window.add(label_nom_utilisateur);

        nom_utilisateur = new JTextField();
        nom_utilisateur.setBounds(80, 28, 70, 30);
        window.add(nom_utilisateur);

        JLabel label_port = new JLabel("Port:");
        label_port.setBounds(180, 28, 50, 30);
        window.add(label_port);

        port = new JTextField();
        port.setBounds(230, 28, 50, 30);
        window.add(port);

        connect = new JButton("Entrer");
        connect.setBounds(300, 28, 90, 30);
        window.add(connect);

        sortir = new JButton("Sortir");
        sortir.setBounds(400, 28, 90, 30);
        window.add(sortir);

        textMessage = new JTextArea();
        textMessage.setBounds(10, 70, 340, 220);
        textMessage.setEditable(false);

        textMessage.setLineWrap(true);
        textMessage.setWrapStyleWord(true);

        JLabel label_text = new JLabel("Zone des messages");
        label_text.setBounds(100, 58, 200, 50);
        window.add(label_text);

        JScrollPane paneText = new JScrollPane(textMessage);
        paneText.setBounds(10, 90, 360, 240);
        window.add(paneText);

        JLabel label_liste_utilisatuer = new JLabel("Liste des utilisateurs");
        label_liste_utilisatuer.setBounds(380, 58, 200, 50);
        window.add(label_liste_utilisatuer);

        utilisateur = new JList<String>();
        JScrollPane paneutilisateur = new JScrollPane(utilisateur);
        paneutilisateur.setBounds(375, 90, 140, 240);
        window.add(paneutilisateur);

        JLabel label_notif = new JLabel("Entrer message pour le groupe");
        label_notif.setBounds(10, 320, 180, 50);
        window.add(label_notif);

        message = new JTextField();
        message.setBounds(10, 355, 188, 30);
        message.setText(null);
        window.add(message);

        JLabel label_util = new JLabel("Nom d'un utilisateur pour message prive");
        label_util.setBounds(272, 320, 250, 50);
        window.add(label_util);

        msgPriv = new JTextField();
        msgPriv.setBounds(272, 355, 100, 30);
        window.add(msgPriv);

        msgprive = new JButton("Message Prive");
        msgprive.setBounds(376, 355, 140, 30);
        window.add(msgprive);

        envoyer = new JButton("Message Public");
        envoyer.setBounds(190, 355, 77, 30);
        window.add(envoyer);

        apio();
        window.setVisible(true);
    }

    public void apio() {
        window.addWindowListener(new AdapteurC(socket, this));

        sortir.addActionListener(new MpihainoB(this, "sortir"));

        connect.addActionListener(new MpihainoB(this, "connect"));

        msgprive.addActionListener(new MpihainoB(this, "prive"));

        envoyer.addActionListener(new MpihainoB(this, "envoye"));
    }

    public void envoyerMsg() {
        String messages = message.getText();
        if ("".equals(messages)) {
            JOptionPane.showMessageDialog(window, "Il n'y a rien a envoyer");
        } else if (socket == null || !socket.isConnected()) {
            JOptionPane.showMessageDialog(window, "Pas de connection");
        } else {
            try {
                new EnvoyeC(socket, getnom_utilisateur() + ": " + messages, "1", "");
                message.setText(null);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(window, "Echec de l'envoi !");
            }
        }
    }

    public void envoyerMsgPriv() {
        String messages = message.getText();
        if ("".equals(messages)) {
            JOptionPane.showMessageDialog(window, "Il n'y a rien a envoyer !");
        } else if (socket == null || !socket.isConnected()) {
            JOptionPane.showMessageDialog(window, "Pas de connection");
        } else {
            try {
                new EnvoyeC(socket, getnom_utilisateur() + ": " + messages, "4", getmsgprive());
                GClient.textMessage.append(getnom_utilisateur() + ": " + messages + "\r\n");
                message.setText(null);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(window, "Message prive non envoye ");
            }
        }
    }

    public String getnom_utilisateur() {
        return nom_utilisateur.getText();
    }

    public String getmsgprive() {
        return msgPriv.getText();
    }

    public GClient() {
        init();
    }
}
