package com.qiqi.jiaoyou_app.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.express.CloudUtils;
import com.qiqi.jiaoyou_app.express.ExpressDo;
import com.qiqi.jiaoyou_app.express.ExpressVo;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.NoticeMapper;
import com.qiqi.jiaoyou_app.mapper.OrderManageMapper;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.*;
import com.qiqi.jiaoyou_app.util.JsonUtils;
import com.qiqi.jiaoyou_app.util.OrderNumberUtils;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 订单信息表(OrderManage)表服务实现类
 * orderManage
 *
 * @author cfx
 * @since 2020-12-03 16:56:22
 */
@Slf4j
@Service("orderManageService")
public class OrderManageServiceImpl extends ServiceImpl<OrderManageMapper, OrderManage> implements OrderManageService
{
    @Autowired
    private OrderManageMapper orderManageMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ServerInfoManagerService serverInfoManagerService;

    @Autowired
    private ShopManageService shopManageService;

    @Autowired
    private EvaluateService evaluateService;

	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private MemberMapper memberMapper;

    @Override
    public ResultUtils orderMyList(OrderManage orderManage)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  EntityWrapper<OrderManage> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("create_time", false).eq("user_id", orderManage.getUserId()).eq("order_del_status", "1").eq(StringUtils.isNotBlank(orderManage.getOrderStatus()), "order_status", orderManage.getOrderStatus());
	  Page page = new Page(orderManage.getPageNum(), orderManage.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<OrderManage> orderManages = orderManageMapper.selectPage(page, wrapper);
	  if (orderManages == null || orderManages.size() == 0)
		return resultUtils;

	  resultUtils.setData(orderManages);
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  return resultUtils;
    }

    @Override
    public ResultUtils orderDetail(Integer id)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new OrderManage());
	  OrderManage orderManage = selectById(id);
	  if (orderManage == null)
		return resultUtils;
	  resultUtils.setData(orderManage);
	  return resultUtils;
    }

    @Override
    public ResultUtils orderDown(OrderManage orderManage)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  orderManage.setCreateTime(new Date());
		ShopManage shopManage = null;
		//	0服务1商品
		if ("1".equals(orderManage.getType()))
	  {
		ServerInfoManager serverInfoManager = serverInfoManagerService.selectById(orderManage.getOrderShopId());
		if (serverInfoManager == null)
		{
		    resultUtils.setMessage("商品为空");
		    return resultUtils;
		}
		orderManage.setOrderShopName(serverInfoManager.getServerTitle());
		orderManage.setBusinessName(serverInfoManager.getBusinessName());
		orderManage.setShopImg(serverInfoManager.getServerCover());
		orderManage.setWidth(serverInfoManager.getWidth());
		orderManage.setShopIntroduce(serverInfoManager.getServerIntroduce());
		orderManage.setBusinessIcon(serverInfoManager.getBusinessIcon());
	  }
	  else
	  {
		 shopManage = shopManageService.selectById(orderManage.getOrderShopId());
		if (shopManage == null)
		{
		    resultUtils.setMessage("商品为空");
		    return resultUtils;
		}
		orderManage.setOrderShopName(shopManage.getShopName());
		orderManage.setBusinessName(shopManage.getBusinessName());
		orderManage.setShopImg(shopManage.getShopImg());
		orderManage.setWidth(shopManage.getWidth());
		orderManage.setShopIntroduce(shopManage.getShopIntroduce());
		orderManage.setBusinessIcon(shopManage.getBusinessIcon());
		orderManage.setOrderRealPrice(shopManage.getShopPrice().multiply(new BigDecimal(orderManage.getOrderShopNumber())));

	  }

	  Address address = addressService.selectById(orderManage.getOrderAddressId());
	  orderManage.setReceverAddress(address.getConsigneeAddress());
	  orderManage.setReceverPeopleName(address.getConsigneeName());
	  orderManage.setReceverPhone(address.getConsigneePhone());
	  orderManage.setOrderStatus("1");
	  orderManage.setOrderDelStatus("1");
	  orderManage.setOrderNumber(OrderNumberUtils.getOrderNumber("1"));
	  Integer insert = orderManageMapper.insert(orderManage);
	  if (insert == 0){
		  return resultUtils;
	  }
	  OrderManage orderManage1 = selectById(orderManage.getId());
	  resultUtils.setData(orderManage1);
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  /*操作成功之后，在这里给下订单加上提醒信息*/

		Member member = memberMapper.selectById(orderManage.getUserId());
		Notice notice = new Notice();
//		notice.setShenqingId(acceptOfflineActivities.getId());
		notice.setOfMember(member.getId());
		notice.setNickname(member.getNickName());
		notice.setHead(member.getHead());
		notice.setSex(member.getSex());
		notice.setShenqingTime(new Timestamp(System.currentTimeMillis()));
//		notice.setDaochangTime(acceptOfflineActivities.getKeepAnAppointmentTime());

		/*查询用户参与的聚会主题*/
		notice.setTitle("您已成功购买商品\""+shopManage.getShopName()+"\",请等待收货。");
//		notice.setPerSize(sendOfflineActivities.getPerSize());
//		notice.setStartTime(sendOfflineActivities.getStartTime());
//		notice.setAddress(sendOfflineActivities.getAddress());
//		notice.setOfMember(sendOfflineActivities.getSendMemberId());
		notice.setType(3);
		notice.setAddTime(new Timestamp(System.currentTimeMillis()));
		Integer insert1 = noticeMapper.insert(notice);
	  return resultUtils;
    }

    @Override
    public OrderManage getOrderManage(String orderNumber)
    {
	  return selectOne(new EntityWrapper<OrderManage>().eq("order_number", orderNumber));
    }

    @Override
    public ResultUtils expressDetail(Integer id)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("暂未发货");
	  Map<String, String> map = new HashMap<>();
	  OrderManage orderManage = selectById(id);
	  if (StringUtils.isEmpty(orderManage.getExpressCode()) || StringUtils.isEmpty(orderManage.getExpressNum()))
		return resultUtils;
	  ExpressDo expressDo = new ExpressDo();
	  expressDo.setCom(orderManage.getExpressCode());
	  expressDo.setNum(orderManage.getExpressNum());
	  expressDo.setFrom(orderManage.getExpressFrom());
	  expressDo.setTo(orderManage.getExpressTo());
	  expressDo.setPhone(orderManage.getReceverPhone());
	  expressDo.setResultv2("2");
	  String json = JsonUtils.objectToJson(expressDo);
	  map.put("param", json);
	  String post = CloudUtils.post(map);
	  JSONObject obj = JSONObject.parseObject(post);
	  String status = obj.getString("status");
	  String message = obj.getString("message");
	  String returnCode = obj.getString("returnCode");
	  if (!"ok".equals(message) || StringUtils.isBlank(status))
	  {
		resultUtils.setMessage(message);
		return resultUtils;
	  }

	  List<ExpressVo> list = new ArrayList<>();
	  String state = obj.getString("state");
	  String data = obj.getString("data");
	  JSONArray jsonArray = JSONObject.parseArray(data);
	  for (int i = 0; i < jsonArray.size(); i++)
	  {
		JSONObject jsonObject2 = jsonArray.getJSONObject(i);
		ExpressVo expressVo = new ExpressVo();
		expressVo.setContext(jsonObject2.getString("context"));
		expressVo.setTime(jsonObject2.getString("time"));
		list.add(expressVo);
	  }
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(list);
	  resultUtils.setData1(orderManage.getExpressName());
	  resultUtils.setData2(orderManage.getExpressNum());
	  return resultUtils;
    }

    @Override
    public ResultUtils updateOrderStatus(OrderManage orderManage)
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


}