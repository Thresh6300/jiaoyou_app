package com.qiqi.jiaoyou_app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.qiqi.jiaoyou_app.mapper")
public class JiaoyouAppApplication
{

    public static void main(String[] args)
    {
	  SpringApplication.run(JiaoyouAppApplication.class, args);
    }

}
