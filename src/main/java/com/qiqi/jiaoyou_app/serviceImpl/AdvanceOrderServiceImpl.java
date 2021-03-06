package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.AdvanceOrderMapper;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.NoticeMapper;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.AdvanceOrderService;
import com.qiqi.jiaoyou_app.service.ServerInfoManagerService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (AdvanceOrder)表服务实现类
 * advanceOrder
 *
 * @author cfx
 * @since 2020-12-09 14:53:36
 */
@Slf4j
@Service("advanceOrderService")
public class AdvanceOrderServiceImpl extends ServiceImpl<AdvanceOrderMapper, AdvanceOrder> implements AdvanceOrderService
{

    @Autowired
    private AdvanceOrderMapper advanceOrderMapper;
    @Autowired
    private ServerInfoManagerService serverInfoManagerService;

	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private MemberMapper memberMapper;
    @Override
    public ResultUtils orderDown(AdvanceOrder advanceOrder)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  advanceOrder.setCreateTime(new Date());

	  ServerInfoManager serverInfoManager = serverInfoManagerService.selectById(advanceOrder.getShopId());
	  if (serverInfoManager == null)
	  {
		resultUtils.setMessage("商品为空");
		return resultUtils;
	  }
	  advanceOrder.setShopName(serverInfoManager.getServerTitle());
	  advanceOrder.setShopUrl(serverInfoManager.getServerCover());
	  advanceOrder.setWidth(serverInfoManager.getWidth());
	  advanceOrder.setShopIntroduce(serverInfoManager.getServerIntroduce());
	  advanceOrder.setBusinessName(serverInfoManager.getBusinessName());
	  advanceOrder.setBusinessIcon(serverInfoManager.getBusinessIcon());
	  advanceOrder.setStatus("0");
	  if (insert(advanceOrder))
	  {
	  	/*在这里增加预约商品的提醒*/
		  Member member = memberMapper.selectById(advanceOrder.getUserId());
		  Notice notice = new Notice();
		  notice.setOfMember(member.getId());
		  notice.setNickname(member.getNickName());
		  notice.setHead(member.getHead());
		  notice.setSex(member.getSex());
		  notice.setShenqingTime(new Timestamp(System.currentTimeMillis()));
		  /*查询用户参与的聚会主题*/
		  notice.setTitle("您已成功预订\""+serverInfoManager.getServerTitle()+"\"服务，请等待客服与您联系。");
		  notice.setType(3);
		  notice.setAddTime(new Timestamp(System.currentTimeMillis()));
		  Integer insert1 = noticeMapper.insert(notice);
	  	return resultUtils;
	  }
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");

	  return null;
    }

    @Override
    public ResultUtils updateOrderStatus(AdvanceOrder orderManage)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  if (updateById(orderManage))
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils orderDetail(Integer id)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new OrderManage());
	  AdvanceOrder advanceOrder = selectById(id);
	  if (advanceOrder == null)
		return resultUtils;
	  resultUtils.setData(advanceOrder);
	  return resultUtils;
    }

    @Override
    public ResultUtils orderMyList(AdvanceOrder advanceOrder)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  EntityWrapper<AdvanceOrder> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("create_time", false).eq("user_id", advanceOrder.getUserId());
	  Page page = new Page(advanceOrder.getPageNum(), advanceOrder.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<AdvanceOrder> orderManages = advanceOrderMapper.selectPage(page, wrapper);
	  if (orderManages == null  ||  orderManages.size() == 0)
		return resultUtils;

	  resultUtils.setData(orderManages);
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  return resultUtils;
    }
}