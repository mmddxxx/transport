package com.cug.mytrain.business.controller;

import com.cug.mytrain.business.req.DailyTrainTicketQueryReq;
import com.cug.mytrain.business.resp.DailyTrainTicketQueryResp;
import com.cug.mytrain.business.service.DailyTrainTicketService;
import com.cug.mytrain.resp.CommonResp;
import com.cug.mytrain.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily-train-ticket")
public class DailyTrainTicketController {

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq req) {
        PageResp<DailyTrainTicketQueryResp> list = dailyTrainTicketService.queryList(req);
        return new CommonResp<>(list);
    }


}
