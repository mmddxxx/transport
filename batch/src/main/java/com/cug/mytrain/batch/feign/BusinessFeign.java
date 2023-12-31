package com.cug.mytrain.batch.feign;

import com.cug.mytrain.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

//fallback就是熔断的降级
@FeignClient(value = "business", fallback = BusinessFeignFallback.class)

//@FeignClient(name = "business", url = "http://127.0.0.1:8082/business")
public interface BusinessFeign {

    @GetMapping("/hello")
    String hello();

    @GetMapping("/admin/daily-train/gen-daily/{date}")
    CommonResp<Object> genDaily(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
}
