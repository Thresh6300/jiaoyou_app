package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.Address;
import com.qiqi.jiaoyou_app.util.ResultUtils;


/**
 * (Address)表服务接口
 *
 * @author cfx
 * @since 2020-12-03 16:17:54
 */
public interface AddressService extends IService<Address>
{



    ResultUtils getMyAddress(Integer addressUserId);

    ResultUtils add(Address address);

    ResultUtils up(Address address);

    ResultUtils updateStatus(Address address);
}