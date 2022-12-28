package ru.bojark.web_chat.client.misc;

public enum Strings {
    CLIENT_RUNS("Клиент запущен."),
    CLIENT_EXIT("Клиент отключился."),
    CLIENT_INPUT_MESSAGE("Введите сообщение:\n>>"),
    CLIENT_CHANGE_USERNAME_MESSAGE("Имя пользователя будет изменено на "),
    CLIENT_SEND_ERROR("Ошибка при отправке сообщения на сервер."),
    CLIENT_EXIT_COMMAND("/exit"),
    CLIENT_SETNAME_MESSAGE("Введите имя пользователя:\n>>"),
    MESSAGE_WRONG_FORMAT("Неверный формат сообщения."),
    SERVER_RECEIVE_MESSAGE_ERROR("Проблема с получением сообщения от сервера."),
    SERVER_IS_NOT_RUNNING_ERROR("Невозможно подключиться к серверу, перезапустите клиент.");



    private String text;
    Strings(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
