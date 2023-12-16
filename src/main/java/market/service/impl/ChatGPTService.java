package market.service.impl;

import market.service.AIService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ChatGPTService implements AIService {
    private static final String URL_STRING = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";
    private static final String MODEL = "gpt-3.5-turbo";
    @Override
    public String getMessage(String query) {
        try {
            URL obj = new URL(URL_STRING);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + MODEL + "\", \"messages\": [{\"role\": \"user\", " +
                          "\"content\": \"" + query + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);
    }


}
