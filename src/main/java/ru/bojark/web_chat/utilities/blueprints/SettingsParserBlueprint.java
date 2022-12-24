package ru.bojark.web_chat.utilities.blueprints;

public interface SettingsParserBlueprint {
    int parsePort();
    String parseHost();
    String parseUserName();
    String parseLogPath();
}
