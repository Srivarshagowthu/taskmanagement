package com.sky.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Slf4j
@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);
        if(redisson.getKeys()!=null){
            log.info(" ✅  Redisson connected successfully to the server ");
        }
        else{
            log.info("Not connected to the server , sorry sir");
        }
return redisson;
    }

}
