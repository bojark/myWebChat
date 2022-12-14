package ru.bojark.web_chat.server;

import ru.bojark.web_chat.server.misc.Strings;
import ru.bojark.web_chat.utilities.Logger;
import ru.bojark.web_chat.utilities.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final String PORT;
    private final String NAME;
    private final Logger LOGGER;
    private List<Socket> socketList = new ArrayList<>();

    public Server(String port, String name, String loggerPath){

        this.PORT = port;
        this.NAME = name;
        this.LOGGER = new Logger(loggerPath);
    }

    public void start() {
        printMessage(new Message(NAME, Strings.SERVER_IS_STARTING.toString()));
        try{
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(PORT));
            printMessage(new Message(NAME, Strings.SERVER_IS_ON.toString()));

        } catch (IOException e){
            printMessage(new Message("Error", "Ошибка запуска запуска сервера."));
        }

    }

    private void printMessage(Message message){
        System.out.println(message);
        LOGGER.log(message);

    }



    public void recieveMessage() {

    }


    public void log() {

    }
}
