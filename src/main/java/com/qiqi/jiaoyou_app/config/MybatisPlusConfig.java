package com.qiqi.jiaoyou_app.config;


import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.qiqi.jiaoyou_app.mapper")
public class MybatisPlusConfig
{

    /*
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public PaginationInterceptor paginationInterceptor()
    {
	  PaginationInterceptor page = new PaginationInterceptor();
	  page.setDialectType("mysql");
	  return page;
    }

    /**
     * SQL执行效率插件
     */
/*    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }*/

}
