package com.lwb.statistics.service.controller;

import com.lwb.entity.User;
import com.lwb.statistics.service.microservice.UserApiService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.lwb.enums.MicroServiceConstant.STATISTICS_SERVICE_PREFIX;

/**
 * @author liuweibo
 * @date 2019/10/9
 */
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping(STATISTICS_SERVICE_PREFIX)
public class StatisticsController {

    @Resource
    UserApiService userApi;

    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/user")
    public Object statistics(Long id) {
        return this.userApi.getUser(id);
    }

    @PostMapping("/user/info")
    public Object statisticsInfo(User user) {
        return this.userApi.getUser(user);
    }

    @GetMapping("/lockUser")
    public void lockUser() {
        boolean success = this.redissonClient.getLock("USER_LOCK").tryLock();
        System.out.println(success);
    }

}
