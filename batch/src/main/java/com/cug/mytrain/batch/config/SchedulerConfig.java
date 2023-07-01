package com.cug.mytrain.batch.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SchedulerConfig {

    @Resource
    private MyJobFactory myJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("dataSource") DataSource dataSource) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJobFactory(myJobFactory);  //关联jobFactory
        factory.setStartupDelay(2);  //springboot程序启动号2秒后开始执行quartz
        return factory;
    }
}
