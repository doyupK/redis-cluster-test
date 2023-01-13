package com.example.redisclustertest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String ,String> redisTemplate;
    private final RedisTemplate<String ,Long> redisLongTemplate;


    //Long 타입 key, value set
    public void setValues(String key, Long count) {
        ValueOperations<String, Long> values = redisLongTemplate.opsForValue();
        values.set(key, count);
    }

    //String 타입 key,value+expire time Set
    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    //Long 타입 value Get
    public Long getLongValues(String key) {
        ValueOperations<String, Long> values = redisLongTemplate.opsForValue();
        return values.get(key);
    }

    //String 타입 value Get
    public String getStringValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    //String 타입 key & value set 및 만료 시간 설정
    public String setExpire(String key, Long timeout, TimeUnit unit) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.getAndExpire(key, timeout, unit);
    }

    //String 타입 key & value 삭제
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }


}
