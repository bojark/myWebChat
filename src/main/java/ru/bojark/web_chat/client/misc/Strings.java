package ru.bojark.web_chat.client.misc;

import ru.bojark.web_chat.utilities.Message;

public enum Strings {
    CLIENT_RUNS("Клиент запущен."),
    CLIENT_EXIT("Клиент отключился."),
    CLIENT_INPUT_MESSAGE("Введите сообщение:\n>>"),
    CLIENT_SEND_ERROR("Ошибка при отправке сообщения на сервер."),
    MESSAGE_WRONG_FORMAT("Неверный формат сообщения.");

    private String text;
    Strings(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
