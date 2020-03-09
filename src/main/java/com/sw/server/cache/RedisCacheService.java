package com.sw.server.cache;

import com.google.inject.Inject;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisCacheService {

    private final RedisCommands<String, String> redisCommands;

    @Inject
    public RedisCacheService(StatefulRedisConnection<String, String> connection) {
        this.redisCommands = connection.sync();
    }

    public String getKey(String key) {
        return redisCommands.get(key);
    }

    public void setKey(String key, String value) {
        redisCommands.set(key, value);
    }

}
