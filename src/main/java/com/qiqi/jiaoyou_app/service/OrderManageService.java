package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.OrderManage;
import com.qiqi.jiaoyou_app.util.ResultUtils;


/**
 * 订单信息表(OrderManage)表服务接口
 *
 * @author cfx
 * @since 2020-12-03 16:56:22
 */
public interface OrderManageService extends IService<OrderManage>
{

    ResultUtils orderMyList(OrderManage orderManage);

    ResultUtils orderDetail(Integer id);

    ResultUtils orderDown(OrderManage orderManage);

    OrderManage getOrderManage(String orderNumber);

    ResultUtils expressDetail(Integer id);

    ResultUtils updateOrderStatus(OrderManage orderManage);
}