package com.lwb.redission.server.demo;

import com.lwb.entity.User;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Controller;

public class ObjectBucketDemo extends BaseDemo{

    /**
     * 通用对象桶
     */
    @Test
    public void ObjectBucket() {
        RBucket<User> objectBucket = redissonClient.getBucket("OBJECT_BUCKET");
        User user2 = new User();
        user2.setId(2L);
        user2.setName("小明");

        User user1 = new User();
        user1.setId(1L);
        user1.setName("小明");

        objectBucket.compareAndSet(user1, user2);

        System.out.println(objectBucket.get());
        System.out.println();
    }

}
