package ru.bojark.web_chat.server;

import ru.bojark.web_chat.utilities.SettingsParser;

public class Main {

    public static void main(String[] args) {
        SettingsParser settingsParser = new SettingsParser("settings.txt");
        System.out.println(settingsParser.parseUserName());
    }

}
