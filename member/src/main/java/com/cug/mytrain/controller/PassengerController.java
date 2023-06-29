package com.cug.mytrain.controller;

import com.cug.mytrain.context.LoginMemberContext;
import com.cug.mytrain.req.PassengerQueryReq;
import com.cug.mytrain.req.PassengerSaveReq;
import com.cug.mytrain.resp.CommonResp;
import com.cug.mytrain.resp.PageResp;
import com.cug.mytrain.resp.PassengerQueryResp;
import com.cug.mytrain.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    //查询使用get请求，入参直接拼接在url上
    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req) {  //加上这个注解才能让valid生效
        req.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerQueryResp> passengerList = passengerService.queryList(req);
        return new CommonResp<>(passengerList);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<?> delete(@PathVariable Long id) {  //加上这个注解才能让valid生效
        passengerService.delete(id);
        return new CommonResp<>();
    }
}
