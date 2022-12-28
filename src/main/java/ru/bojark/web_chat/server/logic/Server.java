package ru.bojark.web_chat.server.logic;

import ru.bojark.web_chat.server.misc.Strings;
import ru.bojark.web_chat.utilities.Logger;
import ru.bojark.web_chat.utilities.Message;
import ru.bojark.web_chat.utilities.SettingsParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Server {

    private final int PORT;
    private final String NAME;
    private final Logger LOGGER;
    private final Set<Socket> clientSet;


    private Server(int port, String name, String loggerPath) {

        this.PORT = port;
        this.NAME = name;
        this.LOGGER = new Logger(loggerPath);
        clientSet = new CopyOnWriteArraySet<>();
    }

    public static Server buildFormSettings(String settingsPath){
        SettingsParser sp = new SettingsParser(settingsPath);
        return new Server(sp.parsePort(), sp.parseUserName(), sp.parseLogPath());
    }

    public void start() {
        LOGGER.printMessage(new Message(NAME, Strings.SERVER_IS_STARTING.toString()));
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.printMessage(new Message(NAME, Strings.SERVER_IS_ON.toString()));
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientSet.add(clientSocket);
                    //LOGGER.printMessage(new Message(NAME, Strings.SERVER_NEW_CONNECTION.toString()));
                    new Thread(() -> {
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                            Message message_in = Message.fromJSON(in.readLine());
                            if (message_in.isExit()) {
                                LOGGER.printMessage(new Message(message_in.getSender(), Strings.SENDER_EXIT.toString()));
                                clientSet.removeIf((x) -> x.equals(clientSocket));
                                try {
                                    clientSocket.close();
                                } catch (IOException e) {
                                    LOGGER.printMessage(new Message("Error", Strings.SERVER_FAILED_TO_CLOSE_CONNECTION.toString()));
                                }
                            } else {
                                LOGGER.printMessage(message_in);
                            }

                            for (Socket client : clientSet
                            ) {
                                try (PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                                    out.println(new Message(NAME, Strings.SERVER_CONFIRMS.toString()).toJSON());
                                    out.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }


                        } catch (IOException e) {
                            LOGGER.printMessage(new Message(NAME, Strings.SERVER_FAILED_TO_CONNECT.toString()));
                        }
                    }).start();

                } catch (IOException e) {
                    LOGGER.printMessage(new Message("Error", Strings.SERVER_FAILED_TO_ACCEPT_CONNECTION.toString()));
                }
            }
        } catch (IOException e) {
            LOGGER.printMessage(new Message("Error", Strings.SERVER_STARTING_ER.toString()));
        }

    }


}
