package ru.bojark.web_chat.server.logic;

import ru.bojark.web_chat.server.misc.Strings;
import ru.bojark.web_chat.utilities.Logger;
import ru.bojark.web_chat.utilities.Message;
import ru.bojark.web_chat.utilities.SettingsParser;
import ru.bojark.web_chat.utilities.misc.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    private final int PORT;
    private final String NAME;
    private final Logger LOGGER;

    private static Map<Integer, User> users;

    private Server(int port, String name, String loggerPath){

        this.PORT = port;
        this.NAME = name;
        this.LOGGER = new Logger(loggerPath);
        users = new HashMap<>();
    }
    public static Server buildFromSettings(String settingsPath){
        SettingsParser sp = new SettingsParser(settingsPath);
        return new Server(sp.parsePort(), sp.parseUserName(), sp.parseLogPath());
    }

    public void start(){
        LOGGER.printMessage(new Message(NAME, Strings.SERVER_IS_ON.toString()));
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while (true){
                try{
                    Socket clientSocket = serverSocket.accept();
                    Message newConnectionMsg = new Message(NAME, Strings.SERVER_NEW_CONNECTION.toString()
                            + clientSocket.getPort());
                    LOGGER.printMessage(newConnectionMsg);
                    sendMessToAll(newConnectionMsg);
                    new Thread(() -> {
                        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                            User user = new User(clientSocket, out);
                            users.put(clientSocket.getPort(), user);
                            waitMessAndSend(clientSocket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void waitMessAndSend(Socket clientSocket) {
        try (Scanner inMess = new Scanner(clientSocket.getInputStream())) {
            while (true) {
                if (inMess.hasNext()) {
                    Message msg = Message.fromJSON(inMess.nextLine());
                    if (msg.isExit()) {
                        users.remove(clientSocket.getPort());
                        Message userLeftMsg = new Message(NAME, Strings.USER_LEFT_CHAT + msg.getSender());
                        LOGGER.printMessage(userLeftMsg);
                        sendMessToAll(userLeftMsg);
                    } else {
                        LOGGER.printMessage(msg);
                        sendMessToAll(msg);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessToAll(Message msg) {
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            entry.getValue().sendMsg(msg);
        }
//        LOGGER.printMessage(new Message(NAME, Strings.SERVER_MESSAGE_SEND_TO_ALL.toString()));
    }

}
