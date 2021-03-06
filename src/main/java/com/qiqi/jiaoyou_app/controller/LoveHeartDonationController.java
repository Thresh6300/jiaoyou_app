package com.qiqi.jiaoyou_app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.LoveHeartDonation;
import com.qiqi.jiaoyou_app.service.LoveHeartDonationService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 爱心捐赠(LoveHeartDonation)表控制层
 *
 * @author cfx
 * @since 2020-12-03 14:10:27
 */
@RestController
@RequestMapping("/jiaoyou_app/loveHeartDonation")
@Api(tags = " 爱心捐赠管理")
public class LoveHeartDonationController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-12-03 14:10:27
     */
    @Autowired
    private LoveHeartDonationService loveHeartDonationService;


    @ApiOperation(value = "爱心捐赠列表(已完成)")
    @PostMapping("/loveList")
    @DynamicParameters(name = "loveList",properties = {
		  @DynamicParameter(name = "pageSize",value = "页数据量",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "pageNum",value = "页码",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils loveList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  LoveHeartDonation loveHeartDonation = JSON.toJavaObject(jsonObject, LoveHeartDonation.class);
	  return loveHeartDonationService.loveList(loveHeartDonation);
    }

    @ApiOperation(value = "爱心捐赠详情(已完成)")
    @PostMapping("/loveDetail")
    @DynamicParameters(name = "loveDetail",properties = {
		  @DynamicParameter(name = "id",value = "id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils loveDetail(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  LoveHeartDonation loveHeartDonation = JSON.toJavaObject(jsonObject, LoveHeartDonation.class);
	  return loveHeartDonationService.getOne(loveHeartDonation);
    }


}