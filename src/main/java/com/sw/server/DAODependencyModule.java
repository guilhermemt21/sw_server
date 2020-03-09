package com.sw.server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.sw.server.planet.PlanetDAO;
import io.lettuce.core.api.StatefulRedisConnection;
import org.jdbi.v3.core.Jdbi;

public class DAODependencyModule extends AbstractModule {

    private final Jdbi applicationJdbi;
    private final StatefulRedisConnection<String, String> connection;

    public DAODependencyModule(Jdbi applicationJdbi, StatefulRedisConnection<String, String> connection) {
        this.applicationJdbi = applicationJdbi;
        this.connection = connection;
    }

    @Override
    protected void configure() {
    }

    @Singleton
    @Provides
    PlanetDAO planetDAO() {
        return applicationJdbi.onDemand(PlanetDAO.class);
    }

    @Singleton
    @Provides
    StatefulRedisConnection<String, String> statefulRedisConnection() {return connection;}

}
