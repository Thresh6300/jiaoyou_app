package com.qiqi.jiaoyou_app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.ServerInfoManager;
import com.qiqi.jiaoyou_app.service.ServerInfoManagerService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 服务管理信息表(ServerInfoManager)表控制层
 *
 * @author cfx
 * @since 2020-12-03 15:45:55
 */
@RestController
@RequestMapping("/jiaoyou_app/serverInfoManager")
@Api(tags = " 服务管理信息表管理")
public class ServerInfoManagerController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-12-03 15:45:55
     */
    @Autowired
    private ServerInfoManagerService serverInfoManagerService;

    @ApiOperation(value = "字段说明(已完成)")
    @PostMapping("/list")
    public ResultUtils list(ServerInfoManager shopManage){
	  return null;
    }

    @ApiOperation(value = "服务类型(已完成)")
    @PostMapping("/geServerType")
    public ResultUtils geServerType(){
	  return serverInfoManagerService.geServerType();
    }

    @ApiOperation(value = "服务列表(已完成)")
    @PostMapping("/serverList")
    @DynamicParameters(name = "serverList",properties = {
		  @DynamicParameter(name = "pageSize",value = "页数据量",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "pageNum",value = "页码",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "serverTypeId",value = "服务类型id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils shopList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  ServerInfoManager serverInfoManager = JSON.toJavaObject(jsonObject, ServerInfoManager.class);
	  return serverInfoManagerService.serverList(serverInfoManager);
    }


    @ApiOperation(value = "服务详情(已完成)")
    @GetMapping("/serverDetail")
    @DynamicParameters(name = "serverDetail",properties = {
		  @DynamicParameter(name = "id",value = "id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "userId",value = "用户id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils shopDetail(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,Integer id,Integer userId ){
	  return serverInfoManagerService.serverDetail(id,userId);
    }
}