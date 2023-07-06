package com.cug.mytrain.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());  //序列化后也是json，但是不需要传类对象
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));  //json，需要传一个类对象
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());  //redis默认的是jdk的序列化,二进制
        //hash类型的key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash类型的value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

//    /**
//     * lua应该部署在服务器上，不然会占用网络通信
//     * @return
//     */
//    @Bean
//    public DefaultRedisScript<Long> script() {
//        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
//        //lock.lua脚本位置，要和application.yml同一目录
//        script.setLocation(new ClassPathResource("stock.lua"));
//        script.setResultType(Long.class);
//        return script;
//    }
}
