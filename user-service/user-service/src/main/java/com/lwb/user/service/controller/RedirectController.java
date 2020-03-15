package com.lwb.user.service.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.lwb.enums.MicroServiceConstant.USER_SERVICE_PREFIX;

@Controller
@RequestMapping(USER_SERVICE_PREFIX)
public class RedirectController {

    @GetMapping("/redirect")
    public String redirect() {
        return "redirect:user.html";
    }
}
