package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Diamondconsumptionrecord;
import com.qiqi.jiaoyou_app.pojo.RedLog;
import com.qiqi.jiaoyou_app.pojo.RedReceiveLog;
import com.qiqi.jiaoyou_app.serviceImpl.RedLogServiceImpl;
import com.qiqi.jiaoyou_app.util.JsonUtils;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GR123
 * @since 2020-06-19
 */
@Api(tags = {"红包表"})
@RestController
@RequestMapping("/jiaoyou_app/redLog")
public class RedLogController {


    @Autowired
    private RedLogServiceImpl redLogService;

    @ApiOperation(value = "发送红包(一对一)/(一对多)(已完成)")
    @PostMapping("/sendARedEnvelopeOneToOne")
    @DynamicParameters(name = "sendARedEnvelopeOneToOne",properties = {
            @DynamicParameter(name = "redLogMemberId",value = "发送方ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "redLogRedSize",value = "红包个数",required = false,dataTypeClass = Integer.class),
            @DynamicParameter(name = "redLogGoldSize",value = "钻石数量",required = true,dataTypeClass = Long.class),
            @DynamicParameter(name = "redLogSex",value = "性别限制 1:只限男 2：只限女 2：不限",required = true,dataTypeClass = Long.class),
            @DynamicParameter(name = "redLogRemarks",value = "备注",required = true,dataTypeClass = Long.class),
            @DynamicParameter(name = "type",value = "1:一对一 2：一对多",required = true,dataTypeClass = Long.class),
    })
    public ResultUtils aopsendARedEnvelopeOneToOne(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        RedLog redLog = JSON.toJavaObject(jsonObject, RedLog.class);
        return redLogService.sendARedEnvelopeOneToOne(redLog);
    }

    @ApiOperation(value = "红包详情(已完成)")
    @PostMapping("/redPacketDetails")
    @DynamicParameters(name = "redPacketDetails",properties = {
            @DynamicParameter(name = "redLogId",value = "红包ID",required = true,dataTypeClass = Integer.class)
    })
    public ResultUtils aopredPacketDetails(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        RedLog redLog = JSON.toJavaObject(jsonObject, RedLog.class);
        return redLogService.redPacketDetails(redLog);
    }

    @ApiOperation(value = "抢红包(已完成)")
    @PostMapping("/grabARedEnvelope")
    @DynamicParameters(name = "grabARedEnvelope",properties = {
            @DynamicParameter(name = "redReceiveLogRedId",value = "红包ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "redReceiveLogMemberId",value = "抢红包人ID",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils aopgrabARedEnvelope(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        RedReceiveLog redReceiveLog = JSON.toJavaObject(jsonObject, RedReceiveLog.class);
        return redLogService.grabARedEnvelope(redReceiveLog);
    }


    @ApiOperation(value = "我送出的红包(已完成)")
    @GetMapping("/giftsRed")
    public ResultUtils aopgiftsRed(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                  @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                  @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                  @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id){

        return redLogService.giftsRed(pageSize,pageNum,id);
    }

}

