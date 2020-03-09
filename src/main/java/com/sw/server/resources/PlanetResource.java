package com.sw.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.sw.server.command.PlanetCommand;
import com.sw.server.planet.PlanetService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/planets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlanetResource {

    private final PlanetService planetService;

    @Inject
    public PlanetResource(PlanetService planetService) {
        this.planetService = planetService;
    }

    @POST
    @Timed
    public Response createPlanet(PlanetCommand planet) {
        planetService.insertPlanet(planet);
        return Response.ok().build();
    }

    @GET
    @Timed
    public Response listPlanets(@QueryParam("name") String name, @QueryParam("id") Long id) {
        if (id != null) {
            return Response.ok(planetService.findById(id)).build();
        }
        if (name != null) {
            return Response.ok(planetService.findByName(name)).build();
        }
        return Response.ok(planetService.findAll()).build();
    }


    @DELETE
    @Path("/{id}")
    @Timed
    public Response deletePlanet(@PathParam("id") Long id) {
        planetService.deletePlanet(id);
        return Response.ok().build();
    }

}
