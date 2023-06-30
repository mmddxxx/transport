package com.cug.mytrain.batch.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * springboot自带的定时任务适合单体应用，不适合集群，可以增加分布式锁，解决集群问题
 * 但是不能手动暂停定时任务，无法实时更改任务的状态和策略
 */
@Component
@EnableScheduling
public class SpringBootTestJob {

    //每秒除以5余数是0时就触发以下函数
    @Scheduled(cron = "0/5 * * * * ?")
    public void test() {
        System.out.println("成功");
    }
}

