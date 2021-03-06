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

package com.qiqi.jiaoyou_app.server.service.impl;

import com.qiqi.jiaoyou_app.server.dao.IRestDao;
import com.qiqi.jiaoyou_app.server.request.user.UserCreateRequest;
import com.qiqi.jiaoyou_app.server.request.user.UserUpdateRequest;
import com.qiqi.jiaoyou_app.server.response.user.UserCreateResponse;
import com.qiqi.jiaoyou_app.server.service.INimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Function: 用户服务实现类. <br>
 * Reason: TODO ADD REASON. <br>
 * Date: 2019年1月22日 下午2:53:14
 * @author zhuojianhui@gmail.com
 * @since 1.0
 * @description
 */
@Service
public class NimUserService implements INimUserService {

	@Autowired
	IRestDao restDao;

	@Override
	public UserCreateResponse create(UserCreateRequest request) {
		return restDao.execute(request, UserCreateResponse.class);
	}

	@Override
	public void update(UserUpdateRequest request) {

		// TODO Auto-generated method stub

	}

}
