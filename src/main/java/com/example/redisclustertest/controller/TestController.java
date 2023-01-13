package com.example.redisclustertest.controller;

import com.example.redisclustertest.service.RedisService;
import com.example.redisclustertest.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final RedisService redisService;
    private final TestService testService;

    @GetMapping("/insert")
    public Object insert(){
        log.info("insert Controller");
        String d = String.valueOf(Math.random());

        log.info("key : {}", d);
        redisService.setValues(d.substring(d.length()-8),1L);


        return "OK";
    }


}
