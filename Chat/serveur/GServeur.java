package serveur;

import javax.swing.*;

public class GServeur {
    public static JFrame window;
    public static int ports;
    public static JTextArea console;
    public static JList<String> utilisateur;
    JButton init;
    JButton sortir;
    JTextField nom_serveur;
    JTextField port_serveur;

    public void init() {
        window = new JFrame("Serveur");
        window.setLayout(null);
        window.setBounds(200, 200, 510, 340);

        JLabel labelnom_serveur = new JLabel("Serveur:");
        labelnom_serveur.setBounds(10, 8, 80, 30);
        window.add(labelnom_serveur);

        nom_serveur = new JTextField();
        nom_serveur.setBounds(80, 8, 60, 30);
        window.add(nom_serveur);

        JLabel label_port = new JLabel("Port:");
        label_port.setBounds(150, 8, 60, 30);
        window.add(label_port);

        port_serveur = new JTextField();
        port_serveur.setBounds(200, 8, 70, 30);
        window.add(port_serveur);

        init = new JButton("Initier");
        init.setBounds(280, 8, 90, 30);
        window.add(init);

        sortir = new JButton("Sortir");
        sortir.setBounds(380, 8, 110, 30);
        window.add(sortir);

        console = new JTextArea();
        console.setBounds(10, 70, 340, 320);
        console.setEditable(false);

        console.setLineWrap(true);
        console.setWrapStyleWord(true);

        JLabel label_text = new JLabel("Operations");
        label_text.setBounds(100, 47, 190, 30);
        window.add(label_text);

        JScrollPane paneText = new JScrollPane(console);
        paneText.setBounds(10, 70, 340, 220);
        window.add(paneText);

        JLabel label_liste_utilisateur = new JLabel("Liste des utilisateurs");
        label_liste_utilisateur.setBounds(357, 47, 180, 30);
        window.add(label_liste_utilisateur);

        utilisateur = new JList<String>();
        JScrollPane paneutilisateur = new JScrollPane(utilisateur);
        paneutilisateur.setBounds(355, 70, 130, 220);
        window.add(paneutilisateur);

        mpihaino();
        window.setVisible(true);
    }

    public void mpihaino() {
        window.addWindowListener(new Adapteur());

        sortir.addActionListener(new MpihainoA(this, "sortir"));

        init.addActionListener(new MpihainoA(this, "init"));
    }

    public int getport() {
        String port = port_serveur.getText();
        String name = nom_serveur.getText();
        if ("".equals(port) || "".equals(name)) {
            JOptionPane.showMessageDialog(window, "Port ou nom du serveur non specifie!");
            return 0;
        } else {
            return Integer.parseInt(port);
        }
    }

    public String getnom_serveur() {
        return nom_serveur.getText();
    }

    public GServeur() {
        init();
    }
}