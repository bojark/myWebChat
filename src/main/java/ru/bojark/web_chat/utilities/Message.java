package ru.bojark.web_chat.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message {
    private final String date;
    private final String sender;
    private final String messageText;
    private Boolean isExit = false;

    private Message(@JsonProperty("date") String date,
                   @JsonProperty("sender") String sender,
                   @JsonProperty("messageText") String messageText) {

        this.date = date;
        this.sender = sender;
        this.messageText = messageText;
    }

    public Message(String sender, String messageText){
        this(currentTime(), sender, messageText);
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

    public String toString(){
        return date + " " + sender + ": " + messageText + "\n";
    }

    public static String currentTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

    public Message exit(){
        this.isExit = true;
        return this;
    }

    public Boolean isExit(){
        return isExit;
    }
}
