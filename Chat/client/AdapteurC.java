package client;

import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

public class AdapteurC extends WindowAdapter {
    Socket socket;
    GClient gclient;

    Socket getsocket() {
        return this.socket;
    }

    GClient getgclient() {
        return this.gclient;
    }

    void setsocket(Socket s) {
        this.socket = s;
    }

    void setgclient(GClient gc) {
        this.gclient = gc;
    }

    public AdapteurC(Socket s, GClient gc) {
        this.setsocket(s);
        this.setgclient(gc);
    }

    public void windowClosing(WindowEvent e) {
        if (getsocket() != null && getsocket().isConnected()) {
            try {
                new EnvoyeC(getsocket(), getgclient().getnom_utilisateur(), "3", "");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        System.exit(0);
    }

}
