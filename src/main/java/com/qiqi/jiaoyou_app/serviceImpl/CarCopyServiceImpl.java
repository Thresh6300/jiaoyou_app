package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.CarCopyMapper;
import com.qiqi.jiaoyou_app.pojo.CarCopy;
import com.qiqi.jiaoyou_app.service.CarCopyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 车辆表(CarCopy)表服务实现类
 * carCopy
 *
 * @author cfx
 * @since 2020-12-23 16:09:00
 */
@Slf4j
@Service("carCopyService")
public class CarCopyServiceImpl extends ServiceImpl<CarCopyMapper, CarCopy> implements CarCopyService
{


}