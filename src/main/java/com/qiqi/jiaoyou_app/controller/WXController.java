package com.qiqi.jiaoyou_app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.controller.award.Award;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.PlatformParameterSettingMapper;
import com.qiqi.jiaoyou_app.pay.util.PayUtil;
import com.qiqi.jiaoyou_app.pay.util.WXPayConstants;
import com.qiqi.jiaoyou_app.pay.util.WXPayUtil;
import com.qiqi.jiaoyou_app.pay.util.WXSignUtil;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.LoveHeartDonationLogsService;
import com.qiqi.jiaoyou_app.service.OrderManageService;
import com.qiqi.jiaoyou_app.service.ShopManageService;
import com.qiqi.jiaoyou_app.serviceImpl.DiamondsServiceImpl;
import com.qiqi.jiaoyou_app.serviceImpl.MemberAssetsServiceImpl;
import com.qiqi.jiaoyou_app.serviceImpl.MemberServiceImpl;
import com.qiqi.jiaoyou_app.serviceImpl.RechargeRecordServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Api(tags = {"微信支付"})
@RestController
@RequestMapping("/jiaoyou_app")
public class WXController
{

    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private DiamondsServiceImpl diamondsService;
    @Autowired
    private MemberAssetsServiceImpl memberAssetsService;
    @Autowired
    private RechargeRecordServiceImpl rechargeRecordService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PlatformParameterSettingMapper platformParameterSettingMapper;
    @Autowired
    private LoveHeartDonationLogsService loveHeartDonationLogsService;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private ShopManageService shopManageService;

    //获取mou个月后的今天
    public static Date getFourAfter(Date date, int month)
    {
	  Calendar calendar = Calendar.getInstance();
	  calendar.setTime(date); // 设置为当前时间
	  calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
	  date = calendar.getTime();
	  return date;
    }

    @GetMapping("/WXpay")
    public ResultUtils WXpay(@ApiParam(name = "memberId", value = "会员ID", required = false, type = "Integer") Integer memberId, @ApiParam(name = "body", value = "产品描述", required = true, type = "String") String body, @ApiParam(name = "price", value = "金额", required = true, type = "String") String price, @ApiParam(name = "donationProjectId", value = "捐赠项目id", required = false, type = "String") Integer donationProjectId, @ApiParam(name = "orderNumber", value = "订单编号", required = false, type = "String") String orderNumber, @ApiParam(name = "type", value = "1:购买黑卡 2：开通会员 3：购买金钻 4：商品付款 5：捐款", required = true, type = "Integer") Integer type)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  //加签验签的参数需要排序
	  SortedMap<String, String> params = new TreeMap<String, String>();
	  //应用ID
	  params.put("appid", WXPayConstants.APPID);
	  //商户号
	  params.put("mch_id", WXPayConstants.MCHID);
	  //String randomString = WXPayConstants.getRandomString(32);
	  String randomString = System.currentTimeMillis() + "";
	  //随机字符串
	  params.put("nonce_str", randomString);
	  //签名类型，默认MD5
	  params.put("sign_type", "MD5");
	  //商品描述
	  params.put("body", body);
	  //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	  params.put("attach", memberId + "," + type + "," + price);
	  //商户订单号
	  if (org.apache.commons.lang3.StringUtils.isNotBlank(orderNumber))
	  {
		OrderManage orderManage = orderManageService.getOrderManage(orderNumber);
		params.put("out_trade_no", orderNumber);
		//总金额,微信以分做单位
		params.put("total_fee", String.valueOf(orderManage.getOrderRealPrice().multiply(new BigDecimal(100)).intValue()));
	  }
	  else
	  {//订单号要是空的话放当前时间戳什么意思????????不应该查出来订单编号放进来吗????要不然用回传的时间戳拿到订单信息?
		params.put("out_trade_no", randomString);
		//总金额,微信以分做单位
		params.put("total_fee", String.valueOf(new BigDecimal(price).multiply(new BigDecimal(100)).intValue()));
	  }

	  //终端ip
	  params.put("spbill_create_ip", WXPayConstants.SPBILLCREATEIP);
	  //通知地址
	  params.put("notify_url", WXPayConstants.NOTIFYURL);
	  //交易类型
	  params.put("trade_type", "APP");

	  String sign = WXSignUtil.createSign(params);
	  params.put("sign", sign);
	  try
	  {
		String xml = WXPayUtil.mapToXml(params).replaceAll(" ", "");
		String result = PayUtil.httpRequest(WXPayConstants.UNIFIEDORDER, "POST", xml);
		Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
		if (resultMap.get("return_code") != null && WXPayConstants.SUCCESS.equals(resultMap.get("return_code")))
		{
		    //将app支付所需参数返回给商户客户端（ios 安卓），前端需要这些参数进行支付调用
		    SortedMap<String, String> data = new TreeMap<String, String>();
		    data.put("appid", WXPayConstants.APPID);
		    data.put("partnerid", WXPayConstants.MCHID);
		    data.put("prepayid", resultMap.get("prepay_id"));
		    data.put("package", WXPayConstants.PACKAGE);
		    data.put("noncestr", WXPayConstants.getRandomString(32));
		    data.put("timestamp", System.currentTimeMillis() / 1000 + "");
		    String sign1 = WXSignUtil.createSign(data);
		    data.put("sign", sign1);
		    resultUtils.setData(data);
		    resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		    resultUtils.setMessage("统一下单成功");
		    return resultUtils;
		}
		// throw new ServiceException(resultMap.get("return_msg"));
	  }
	  catch (Exception e)
	  {
		//throw new ServiceException(e.getMessage());
	  }
	  return null;
    }

    /**
     * 微信支付回调
     *
     * @param
     * @return
     */
    @PostMapping("/tenpayNotify")
    public String tenpayNotify(HttpServletRequest request)
    {
	  System.out.println("微信支付异步通知开始==============》");
	  try
	  {
		BufferedReader br = request.getReader();
		String xml = "";
		String line = br.readLine();
		while (!StringUtils.isEmpty(line))
		{
		    xml += new String(line.getBytes());
		    line = br.readLine();
		}
		if (StringUtils.isEmpty(xml))
		{
		    System.err.println("未解析到回调参数");
		    return null;
		}
		else
		{
		    System.out.println("回调参数xml ：{} " + xml);
		}

		Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
		//商户订单号
		String orderCode = resultMap.get("out_trade_no");
		if (resultMap.get("return_code") != null && WXPayConstants.SUCCESS.equals(resultMap.get("return_code")) && WXPayConstants.SUCCESS.equals(resultMap.get("result_code")))
		{
		    System.out.println("微信支付回调返回成功，信息：{}" + resultMap);
		    /**
		     *  验签
		     *  查询订单支付状态
		     *  修改订单状态
		     *  通知微信收到通知
		     *
		     */

		    String sign = resultMap.get("sign");
		    SortedMap<String, String> map = new TreeMap<String, String>(resultMap);
		    String sign1 = WXSignUtil.createSign(map);
		    if (!sign.equals(sign1))
		    {
			  System.err.println("微信支付回调验签失败");
			  return null;
		    }

		    //微信支付订单号
		    resultMap.get("transaction_id");
		    //订单附件
		    String attach = resultMap.get("attach");
		    if (StringUtils.isEmpty(orderCode))
		    {
			  System.err.println("未能获取到订单号");
			  return null;
		    }
		    String[] split = attach.split(",");
		    String memberId = "";
		    try
		    {
			  memberId = split[0];
		    }
		    catch (Exception e)
		    {

		    }
		    Integer type = Double.valueOf(split[1]).intValue();
		    Integer price = Double.valueOf(split[2]).intValue();
		    Diamonds diamonds = new Diamonds();
		    RechargeRecord rechargeRecord = new RechargeRecord();

		    //此处做业务成功处理
		    //处理业务逻辑
		    boolean status = false;
		    if (type == 1)
		    {
			  if (memberId == null || "".equals(memberId) || "null".equals(memberId))
			  {

			  }
			  else
			  {
				diamonds = diamondsService.selectList(new EntityWrapper<Diamonds>().eq("diamonds_price", price).eq("type", 1)).get(0);
				MemberAssets memberAssets = memberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberId)).get(0);
				Member member = memberService.selectById(memberId);
				if (member.getIsvip() == 2)
				{
				    member.setIsvip(1);
				    member.setMemberExpirationDate(getFourAfter(new Date(), 36));
				}
				else
				{
				    member.setMemberExpirationDate(getFourAfter(member.getMemberExpirationDate(), 36));
				}
				status = memberService.updateById(member);
				//增加会员经验值
				//增加会员钻石数
				memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 28888);
				memberAssets.setMeberExperienceSize(memberAssets.getMeberExperienceSize() + diamonds.getDiamondsPrice().intValue());
				rechargeRecord.setMemberId(member.getId());
				rechargeRecord.setCurrency(2);
				rechargeRecord.setRunSize(28888L);
				rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
				rechargeRecord.setName("续费黑卡");
				rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
				rechargeRecord.setType(1);
				rechargeRecord.setMode(1);
				rechargeRecordService.insert(rechargeRecord);
			  }
		    }
		    else if (type == 2)
		    {
			  diamonds = diamondsService.selectList(new EntityWrapper<Diamonds>().eq("diamonds_price", price).eq("type", 2)).get(0);
			  MemberAssets memberAssets = memberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberId)).get(0);
			  Member member = memberService.selectById(memberId);
			  if ("月卡".equals(diamonds.getDiamondsName()))
			  {
				if (member.getIsvip() == 2)
				{
				    member.setIsvip(1);
				    member.setMemberExpirationDate(getFourAfter(new Date(), 1));
				}
				else
				{
				    member.setMemberExpirationDate(getFourAfter(member.getMemberExpirationDate(), 1));
				}
				memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 688);
				rechargeRecord.setRunSize(688L);
				rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
				rechargeRecord.setName("购买会员月卡");
			  }
			  else if ("季卡".equals(diamonds.getDiamondsName()))
			  {
				if (member.getIsvip() == 2)
				{
				    member.setIsvip(1);
				    member.setMemberExpirationDate(getFourAfter(new Date(), 3));
				}
				else
				{
				    member.setMemberExpirationDate(getFourAfter(member.getMemberExpirationDate(), 3));
				}
				memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 1888);
				rechargeRecord.setRunSize(1888L);
				rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
				rechargeRecord.setName("购买会员季卡");
			  }
			  else if ("年卡".equals(diamonds.getDiamondsName()))
			  {
				if (member.getIsvip() == 2)
				{
				    member.setIsvip(1);
				    member.setMemberExpirationDate(getFourAfter(new Date(), 12));
				}
				else
				{
				    member.setMemberExpirationDate(getFourAfter(member.getMemberExpirationDate(), 12));
				}
				memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 7888);
				rechargeRecord.setRunSize(7888L);
				rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
				rechargeRecord.setName("购买会员年卡");
			  }
			  status = memberAssetsService.updateById(memberAssets);
			  status = memberService.updateById(member);
			  rechargeRecord.setCurrency(1);
			  //增加会员经验值
			  memberAssets.setMeberExperienceSize(memberAssets.getMeberExperienceSize() + diamonds.getDiamondsPrice().intValue());
			  memberAssetsService.updateById(memberAssets);
			  //增加记录
			  rechargeRecord.setMemberId(memberAssets.getMemberId());
			  rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
			  rechargeRecord.setType(1);
			  rechargeRecord.setMode(1);
			  rechargeRecordService.insert(rechargeRecord);
		    }
		    else if (type == 3)
		    {
			  System.out.println("微信支付充值************************");
			  List<Diamonds> diamonds1 = diamondsService.selectList(new EntityWrapper<Diamonds>().eq("diamonds_price", price).eq("type", 3));

			  if (diamonds1.size() <= 0)
			  {
				PlatformParameterSetting platformParameterSetting = platformParameterSettingMapper.selectById(1);
				MemberAssets memberAssets = memberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberId)).get(0);
				memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + price * platformParameterSetting.getGoldDiamondsPerYuan());
				status = memberAssetsService.updateById(memberAssets);
				rechargeRecord.setRunSize(Long.valueOf(price * platformParameterSetting.getGoldDiamondsPerYuan()));
				rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
				rechargeRecord.setCurrency(3);
				rechargeRecord.setName("购买钻石");
				//增加会员经验值
				memberAssets.setMeberExperienceSize(memberAssets.getMeberExperienceSize() + price.intValue());
				memberAssetsService.updateById(memberAssets);
				//增加记录
				rechargeRecord.setMemberId(memberAssets.getMemberId());
				rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
				rechargeRecord.setType(1);
				rechargeRecord.setMode(2);
				rechargeRecordService.insert(rechargeRecord);
			  }
			  else
			  {
				diamonds = diamonds1.get(0);
				MemberAssets memberAssets = memberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberId)).get(0);
				memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + diamonds.getDiamondsSize());
				status = memberAssetsService.updateById(memberAssets);
				rechargeRecord.setRunSize(Long.valueOf(diamonds.getDiamondsSize()));
				rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
				rechargeRecord.setCurrency(3);
				rechargeRecord.setName("购买钻石");
				//增加会员经验值
				memberAssets.setMeberExperienceSize(memberAssets.getMeberExperienceSize() + diamonds.getDiamondsPrice().intValue());
				memberAssetsService.updateById(memberAssets);
				//增加记录
				rechargeRecord.setMemberId(memberAssets.getMemberId());
				rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
				rechargeRecord.setType(1);
				rechargeRecord.setMode(1);
				rechargeRecordService.insert(rechargeRecord);
			  }

			  System.out.println("微信支付回调，订单：{} -------------------------------！" + orderCode);
		    }
		    else if (type == 4)
		    {

			  //author:nan
			  //需要在这里判断这个订单的数据库支付状态,成功的话不鸟这个回调,没成功鸟一下
			  OrderManage orderManageas = orderManageService.getOrderManage(orderCode);
			  System.out.println(orderManageas + "订单号码打印测试");
			  if (orderManageas.getOrderStatus() == "2")
			  {
				//拜拜了您嘞
				System.out.println(orderManageas + "该订单已经进入发货状态,回调信息被抛弃");
				return null;
			  }

			  OrderManage orderManage = orderManageService.getOrderManage(orderCode);
			  orderManage.setOrderStatus("2");
			  orderManage.setPayType("0");
			  orderManage.setPayStatus("1");
			  ShopManage shopManage = shopManageService.selectById(orderManage.getOrderShopId());
			  if (shopManage.getPaymentNumber() == null)
			  {
				shopManage.setPaymentNumber(1);
			  }
			  else
			  {
				shopManage.setPaymentNumber(shopManage.getPaymentNumber() + 1);
			  }
			  shopManageService.updateById(shopManage);
			  orderManageService.updateById(orderManage);
		    }
		    else if (type == 5)
		    {
			  Integer donationProjectId = Double.valueOf(split[3]).intValue();
			  LoveHeartDonationLogs loveHeartDonationLogs = new LoveHeartDonationLogs();
			  loveHeartDonationLogs.setDonationProjectId(donationProjectId);
			  loveHeartDonationLogs.setUserId(Integer.valueOf(memberId));
			  loveHeartDonationLogs.setDonationMoney(new BigDecimal(price));
			  loveHeartDonationLogs.setDonationTime(new Date());
			  loveHeartDonationLogsService.insert(loveHeartDonationLogs);
		    }
		    System.out.println("微信支付回调，订单：{} 66666666666666666666666666666666666666！" + orderCode);
		    if (org.apache.commons.lang3.StringUtils.isNotBlank(memberId) && rechargeRecord.getMemberId() != null)
		    {
			  System.out.println("微信支付回调，订单：{} 返佣返佣返佣返佣返佣返佣返佣返佣返佣返佣返佣返佣返佣返佣！" + orderCode);
			  //返佣
			  Integer memberId1 = rechargeRecord.getMemberId();
			  //获取接收礼物的上级
			  Member member1 = memberMapper.selectById(memberId1);
			  Integer registrationChannel = member1.getRegistrationChannel();
			  //必须为车友注册用户以及黑卡注册用户
			  if (registrationChannel != 1)
			  {
				Integer pid = member1.getPid();
				if (pid == null || pid == 0)
				{

				}
				else
				{
				    Award award = new Award(platformParameterSettingMapper, memberService, memberAssetsService, rechargeRecordService);
				    award.oneAward(pid, rechargeRecord.getRunSize(), 2);
				    //获取接收礼物的上上级
				    Integer pid1 = memberMapper.selectById(pid).getPid();
				    if (pid1 == null || pid1 == 0)
				    {

				    }
				    else
				    {
					  award.twoAward(pid1, rechargeRecord.getRunSize(), 2);
				    }
				}
			  }
		    }
		    System.out.println("微信支付回调，订单：{} 处理成功！" + orderCode);
		    //此处向微信返回成功收到通知
		    HashMap<String, String> wechatReturnMap = new HashMap<>();
		    wechatReturnMap.put("return_code", WXPayConstants.SUCCESS);
		    wechatReturnMap.put("return_msg", WXPayConstants.OK);
		    String returnXml = WXPayUtil.mapToXml(wechatReturnMap);
		    return returnXml;
		}
		else
		{
		    System.err.println("微信支付回调返回失败，信息：{}" + resultMap.toString());
		    OrderManage orderManage = orderManageService.getOrderManage(orderCode);
		    orderManage.setOrderStatus("1");
		    orderManage.setPayStatus("2");
		    orderManageService.updateById(orderManage);
		}
		System.out.println("微信支付回调，订单：{} *****************！" + orderCode);
	  }
	  catch (Exception e)
	  {

	      e.printStackTrace();
		System.err.println("微信支付回调处理失败， 信息：" + e);
	  }
	  return null;
    }

}
