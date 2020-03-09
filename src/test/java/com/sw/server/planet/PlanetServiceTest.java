package com.sw.server.planet;

import com.sw.server.cache.SWApiPlanetCacheService;
import com.sw.server.command.PlanetCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


class PlanetServiceTest {

    private PlanetService service;

    @Mock
    private PlanetDAO planetDAO;
    @Mock
    private SWApiPlanetCacheService swApiPlanetCacheService;

    @BeforeEach
    void setup() {
        initMocks(this);
        service = new PlanetService(planetDAO, swApiPlanetCacheService);
    }

    @Test
    void shouldInsertPlanet() {
        PlanetCommand planet = mock(PlanetCommand.class);
        int apparitions = 5;
        when(swApiPlanetCacheService.retrievePlanetApparitionsByName(planet.getName())).thenReturn(apparitions);
        service.insertPlanet(planet);
        verify(planetDAO).insertPlanet(planet, apparitions);
    }

    @Test
    void shouldFindAllPlanets() {
        Planet planet = mock(Planet.class);
        when(planetDAO.findAllPlanets()).thenReturn(singletonList(planet));
        assertEquals(singletonList(planet), service.findAll());
    }

    @Test
    void shouldFindPlanetById() {
        Planet planet = mock(Planet.class);
        when(planetDAO.findPlanet(1L)).thenReturn(planet);
        assertEquals(planet, service.findById(1L));
    }

    @Test
    void shouldFindPlanetByName() {
        Planet planet = mock(Planet.class);
        when(planetDAO.findPlanet("name")).thenReturn(planet);
        assertEquals(planet, service.findByName("name"));
    }

    @Test
    void shouldDeletePlanet() {
        long id = 1L;
        service.deletePlanet(id);
        verify(planetDAO).deletePlanet(id);
    }

}