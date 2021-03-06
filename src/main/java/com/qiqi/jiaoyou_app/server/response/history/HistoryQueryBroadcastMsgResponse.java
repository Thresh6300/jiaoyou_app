/**
* Copyright (C) 2019, the original author or authors. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
*/

package com.qiqi.jiaoyou_app.server.response.history;

import com.qiqi.jiaoyou_app.server.response.HttpResponse;

import java.util.List;

/**
 * Function: 批量查询广播消息响应对象. <br>
 * Reason: TODO ADD REASON. <br>
 * Date: 2019年1月22日 上午10:08:30
 * @author zhuojianhui@gmail.com
 * @since 1.0
 * @description 批量查询广播消息。<br>
 */
public class HistoryQueryBroadcastMsgResponse extends HttpResponse {

	/** 批量查询广播消息列表 */
	private List<BroadcastMsgEntity> msgs;

	public List<BroadcastMsgEntity> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<BroadcastMsgEntity> msgs) {
		this.msgs = msgs;
	}
}
