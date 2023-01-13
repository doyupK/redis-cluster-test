package com.example.redisclustertest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Getter
@Setter
public class RedisClusterConfigProperties {
    List<String> nodes;

}
