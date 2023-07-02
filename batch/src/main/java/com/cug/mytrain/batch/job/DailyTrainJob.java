package com.cug.mytrain.batch.job;

import cn.hutool.core.util.RandomUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

//quartz默认的线程池大小是10，多了就排队
@DisallowConcurrentExecution
public class DailyTrainJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));
        LOG.info("生成每日车次数据开始");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println("Test Job111111");
    }
}
