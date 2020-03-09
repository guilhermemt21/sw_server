package com.sw.server.cache;

import com.sw.server.http.HttpResponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class SWApiPlanetCacheServiceTest {

    private SWApiPlanetCacheService service;

    @Mock
    private RedisCacheService redisCacheService;
    @Mock
    private HttpResponseService httpResponseService;

    @BeforeEach
    void setup() {
        initMocks(this);
        service = new SWApiPlanetCacheService(redisCacheService, httpResponseService);
    }

    @Test
    void shouldRetrievePlanetApparitionsFromRedisWhenItIsPersisted() {
        String planetName = "Dagobah";
        when(redisCacheService.getKey(planetName.toLowerCase())).thenReturn("10");
        assertEquals(10, service.retrievePlanetApparitionsByName(planetName));
    }

    @Test
    void shouldRetrievePlanetApparitionsFromApiWheItIsNotPersisted() throws IOException, InterruptedException {
        String planetName = "Dagobah";
        when(redisCacheService.getKey(planetName.toLowerCase())).thenReturn(null);
        String responseBody = "{\"count\": 1, \"results\": [{\"films\": [{\"film\": 1}, {\"film\": 2}, {\"film\": 3}]}]}";
        when(httpResponseService.httpGet(any())).thenReturn(responseBody);
        assertEquals(3, service.retrievePlanetApparitionsByName(planetName));
    }


    @Test
    void shouldAlwaysReturnZeroAparitionsIfThereAreMultiplePlanetResults() throws IOException, InterruptedException {
        String planetName = "Dagobah";
        when(redisCacheService.getKey(planetName.toLowerCase())).thenReturn(null);
        String responseBody = "{\"count\": 10}";
        when(httpResponseService.httpGet(any())).thenReturn(responseBody);
        assertEquals(0, service.retrievePlanetApparitionsByName(planetName));
    }

}