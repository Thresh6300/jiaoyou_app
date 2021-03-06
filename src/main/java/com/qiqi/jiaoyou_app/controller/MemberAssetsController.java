package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.MemberAssets;
import com.qiqi.jiaoyou_app.serviceImpl.MemberAssetsServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员资产表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@RestController
@RequestMapping("/jiaoyou_app/memberAssets")
@Api(tags = "会员资产表")
public class MemberAssetsController {

    @Autowired
    private MemberAssetsServiceImpl memberAssetsService;

    @ApiOperation(value = "我的钻石数量以及喇叭数量(已完成)")
    @GetMapping(value = "/myTrumpet")
    public ResultUtils aopmyTrumpet(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,Integer id){
        return memberAssetsService.myTrumpet(id);
    }


    @ApiOperation(value = "银钻兑换金钻(已完成)")
    @PostMapping(value = "/silverDiamondForGoldDiamond")
    @DynamicParameters(name = "silverDiamondForGoldDiamond",properties = {
            @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberDiamondsizeOfGold",value = "金钻数量",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberDiamondsizeOfSilver",value = "银钻数量",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils aopsilverDiamondForGoldDiamond(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        MemberAssets memberAssets = JSON.toJavaObject(jsonObject,MemberAssets.class);
        return memberAssetsService.silverDiamondForGoldDiamond(memberAssets);
    }

    @ApiOperation(value = "金钻兑换银钻(已完成)")
    @PostMapping(value = "/goldDiamondForSilverDiamond")
    @DynamicParameters(name = "goldDiamondForSilverDiamond",properties = {
            @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberDiamondsizeOfGold",value = "金钻数量",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberDiamondsizeOfSilver",value = "银钻数量",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils aopGoldDiamondForSilverDiamond(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        MemberAssets memberAssets = JSON.toJavaObject(jsonObject,MemberAssets.class);
        return memberAssetsService.GoldDiamondForSilverDiamond(memberAssets);
    }

    @ApiOperation(value = "银钻兑换黑钻(已完成)")
    @PostMapping(value = "/silverDiamondForBlackDiamond")
    @DynamicParameters(name = "silverDiamondForGoldDiamond",properties = {
            @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberDiamondsizeOfBlack",value = "黑钻数量",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberDiamondsizeOfSilver",value = "银钻数量",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils aopsilverDiamondForBlackDiamond(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        MemberAssets memberAssets = JSON.toJavaObject(jsonObject,MemberAssets.class);
        return memberAssetsService.silverDiamondForBlackDiamond(memberAssets);
    }
}

