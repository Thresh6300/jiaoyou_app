package com.qiqi.jiaoyou_app.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MemberJson
{

    /*{
	  "GroupId": "@TGS#2J4SZEAEL", // 要操作的群组（必填）
		  "MemberList": [ // 一次最多添加300个成员
	  {
		"Member_Account": "tommy" // 要添加的群成员ID（必填）
	  },
	  {
		"Member_Account": "jared"
	  }]
    }*/
    private String Member_Account;


    @JsonProperty("Member_Account")
    public void setMember_Account(String member_Account)
    {
	  Member_Account = member_Account;
    }

    public MemberJson()
    {
    }

    public MemberJson(String member_Account)
    {
	  Member_Account = member_Account;
    }
}
