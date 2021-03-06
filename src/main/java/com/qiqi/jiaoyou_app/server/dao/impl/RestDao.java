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

package com.qiqi.jiaoyou_app.server.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiqi.jiaoyou_app.server.dao.IRestDao;
import com.qiqi.jiaoyou_app.server.request.BaseHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * Function: Rest数据访问实现类. <br>
 * Reason: TODO ADD REASON. <br>
 * Date: 2019年1月22日 下午3:24:18
 * @author zhuojianhui@gmail.com
 * @since 1.0
 * @description
 */
@Service
public class RestDao implements IRestDao {

	private ObjectMapper objectMapper = new ObjectMapper();
	private ClientHttpRequestFactory factory;
    private RestTemplate restTemplate;
	/*@Autowired
	public void setRestTemplate() {
		this.restTemplate = new RestTemplate(this.factory);
	}

	@Autowired
	public void setFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(5000);//ms
		factory.setConnectTimeout(15000);//ms
		this.factory = factory;
	}*/

	@Override
	public <T> T execute(BaseHttpRequest request, Class<T> responseType) {
		// TODO 此处只适用于网易云的请求格式，表单提交使用键值对
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		// 得到类对象
		Class<?> clazz = (Class<?>) request.getClass();
		/* 得到类中的所有属性集合 */
		Field[] fields = clazz.getDeclaredFields();
		try {
			Object value = null;
			for (Field field : fields) {
				field.setAccessible(true);
				if (!Modifier.isStatic(field.getModifiers())) {
					value = field.get(request);
					if (Objects.isNull(value)) {
						// TODO 非常基础类型的字段需要转成Json字符串
						bodyMap.add(field.getName(), value);
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(bodyMap, request.getHeaders());
		ResponseEntity<T> responseEntity = restTemplate.exchange(request.getURI(), request.getMethod(), httpEntity,
				responseType);
		return responseEntity.getBody();
	}

}
