package com.lwb.redission.server.demo;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

public class BaseDemo {

    protected static RedissonClient redissonClient = Redisson.create();

}
