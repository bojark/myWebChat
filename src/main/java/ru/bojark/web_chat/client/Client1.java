package ru.bojark.web_chat.client;

import ru.bojark.web_chat.client.logic.Client;

import java.io.IOException;

public class Client1 {

    private static final String SETTINGS_PATH = "settings_client_1.txt";

    public static void main(String[] args) throws IOException {
        Client client = Client.buildFromSettings(SETTINGS_PATH);
        client.start();
    }


}
