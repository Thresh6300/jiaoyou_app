package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.CarMapper;
import com.qiqi.jiaoyou_app.pojo.Car;
import com.qiqi.jiaoyou_app.pojo.CarCopy;
import com.qiqi.jiaoyou_app.service.CarCopyService;
import com.qiqi.jiaoyou_app.service.ICarService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 车辆表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService
{

    @Autowired(required = false)
    private CarMapper carMapper;
    @Autowired
    private CarCopyService carCopyService;

    @Override
    public ResultUtils bindingVehicle(Car car)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.VEHICLE_BOUND_SUCCESSFULLY);
	  List<Car> memberId = carMapper.selectList(new EntityWrapper<Car>().eq("memberId", car.getMemberId()));
	  if (memberId.size() <= 0)
	  {
		car.setAuditState(1);//审核中
		car.setAddTime(new Timestamp(System.currentTimeMillis()));
		car.setDeledeState(2);
		Integer insert = carMapper.insert(car);
		if (insert <= 0)
		{
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    resultUtils.setMessage(Constant.FAILED_TO_BIND_VEHICLE);
		    return resultUtils;
		}
		return resultUtils;
	  }
	  else
	  {
		Car car1 = memberId.get(0);
		car.setAuditState(1);//审核中
		car.setAddTime(new Timestamp(System.currentTimeMillis()));
		car.setDeledeState(2);
		car.setId(car1.getId());
		// car1.setDeledeState(1);
		Integer insert1 = baseMapper.updateById(car);

		// car.setAuditState(1);//审核中
		// car.setAddTime(new Timestamp(System.currentTimeMillis()));
		// car.setDeledeState(2);
		// car.setId(car1.getId());
		// CarCopy carCopy = new CarCopy();
		// BeanUtils.copyProperties(car, carCopy);
		// carCopyService.deleteById(carCopy);
		// boolean insert = carCopyService.insert(carCopy);
		// if (!insert)
		if(insert1 <= 0)
		{
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    resultUtils.setMessage(Constant.FAILED_TO_BIND_VEHICLE);
		    return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.VEHICLE_BOUND_SUCCESSFULLY);
		return resultUtils;
	  }
    }
}
