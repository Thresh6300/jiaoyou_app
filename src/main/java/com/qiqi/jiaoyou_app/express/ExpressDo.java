package com.qiqi.jiaoyou_app.express;

import lombok.Data;

@Data
public class ExpressDo
{
    // 参数名称	是否必填	示例值	            参数描述
    // com	      必须	      ems	            查询的快递公司的编码，一律用小写字母
    // num	      必须	      EM263999513JP	查询的快递单号， 单号的最大长度是32个字符
    // phone	可选	      13868688888	      收件人或寄件人的手机号或固话（顺丰单号必填，也可以填写后四位，如果是固话，请不要上传分机号）
    // from	      可选	      广东省深圳市南山区	出发地城市，省-市-区
    // to	      可选	      北京市朝阳区	      目的地城市，省-市-区
    // resultv2	可选	      1	            添加此字段表示开通行政区域解析功能。0：关闭（默认），1：开通行政区域解析功能，2：开通行政解析功能并且返回出发、目的及当前城市信息
    // show	      可选	      0	            返回数据格式。0：json（默认），1：xml，2：html，3：text
    // order	可选	      desc	            返回结果排序方式。desc：降序（默认），asc：升序
    private String com;
    private String num;
    private String from;
    private String phone;
    private String to;
    private String resultv2;
    // private String show;
    // private String order;
}
