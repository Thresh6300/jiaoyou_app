package com.qiqi.jiaoyou_app.Instant;


import com.alibaba.fastjson.JSON;
import com.qiqi.jiaoyou_app.controller.TLSSigAPIv2;
import com.qiqi.jiaoyou_app.util.JsonUtils;
import com.qiqi.jiaoyou_app.util.UserDataItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/3/12.
 */
public class NewFriendList
{


    /**
     * @Description: friend_get 获取所有好友
     * @param: groupJson
     * @return: List<UserDataItem>
     * @Author: cfx
     * @Date: 2020-12-10 11:44
     * https://console.tim.qq.com/v4/sns/friend_get?usersig=
     */
    public static List<UserDataItem> friend_get(String memberId)
    {
	  String payload = "{\"From_Account\":\"" + memberId + "\",\"StartIndex\":0}";
	  String urls = "https://console.tim.qq.com/v4/sns/friend_get?usersig=";
	  StringBuffer jsonString;
	  try
	  {

		HttpURLConnection connection = getHttpURLConnection(getUrl(urls));

		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		writer.write(payload);
		writer.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		jsonString = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null)
		{
		    jsonString.append(line);
		}
		br.close();
		connection.disconnect();
	  }
	  catch (Exception e)
	  {
		throw new RuntimeException(e.getMessage());
	  }
	  String s = jsonString.toString();
	  List<UserDataItem> userApplyDetailInfos = JSON.parseArray(JSON.parseObject(s).getString("UserDataItem"), UserDataItem.class);
	  return userApplyDetailInfos;
    }

    public static void main(String[] args)
    {
	  List<UserDataItem> userDataItems = friend_get("1205");
	  // userDataItems.
	  List<String> collect = userDataItems.stream().map(UserDataItem::getTo_Account).collect(Collectors.toList());
	  collect.add("1205");
	  System.out.println(collect.get(collect.size() - 1));
    }



    /**
     * @Description: create_group 创建群
     * @param: groupJson
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-09 13:19
     * https://console.tim.qq.com/v4/group_open_http_svc/create_group
     */
    public static String create_group(GroupJson groupJson)
    {

         /*{
	         "Owner_Account": "leckie", // 群主的 UserId（选填）
		  "Type": "Public", // 群组类型：Private/Public/ChatRoom/AVChatRoom（必填）
		  "GroupId": "MyFirstGroup", // 用户自定义群组 ID（选填）
		  "Name": "TestGroup"   // 群名称（必填）
           }*/
	  String urls = "https://console.tim.qq.com/v4/group_open_http_svc/create_group?usersig=";
	  return sendPost(getUrl(urls), groupJson);

    }

    /**
     * @Description: add_group_member 增加群成员
     * @param: clubId
     * @param: memberId
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-09 13:20
     * https://console.tim.qq.com/v4/group_open_http_svc/add_group_member?usersig=
     */
    public static String add_group_member(GroupJson groupJson)
    {
	  String urls = "https://console.tim.qq.com/v4/group_open_http_svc/add_group_member?usersig=";
	  return sendPost(getUrl(urls), groupJson);
         /*{
		    "GroupId": "@TGS#2NA7BGSGI", // 要操作的群组（必填）
		    "MemberList": [ // 一次最多添加300个成员
		    {
			  "Member_Account": "tommy" // 要添加的群成员ID（必填）
		    },
		    {
			  "Member_Account": "jared"
		    }]
           }*/

    }

    /**
     * @Description: delete_group_member 删除群成员
     * @param: clubId
     * @param: memberId
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-09 13:20
     * https://console.tim.qq.com/v4/group_open_http_svc/add_group_member?usersig=
     */
    public static String delete_group_member(GroupJson groupJson)
    {
	  String urls = "https://console.tim.qq.com/v4/group_open_http_svc/delete_group_member?usersig=";
	  return sendPost(getUrl(urls), groupJson);
         /*{
		"GroupId": "@TGS#2NA7BGSGI", // 要操作的群组（必填）
		"Silence": 1, // 是否静默删除（选填）
		"MemberToDel_Account": [ // 要删除的群成员列表，最多500个
						  "tommy",
						  "jared"
						]
       }*/

    }

    /**
     * @Description: destroy_group 解散群组
     * @param: clubId
     * @param: memberId
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-09 13:20
     * https://console.tim.qq.com/v4/group_open_http_svc/add_group_member?usersig=
     */
    public static String destroy_group(GroupJson groupJson)
    {
	  String urls = "https://console.tim.qq.com/v4/group_open_http_svc/destroy_group?usersig=";
	  return sendPost(getUrl(urls), groupJson);
         /*{
             "GroupId": "@TGS#2J4SZEAEL"
          }*/

    }


    public static String send_group_system_notification(GroupJson groupJson)
    {
	  String payload = "{\n" + "  \"GroupId\": \"@TGS#2NA7BGSGI\",\n" + "  \"Content\": \"Hello World\"\n" + "}";

	  String urls = "https://console.tim.qq.com/v4/group_open_http_svc/send_group_system_notification?usersig=";
	  return sendPost(getUrl(urls), groupJson);

    }


    public static String send_group_msg(GroupJson groupJson)
    {
	  String payload = "{\n" + "  \"GroupId\": \"@TGS#2NA7BGSGI\",\n" + "  \"Random\": 8912345,\n" + "  \"MsgBody\": [\n" + "      {\n" + "          \"MsgType\": \"TIMTextElem\",\n" + "          \"MsgContent\": {\n" + "              \"Text\": \"red packet\"\n" + "          }\n" + "      },\n" + "      {\n" + "          \"MsgType\": \"TIMFaceElem\",\n" + "          \"MsgContent\": {\n" + "              \"Index\": 6,\n" + "              \"Data\": \"abc\\u0000\\u0001\"\n" + "          }\n" + "      }\n" + "  ]\n" + "}";

	  String urls = "https://console.tim.qq.com/v4/group_open_http_svc/send_group_msg?usersig=";
	  return sendPost(getUrl(urls), groupJson);

    }


    /**
     * @Description: modify_group_base_info 扩充群组容量
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-09 13:29
     */
    public static String modify_group_base_info(GroupJson groupJson)
    {
	  String payload = "{\n" + "  \"GroupId\": \"@TGS#2NA7BGSGI\",\n" + "  \"MaxMemberNum\": 10000\n" + "}";
	  String urls = "https://console.tim.qq.com/v4/group_open_http_svc/modify_group_base_info?usersig=";
	  return sendPost(getUrl(urls), groupJson);
    }


    /**
     * @Description: accountDelete 删除腾讯注册用户
     * @param: id
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-09 13:29
     */
    public static String account_delete(GroupJson groupJson)
    {

	  String payload = "{\n" + "  \"DeleteItem\":\n" + "  [\n" + "      {\n" + "          \"UserID\":\"UserID_1\"\n" + "      }\n" + "  ]\n" + "}";
	  String urls = "https://console.tim.qq.com/v4/im_open_login_svc/account_delete?usersig=";
	  return sendPost(getUrl(urls), groupJson);
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 json 的形式。实体类转json
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(URL url, Object param)
    {

	  String json = "{\n" + "  \"GroupId\": \"@TGS#2NA7BGSGI\",\n" + "  \"Random\": 8912345,\n" + "  \"MsgBody\": [\n" + "      {\n" + "          \"MsgType\": \"TIMTextElem\",\n" + "          \"MsgContent\": {\n" + "              \"Text\": \"red packet\"\n" + "          }\n" + "      },\n" + "      {\n" + "          \"MsgType\": \"TIMFaceElem\",\n" + "          \"MsgContent\": {\n" + "              \"Index\": 6,\n" + "              \"Data\": \"abc\\u0000\\u0001\"\n" + "          }\n" + "      }\n" + "  ]\n" + "}";
	  json = JsonUtils.objectToJson(param);
	  StringBuffer jsonString;
	  try
	  {

		HttpURLConnection connection = getHttpURLConnection(url);

		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		writer.write(json);
		writer.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		jsonString = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null)
		{
		    jsonString.append(line);
		}
		br.close();
		connection.disconnect();
	  }
	  catch (Exception e)
	  {
		throw new RuntimeException(e.getMessage());
	  }
	  String s = jsonString.toString();
	  return s;
    }

    public static HttpURLConnection getHttpURLConnection(URL url) throws IOException
    {
	  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	  connection.setDoInput(true);
	  connection.setDoOutput(true);
	  connection.setRequestMethod("POST");
	  connection.setRequestProperty("Accept", "application/json");
	  connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	  return connection;
    }

    public static URL getUrl(String s2)
    {
	  try
	  {
		return new URL(s2 + TLSSigAPIv2.genSig(TLSSigAPIv2.adminName, 604800L, null) + "&identifier=" + TLSSigAPIv2.adminName + "&sdkappid=" + TLSSigAPIv2.sdkappidStr + "&contenttype=json");
	  }
	  catch (MalformedURLException e)
	  {
		e.printStackTrace();
	  }
	  return null;
    }

}