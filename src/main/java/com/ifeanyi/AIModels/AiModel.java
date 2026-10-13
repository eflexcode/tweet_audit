package com.ifeanyi.AIModels;

import com.google.genai.Client;
import com.google.genai.ResponseStream;
import com.google.genai.types.ClientOptions;
import com.google.genai.types.GenerateContentResponse;
import com.ifeanyi.AIModels.model.AiResponse;

public class AiModel {

    private AiModel() {

    }

    private static AiModel instance;
    private static String geminiKey;
    private static String openKey;
    private static String modelId;

    public static AiModel getInstance(String geminiKey, String openKey, String modelId) {

        AiModel.geminiKey = geminiKey;
        AiModel.openKey = openKey;
        AiModel.modelId = modelId;

        if (instance == null) {
            instance = new AiModel();
            return instance;
        } else {
            return instance;
        }

    }

    private Client client = Client.builder()
            .apiKey(geminiKey)
            .clientOptions(ClientOptions.builder().maxConnections(64).maxConnectionsPerHost(16).build())
            .build();

    public AiResponse GeminiCheckTweet(String fullText, String criterial) {

        String prompt = fullText + "\n from the above text check if it contains or suggest the follow " + criterial + " if it does reply KEEP else reply DELETE";

        String reply = null;

        try (ResponseStream<GenerateContentResponse> responseStream = client.models.generateContentStream(modelId, prompt, null)) {
            for (GenerateContentResponse chunk : responseStream) {
                reply = chunk.text();
            }
        } catch (Exception e) {
            System.err.println("Something went wrong: " + e.getMessage());
        }

        if (reply != null) {

            if (reply.equals("KEEP")){
                return AiResponse.KEEP;
            }else {
                return AiResponse.DELETE;

            }

        } else {
            return AiResponse.ERROR;
        }


    }

}
