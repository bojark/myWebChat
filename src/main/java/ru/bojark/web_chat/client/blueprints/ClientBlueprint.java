package ru.bojark.web_chat.client.blueprints;

public interface ClientBlueprint {
    void start();
    void listen();
    void setName();
    void connect();
    void send();
    void log();
    void exit();

}
