package ru.bojark.web_chat.utilities;

import java.io.*;

public class Logger {
    private final String PATH;

    public Logger(String path){
        this.PATH = path;
    }

    public void log(Message message){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH, true))){
            bw.write(message.toString());
            bw.flush();

        } catch (IOException e) {
            System.out.println(new Message("Error", "Ошибка записи в файл логгирования."));
        }
    }


}
