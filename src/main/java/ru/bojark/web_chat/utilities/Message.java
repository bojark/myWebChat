package ru.bojark.web_chat.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.bojark.web_chat.utilities.misc.Strings;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message {
    public String date;
    public String sender;
    public String messageText;
    private final Boolean isExit;


    public Message(String date, String sender, String messageText, Boolean isExit) {

        this.date = date;
        this.sender = sender;
        this.messageText = messageText;
        this.isExit = isExit;
    }

    public Message(String sender, String messageText) {
        this(sender, messageText, false);
    }

    public Message(String sender, Boolean isExit) {
        this(sender, "", isExit);
    }

    public Message(String sender, String messageText, Boolean isExit) {
        this(currentTime(), sender, messageText, isExit);
    }

    public String getSender() {
        return sender;
    }

    public String toString() {
        return date + " " + sender + ": " + messageText;
    }

    public static String currentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

    public Boolean isExit() {
        return isExit;
    }

    public static Message fromJSON(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            return new Message("Error", Strings.MESSAGE_ERROR.toString());
        }
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(jsonObject.toString(), Message.class);

    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
