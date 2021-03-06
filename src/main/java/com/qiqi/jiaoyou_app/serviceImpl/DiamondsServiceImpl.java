package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.DiamondsMapper;
import com.qiqi.jiaoyou_app.pojo.Diamonds;
import com.qiqi.jiaoyou_app.service.IDiamondsService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GR123
 * @since 2020-07-15
 */
@Service
public class DiamondsServiceImpl extends ServiceImpl<DiamondsMapper, Diamonds> implements IDiamondsService {

    @Autowired
    private DiamondsMapper diamondsMapper;


    @Override
    public ResultUtils bindingVehicle(Integer type, Integer terminal) {
        ResultUtils resultUtils = new ResultUtils();
        List<Diamonds> diamonds = diamondsMapper.selectList(new EntityWrapper<Diamonds>().eq("diamonds_type", type).eq("diamonds_terminal", terminal));
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setData(diamonds);
        return resultUtils;
    }
}
