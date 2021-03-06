package com.qiqi.jiaoyou_app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Collect;
import com.qiqi.jiaoyou_app.pojo.ShopManage;
import com.qiqi.jiaoyou_app.service.CollectService;
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
 * 喜欢(Collect)表控制层
 *
 * @author cfx
 * @since 2020-12-03 15:30:46
 */
@RestController
@RequestMapping("/jiaoyou_app/collect")
@Api(tags = " 喜欢管理")
public class CollectController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-12-03 15:30:46
     */
    @Autowired
    private CollectService collectService;

    @ApiOperation(value = "字段说明(已完成)")
    @PostMapping("/list")
    public ResultUtils list(Collect shopManage)
    {
	  return null;
    }

    @ApiOperation(value = "添加喜欢(已完成)")
    @PostMapping("/addCollec")
    @DynamicParameters(name = "addCollec", properties = {
		  @DynamicParameter(name = "collectUserId", value = "会员的id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "collectShopId", value = "商品的id", required = false, dataTypeClass = Integer.class), @DynamicParameter(name = "collectServerId", value = "服务的id", required = false, dataTypeClass = Integer.class),
    })
    public ResultUtils addCollec(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Collect collect = JSON.toJavaObject(jsonObject, Collect.class);
	  return collectService.add(collect);
    }


    @ApiOperation(value = "取消喜欢(已完成)")
    @PostMapping("/cancelCollec")
    @DynamicParameters(name = "cancelCollec", properties = {
		  @DynamicParameter(name = "collectUserId", value = "会员的id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "collectShopId", value = "商品的id", required = false, dataTypeClass = Integer.class), @DynamicParameter(name = "collectServerId", value = "服务的id", required = false, dataTypeClass = Integer.class),
    })
    public ResultUtils cancelCollec(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Collect collect = JSON.toJavaObject(jsonObject, Collect.class);
	  return collectService.cancelCollec(collect);
    }


    @ApiOperation(value = "我的喜欢列表(已完成)")
    @GetMapping("/collectMyList")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "collectUserId", value = "userId", required = true, dataType = "Integer"),
		  @ApiImplicitParam(name = "pageSize",value = "页数据量",required = true,dataType = "Integer"),
		  @ApiImplicitParam(name = "pageNum",value = "页码",required = true,dataType = "Integer"),
    })
    public ResultUtils collectMyList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiIgnore Collect collect)
    {
	  return collectService.collectMyList(collect);
    }


    @ApiOperation(value = "服务商品合并搜索(已完成)")
    @GetMapping("/searchList")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "pageSize",value = "页数据量",required = true,dataType = "Integer"),
		  @ApiImplicitParam(name = "pageNum",value = "页码",required = true,dataType = "Integer"),
		  @ApiImplicitParam(name = "shopPriceIsAsc",value = "商品单价:0正序;1倒序",required = true,dataType = "String"),
		  @ApiImplicitParam(name = "paymentNumberIsAsc",value = "付款人数:0正序;1倒序",required = true,dataType = "String"),
		  @ApiImplicitParam(name = "searchValue",value = "搜索值",required = true,dataType = "String"),
		  @ApiImplicitParam(name = "startPrice",value = "开始的价格",required = true,dataType = "BigDecimal"),
		  @ApiImplicitParam(name = "endPrice",value = "结束的价格",required = true,dataType = "BigDecimal"),
		  @ApiImplicitParam(name = "city",value = "城市",required = true,dataType = "BigDecimal"),
    })
    public ResultUtils searchList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiIgnore ShopManage shopManage)
    {
	  return collectService.searchList(shopManage);
    }



}