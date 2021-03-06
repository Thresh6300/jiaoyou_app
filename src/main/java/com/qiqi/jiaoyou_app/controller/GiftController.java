package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Giftconsumption;
import com.qiqi.jiaoyou_app.pojo.MemberAssets;
import com.qiqi.jiaoyou_app.pojo.Speakerconsumptionrecord;
import com.qiqi.jiaoyou_app.service.IGiftService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 礼物表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"礼物表"})
@RestController
@RequestMapping("/jiaoyou_app/gift")
public class GiftController {
    @Autowired
    private IGiftService giftService;

    @ApiOperation(value = "礼物列表(已完成)")
    @GetMapping("/giftList")
    public ResultUtils aopgiftList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                @ApiParam(name = "keyWord",value = "关键字",required = false,type = "String") String keyWord){
        return giftService.giftList(pageSize,pageNum,keyWord);
    }

    @ApiOperation(value = "赠送礼物(已完成)")
    @PostMapping("/giveGift")
    @DynamicParameters(name = "yenValueRegister",properties = {
            @DynamicParameter(name = "memberId",value = "接收方ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "sendMemberId",value = "发送方ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "giftId",value = "礼物ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "giftSize",value = "礼物数量",required = true,dataTypeClass = Integer.class)
    })
    public ResultUtils aopgiveGift(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        Giftconsumption giftconsumption = JSON.toJavaObject(jsonObject,Giftconsumption.class);
        return giftService.giveGift(giftconsumption);
    }



    @ApiOperation(value = "接收礼物(已完成)")
    @PostMapping("/receiveGift")
    @DynamicParameters(name = "yenValueRegister",properties = {
            @DynamicParameter(name = "memberId",value = "接收方ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "revision",value = "赠送记录ID",required = true,dataTypeClass = Integer.class)
    })
    public ResultUtils aopReceiveGift(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        Giftconsumption giftconsumption = JSON.toJavaObject(jsonObject,Giftconsumption.class);
        return giftService.ReceiveGift(giftconsumption);
    }

    @ApiOperation(value = "小喇叭消耗记录（已完成）")
    @PostMapping("/speakerConsumption")
    @DynamicParameters(name = "recordStoreToGrabARedEnvelope",properties = {
            @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "surplusSize",value = "剩余数量",required = true,dataTypeClass = Long.class)
    })
    public ResultUtils aopspeakerConsumption(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        Speakerconsumptionrecord speakerconsumptionrecord = JSON.toJavaObject(jsonObject,Speakerconsumptionrecord.class);
        return giftService.speakerConsumption(speakerconsumptionrecord);
    }


    @ApiOperation(value = "购买小喇叭(已完成)")
    @PostMapping("/buyASmallHorn")
    @DynamicParameters(name = "buyASmallHorn",properties = {
            @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberhornSize",value = "购买数量",required = true,dataTypeClass = Long.class)
    })
    public ResultUtils aopbuyASmallHorn(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        MemberAssets memberAssets = JSON.toJavaObject(jsonObject, MemberAssets.class);
        return giftService.buyASmallHorn(memberAssets);
    }

    @ApiOperation(value = "我送出的礼物(已完成)")
    @GetMapping("/giftsGiven")
    public ResultUtils aopgiftsGiven(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                           @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                           @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                           @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id){

        return giftService.giftsGiven(pageSize,pageNum,id);
    }


    @ApiOperation(value = "我收到的礼物(已完成)")
    @GetMapping("/giftsReceived")
    public ResultUtils aopgiftsReceived(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                  @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                  @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                  @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id){

        return giftService.giftsReceived(pageSize,pageNum,id);
    }




}

