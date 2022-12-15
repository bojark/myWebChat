package ru.bojark.web_chat.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message {
    public String date;
    public String sender;
    public String messageText;
    private Boolean isExit = false;

    public Message() {

    }

    public Message(String date, String sender, String messageText) {

        this.date = date;
        this.sender = sender;
        this.messageText = messageText;
    }

    public Message(String sender, String messageText) {
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

    public String toString() {
        return date + " " + sender + ": " + messageText;
    }

    public static String currentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

    public Message exit() {
        this.isExit = true;
        return this;
    }

    public Boolean isExit() {
        return isExit;
    }

    public static Message fromJSON(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            return new Message("Error", "Неправильный формат сообщения.");
        }
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(jsonObject.toString(), Message.class);

    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
