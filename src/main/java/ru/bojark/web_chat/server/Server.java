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
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final String PORT;
    private final String NAME;
    private final Logger LOGGER;
    private List<Socket> socketList = new ArrayList<>();

    public Server(String port, String name, String loggerPath) {

        this.PORT = port;
        this.NAME = name;
        this.LOGGER = new Logger(loggerPath);
    }

    public void start() {
        printMessage(new Message(NAME, Strings.SERVER_IS_STARTING.toString()));
        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(PORT))) {
            printMessage(new Message(NAME, Strings.SERVER_IS_ON.toString()));
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> {
                    try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
                        Message message_in = Message.fromJSON(in.readLine());
                        printMessage(message_in);
                        out.println(message_in.toJSON());
                        out.flush();

                    } catch (IOException e){
                        printMessage(new Message(NAME, Strings.SERVER_FAILED_TO_CONNECT.toString()));
                    } finally {
                        try {clientSocket.close();} catch (IOException e) {
                            printMessage(new Message("Error", Strings.SERVER_FAILED_TO_CLOSE_CONNECTION.toString()));
                        }
                    }
                });
            }
        } catch (IOException e) {
            printMessage(new Message("Error", Strings.SERVER_STARTING_ER.toString()));
        }

    }

    private void printMessage(Message message) {
        System.out.println(message);
        LOGGER.log(message);

    }

}
