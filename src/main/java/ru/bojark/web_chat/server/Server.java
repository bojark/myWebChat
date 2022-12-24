package ru.bojark.web_chat.server;

import ru.bojark.web_chat.server.misc.Strings;
import ru.bojark.web_chat.utilities.Logger;
import ru.bojark.web_chat.utilities.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int PORT;
    private final String NAME;
    private final Logger LOGGER;

    public Server(int port, String name, String loggerPath) {

        this.PORT = port;
        this.NAME = name;
        this.LOGGER = new Logger(loggerPath);
    }

    public void start() {
        LOGGER.printMessage(new Message(NAME, Strings.SERVER_IS_STARTING.toString()));
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.printMessage(new Message(NAME, Strings.SERVER_IS_ON.toString()));
            while (true) {
                try{
                    Socket clientSocket = serverSocket.accept();
                    //LOGGER.printMessage(new Message(NAME, Strings.SERVER_NEW_CONNECTION.toString()));
                    new Thread(() -> {
                        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                            Message message_in = Message.fromJSON(in.readLine());
                            if(message_in.isExit()){
                                LOGGER.printMessage(new Message(message_in.getSender(), Strings.SENDER_EXIT.toString()));
                            } else {LOGGER.printMessage(message_in);}

                            out.println(new Message(NAME, Strings.SERVER_CONFIRMS.toString()).toJSON());
                            out.flush();

                        } catch (IOException e) {
                            LOGGER.printMessage(new Message(NAME, Strings.SERVER_FAILED_TO_CONNECT.toString()));
                        } finally {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                LOGGER.printMessage(new Message("Error", Strings.SERVER_FAILED_TO_CLOSE_CONNECTION.toString()));
                            }
                        }
                    }).start();

                }catch (IOException e){
                    LOGGER.printMessage(new Message("Error", Strings.SERVER_FAILED_TO_ACCEPT_CONNECTION.toString()));
                }
            }
        } catch (IOException e) {
            LOGGER.printMessage(new Message("Error", Strings.SERVER_STARTING_ER.toString()));
        }

    }


}
