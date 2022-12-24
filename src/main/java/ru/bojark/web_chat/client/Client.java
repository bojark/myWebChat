package ru.bojark.web_chat.client;

import ru.bojark.web_chat.client.misc.Strings;
import ru.bojark.web_chat.utilities.Logger;
import ru.bojark.web_chat.utilities.Message;
import ru.bojark.web_chat.utilities.SettingsParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String username;
    private final int PORT;
    private final String HOST;
    private boolean isExit = false;
    private final Logger LOGGER;


    private Client(String username, int port, String host, String logPath) {
        this.HOST = host;
        this.PORT = port;
        this.username = username;
        this.LOGGER = new Logger(logPath);
    }

    public static Client build(String settingsPath) {
            SettingsParser sp = new SettingsParser(settingsPath);
            return new Client(sp.parseUserName(), sp.parsePort(), sp.parseHost(), sp.parseLogPath());
    }

    private void setUsername(String username){
        this.username = username;
    }

    public void start() throws IOException {
        new Thread(() -> {
            LOGGER.printMessage(new Message(username, Strings.CLIENT_RUNS.toString()));
            while(!isExit){
                System.out.print(Strings.CLIENT_INPUT_MESSAGE);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                try {
                    String input = reader.readLine();
                    if(input.equals("/exit")){
                        isExit = true;
                    } else {
                        Message message = new Message(username,input);
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

    private void send(Message message){
        try (Socket clientSocket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            out.println(message.toJSON());
            out.flush();
            LOGGER.printMessage(Message.fromJSON(in.readLine()));

        } catch (IOException e) {
            LOGGER.printMessage(new Message("ERROR", Strings.CLIENT_SEND_ERROR.toString()));
        }
    }




}
