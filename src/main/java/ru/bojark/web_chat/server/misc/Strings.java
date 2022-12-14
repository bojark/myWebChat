package ru.bojark.web_chat.server.misc;

public enum Strings {

        SERVER_IS_ON("Сервер начал работу."),
        SERVER_IS_OFF("Сервер закончил работу."),
        SERVER_IS_STARTING("Пробуем запустить сервер.");
        private String text;
        Strings(String text) {
            this.text = text;
        }

    @Override
    public String toString() {
        return text;
    }
}