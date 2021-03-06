package com.qiqi.jiaoyou_app.red;

import com.qiqi.jiaoyou_app.config.RedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description: 发红包
 *
 * @Author: weishenpeng
 * Date: 2018/6/10
 * Time: 下午 04:25
 */
@SuppressWarnings("ALL")
public class SendRedPacket implements Runnable {
	/**
	 * 金额
	 */
	private double money;
	/**
	 * 数量
	 */
	private int num;
	/**
	 * 发送人ID
	 */
	private String sendId;

	public SendRedPacket(double money, int num, String sendId) {
		this.money = money;
		this.num = num;
		this.sendId = sendId;
	}

	/**
	 * 发红包
	 *
	 * @param money
	 * @param num
	 * @return
	 */
	private boolean sendRedPacket(double money, int num, String senderId) {
		if (money <= 0 || num <= 0) {
			return false;
		}
		if (money * 100 / num < 1) {
			return false;
		}
		List<Integer> rpList = RedPacketUtils.divideRedPackage((int) money, num);
		Jedis jedis = RedisPoolUtils.getConn();
		try {
			if(jedis.exists("red::list" + senderId)) {
				jedis.del("red::list" + senderId);
			}
			for (Integer mon : rpList) {
				jedis.lpush("red::list" + senderId, mon + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisPoolUtils.closeConn();
		}
		//设置过期时间
		jedis.expire("red::list" + senderId, 25*60*60);
		return true;
	}

	@Override
	public void run() {
		sendRedPacket(money, num, sendId);
	}
}