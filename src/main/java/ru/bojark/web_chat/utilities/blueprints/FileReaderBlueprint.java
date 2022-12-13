package ru.bojark.web_chat.utilities.blueprints;

public interface FileReaderBlueprint {
    String readFile();
    String parsePort();
    String parseUserName();
    String parseLogPath();
}
