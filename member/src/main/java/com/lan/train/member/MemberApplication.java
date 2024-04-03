package com.lan.train.member;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.lan")
@MapperScan("com.lan.train.member.mapper")
//SpringBoot启动时扫描的包的路径
// 意味着Spring Boot会扫描这个包及其子包中的注解，比如@Component、@Service、@Repository等，以发现并注册bean。

public class MemberApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MemberApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("start successfully!");
        LOG.info("test: \t http://127.0.0.1:{}{}/login", env.getProperty("server.port"),env.getProperty("server.servlet.context-path"));
    }
}
