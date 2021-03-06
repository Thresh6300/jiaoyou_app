package com.qiqi.jiaoyou_app.red;

import com.qiqi.jiaoyou_app.config.RedisPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: weishenpeng
 * Date: 2018/6/10
 * Time: 下午 04:37
 */
public class GrabRedPacket implements Runnable {



    private String grabSha = null;

    /**
     * 发送人ID
     */
    private String sendId;
    /**
     * 抢购人ID
     */
    private String grabId;


    public GrabRedPacket(String sendId, String grabId) {
        this.sendId = sendId;
        this.grabId = grabId;
    }

    private boolean grapRedPacket(String sendId, String grabId) {
        Jedis jedis = RedisPoolUtils.getConn();
        Long ttl = jedis.ttl("red::list" + sendId);
        try {
            if (grabSha == null || !jedis.scriptExists(grabSha)) {
                grabSha = jedis.scriptLoad(getGrabEval(sendId));
            }
            Object result = jedis.evalsha(grabSha, 0, grabId);
            if (ttl > 0){
                jedis.expire("red::draw" + sendId, Math.toIntExact(ttl));
                jedis.expire("red::task" + sendId, Math.toIntExact(ttl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisPoolUtils.closeConn();
        }
        return true;
    }


    @Override
    public void run() {
        grapRedPacket(sendId, grabId);
    }

    public static String getGrabEval(String sendId) {
        return "-- 领取人的openid为xxxxxxxxxxx\n" +
                "local openid = ARGV[1]\n" +
                "local isDraw = redis.call('HEXISTS', 'red::draw" + sendId + "', openid)\n" +
                "-- 已经领取\n" +
                "if isDraw ~= 0 then\n" +
                "    return true\n" +
                "end\n" +
                "local number = redis.call('RPOP', 'red::list" + sendId + "')\n" +
                "-- 没有红包\n" +
                "if not number then\n" +
                "    return true\n" +
                "end\n" +
                "-- 领取人昵称为Fhb,头像为https://xxxxxxx\n" +
                "local red = {money=number,name=openid,pic='https://xxxxxxx'}\n" +
                "-- 领取记录\n" +
                "redis.call('HSET', 'red::draw" + sendId + "', openid, cjson.encode(red))\n" +
                "-- 处理队列\n" +
                "red['openid'] = openid\n" +
                "redis.call('RPUSH', 'red::task" + sendId + "', cjson.encode(red))\n" +
                "return true\n";
    }
}