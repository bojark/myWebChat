package ru.bojark.web_chat.utilities.blueprints;

public interface SettingsParserBlueprint {
    String parsePort();
    String parseHost();
    String parseUserName();
    String parseLogPath();
}
