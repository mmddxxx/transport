package com.cug.controller;

import com.cug.req.MemberRegisterReq;
import com.cug.req.MemberSendCodeReq;
import com.cug.resp.CommonResp;
import com.cug.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResp<Long> registerr(@Valid MemberRegisterReq req) {  //加上这个注解才能让valid生效
        long register = memberService.register(req);
        return new CommonResp<>(register);
    }

    @PostMapping("/sendCode")
    public CommonResp<Long> sendCode(@Valid MemberSendCodeReq req) {  //加上这个注解才能让valid生效
        memberService.sendCode(req);
        return new CommonResp<>();
    }
}
