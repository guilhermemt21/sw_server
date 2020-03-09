package com.sw.server.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.sw.server.http.HttpResponseService;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.Optional;

@Log4j
public class SWApiPlanetCacheService {

    private final RedisCacheService redisCacheService;
    private final HttpResponseService httpResponseService;

    @Inject
    public SWApiPlanetCacheService(RedisCacheService redisCacheService, HttpResponseService httpResponseService) {
        this.redisCacheService = redisCacheService;
        this.httpResponseService = httpResponseService;
    }

    public Integer retrievePlanetApparitionsByName(String planetName) {
        Optional<String> planetApparitions = Optional.ofNullable(redisCacheService.getKey(planetName.toLowerCase()));
        if (planetApparitions.isPresent()) {
            return Integer.parseInt(planetApparitions.get());
        } else {
            String swApiPlanetUrl = "https://swapi.co/api/planets/?search=" + planetName;
            try {
                String responseBody = httpResponseService.httpGet(swApiPlanetUrl);
                Integer apparitionCount = readApparitionCountFromResponse(responseBody);
                redisCacheService.setKey(planetName.toLowerCase(), apparitionCount.toString());

                return apparitionCount;
            } catch (IOException | InterruptedException e) {
                log.info("It was not possible to load this planet from the official API: " + e.getMessage());
                return 0;
            }
        }
    }

    private Integer readApparitionCountFromResponse(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responseBody);
        int planetsCount = node.get("count").asInt();
        if (planetsCount == 1) {
            return node.findValue("results").findValue("films").size();
        }
        return 0;
    }

}
