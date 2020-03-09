package com.sw.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sw.server.resources.PlanetResource;
import com.sw.server.resources.SWApiPlanetResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.redis.RedisClientBundle;
import io.dropwizard.redis.RedisClientFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.lettuce.core.api.StatefulRedisConnection;
import org.jdbi.v3.core.Jdbi;

public class SWServerApplication extends Application<SWServerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new SWServerApplication().run(args);
    }

    @Override
    public String getName() {
        return "SW Server";
    }

    @Override
    public void initialize(final Bootstrap<SWServerConfiguration> bootstrap) {
        bootstrap.addBundle(redis);
        bootstrap.addBundle(new MigrationsBundle<SWServerConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(SWServerConfiguration configuration) {
                return configuration.getApplicationDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final SWServerConfiguration configuration, final Environment environment) {
        JdbiFactory factory = new JdbiFactory();
        Jdbi applicationJdbi = factory.build(environment, configuration.getApplicationDataSourceFactory(), "applicationDatabase");

        Injector injector = Guice.createInjector(new DAODependencyModule(applicationJdbi, redis.getConnection()));
        injector.injectMembers(applicationJdbi);

        registerResources(injector, environment);
    }


    private void registerResources(Injector injector, Environment environment) {
        environment.jersey().register(injector.getInstance(PlanetResource.class));
        environment.jersey().register(injector.getInstance(SWApiPlanetResource.class));
    }

    private final RedisClientBundle<String, String, SWServerConfiguration> redis = new RedisClientBundle<String, String, SWServerConfiguration>() {
        @Override
        public RedisClientFactory<String, String> getRedisClientFactory(SWServerConfiguration configuration) {
            return configuration.getRedisClientFactory();
        }
    };
}
