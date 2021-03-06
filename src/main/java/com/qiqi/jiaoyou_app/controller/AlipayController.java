package com.qiqi.jiaoyou_app.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.controller.award.Award;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.PlatformParameterSettingMapper;
import com.qiqi.jiaoyou_app.pay.util.AlipayUtil;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.OrderManageService;
import com.qiqi.jiaoyou_app.service.ShopManageService;
import com.qiqi.jiaoyou_app.serviceImpl.DiamondsServiceImpl;
import com.qiqi.jiaoyou_app.serviceImpl.MemberAssetsServiceImpl;
import com.qiqi.jiaoyou_app.serviceImpl.MemberServiceImpl;
import com.qiqi.jiaoyou_app.serviceImpl.RechargeRecordServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Administrator
 */
@Api(tags = {"支付宝支付"})
@RestController
@RequestMapping("/jiaoyou_app")
public class AlipayController
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

    @GetMapping("/alipay")
    public ResultUtils alipay(@ApiParam(name = "memberId", value = "会员ID", required = false, type = "Integer") Integer memberId, @ApiParam(name = "body", value = "产品描述", required = true, type = "String") String body, @ApiParam(name = "price", value = "金额", required = true, type = "Integer") String price, @ApiParam(name = "type", value = "1:购买黑卡 2：开通会员 3：购买金钻 4：商品付款", required = true, type = "Integer") Integer type, @ApiParam(name = "orderNumber", value = "订单号", required = true, type = "String") String orderNumber)
    {
	  //                  EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
	  ResultUtils resultUtils = new ResultUtils();
	  AlipayClient alipayClient = AlipayUtil.getAlipayClient();
	  //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
	  AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
	  //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
	  AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
	  model.setBody(body);
	  model.setSubject(body);
	  //订单号
	  String randomString = System.currentTimeMillis() + "";
	  if (StringUtils.isNotBlank(orderNumber))
	  {
		OrderManage orderManage = orderManageService.getOrderManage(orderNumber);
		model.setOutTradeNo(orderNumber);
		//总金额,微信以分做单位
		model.setTotalAmount(orderManage.getOrderRealPrice().toString());
	  }
	  else
	  {
		model.setOutTradeNo(randomString);
		//总金额
		model.setTotalAmount(price);
	  }


	  //该笔订单允许的最晚付款时间 m分钟
	  model.setTimeoutExpress("30m");
	  //扩展参数
	  model.setPassbackParams(memberId + "," + type + "," + price);

	  //固定值 QUICK_MSECURITY_PAY
	  model.setProductCode(AlipayUtil.PRODUCT_CODE);
	  request.setBizModel(model);
	  //回调接口
	  request.setNotifyUrl(AlipayUtil.NOTIFY_URL);
	  try
	  {
		AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
		//将预支付结果直接返回前端，客户端可以使用该返回直接调用支付
		resultUtils.setMessage(Constant.UNIFIED_ORDER_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(response.getBody());
		return resultUtils;
	  }
	  catch (AlipayApiException e)
	  {
		//throw new ServiceException(e.getMessage());
		return null;
	  }
    }

    /**
     * 支付宝回调接口，放开权限
     *
     * @param request
     * @return
     */
    @PostMapping("/alipayNotify")
    public String alipayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
	  System.out.println("支付宝支付异步通知开始==============》");
	  System.out.println(request.getParameterMap().toString());
	  try
	  {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); )
		{
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++)
		    {
			  valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
		    }
		    //乱码解决，这段代码在出现乱码时使用。
		    //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		    params.put(name, valueStr);
		}
		System.out.println(params.toString());
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		//收到的参数、支付宝公钥、字符集、签名加密类型
		boolean flag = AlipaySignature.rsaCheckV1(params, AlipayUtil.ALIPAY_PUBLIC_KEY, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
		if (!flag)
		{
		    System.err.println("验签失败");
		    return null;
		}
		/**
		 * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		 * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
		 * 4、验证app_id是否为该商户本身。
		 * 上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
		 * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
		 * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
		 */
		//商户订单号
		String orderCode = params.get("out_trade_no");
		String appId = params.get("app_id");
		//回传参数
		String attach = params.get("passback_params");
		if (StringUtils.isEmpty(orderCode))
		{
		    System.err.println("未能获取到回调订单ID");
		    return null;
		}
		if (!AlipayUtil.APP_ID.equals(appId))
		{
		    System.err.println("appId错误");
		    return null;
		}
		String[] split = attach.split(",");
		String memberId = "";
		try
		{
		    memberId = split[0];
		    ;
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
			  //增加会员经验值
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
			  rechargeRecord.setMode(2);
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
		    rechargeRecord.setMode(2);
		    rechargeRecordService.insert(rechargeRecord);
		}
		else if (type == 3)
		{
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
			  rechargeRecord.setMode(2);
			  rechargeRecordService.insert(rechargeRecord);
		    }
		}
		else if (type == 4)
		{
		    OrderManage orderManage = orderManageService.getOrderManage(orderCode);
		    orderManage.setOrderStatus("2");
		    orderManage.setPayType("1");
		    orderManage.setPayStatus("1");
		    orderManageService.updateById(orderManage);

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
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(memberId) && rechargeRecord.getMemberId() != null)
		{
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
		System.out.println("支付宝支付回调，订单： {} 处理成功！" + orderCode);
		System.out.println("支付宝支付异步通知结束==============》");
		//支付宝要求必须返回success，不然就会一直给你回调
/*            PrintWriter writer = null;
            writer = response.getWriter();
            writer.write("success"); // 一定要打印success
            writer.flush();*/
		return "success";
	  }
	  catch (AlipayApiException e)
	  {
		System.err.println("支付宝支付回调发生异常，信息： {} " + e.getMessage());
		System.out.println("支付宝支付异步通知结束==============》");
		return null;
	  }

    }
}
