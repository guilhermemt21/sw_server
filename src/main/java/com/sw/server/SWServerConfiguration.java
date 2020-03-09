package com.sw.server;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.redis.RedisClientFactory;
import lombok.Getter;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class SWServerConfiguration extends Configuration {

    @Getter
    private DataSourceFactory applicationDataSourceFactory = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty("redis")
    @Getter
    private RedisClientFactory<String, String> redisClientFactory;
}
