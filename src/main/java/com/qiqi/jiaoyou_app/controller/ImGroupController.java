// package com.qiqi.jiaoyou_app.controller;
//
// import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
// import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
// import com.qiqi.jiaoyou_app.Instant.GroupJson;
// import com.qiqi.jiaoyou_app.Instant.MemberJson;
// import com.qiqi.jiaoyou_app.service.IMemberService;
// import com.qiqi.jiaoyou_app.util.Convert;
// import com.qiqi.jiaoyou_app.util.ResultUtils;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.util.ArrayList;
// import java.util.List;
//
// @RestController
// @RequestMapping("/jiaoyou_app/clubGroup")
// @Api(tags = "俱乐部群管理")
// public class ImGroupController
// {
//
//
//     @Autowired
//     private IMemberService iMemberService;
//
//     @ApiOperation(value = "创建群(已完成)")
//     @PostMapping(value = "/addGroup")
//     @DynamicParameters(name = "addGroup", properties = {
// 		  @DynamicParameter(name = "Owner_Account", value = "群主的 UserId", required = true, dataTypeClass = String.class),
// 		  @DynamicParameter(name = "GroupId", value = "俱乐部id", required = true, dataTypeClass = String.class),
// 		  @DynamicParameter(name = "Name", value = "群名称", required = true, dataTypeClass = String.class),
//     })
//     public ResultUtils addGroup(GroupJson groupJson)
//     {
// 	  groupJson.setType("Public");
// 	  return iMemberService.create_group(groupJson);
//     }
//
//     @ApiOperation(value = "加入群聊/添加群成员(已完成)")
//     @PostMapping(value = "/addGroupMember")
//     @DynamicParameters(name = "addGroup", properties = {
// 		  @DynamicParameter(name = "memberIds", value = "会员id字符串", required = true, dataTypeClass = String.class),
// 		  @DynamicParameter(name = "groupId", value = "俱乐部id", required = true, dataTypeClass = String.class),
//     })
//     public ResultUtils addGroupMember(String groupId, String memberIds)
//     {
// 	  List<Integer> integers = Convert.toIntList(memberIds);
//
// 	  for (Integer integer : integers)
// 	  {
// 		System.out.println(integer);
// 	  }
// 	  GroupJson groupJson = new GroupJson();
// 	  List<MemberJson> memberList = new ArrayList<>();
// 	  for (int i = 0; i < integers.size(); i++)
// 	  {
// 		MemberJson memberJson = new MemberJson();
// 		memberJson.setMember_Account(integers.get(i).toString());
// 		memberList.add(memberJson);
// 	  }
// 	  groupJson.setMemberList(memberList);
// 	  groupJson.setGroupId(groupId);
// 	  return iMemberService.addGroupMember(groupJson);
//     }
//
//
// }
