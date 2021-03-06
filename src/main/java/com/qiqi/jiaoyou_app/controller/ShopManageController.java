package com.qiqi.jiaoyou_app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.ShopManage;
import com.qiqi.jiaoyou_app.service.ShopManageService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品管理(ShopManage)表控制层
 *
 * @author cfx
 * @since 2020-12-03 13:37:19
 */
@RestController
@RequestMapping("/jiaoyou_app/shopManage")
@Api(tags = " 商品管理管理")
public class ShopManageController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-12-03 13:37:19
     */
    @Autowired
    private ShopManageService shopManageService;

    @ApiOperation(value = "商品列表(已完成)")
    @PostMapping("/shopList")
    @DynamicParameters(name = "shopList",properties = {
		  @DynamicParameter(name = "pageSize",value = "页数据量",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "pageNum",value = "页码",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils shopList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  ShopManage shopManage = JSON.toJavaObject(jsonObject, ShopManage.class);
	  return shopManageService.shopList(shopManage);
    }

    @ApiOperation(value = "商品详情(已完成)")
    @GetMapping("/shopDetail")
    public ResultUtils shopDetail(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
					    @ApiParam(name = "id",value = "id",required = true,type = "Integer") Integer id,
					    @ApiParam(name = "userId",value = "用户id",required = true,type = "Integer") Integer userId ){
	  return shopManageService.shopDetail(id, userId);
    }

    @ApiOperation(value = "字段说明(已完成)")
    @PostMapping("/list")
    public void list(ShopManage shopManage){

    }
}