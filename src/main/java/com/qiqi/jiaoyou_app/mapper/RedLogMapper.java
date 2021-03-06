package com.qiqi.jiaoyou_app.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiqi.jiaoyou_app.pojo.RedLog;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GR123
 * @since 2020-06-19
 */
public interface RedLogMapper extends BaseMapper<RedLog> {

    /**
     * 更新红包数量
     * @param redLogId
     * @return
     */
    int updateRedNum (@Param("redLogId") Integer redLogId);
}
