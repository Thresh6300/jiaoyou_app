package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.ShopManage;
import com.qiqi.jiaoyou_app.util.ResultUtils;


/**
 * 商品管理(ShopManage)表服务接口
 *
 * @author cfx
 * @since 2020-12-03 13:37:19
 */
public interface ShopManageService extends IService<ShopManage>
{

    ResultUtils shopList(ShopManage shopManage);

    ResultUtils shopDetail(Integer id,Integer userId);
}