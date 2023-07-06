package com.cug.mytrain.business.controller;

import com.cug.mytrain.business.req.TrainQueryReq;
import com.cug.mytrain.business.resp.TrainQueryResp;
import com.cug.mytrain.business.service.TrainService;
import com.cug.mytrain.resp.CommonResp;
import com.cug.mytrain.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train")
public class TrainController {

    @Resource
    private TrainService trainService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainQueryResp>> queryList(@Valid TrainQueryReq req) {
        PageResp<TrainQueryResp> list = trainService.queryList(req);
        return new CommonResp<>(list);
    }

}
