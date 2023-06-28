package com.cug.mytrain.controller;

import com.cug.mytrain.req.PassengerSaveReq;
import com.cug.mytrain.resp.CommonResp;
import com.cug.mytrain.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;



    @PostMapping("/save")
    public CommonResp<?> register(@Valid @RequestBody PassengerSaveReq req) {  //加上这个注解才能让valid生效
        passengerService.save(req);
        return new CommonResp<>();
    }

}
