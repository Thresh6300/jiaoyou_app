package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.AddressMapper;
import com.qiqi.jiaoyou_app.pojo.Address;
import com.qiqi.jiaoyou_app.service.AddressService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (Address)表服务实现类
 * address
 *
 * @author cfx
 * @since 2020-12-03 16:17:54
 */
@Slf4j
@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService
{
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public ResultUtils getMyAddress(Integer addressUserId)
    {

	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  List<Address> addresses = selectList(new EntityWrapper<Address>().orderBy("status").eq("address_user_id", addressUserId));
	  if (addresses == null)
		return resultUtils;
	  resultUtils.setData(addresses);
	  return resultUtils;
    }

    @Override
    public ResultUtils add(Address address)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  if (insert(address))
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils up(Address address)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  if (updateById(address))
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils updateStatus(Address address)
    {

	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  EntityWrapper<Address> wrapper = new EntityWrapper<>();
	  wrapper.eq("address_user_id", address.getAddressUserId()).ne("address_id", address.getAddressId());

	  List<Address> list = selectList(wrapper);
	  for (Address address1 : list)
	  {
		address1.setStatus("1");
	  }
	  address.setStatus("0");
	  if (updateById(address))
	  {
		if (list != null && list.size() > 0)
		{
		    if (updateBatchById(list))
			  return resultUtils;
		}
		return resultUtils;
	  }


	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }


}