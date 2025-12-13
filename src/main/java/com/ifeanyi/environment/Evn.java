package com.ifeanyi.environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Evn {

    private static Evn evn;

    public record EvnData(String geminiKey,String gptKey) {
    }

    private Evn() {

    }

    public static Evn Instance() {

        if (evn == null) {
            return evn = new Evn();
        }
        return evn;

    }

    public EvnData initEvn() {

        String evnPath = "/home/ifeanyi/IdeaProjects/Tweet-audit/src/main/resources/evn.json";

        System.out.println("Loading evn.json from "+evnPath);

        File file = new File(evnPath);

        try(Reader reader  = new FileReader(file)) {

            Gson gson = new Gson();
            return gson.fromJson(reader, EvnData.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
