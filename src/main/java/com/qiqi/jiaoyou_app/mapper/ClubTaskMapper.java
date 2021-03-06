package com.qiqi.jiaoyou_app.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiqi.jiaoyou_app.pojo.ClubTask;
import org.springframework.stereotype.Repository;

/**
 * (ClubTask)表数据库访问层
 *
 * @author cfx
 * @since 2020-11-28 10:42:59
 */
@Repository
public interface ClubTaskMapper extends BaseMapper<ClubTask>
{


	/**
	 * @return
	 */
	String getwageNum(Integer memberId);

}