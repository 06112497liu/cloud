package com.lwb.statistics.service.controller;

import com.lwb.statistics.service.microservice.UserApiService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lwb.enums.MicroServiceConstant.STATISTICS_SERVICE_NAME;
import static com.lwb.enums.MicroServiceConstant.STATISTICS_SERVICE_PREFIX;

/**
 * @author liuweibo
 * @date 2019/10/9
 */
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping(STATISTICS_SERVICE_PREFIX)
public class StatisticsController {

    @Autowired
    UserApiService userApi;

    @GetMapping("/user")
    public Object statistics(Long id) {
        return this.userApi.get(id);
    }

}
