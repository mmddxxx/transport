package com.cug.mytrain.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * Hello world!
 *
 */
@ComponentScan("com.cug")  //这个很关键，有的component可能会识别不到，加上这个强制全文件夹识别，
@SpringBootApplication
@EnableFeignClients("com.cug.mytrain.business.feign")
@EnableCaching
public class BusinessApplication {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BusinessApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！！");
        LOG.info("地址：\thttp://127.0.0.1:{}{}/hello", env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));
    }
}
