package com.example.admin.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nruonan
 * @description
 */
@Configuration
public class RBloomFilterConfig {
    /**
     * 防止用户注册查询数据库布隆过滤器
     */
    @Bean
    public RBloomFilter<String> userRegisterCacheBloomFilter(RedissonClient redissonClient){
        RBloomFilter<String> userRegisterCacheBloomFilter = redissonClient.getBloomFilter(
            "userRegisterCacheBloomFilter");
        userRegisterCacheBloomFilter.tryInit(100000000,0.001);
        return userRegisterCacheBloomFilter;
    }
}
