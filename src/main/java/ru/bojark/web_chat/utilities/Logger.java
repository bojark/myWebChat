package ru.bojark.web_chat.utilities;

import ru.bojark.web_chat.utilities.misc.Strings;

import java.io.*;

public class Logger {
    private final String PATH;

    public Logger(String path){
        this.PATH = path;
    }

    public void log(Message message){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true))){
            bw.write(message.toString() + "\n");
            bw.flush();

        } catch (IOException e) {
            System.out.println(new Message("Error", Strings.LOGGER_ERROR.toString()));
        }
    }

    public void printMessage(Message message) {
        System.out.println(message);
        this.log(message);
    }


}
