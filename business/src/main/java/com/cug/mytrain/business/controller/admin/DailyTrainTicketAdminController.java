package com.cug.mytrain.business.controller.admin;

import com.cug.mytrain.context.LoginMemberContext;
import com.cug.mytrain.resp.CommonResp;
import com.cug.mytrain.resp.PageResp;
import com.cug.mytrain.business.req.DailyTrainTicketQueryReq;
import com.cug.mytrain.business.req.DailyTrainTicketSaveReq;
import com.cug.mytrain.business.resp.DailyTrainTicketQueryResp;
import com.cug.mytrain.business.service.DailyTrainTicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-ticket")
public class DailyTrainTicketAdminController {

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainTicketSaveReq req) {
        dailyTrainTicketService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainTicketQueryResp>> queryList(@Valid DailyTrainTicketQueryReq req) {
        PageResp<DailyTrainTicketQueryResp> list = dailyTrainTicketService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainTicketService.delete(id);
        return new CommonResp<>();
    }

}
