package com.qiqi.jiaoyou_app.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Description:
 *
 * @Author: weishenpeng
 * Date: 2018/6/10
 * Time: 下午 02:38
 */

@Component
public class RedisPoolUtils extends CachingConfigurerSupport {

	private static JedisPool jedisPool = null;


	private static ThreadLocal<Jedis> local = new ThreadLocal<Jedis>();



	private RedisPoolUtils() {

	}

	/**
	 * 初始化Jedis连接池
	 */
	public static JedisPool  initialPool() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setMaxIdle(20);
		jedisPoolConfig.setMinIdle(5);
		jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 1000,"123456");
		return jedisPool;

	}

	/**
	 * 获得连接
	 *
	 * @return Jedis
	 */
	public static Jedis getConn() {
		//Redis对象
		Jedis jedis = local.get();
		if (jedis == null) {
			if (jedisPool == null) {
				initialPool();
			}
			jedis = jedisPool.getResource();
			local.set(jedis);
		}
		return jedis;
	}

	/**
	 * 新版本用close归还连接
	 */
	public static void closeConn() {
		//从本地线程中获取
		Jedis jedis = local.get();
		if (jedis != null) {
			jedis.close();
		}
		local.set(null);
	}

	/**
	 * 关闭池
	 */
	public static void closePool() {
		if (jedisPool != null) {
			jedisPool.close();
		}
	}
}