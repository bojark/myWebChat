package ru.bojark.web_chat.utilities.misc;

public enum SetParams {
    DEFPATH("settings.txt"),
    DEFPORT("8090"),
    DEFHOST("127.0.0.1"),
    DEFNAME("user"),
    DEFLOGPATH("log.txt");
    private final String param;

    SetParams(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return param;
    }

}
