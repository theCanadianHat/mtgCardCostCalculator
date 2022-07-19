package org.example.model.scryfall;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScryFallMtgCardResponse {
    List<ScryFallMtgCard> data;
}
