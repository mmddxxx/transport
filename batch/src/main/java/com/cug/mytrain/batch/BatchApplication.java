package com.cug.mytrain.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * Hello world!
 */
@SpringBootApplication
@ComponentScan("com.cug")  //这个很关键，有的component可能会识别不到，加上这个强制全文件夹识别，

public class BatchApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BatchApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BatchApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！！");
        LOG.info("地址：\thttp://127.0.0.1:{}{}/hello", env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));
    }
}
