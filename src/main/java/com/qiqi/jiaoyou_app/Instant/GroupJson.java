package com.qiqi.jiaoyou_app.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GroupJson
{
    /*{
	  "Owner_Account": "leckie", // 群主的 UserId（选填）
	  "Type": "Public", // 群组类型：Private/Public/ChatRoom/AVChatRoom（必填）
	  "GroupId": "MyFirstGroup", // 用户自定义群组 ID（选填）
	  "Name": "TestGroup"   // 群名称（必填）
    }*/
    private String Owner_Account;

    private String Type;

    private String GroupId;

    private String Name;

    private List<MemberJson> MemberList;

    private String From_Account;

    private Integer StartIndex;

    /* 删除*/
    private List<String> MemberToDel_Account;



    @JsonProperty("StartIndex")
    public void setStartIndex(Integer startIndex)
    {
	  StartIndex = startIndex;
    }

    @JsonProperty("MemberToDel_Account")
    public void setMemberToDel_Account(List<String> memberToDel_Account)
    {
	  MemberToDel_Account = memberToDel_Account;
    }


    @JsonProperty("From_Account")
    public void setFrom_Account(String from_Account)
    {
	  From_Account = from_Account;
    }


    @JsonProperty("Owner_Account")
    public void setOwner_Account(String owner_Account)
    {
	  Owner_Account = owner_Account;
    }


    @JsonProperty("Type")
    public void setType(String type)
    {
	  Type = type;
    }


    @JsonProperty("GroupId")
    public void setGroupId(String groupId)
    {
	  GroupId = groupId;
    }


    @JsonProperty("Name")
    public void setName(String name)
    {
	  Name = name;
    }


    @JsonProperty("MemberList")
    public void setMemberList(List<MemberJson> memberList)
    {
	  MemberList = memberList;
    }


}
