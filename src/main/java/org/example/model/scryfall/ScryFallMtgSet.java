package org.example.model.scryfall;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScryFallMtgSet {
    private String code;
    private String name;
    @JsonProperty(value = "card_count")
    private int cardCount;
    @JsonProperty(value = "released_at")
    private Date releaseDate;
}
