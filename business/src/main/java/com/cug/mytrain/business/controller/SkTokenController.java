package com.cug.mytrain.business.controller;

import com.cug.mytrain.context.LoginMemberContext;
import com.cug.mytrain.resp.CommonResp;
import com.cug.mytrain.resp.PageResp;
import com.cug.mytrain.business.req.SkTokenQueryReq;
import com.cug.mytrain.business.req.SkTokenSaveReq;
import com.cug.mytrain.business.resp.SkTokenQueryResp;
import com.cug.mytrain.business.service.SkTokenService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sk-token")
public class SkTokenController {

    @Resource
    private SkTokenService skTokenService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody SkTokenSaveReq req) {
        skTokenService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<SkTokenQueryResp>> queryList(@Valid SkTokenQueryReq req) {
        PageResp<SkTokenQueryResp> list = skTokenService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        skTokenService.delete(id);
        return new CommonResp<>();
    }

}
