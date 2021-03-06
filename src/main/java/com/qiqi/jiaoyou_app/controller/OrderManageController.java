package com.qiqi.jiaoyou_app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.OrderManage;
import com.qiqi.jiaoyou_app.service.OrderManageService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订单信息表(OrderManage)表控制层
 *
 * @author cfx
 * @since 2020-12-03 16:56:22
 */
@RestController
@RequestMapping("/jiaoyou_app/orderManage")
@Api(tags = " 订单信息表管理")
public class OrderManageController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-12-03 16:56:22
     */
    @Autowired
    private OrderManageService orderManageService;

    @ApiOperation(value = "字段说明(已完成)")
    @PostMapping("/list")
    public ResultUtils list(OrderManage shopManage){
	  return null;
    }

    @ApiOperation(value = "我的订单列表(已完成)")
    @PostMapping("/orderMyList")
    @DynamicParameters(name = "loveList",properties = {
		  @DynamicParameter(name = "pageSize",value = "页数据量",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "pageNum",value = "页码",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "userId",value = "用户id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "orderStatus",value = "订单状态（1：待支付，2：待收货，3：已完成，4：已取消）",required = false,dataTypeClass = Integer.class),
    })
    public ResultUtils orderMyList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  OrderManage orderManage = JSON.toJavaObject(jsonObject, OrderManage.class);
	  return orderManageService.orderMyList(orderManage);
    }

    @ApiOperation(value = "订单详情(已完成)")
    @GetMapping("/orderDetail")
    @DynamicParameters(name = "orderDetail",properties = {
		  @DynamicParameter(name = "id",value = "订单id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils orderDetail(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer id){
	  return orderManageService.orderDetail(id);
    }

    @ApiOperation(value = "商品下订单(已完成)")
    @PostMapping("/orderDown")
    @DynamicParameters(name = "orderDetail",properties = {
		  @DynamicParameter(name = "orderShopId",value = "商品id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "userId",value = "用户id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "orderPrice",value = "订单价格",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "orderShopSpecs",value = "商品规格",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "orderAddressId",value = "地址表id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "orderShopNumber",value = "订单商品数量",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "type",value = "0服务1商品",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils orderDown( @RequestBody JSONObject jsonObject){
	  OrderManage orderManage = JSON.toJavaObject(jsonObject, OrderManage.class);
	  return orderManageService.orderDown(orderManage);
    }

    @ApiOperation(value = "物流信息(已完成)")
    @GetMapping("/expressDetail")
    @DynamicParameters(name = "expressDetail",properties = {
		  @DynamicParameter(name = "id",value = "订单id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils expressDetail(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer id){
	  return orderManageService.expressDetail(id);
    }

    @PostMapping("updateOrderStatus")
    @ApiOperation("确认收货/取消/订单")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer"),
		  @ApiImplicitParam(name = "orderStatus", value = "订单状态（1：待支付，2：待收货，3：已完成，4：已取消）", required = true, dataType = "Integer"),
    })
    public ResultUtils updateOrderStatus(@ApiIgnore @RequestBody OrderManage orderManage)
    {
	  return orderManageService.updateOrderStatus(orderManage);
    }
}