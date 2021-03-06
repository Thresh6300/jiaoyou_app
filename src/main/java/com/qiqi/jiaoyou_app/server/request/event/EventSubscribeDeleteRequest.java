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

package com.qiqi.jiaoyou_app.server.request.event;

import com.qiqi.jiaoyou_app.server.request.BaseHttpRequest;

import java.util.List;

/**
 * Function: 取消在线状态事件订阅请求对象. <br>
 * Reason: TODO ADD REASON. <br>
 * Date: 2019年1月22日 上午11:05:23
 * @author zhuojianhui@gmail.com
 * @since 1.0
 * @description 取消在线状态事件订阅。订阅请求 {@link EventSubscribeAddRequest}<br>
 *              POST https://api.netease.im/nimserver/event/subscribe/delete.action HTTP/1.1 <br>
 *              Content-Type:application/x-www-form-urlencoded;charset=utf-8 <br>
 */
public class EventSubscribeDeleteRequest extends BaseHttpRequest {

	@Override
	public String getPath() {
		return "event/subscribe/delete.action";
	}

	/** accid:事件订阅人账号. */
	private String accid;

	/** eventType:事件类型，固定设置为1，即 eventType=1. */
	private int eventType;

	/** publisherAccids:被订阅人的账号列表，最多100个账号，JSONArray格式。示例：["pub_user1","pub_user2"]. */
	private List<String> publisherAccids;

	/**
	 * Creates a new instance of EventSubscribeAddRequest. <br>
	 * 参数为必填字段
	 * @param accid
	 * @param eventType
	 * @param publisherAccids
	 */
	public EventSubscribeDeleteRequest(String accid, int eventType, List<String> publisherAccids) {
		super();
		this.accid = accid;
		this.eventType = eventType;
		this.publisherAccids = publisherAccids;
	}

	public String getAccid() {
		return accid;
	}

	public void setAccid(String accid) {
		this.accid = accid;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public List<String> getPublisherAccids() {
		return publisherAccids;
	}

	public void setPublisherAccids(List<String> publisherAccids) {
		this.publisherAccids = publisherAccids;
	}

}
