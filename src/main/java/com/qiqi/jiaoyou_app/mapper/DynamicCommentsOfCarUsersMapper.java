package com.qiqi.jiaoyou_app.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.qiqi.jiaoyou_app.pojo.DynamicCommentsOfCarUsers;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 车友动态评论表 Mapper 接口
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface DynamicCommentsOfCarUsersMapper extends BaseMapper<DynamicCommentsOfCarUsers> {

    List<DynamicCommentsOfCarUsers> dynamicDetails(Integer id);
}
