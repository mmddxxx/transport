package com.cug.mytrain.business.controller;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {

    private static final Logger LOG = LoggerFactory.getLogger(RedisController.class);

    //名字必须对应上类名，不然会报错,可以用autowired会优先根据类名查找
    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/redis/set/{key}/{value}")
    public String set(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);

        //根据这个输出的ip找到原因了，连的是本地的redis，不知道为什么properties的配置没用
//        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
//        LettuceConnectionFactory lettuceConnectionFactory = ((LettuceConnectionFactory)connectionFactory);
//        String hostName = lettuceConnectionFactory.getHostName();
//        stringRedisTemplate.opsForValue().set("123456", "123456", 3600, TimeUnit.SECONDS);
        LOG.info("key: {}, value: {}", key, value);
//        LOG.info("hostname: {}", hostName);
        return "success";
    }

    @RequestMapping("/redis/get/{key}")
    public Object get(@PathVariable String key) {
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key: {}, value: {}", key, object);
        return object;
    }
}
