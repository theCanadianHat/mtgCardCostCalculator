package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.example.components.AppFrame;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        AppFrame frame = new AppFrame();

        String url = "https://api.scryfall.com/sets";
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(url);

            Map<String, Object> response = client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), Map.class));

            MapUtils.debugPrint(System.out, "sets map", response);
            frame.addSetOptions(response);
        }
        frame.removeLoadingSets();
    }
}