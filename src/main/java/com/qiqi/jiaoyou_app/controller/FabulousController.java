package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Fabulous;
import com.qiqi.jiaoyou_app.service.IFabulousService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-05-08
 */
@Api(tags = {"动态点赞"})
@RestController
@RequestMapping("/jiaoyou_app/fabulous")
public class FabulousController {

    @Autowired
    private IFabulousService fabulousService;

    @ApiOperation(value = "点赞动态(已完成)")
    @PostMapping("/dynamicThumbsUp")
    @DynamicParameters(name = "dynamicThumbsUp",properties = {
            @DynamicParameter(name = "dynamicId",value = " 动态id",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberId",value = " 会员id",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "type",value = " 动态类型 1：朋友圈动态 2：车友圈动态 3俱乐部动态",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils aopdynamicThumbsUp(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        Fabulous fabulous = JSON.toJavaObject(jsonObject,Fabulous.class);
        return fabulousService.dynamicThumbsUp(fabulous);
    }

}

