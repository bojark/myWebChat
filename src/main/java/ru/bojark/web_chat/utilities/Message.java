package ru.bojark.web_chat.utilities;

public class Message {
    private final String date;
    private final String sender;
    private final String messageText;

    public Message(String date, String sender, String messageText) {
        this.date = date;
        this.sender = sender;
        this.messageText = messageText;
    }

    public String getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getMessengeText() {
        return messageText;
    }
}
