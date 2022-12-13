package ru.bojark.web_chat.server.blueprints;

public interface ServerBlueprint {
    void start(String port);
    void clientListner();
    void connection();
    void send();
    void log();
}
