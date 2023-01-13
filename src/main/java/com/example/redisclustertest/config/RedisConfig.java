package com.example.redisclustertest.config;

import io.lettuce.core.ReadFrom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

//    @Value("${spring.redis.host}")
//    private String host;

//    @Value("${spring.redis.port}")
//    private int port;

//    @Value("${spring.redis.database}")
//    private int dbIndex;

    @Value("${spring.redis.password}")
    private String password;

    private final RedisClusterConfigProperties clusterProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)// 복제본 노드에서 읽지만 사용할 수없는 경우 마스터에서 읽습니다.
                .build();
        // 모든 클러스터(master, slave) 정보를 적는다. (해당 서버중 접속되는 서버에서 cluster nodes 명령어를 통해 모든 클러스터 정보를 읽어오기에 다운 됐을 경우를 대비하여 모든 노드 정보를 적어두는편이 좋다.)
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterProperties.getNodes());
        redisClusterConfiguration.setMaxRedirects(3);

        redisClusterConfiguration.setPassword(password);

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);

        // Redis DB 설정  -  클러스터모드는 인덱스를 못나눔
//        lettuceConnectionFactory.setDatabase(dbIndex);
        return lettuceConnectionFactory;
    }


    @Bean
    public RedisTemplate<String ,String> redisTemplate(){
        RedisTemplate<String ,String > redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String ,Long> redisLongTemplate(){
        RedisTemplate<String ,Long > redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer( new GenericToStringSerializer<>( Long.class ));
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }


}