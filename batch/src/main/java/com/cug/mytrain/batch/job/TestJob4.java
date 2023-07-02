package com.cug.mytrain.batch.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.cug.mytrain.batch.feign.BusinessFeign;
import com.cug.mytrain.resp.CommonResp;
import jakarta.annotation.Resource;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@DisallowConcurrentExecution
public class TestJob4 implements Job {

    @Resource
    BusinessFeign businessFeign;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Date date = new Date();
        DateTime dateTime = DateUtil.offsetDay(date, 15);//注意边界值
        Date offsetDate = dateTime.toJdkDate();
        CommonResp<Object> commonResp = businessFeign.genDaily(offsetDate);
        System.out.println("Test Job111111");
    }
}
