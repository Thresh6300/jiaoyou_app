package com.qiqi.jiaoyou_app.controller;


import com.qiqi.jiaoyou_app.service.ISystemMessageService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.*;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 系统消息表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"系统消息"})
@Controller
@RequestMapping("/jiaoyou_app/systemMessage")
public class SystemMessageController
{

    @Autowired
    private ISystemMessageService systemMessageService;

    @ApiOperation(value = "是否未读(已完成)")
    @GetMapping("/notRead")
    @ResponseBody
    public ResultUtils aopnotRead(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer memberId)
    {
	  return systemMessageService.notRead(memberId);
    }




    @ApiOperation(value = "系统消息列表(已完成)")
    @GetMapping("/messageList")
    @ResponseBody
    public ResultUtils aopmessageList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "memberId", value = "会员ID", required = true, type = "Integer") Integer memberId, @ApiParam(name = "pageSize", value = "页数据量", required = true, type = "Integer") Integer pageSize, @ApiParam(name = "pageNum", value = "页码", required = true, type = "Integer") Integer pageNum, @ApiParam(name = "keyWord", value = "关键字", required = false, type = "String") String keyWord)
    {
	  return systemMessageService.messageList(memberId, pageSize, pageNum, keyWord);
    }

    @ApiOperation(value = "系统消息详情(已完成)")
    @GetMapping("/messageDetail")
    @ResponseBody
    public ResultUtils aopmessageDetail(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
						    @ApiParam(name = "id", value = "消息ID", required = true, type = "Integer") Integer id,
						    @ApiParam(name = "memberId", value = "会员ID", required = true, type = "Integer") Integer memberId,
						    @ApiParam(name = "type", value = "0系统消息;1普通公告;2聚会;3会员专享", required = true, type = "Integer") String type
    )
    {
	  return systemMessageService.messageDetail(memberId, id,type);
    }

    @ApiOperation(value = "系统消息单个列表")
    @GetMapping("/messageOnes")
    @ResponseBody
    @ApiResponse(code = 200, message = "返回信息:data系统消息;data1普通公告;data2聚会;data3会员专享")
    public ResultUtils messageOnes(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "memberId", value = "会员ID", required = true, type = "Integer") Integer memberId, @ApiParam(name = "keyWord", value = "关键字", required = false, type = "String") String keyWord)
    {
	  return systemMessageService.messageOnes(memberId, keyWord);
    }

    @ApiOperation(value = "系统消息列表")
    @GetMapping("/messageLists")
    @ResponseBody
    public ResultUtils messageLists(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "memberId", value = "会员ID", required = true, type = "Integer") Integer memberId, @ApiParam(name = "keyWord", value = "关键字", required = false, type = "String") String keyWord, @ApiParam(name = "pageSize", value = "页数据量", required = true, type = "Integer") Integer pageSize, @ApiParam(name = "pageNum", value = "页码", required = true, type = "Integer") Integer pageNum, @ApiParam(name = "type", value = "0系统消息;1普通公告;2聚会;3会员专享", required = true, type = "Integer") String type)
    {
	  return systemMessageService.messageLists(memberId, pageNum, pageSize, keyWord, type);
    }

    @ApiOperation(value = "未读得数量")
    @GetMapping("/isRead")
    @ResponseBody
    @ApiImplicitParam(name = "memberId", value = "会员ID", required = true, type = "Integer")
    public ResultUtils isRead(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer memberId)
    {
	  return systemMessageService.isRead(memberId);
    }
}

