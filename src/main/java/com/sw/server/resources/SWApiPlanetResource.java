package com.sw.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.sw.server.api.SWApiPlanetService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.http.HttpResponse;

@Path("/sw_api/planets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SWApiPlanetResource {

    private SWApiPlanetService swApiPlanetService;

    @Inject
    public SWApiPlanetResource(SWApiPlanetService swApiPlanetService) {
        this.swApiPlanetService = swApiPlanetService;
    }

    @GET
    @Timed
    public Response listPlanets(@QueryParam("page") Integer page) throws IOException, InterruptedException {
        String responseBody = swApiPlanetService.findPlanetsFromSWApi(page);
        return Response.ok(responseBody).build();
    }

}
