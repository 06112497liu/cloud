package com.lwb.user.service.controller;

import com.lwb.entity.User;
import com.lwb.user.service.api.UserApi;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuweibo
 * @date 2019/10/9
 */
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController implements UserApi {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public User getUser(Long id) {
        System.out.println("尼玛");
        User user = new User();
        user.setId(id);
        user.setName("sam");
        return user;
    }

    @Override
    public User getUser(User user) {
        User user1 = new User();
        user1.setName("tom");
        return user1;
    }

    @GetMapping("/lockUser")
    public void lockTest() {
        boolean success = this.redissonClient.getLock("USER_LOCK").tryLock();
        System.out.println(success);
    }

}
