package ru.bojark.web_chat.client;

import java.io.IOException;

public class Main {

    private static final String SETTINGS_PATH = "settings_client_1.txt";
    private static final String SETTINGS_PATH2 = "settings_client_2.txt";

    public static void main(String[] args) throws IOException {
        Client client = Client.build(SETTINGS_PATH);
        Client client2 = Client.build(SETTINGS_PATH2);
        client.start();
        client2.start();
    }


}
