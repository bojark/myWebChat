package ru.bojark.web_chat.server;

import ru.bojark.web_chat.utilities.Message;
import ru.bojark.web_chat.utilities.SettingsParser;

public class Main {

    public static void main(String[] args) {
        System.out.println(new Message("Test", "Тестовое сообщение").toJSON());
        SettingsParser sp = new SettingsParser("settings_server.txt");
        Server server = new Server(sp.parsePort(), sp.parseUserName(), sp.parseLogPath());
        server.start();

    }

}
