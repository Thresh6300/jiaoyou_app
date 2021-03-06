package com.qiqi.jiaoyou_app.server.controller;

import com.qiqi.jiaoyou_app.server.request.user.UserCreateRequest;
import com.qiqi.jiaoyou_app.server.response.user.UserCreateResponse;
import com.qiqi.jiaoyou_app.server.service.impl.NimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NimUserController {

    @Autowired
    private NimUserService nimUserService;

    @RequestMapping("/create")
    public UserCreateResponse aaa(UserCreateRequest request){
        return nimUserService.create(request);
    }
}
