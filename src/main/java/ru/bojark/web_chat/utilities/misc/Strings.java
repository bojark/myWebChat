package ru.bojark.web_chat.utilities.misc;

public enum Strings {

    MESSAGE_ERROR("Неверный формат сообщения."),
    PARSER_ERROR("Файл не найден, загружаю параметры по умолчанию."),
    LOGGER_ERROR("Ошибка записи в файл логирования.");

    private String text;
    Strings(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
