package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.LoveHeartDonationMapper;
import com.qiqi.jiaoyou_app.pojo.LoveHeartDonation;
import com.qiqi.jiaoyou_app.pojo.LoveHeartDonationLogs;
import com.qiqi.jiaoyou_app.service.LoveHeartDonationLogsService;
import com.qiqi.jiaoyou_app.service.LoveHeartDonationService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 爱心捐赠(LoveHeartDonation)表服务实现类
 * loveHeartDonation
 *
 * @author cfx
 * @since 2020-12-03 14:10:27
 */
@Slf4j
@Service("loveHeartDonationService")
public class LoveHeartDonationServiceImpl extends ServiceImpl<LoveHeartDonationMapper, LoveHeartDonation> implements LoveHeartDonationService
{
    @Autowired
    private LoveHeartDonationMapper loveHeartDonationMapper;

    @Autowired
    private LoveHeartDonationLogsService loveHeartDonationLogsService;
    @Override
    public ResultUtils loveList(LoveHeartDonation loveHeartDonation)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  EntityWrapper<LoveHeartDonation> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("id", false).eq("love_state", "1");
	  Page page = new Page(loveHeartDonation.getPageNum(), loveHeartDonation.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<LoveHeartDonation> loveHeartDonations = loveHeartDonationMapper.selectPage(page, wrapper);
	  resultUtils.setData(loveHeartDonations);
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  return resultUtils;
    }

    @Override
    public ResultUtils getOne(LoveHeartDonation loveHeartDonation)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  LoveHeartDonation loveHeartDonation1 = loveHeartDonationMapper.selectById(loveHeartDonation.getId());
	  if (loveHeartDonation1==null)
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("操作失败");
		return resultUtils;
	  }
	  resultUtils.setData(loveHeartDonation1);
	  EntityWrapper<LoveHeartDonationLogs> wrapper = new EntityWrapper<>();
	  wrapper.setSqlSelect("id,user_id userId, donation_project_id donationProjectId, donation_time donationTime, donation_money donationMoney, " +
			"(SELECT head FROM member WHERE id=user_id) head, (SELECT nickName FROM member WHERE id=user_id) nickName");
	  wrapper.eq("donation_project_id",loveHeartDonation.getId());

	  List<LoveHeartDonationLogs> loveHeartDonationLogs = loveHeartDonationLogsService.selectList(wrapper);
	  resultUtils.setData1(loveHeartDonationLogs);

	  return resultUtils;
    }
}