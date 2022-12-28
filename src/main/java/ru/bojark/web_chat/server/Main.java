package ru.bojark.web_chat.server;

import ru.bojark.web_chat.server.logic.Server;

public class Main {
    final static String SETTINGS_PATH = "settings_server.txt";

    public static void main(String[] args) {
        Server server = Server.buildFormSettings(SETTINGS_PATH);
        server.start();

    }

}
