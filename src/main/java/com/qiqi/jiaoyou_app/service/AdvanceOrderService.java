package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.AdvanceOrder;
import com.qiqi.jiaoyou_app.util.ResultUtils;


/**
 * (AdvanceOrder)表服务接口
 *
 * @author cfx
 * @since 2020-12-09 14:53:36
 */
public interface AdvanceOrderService extends IService<AdvanceOrder>
{



    ResultUtils orderDown(AdvanceOrder advanceOrder);

    ResultUtils updateOrderStatus(AdvanceOrder orderManage);

    ResultUtils orderDetail(Integer id);

    ResultUtils orderMyList(AdvanceOrder advanceOrder);
}