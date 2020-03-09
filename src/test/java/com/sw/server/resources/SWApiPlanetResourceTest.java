package com.sw.server.resources;

import com.sw.server.api.SWApiPlanetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class SWApiPlanetResourceTest {

    private SWApiPlanetResource resource;

    @Mock
    private SWApiPlanetService swApiPlanetService;

    @BeforeEach
    void setup() {
        initMocks(this);
        resource = new SWApiPlanetResource(swApiPlanetService);
    }

    @Test
    void shouldListPlanets() throws IOException, InterruptedException {
        String response = "any";
        int page = 10;
        when(swApiPlanetService.findPlanetsFromSWApi(page)).thenReturn(response);
        assertEquals(response, resource.listPlanets(page).getEntity().toString());
    }
}