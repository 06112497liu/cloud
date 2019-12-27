package com.lwb.redission.server.demo;

import cn.hutool.core.util.RandomUtil;
import org.junit.Test;
import org.redisson.api.RKeys;
import org.redisson.api.RList;

import java.util.concurrent.TimeUnit;

/**
 * @author liuweibo
 * @date 2019/12/23
 */
public class MaxMemoryDemo extends BaseDemo {


    /**
     * 前提：
     * 配置了maxmemory和maxmemory-policy策略为默认的noeviction（不清理数据）
     * 当redis达到最大只用内存时，下面的获取命令能正常执行，写入等命令直接报oom错误。
     */
    @Test
    public void redisOOM() {
        RKeys keys = redissonClient.getKeys();
        keys.getKeys().forEach(System.out::println);
        RList<String> oomList = redissonClient.getList("OOM_LIST");

        for (int i = 0; i < 1024; i++) {
            oomList.set(i, "item_" + i);
        }

        System.out.println(oomList);
    }

    /**
     */
    @Test
    public void redisMaxMemoryPolicy() {
        int investigationTypes = 1;
        for (int i = 0; i < 1024000; i++) {
            RList<Integer> oomList = redissonClient.getList("ITEM_" + i);
//            oomList.expire(RandomUtil.randomLong(500, 1000), TimeUnit.SECONDS);
            oomList.add(i);
        }
    }

}
