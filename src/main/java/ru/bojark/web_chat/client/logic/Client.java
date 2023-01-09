package ru.bojark.web_chat.client.logic;

import ru.bojark.web_chat.client.misc.Strings;
import ru.bojark.web_chat.utilities.Logger;
import ru.bojark.web_chat.utilities.Message;
import ru.bojark.web_chat.utilities.SettingsParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    private String username;
    private final int PORT;
    private final String HOST;
    private final Logger LOGGER;

    private AtomicBoolean flag;

    private Socket clientSocket;
    private BufferedReader inMes;
    private PrintWriter outMes;
    private Scanner console;

    public String getUsername() {
        return username;
    }

    private Client(String username, int port, String host, String logPath) {
        this.username = username;
        PORT = port;
        HOST = host;
        this.LOGGER = new Logger(logPath);
    }

    public static Client buildFromSettings(String settingsPath) {
        SettingsParser sp = new SettingsParser(settingsPath);
        return new Client(sp.parseUserName(), sp.parsePort(), sp.parseHost(), sp.parseLogPath());
    }

    public void start() throws IOException {
        if (connect()) {
            outMes = new PrintWriter(clientSocket.getOutputStream(), true);
            inMes = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            console = new Scanner(System.in);

            flag = new AtomicBoolean(true);

            chooseUserName();

            LOGGER.printMessage(new Message(username, Strings.CLIENT_RUNS.toString()));

            serverListener();
            userListener();
        }
    }

    public void serverListener() {
        new Thread(() -> {
            try {
                while (true) {
                    if (!flag.get()) {
                        inMes.close();
                        clientSocket.close();
                        LOGGER.printMessage(new Message(username, Strings.CLIENT_EXIT.toString()));
                        break;
                    }
                    if (inMes.ready()) {
                        LOGGER.printMessage(Message.fromJSON(inMes.readLine()));
                    }
                }
            } catch (IOException ex) {
                LOGGER.printMessage(new Message("ERROR", Strings.SERVER_RECEIVE_MESSAGE_ERROR.toString()));
            }
        }).start();
    }

    public void userListener() {
        new Thread(() -> {
            while (true) {
                if (console.hasNext()) {
                    String input = console.nextLine();
                    if (input.equalsIgnoreCase(Strings.CLIENT_EXIT_COMMAND.toString())) {
                        Message exitMessage = new Message(username, true);
                        outMes.println(exitMessage.toJSON());
                        console.close();
                        outMes.close();
                        flag.set(false);
                        break;
                    }
                    outMes.println(new Message(username, input).toJSON());
                }
            }
        }).start();
    }

    public void chooseUserName() {
        System.out.print(Strings.CLIENT_SETNAME_MESSAGE);
        String input = console.nextLine();
        if (!input.equals(Strings.CLIENT_DEF_NAME_COMMAND.toString())) {
            setName(input);
        } else {
            LOGGER.printMessage(new Message(getUsername(), Strings.CLIENT_DEF_NAME.toString()));
        }


    }

    private void setName(String name) {
        LOGGER.printMessage(new Message(username, Strings.CLIENT_CHANGE_USERNAME_MESSAGE.toString() + name));
        this.username = name;
    }

    private Boolean connect() {
        try {
            clientSocket = new Socket(HOST, PORT);
            return true;
        } catch (IOException e) {
            LOGGER.printMessage(new Message("ERROR", Strings.SERVER_IS_NOT_RUNNING_ERROR.toString()));
            return false;
        }
    }


}
