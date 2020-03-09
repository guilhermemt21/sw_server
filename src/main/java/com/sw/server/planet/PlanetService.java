package com.sw.server.planet;

import com.google.inject.Inject;
import com.sw.server.cache.SWApiPlanetCacheService;
import com.sw.server.command.PlanetCommand;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.List;

public class PlanetService {

    private PlanetDAO planetDAO;
    private SWApiPlanetCacheService swApiPlanetCacheService;

    @Inject
    public PlanetService(PlanetDAO planetDAO, SWApiPlanetCacheService swApiPlanetCacheService) {
        this.planetDAO = planetDAO;
        this.swApiPlanetCacheService = swApiPlanetCacheService;
    }

    @Transaction
    public void insertPlanet(PlanetCommand planet) {
        Integer apparitionCount = swApiPlanetCacheService.retrievePlanetApparitionsByName(planet.getName());
        planetDAO.insertPlanet(planet, apparitionCount);
    }

    public List<Planet> findAll(){
        return planetDAO.findAllPlanets();
    }

    public Planet findByName(String name){
        return planetDAO.findPlanet(name);
    }

    public Planet findById(Long id){
        return planetDAO.findPlanet(id);
    }

    public void deletePlanet(Long id){
        planetDAO.deletePlanet(id);
    }

}
