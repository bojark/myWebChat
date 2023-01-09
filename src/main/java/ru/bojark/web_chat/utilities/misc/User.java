package ru.bojark.web_chat.utilities.misc;
import ru.bojark.web_chat.utilities.Message;

import java.io.PrintWriter;
import java.net.Socket;

public class User {
    private Socket clientSocket;
    private PrintWriter outMes;

    public User(Socket clientSocket, PrintWriter outMess) {
        this.clientSocket = clientSocket;
        this.outMes = outMess;
    }

    public void sendMsg(Message msg) {
        outMes.println(msg.toJSON());
        outMes.flush();
    }


}
