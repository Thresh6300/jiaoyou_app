package com.qiqi.jiaoyou_app.util;

import org.springframework.data.redis.core.RedisTemplate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumberUtils
{
    public static String getOrderNumber(String prefix)
    {
	  //生成订单号 redis incr
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	  String time = sdf.format(new Date());
	  //当天流水7位
	  Long tx = SpringUtils.getBean("redisTemplate", RedisTemplate.class).opsForValue().increment("orderId", 1);
	  if (tx < 1)
	  {
		//没值，自动添加
		SpringUtils.getBean("redisTemplate", RedisTemplate.class).opsForValue().set("orderId", 1);
		tx = SpringUtils.getBean("redisTemplate", RedisTemplate.class).opsForValue().increment("orderId", 1);
	  }
	  //自动补0
	  String end = autoAddZero(String.valueOf(tx));
	  return prefix + time + end;
    }


    public static String autoAddZero(String liuShuiHao)
    {
	  Integer intHao = Integer.parseInt(liuShuiHao);
	  DecimalFormat df = new DecimalFormat("1");
	  System.out.println(df.format(intHao));
	  return df.format(intHao);
    }


}
