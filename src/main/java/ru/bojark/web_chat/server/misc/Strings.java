package ru.bojark.web_chat.server.misc;

public enum Strings {
        SERVER_IS_ON("Сервер начал работу."),
        SERVER_IS_STARTING("Пробуем запустить сервер."),
        SERVER_STARTING_ER("Ошибка запуска запуска сервера."),
        SERVER_FAILED_TO_CONNECT("Ошибка подключения к серверу."),
        SERVER_FAILED_TO_CLOSE_CONNECTION("Ошибка при закрытии клиентского сокета.");
        private String text;
        Strings(String text) {
            this.text = text;
        }

    @Override
    public String toString() {
        return text;
    }
}
