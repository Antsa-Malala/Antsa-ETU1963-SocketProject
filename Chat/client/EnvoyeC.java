package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class EnvoyeC {
    EnvoyeC(Socket s, Object message, String info, String name) throws IOException {
        String messages = info + ",," + message + ",," + name;
        PrintWriter po = new PrintWriter(s.getOutputStream(), true);
        po.println(messages);
    }
}
