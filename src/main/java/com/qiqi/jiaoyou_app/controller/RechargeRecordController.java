package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.RechargeRecord;
import com.qiqi.jiaoyou_app.serviceImpl.RechargeRecordServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 充值记录 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"记录表"})
@RestController
@RequestMapping("/jiaoyou_app/rechargeRecord")
public class RechargeRecordController {

    @Autowired
    private RechargeRecordServiceImpl rechargeRecordService;

    @ApiOperation(value = "钻石充值记录已完成(已完成)")
    @GetMapping(value = "/rechargeLog")
    public ResultUtils aopRechargeLog(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                      @ApiParam(name = "pageNum",value = "页数",required = true,type = "Integer") Integer pageNum,
                                      @ApiParam(name = "pageSize",value = "每页数据量",required = true,type = "Integer") Integer pageSize,
                                      @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id){
        return rechargeRecordService.aopRechargeLog(id,pageNum,pageSize);
    }

    @ApiOperation(value = "钻石消费记录 世界发言频道（已完成）")
    @PostMapping("/speakerConsumption")
    @DynamicParameters(name = "speakerConsumption",properties = {
		  @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils aopspeakerConsumption(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  RechargeRecord rechargeRecord = JSON.toJavaObject(jsonObject, RechargeRecord.class);
	  return rechargeRecordService.speakerConsumption(rechargeRecord);
    }

}

