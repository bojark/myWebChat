package ru.bojark.web_chat.client;

import ru.bojark.web_chat.client.logic.Client;

import java.io.IOException;

public class Client2 {

    private static final String SETTINGS_PATH = "settings_client_2.txt";

    public static void main(String[] args) throws IOException {
        Client client = Client.buildFromSettings(SETTINGS_PATH);
        client.start();
    }


}
