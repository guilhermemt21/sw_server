package com.sw.server.api;

import com.sw.server.http.HttpResponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


class SWApiPlanetServiceTest {

    private SWApiPlanetService service;

    @Mock
    private HttpResponseService httpResponseService;

    @BeforeEach
    void setup() {
        initMocks(this);
        service = new SWApiPlanetService(httpResponseService);
    }

    @Test
    void shouldFindPlanetsFromSWApiWhenNoPageIsProvided() throws IOException, InterruptedException {
        service.findPlanetsFromSWApi(null);
        verify(httpResponseService).httpGet("https://swapi.co/api/planets/?page=1");
    }

    @Test
    void shouldFindPlanetsFromSWApiWhenPageIsProvided() throws IOException, InterruptedException {
        service.findPlanetsFromSWApi(2);
        verify(httpResponseService).httpGet("https://swapi.co/api/planets/?page=2");
    }
}