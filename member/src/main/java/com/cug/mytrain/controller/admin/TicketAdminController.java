package com.cug.mytrain.controller.admin;

import com.cug.mytrain.req.TicketQueryReq;
import com.cug.mytrain.resp.CommonResp;
import com.cug.mytrain.resp.PageResp;
import com.cug.mytrain.resp.TicketQueryResp;
import com.cug.mytrain.service.TicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

    @Resource
    private TicketService ticketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> queryList(@Valid TicketQueryReq req) {
        PageResp<TicketQueryResp> list = ticketService.queryList(req);
        return new CommonResp<>(list);
    }

}
