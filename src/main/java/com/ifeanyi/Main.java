package com.ifeanyi;

import com.ifeanyi.AIModels.AiModel;
import com.ifeanyi.AIModels.model.AiResponse;
import com.ifeanyi.environment.Evn;
import com.ifeanyi.storage.Storage;
import com.ifeanyi.storage.model.Tweet;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println(" _____                 _      ___            _ _ _   \n" +
                "|_   _|               | |    / _ \\          | (_) |  \n" +
                "  | |_      _____  ___| |_  / /_\\ \\_   _  __| |_| |_ \n" +
                "  | \\ \\ /\\ / / _ \\/ _ \\ __| |  _  | | | |/ _` | | __|\n" +
                "  | |\\ V  V /  __/  __/ |_  | | | | |_| | (_| | | |_ \n" +
                "  \\_/ \\_/\\_/ \\___|\\___|\\__| \\_| |_/\\__,_|\\__,_|_|\\__|\n" +
                "                                                     \n" +
                "                                                     ");
        Evn evn = Evn.Instance();
        Evn.EvnData evnData = evn.initEvn();
        System.out.println("Evn loaded successfully");

        Storage storage = Storage.getInstance();

        Storage.Config config = storage.loadConfig();
        System.out.println("Config file loaded successfully");
        System.out.println("Loading tweets");

        AiModel aiModel = AiModel.getInstance(evnData.geminiKey(), evnData.gptKey(), config.modelId());

        while (true) {

            List<Tweet> tweets = storage.loadTweets(config.startFromIndex(), config.readLimit());
            storage.upDateStarFrom(config.startFromIndex() + config.readLimit());

            if (tweets.isEmpty()) {
                System.out.println("All done");
                break;
            }

            tweets.forEach(tweet -> {

                if (config.aiModel().equals("Google")) {

                    AiResponse aiResult = aiModel.GeminiCheckTweet(tweet.full_text, config.criterial());
                    boolean delete = true;

                    switch (aiResult) {
                        case KEEP:
                            delete = false;
                        case DELETE:
                            delete = true;
                        case ERROR:

                    }

                    boolean success = storage.writeTweetResultCsv("https://x.com/" + config.username() + "/status/" + tweet.id, delete);

                    if (success) {
                        System.out.println("Tweet with id: " + tweet.id + " analyzed and documented successfully");
                    } else {
                        System.out.println("Tweet with id: " + tweet.id + " analyzed but failed to  document ");
                    }

                } else {
                    //GPT  not implemented
                }
            });


        }


    }
}