package com.qiqi.jiaoyou_app.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiqi.jiaoyou_app.pojo.Club;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 俱乐部(Club)表数据库访问层
 *
 * @author cfx
 * @since 2020-11-27 09:36:33
 */
@Repository
public interface ClubMapper extends BaseMapper<Club>
{
    List<Club> getQuestionsTime(@Param("clubIds") Integer[] clubIds);
}