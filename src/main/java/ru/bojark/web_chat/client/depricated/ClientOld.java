package ru.bojark.web_chat.client.depricated;

import ru.bojark.web_chat.client.misc.Strings;
import ru.bojark.web_chat.utilities.Logger;
import ru.bojark.web_chat.utilities.Message;
import ru.bojark.web_chat.utilities.SettingsParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientOld {
    private String username;
    private final int PORT;
    private final String HOST;
    private boolean isExit = false;
    private final Logger LOGGER;
    private Socket clientSocket;


    private ClientOld(String username, int port, String host, String logPath) {
        this.HOST = host;
        this.PORT = port;
        this.username = username;
        this.LOGGER = new Logger(logPath);

    }

    public static ClientOld build(String settingsPath) throws IOException {
        SettingsParser sp = new SettingsParser(settingsPath);
        return new ClientOld(sp.parseUserName(), sp.parsePort(), sp.parseHost(), sp.parseLogPath());
    }

    public Boolean connect(){
        try {
            clientSocket = new Socket(HOST, PORT);
            return true;
        } catch (IOException e) {
            LOGGER.printMessage(new Message("ERROR", Strings.SERVER_IS_NOT_RUNNING_ERROR.toString()));
            return false;
        }
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public void start() throws IOException {
        if(connect()){
            LOGGER.printMessage(new Message(username, Strings.CLIENT_RUNS.toString()));
            userListner();
            serverListner();
        }
    }

    private void serverListner() {

        new Thread(() -> {
            while (!isExit) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    if(in.ready()){
                        LOGGER.printMessage(Message.fromJSON(in.readLine()));
                    }
                } catch(IOException e) {
                    e.printStackTrace();
//                    LOGGER.printMessage(new Message("ERROR", Strings.SERVER_RECEIVE_MESSAGE_ERROR.toString()));
                }
            }
        }).start();
    }

    private void userListner() {
        new Thread(() -> {
            while (!isExit) {
                System.out.print(Strings.CLIENT_INPUT_MESSAGE);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                try {
                    String input = reader.readLine();
                    if (input.equals("/exit")) {
                        isExit = true;
                    } else {
                        Message message = new Message(username, input);
                        send(message);
                    }
                } catch (IOException e) {
                    System.out.println(Strings.MESSAGE_WRONG_FORMAT);
                }
            }
            Message closeMessage = new Message(username, Strings.CLIENT_EXIT.toString(), true);
            send(closeMessage);
            LOGGER.log(closeMessage);

        }).start();
    }

    private void send(Message message) {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            out.println(message.toJSON());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
//            LOGGER.printMessage(new Message("ERROR", Strings.CLIENT_SEND_ERROR.toString()));
        }
    }


}
