package ru.bojark.web_chat.client;

import java.io.IOException;

public class Main {

    private static final String SETTINGS_PATH = "settings_client_1.txt";

    public static void main(String[] args) throws IOException {
        Client client = Client.build(SETTINGS_PATH);
        client.start();
    }


}
