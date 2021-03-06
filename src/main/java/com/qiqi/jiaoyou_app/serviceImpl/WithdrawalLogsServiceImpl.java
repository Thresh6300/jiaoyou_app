package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.WithdrawalLogsDao;
import com.qiqi.jiaoyou_app.pojo.WithdrawalLogs;
import com.qiqi.jiaoyou_app.service.WithdrawalLogsService;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 金钻银钻兑换记录表(WithdrawalLogs)表服务实现类
 *
 * @author nan
 * @since 2020-12-12 17:28:40
 */
@Service("withdrawalLogsService")
public class WithdrawalLogsServiceImpl extends ServiceImpl<WithdrawalLogsDao,WithdrawalLogs> implements WithdrawalLogsService {

	@Override
	public Boolean add(WithdrawalLogs withdrawalLogs) {
		WithdrawalLogs withdrawalLogs1 = new WithdrawalLogs();
		withdrawalLogs1.setMemberid(withdrawalLogs.getMemberid());
		withdrawalLogs1.setGold(withdrawalLogs.getGold());
		withdrawalLogs1.setSliver(withdrawalLogs.getSliver());
		withdrawalLogs1.setType(withdrawalLogs.getType());
		withdrawalLogs1.setCreatetime(new Date());
		Integer a = baseMapper.insert(withdrawalLogs1);
		if (a > 0) {
			return true;
		} else {
			return false;
		}
	}
}