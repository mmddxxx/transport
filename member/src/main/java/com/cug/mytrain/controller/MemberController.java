package com.cug.mytrain.controller;

import com.cug.mytrain.req.MemberLoginReq;
import com.cug.mytrain.req.MemberRegisterReq;
import com.cug.mytrain.resp.MemberLoginResp;
import com.cug.mytrain.service.MemberService;
import com.cug.mytrain.req.MemberSendCodeReq;
import com.cug.mytrain.resp.CommonResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Integer> count() {
        int count = memberService.count();
        return new CommonResp<>(count);
    }

    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq req) {  //加上这个注解才能让valid生效
        long register = memberService.register(req);
        return new CommonResp<>(register);
    }

    @PostMapping("/send-code")  //中间加"-"是url规范
    public CommonResp<?> sendCode(@Valid  @RequestBody MemberSendCodeReq req) {  //加上这个注解才能让valid生效
        memberService.sendCode(req);
        return new CommonResp<>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid @RequestBody MemberLoginReq req) {  //加上这个注解才能让valid生效
        MemberLoginResp resp = memberService.login(req);
        return new CommonResp<>(resp);
    }
}
