package org.example.model.scryfall;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScryFallMtgCard {
    @Data
    public class ScryFallMtgCardPrices {
        private float eur;
        private float eur_foil;
        private float tix;
        private float usd;
        private float usd_etched;
        private float usd_foil;
    }

    private String name;
    private ScryFallMtgCardPrices prices;
    @JsonProperty(value = "oracle_text")
    private String oracleText;
    private String rarity;
    @JsonProperty(value = "collector_number")
    private String collectorNumber;
}
