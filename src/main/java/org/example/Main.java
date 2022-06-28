package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.example.components.AppFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> result = new ArrayList<>();
        try (InputStreamReader streamReader =
                     new InputStreamReader(Main.class.getResourceAsStream("/input.txt"), StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);

        String url = "https://api.scryfall.com/cards/named?fuzzy=aust+com";
        final ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(url);

            Map<String, Object> response = client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), Map.class));

            MapUtils.debugPrint(System.out, "search map", response);
        }
        AppFrame frame = new AppFrame();

        url = "https://api.scryfall.com/sets";
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