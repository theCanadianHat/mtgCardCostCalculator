package org.example.service.scryfall;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.example.model.scryfall.ScryFallMtgSetResponse;

import java.io.IOException;

public class ScyFallApi {
    private ObjectMapper mapper;

    public ScyFallApi() {
        mapper = new ObjectMapper();
    }

    public ScryFallMtgSetResponse getSets() {
        String url = "https://api.scryfall.com/sets";
        ObjectMapper mapper = new ObjectMapper();
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(url);

            ScryFallMtgSetResponse response = client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), ScryFallMtgSetResponse.class));

            System.out.println(response.getData());
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
