package com.lwb.user.service.controller;

import com.lwb.entity.User;
import com.lwb.user.service.api.UserApi;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuweibo
 * @date 2019/10/9
 */
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController implements UserApi {


    @Override
    public User getUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("sam");
        return user;
    }

    @Override
    public User getUser(User user, User user2) {
        User user1 = new User();
        user1.setName("tom");
        return user1;
    }

}
