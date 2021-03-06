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

package com.qiqi.jiaoyou_app.server.response.msg;

import com.qiqi.jiaoyou_app.server.response.HttpResponse;

import java.util.List;

/**
 * Function: 批量发送点对点普通消息响应对象. <br>
 * Reason: TODO ADD REASON. <br>
 * Date: 2019年1月22日 上午8:33:31
 * @author zhuojianhui@gmail.com
 * @since 1.0
 * @description 批量发送点对点普通消息{@link MsgSendBatchRequest}
 */
public class MsgSendBatchResponse extends HttpResponse {

	/** unregister:未注册的帐号. */
	private List<String> unregister;

	public List<String> getUnregister() {
		return unregister;
	}

	public void setUnregister(List<String> unregister) {
		this.unregister = unregister;
	}
}
