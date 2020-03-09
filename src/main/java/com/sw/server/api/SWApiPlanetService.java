package com.sw.server.api;

import com.google.inject.Inject;
import com.sw.server.http.HttpResponseService;

import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.util.Optional;

public class SWApiPlanetService {

    private HttpResponseService httpResponseService;

    @Inject
    public SWApiPlanetService(HttpResponseService httpResponseService) {
        this.httpResponseService = httpResponseService;
    }

    public String findPlanetsFromSWApi(@QueryParam("page") Integer page) throws IOException, InterruptedException {
        String swApiPlanetsUrl = "https://swapi.co/api/planets/?page=" + Optional.ofNullable(page).orElse(1);
        return httpResponseService.httpGet(swApiPlanetsUrl);
    }

}
