package com.lwb.redission.server.demo;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

public class BaseDemo {

    protected static RedissonClient redissonClient;

    static {
        Config config = new Config();
        config.useSingleServer()
            .setTimeout(1000000)
//            .setPassword("123456")
            .setAddress("redis://127.0.0.1:6379");
        redissonClient = Redisson.create(config);
    }

}
