package com.ifeanyi.storage;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.ifeanyi.storage.model.Result;
import com.ifeanyi.storage.model.Tweet;
import com.ifeanyi.storage.model.TweetRoot;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Storage {

    private static Storage storage;

    private Storage() {

    }

    public static Storage getInstance() {

        if (storage == null) {
            storage = new Storage();
            return storage;
        } else {
            return storage;
        }

    }

    private String configPath = "/home/ifeanyi/IdeaProjects/tweet_audit/src/main/resources/config.json";
    String[] csvHeaders = {"tweet_url", "deleted"};

    public void upDateStarFrom(int newStartFromIndex) {

        File file = new File(configPath);
        Gson gson = new Gson();

        try (Reader reader = new FileReader(file)) {

            Config config = gson.fromJson(reader, Config.class);

            Config newConfig = new Config(config.aiModel, config.modelId, config.readLimit, config.criterial, newStartFromIndex,config.username);
            FileWriter fileWriter = new FileWriter(configPath);

            gson.toJson(newConfig, fileWriter);
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public record Config(String aiModel, String modelId, int readLimit, String criterial, int startFromIndex,String  username) {

    }

    public Config loadConfig() {

        System.out.println("Loading config.json from " + configPath);

        File file = new File(configPath);
        Gson gson = new Gson();

        if (file.exists()) {

            try (Reader reader = new FileReader(file)) {

                return gson.fromJson(reader, Config.class);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {

            System.out.println("No config file found creating with default config settings.");
            System.out.println("Please enter your tweeter username in the json file");

            Config config = new Config("Google", "gemini-2.5-flash", 50, "Sextual misconduct,Outdated political opinions,Controversial statements,Insults,Unprofessional criticism ", 0,"username");

            try (FileOutputStream fileOutputStream = new FileOutputStream(configPath)) {

//                FileWriter fileWriter = new FileWriter(configPath);
//                gson.toJson(config, fileWriter);
//                fileWriter.flush();
//                fileWriter.close();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                gson.toJson(config, outputStreamWriter);
                outputStreamWriter.flush();
                fileOutputStream.flush();
                fileOutputStream.getFD().sync();
                loadConfig();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    public List<Tweet> loadTweets(int startFromIndex, int loadAmount) {

        Gson gson = new Gson();
        List<Tweet> result = new ArrayList<>();

        int maxload = startFromIndex + loadAmount;

        String filePath = "/home/ifeanyi/IdeaProjects/tweet_audit/src/main/resources/tweets.json";
        System.out.println("Loading tweets from " + filePath + " from " + startFromIndex + " to " + maxload);

        File file = new File(filePath);

        try (Reader reader = new FileReader(file)) {

            Type type = new TypeToken<List<TweetRoot>>() {
            }.getType();

            List<TweetRoot> tweet = gson.fromJson(reader, type);

            if (tweet.size() < startFromIndex) {
                System.out.println("Error: startFromIndex if greater than start tweet length");
                return Collections.emptyList();
            }

            for (int i = 0; i < loadAmount; i++) {
                result.add(tweet.get(startFromIndex + i).getTweet());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public boolean writeTweetResultCsv(String url, boolean deleted) {

        String filePath = "/home/ifeanyi/IdeaProjects/tweet_audit/src/main/resources/result.csv";

        File file = new File(filePath);

        if (file.exists()) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .build();

            try {

                FileWriter fileWriter = new FileWriter(file, true);
                CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);

                csvPrinter.printRecord(url, deleted);
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {

            try {

                BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(csvHeaders));

                csvPrinter.printRecord(url, deleted);
                writer.flush();
                writer.close();

            } catch (IOException e) {
                return false;
            }

        }

        return true;

    }

    public boolean writeTweetResult(Result result) {

        String filePath = "/home/ifeanyi/IdeaProjects/tweet_audit/src/main/resources/result.json";

        File file = new File(filePath);
        Gson gson = new Gson();

        if (file.exists()) {

            Type type = new TypeToken<List<Result>>() {
            }.getType();

            try (Reader reader = new FileReader(file)) {

                List<Result> results = gson.fromJson(reader, type);
                results.add(result);

                FileWriter fileWriter = new FileWriter(filePath);

                gson.toJson(results, fileWriter);
                fileWriter.flush();

            } catch (IOException e) {
                return false;
            }

        } else {

            try {

                FileWriter fileWriter = new FileWriter(filePath);

                List<Result> results = new ArrayList<>();
                results.add(result);

                gson.toJson(results, fileWriter);
                fileWriter.flush();

            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

}
