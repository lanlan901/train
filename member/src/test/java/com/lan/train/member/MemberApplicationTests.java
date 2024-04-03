package com.lan.train.member;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = MemberApplication.class)
class MemberApplicationTests {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Test
    public void testRedisTemplate(){
        String mobile = "12312341235";
        String code = "8888";
        redisTemplate.opsForValue().set("smsCode:" + mobile, code, 5, TimeUnit.MINUTES);

        System.out.println(redisTemplate);
        String savedCode = redisTemplate.opsForValue().get("smsCode:" + mobile).toString();
        System.out.println(savedCode);
    }

}
