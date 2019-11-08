package com.lwb.user.service.api;

import com.lwb.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.lwb.enums.MicroServiceConstant.USER_SERVICE_PREFIX;

/**
 * @author liuweibo
 * @date 2019/11/8
 */
@RequestMapping(USER_SERVICE_PREFIX)
public interface UserApi {

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return {@link User}
     */
    @GetMapping("/user")
    User get(Long id);
}
