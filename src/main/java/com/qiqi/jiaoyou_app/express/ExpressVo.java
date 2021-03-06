package com.qiqi.jiaoyou_app.express;

import lombok.Data;

@Data
public class ExpressVo
{
    /*{
	    "message":"ok",
	    "state":"0", //快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回，7转投 等8个状态
	    "status":"200",
	    "condition":"F00",
	    "ischeck":"0", //是否签收标记
	    "com":"yuantong", //快递公司编码,
	    "nu":"V030344422", //快递单号
	    "data":[  //数组，包含多个对象
			  {
			    "context":"上海分拨中心/装件入车扫描 ", //物流轨迹节点内容
			    "time":"2012-08-28 16:33:19", //时间，原始格式
			    "ftime":"2012-08-28 16:33:19", //格式化后时间
			  },
			  {
			    "context":"上海分拨中心/下车扫描 ",
			    "time":"2012-08-27 23:22:42",
			    "ftime":"2012-08-27 23:22:42",
			  }]
    }*/

    private String context;
    private String time;

}
