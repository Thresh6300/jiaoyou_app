package com.qiqi.jiaoyou_app.mapper;

import com.qiqi.jiaoyou_app.pojo.Giftconsumption;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 礼物记录表 Mapper 接口
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface GiftconsumptionMapper extends BaseMapper<Giftconsumption> {
    List<Map<String,Object>> groupCount(@Param("uid") Integer uid);
}
