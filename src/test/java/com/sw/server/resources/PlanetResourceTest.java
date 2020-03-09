package com.sw.server.resources;

import com.sw.server.command.PlanetCommand;
import com.sw.server.planet.Planet;
import com.sw.server.planet.PlanetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

class PlanetResourceTest {

    private PlanetResource planetResource;

    @Mock
    private PlanetService planetService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        planetResource = new PlanetResource(planetService);
    }

    @Test
    void shouldDelegateToServiceOnCreatePlanet() {
        PlanetCommand planet = mock(PlanetCommand.class);
        Response response = planetResource.createPlanet(planet);
        verify(planetService).insertPlanet(planet);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void shouldFindAllPlanetsWhenArgsAreNull() {
        Planet planet = mock(Planet.class);
        when(planetService.findAll()).thenReturn(singletonList(planet));

        Response response = planetResource.listPlanets(null, null);
        Assertions.assertEquals(singletonList(planet), response.getEntity());
    }


    @Test
    void shouldFindPlanetByNameWhenHasNameArg() {
        Planet expectedPlanet = mock(Planet.class);
        Planet wrongPlanet = mock(Planet.class);
        when(planetService.findById(1L)).thenReturn(wrongPlanet);
        when(planetService.findByName("name")).thenReturn(expectedPlanet);
        when(planetService.findAll()).thenReturn(singletonList(wrongPlanet));

        Response response = planetResource.listPlanets("name", null);
        Assertions.assertEquals(expectedPlanet, response.getEntity());
    }

    @Test
    void shouldFindPlanetByNameWhenHasIdArg() {
        Planet expectedPlanet = mock(Planet.class);
        Planet wrongPlanet = mock(Planet.class);
        when(planetService.findById(1L)).thenReturn(expectedPlanet);
        when(planetService.findByName("name")).thenReturn(wrongPlanet);
        when(planetService.findAll()).thenReturn(singletonList(wrongPlanet));

        Response response = planetResource.listPlanets(null, 1L);
        Assertions.assertEquals(expectedPlanet, response.getEntity());
    }

    @Test
    void shouldDeletePlanetById() {
        long id = 1L;
        Response response = planetResource.deletePlanet(id);
        verify(planetService).deletePlanet(id);
        Assertions.assertEquals(200, response.getStatus());
    }

}