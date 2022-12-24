package ru.bojark.web_chat.server;

import ru.bojark.web_chat.utilities.Message;
import ru.bojark.web_chat.utilities.SettingsParser;

public class Main {

    public static void main(String[] args) {
        Message test_message = new Message("Test", "Тестовое сообщение");
        System.out.println(test_message.toJSON());
        System.out.println(Message.fromJSON(test_message.toJSON()));
        SettingsParser sp = new SettingsParser("settings_server.txt");
        Server server = new Server(sp.parsePort(), sp.parseUserName(), sp.parseLogPath());
        server.start();

    }

}
