package ru.bojark.web_chat.client.misc;

public enum Strings {
    CLIENT_EXIT("Клиент отключился."),
    CLIENT_CHANGE_USERNAME_MESSAGE("Имя пользователя будет изменено на "),
    CLIENT_EXIT_COMMAND("/exit"),
    CLIENT_RUNS("Клиент запущен. Начните вводить сообщения. " + CLIENT_EXIT_COMMAND + " -- выход."),
    CLIENT_DEF_NAME_COMMAND("/def"),
    CLIENT_SETNAME_MESSAGE("Введите имя пользователя (" + CLIENT_DEF_NAME_COMMAND + " -- имя по умолчанию):\n>>"),
    SERVER_RECEIVE_MESSAGE_ERROR("Проблема с получением сообщения от сервера."),
    SERVER_IS_NOT_RUNNING_ERROR("Невозможно подключиться к серверу, перезапустите клиент."),
    CLIENT_DEF_NAME("Выбрано имя по умолчанию.");

    private String text;

    Strings(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
