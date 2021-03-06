package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.AdvanceOrder;
import com.qiqi.jiaoyou_app.service.AdvanceOrderService;
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
 * (AdvanceOrder)表控制层
 *
 * @author cfx
 * @since 2020-12-09 14:53:41
 */
@RestController
@RequestMapping("/jiaoyou_app/advanceOrder")
@Api(tags = "预支付订单管理")
public class AppAdvanceOrderController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-12-09 14:53:41
     */
    @Autowired
    private AdvanceOrderService advanceOrderService;

    @ApiOperation(value = "字段说明(已完成)")
    @PostMapping("/list")
    public void list(AdvanceOrder advanceOrder)
    {
    }


    @ApiOperation(value = "预订下订单(已完成)")
    @PostMapping("/orderDown")
    @DynamicParameters(name = "orderDetail",properties = {
		  @DynamicParameter(name = "shopId",value = "商品id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "userId",value = "用户id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "call",value = "称呼",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "checkIn",value = "入住人数",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "toShopTime",value = "到店时间",required = true,dataTypeClass = String.class),
    })
    public ResultUtils orderDown(@RequestBody JSONObject jsonObject){
	  AdvanceOrder advanceOrder = JSON.toJavaObject(jsonObject, AdvanceOrder.class);
	  return advanceOrderService.orderDown(advanceOrder);
    }


    @ApiOperation(value = "订单详情(已完成)")
    @GetMapping("/orderDetail")
    @DynamicParameters(name = "orderDetail",properties = {
		  @DynamicParameter(name = "id",value = "订单id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils orderDetail(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer id){
	  return advanceOrderService.orderDetail(id);
    }

    @PostMapping("updateStatus")
    @ApiOperation("取消订单")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer"),
		  @ApiImplicitParam(name = "status", value = "订单状态（0:进行中;1：已完成，2：已取消）", required = true, dataType = "Integer"),
    })
    public ResultUtils updateOrderStatus(@ApiIgnore @RequestBody AdvanceOrder orderManage)
    {
	  return advanceOrderService.updateOrderStatus(orderManage);
    }


    @ApiOperation(value = "我的订单列表(已完成)")
    @PostMapping("/orderMyList")
    @DynamicParameters(name = "loveList",properties = {
		  @DynamicParameter(name = "pageSize",value = "页数据量",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "pageNum",value = "页码",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "userId",value = "用户id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils orderMyList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  AdvanceOrder advanceOrder = JSON.toJavaObject(jsonObject, AdvanceOrder.class);
	  return advanceOrderService.orderMyList(advanceOrder);
    }
}