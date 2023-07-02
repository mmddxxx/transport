package com.cug.mytrain.batch.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.cug.mytrain.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

//quartz默认的线程池大小是10，多了就排队
@DisallowConcurrentExecution
public class DailyTrainJob1 implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainJob1.class);

    @Resource
    BusinessFeign businessFeign;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));
//        LOG.info("生成15天后的车次数据开始");
        Date date = new Date();
        DateTime dateTime = DateUtil.offsetDay(date, 15);//注意边界值
        Date offsetDate = dateTime.toJdkDate();
//        CommonResp<Object> commonResp = businessFeign.genDaily(offsetDate);
//        LOG.info("生成15天后的车次数据结束，结果：{}", commonResp);

//        System.out.println("Test Job111111");
    }
}
