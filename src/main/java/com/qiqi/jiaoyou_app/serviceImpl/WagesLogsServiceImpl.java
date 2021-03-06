package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.WagesLogsDao;
import com.qiqi.jiaoyou_app.pojo.WagesLogs;
import com.qiqi.jiaoyou_app.service.WagesLogsService;
import org.springframework.stereotype.Service;

/**
 * 工资记录表(WagesLogs)表服务实现类
 *
 * @author makejava
 * @since 2020-12-14 14:00:39
 */
@Service("wagesLogsService")
public class WagesLogsServiceImpl extends ServiceImpl<WagesLogsDao,WagesLogs> implements WagesLogsService {

	@Override
	public Boolean add(WagesLogs wagesLogs) {
     	int i =	baseMapper.insert(wagesLogs);
     	if(i > 0){
     		return true;
		}
		return false;
	}
}