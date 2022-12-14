package ru.bojark.web_chat.utilities;

import ru.bojark.web_chat.utilities.blueprints.SettingsParserBlueprint;
import ru.bojark.web_chat.utilities.misc.SetParams;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsParser implements SettingsParserBlueprint {

    private final List<String> settings;
    private final String DEFPORT;
    private final String DEFHOST;
    private final String DEFNAME;
    private final String DEFLOGPATH;

    public SettingsParser(){
        this(SetParams.DEFPATH.toString());
    }
    public SettingsParser(String settingsPath){

        this(settingsPath,
                SetParams.DEFPORT.toString(),
                SetParams.DEFHOST.toString(),
                SetParams.DEFNAME.toString(),
                SetParams.DEFLOGPATH.toString());
    }

    public SettingsParser(String settingsPath, String defaultPort, String defaultHost, String defaultName, String defaultLogPath){

        this.DEFPORT = defaultPort;
        this.DEFHOST = defaultHost;
        this.DEFNAME = defaultName;
        this.DEFLOGPATH = defaultLogPath;

        settings = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(settingsPath));
            // считаем сначала первую строку
            String line = reader.readLine();
            settings.add(line);
            while (line != null) {
                line = reader.readLine();
                settings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String parseSetting(String set, String defSet){

        for (String line : settings
        ) {
            String[] setting = line.split(":", 2);
            if(setting[0].equals(set)){
                return setting[1];
            }
        }
        return defSet;
    }

    @Override
    public String parsePort() {
        return parseSetting("port", DEFPORT);
    }

    @Override
    public String parseHost() {
        return parseSetting("host", DEFHOST);
    }

    @Override
    public String parseUserName() {
        return parseSetting("name-default", DEFNAME);
    }

    @Override
    public String parseLogPath() {
        return parseSetting("logpath", DEFLOGPATH);
    }
}