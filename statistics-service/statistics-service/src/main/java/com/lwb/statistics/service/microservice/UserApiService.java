package com.lwb.statistics.service.microservice;

import com.lwb.constants.SystemConstant;
import com.lwb.user.service.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liuweibo
 * @date 2019/10/9
 */
@FeignClient(SystemConstant.SERVER_NAME_USER_SERVICE)
public interface UserApiService extends UserApi {

}
